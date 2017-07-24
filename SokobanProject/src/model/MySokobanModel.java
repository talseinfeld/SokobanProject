package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;

import common.Direction;
import model.data.Level;
import model.db.MyDatabaseManager;
import model.entities.EntityDB;
import model.entities.ScoreboardDB;
import model.policy.Policy;

public class MySokobanModel extends Observable implements Model {

	private Level level;
	private Policy policy;
	private MyDatabaseManager dbm;
	private EntityDB entityDb;
	private int port;

	public MySokobanModel(Policy policy, int port) {
		this.policy = policy;
		this.port = port;
		dbm = MyDatabaseManager.getInstance();
	}

	@Override
	public Boolean isWon() {
		//isWin(Level level) is defined in Policy class - Policy dictates the game rules
		return this.policy.isWon(this.level);
	}

	@Override
	public void move(Direction direction) {
		/*
		 * If the level's reference exists,
		 * and the user didn't beat the level => try to move.
		 */
		if ((this.level!=null) && (this.level.getWonFlag() == false)) {
			List<String> params = new LinkedList<>();
			this.policy.move(this.level, direction);
			this.setChanged();
			//Checking if user won after each movement
			if (this.isWon() == true) {
				params.add("win");
				this.notifyObservers(params);
			}
			else {
				params.add("display");
				this.notifyObservers(params);
			}
		}

	}
	@Override
	public void setLevel(Level level) {
		this.level = level;
		//Checking if user loaded a level that is already won
		if (this.isWon()) {
			List<String> params = new LinkedList<>();
			params.add("win");
			this.notifyObservers(params);
		}
		List<String> arg = new LinkedList<>();
		arg.add("display");
		this.setChanged();
		this.notifyObservers(arg);		
	}

	@Override
	public void saveToDb(String levelName, String username) {
		entityDb = new ScoreboardDB(levelName, username, level.getStepsCounter(),level.getTimeCounter());
		dbm.save(entityDb);
	}

	@Override
	public void solveLevel() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				solve();
			}
		}).start();

	}

	private void solve() {
		Client client = new Client();
		client.start("localhost", port, level);
		char[] solArr = client.getSolution().toCharArray();
		String[] solution = parseSolution(solArr);
		for (String sol : solution) {
			try {
				if(!sol.equals("")) {
					move(Direction.valueOf(sol));			
					Thread.sleep(300);
				}
			} catch (InterruptedException e) {}
		}
	}
	private String[] parseSolution(char[] solArr) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0;i<solArr.length;i++) {
			if (solArr[i] == 'u') { 
				sb.append("UP,");
			} else if(solArr[i] == 'd') {
				sb.append("DOWN,");
			} else if(solArr[i] == 'l') {
				sb.append("LEFT,");
			} else if(solArr[i] == 'r') {
				sb.append("RIGHT,");
			}
		}
		String[] parsedSolution = sb.toString().split(",");
		return parsedSolution;
	}
	
	@Override
	public void hint() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				getHalfSolution();
			}
		}).start();
	}
	private void getHalfSolution() {
		Client client = new Client();
		client.start("localhost", port, level);
		char[] solArr = client.getSolution().toCharArray();
		String[] solution = parseSolution(solArr);
		
		if (solution == null) // to avoid division by zero
			return;
		for (int i = 0;i<solution.length/2;i++) {
			try {
				if(!solution[i].equals("")) {
					move(Direction.valueOf(solution[i]));			
					Thread.sleep(300);
				}
			} catch (InterruptedException e) {}
		}
	}
	//========Setters&Getters========//
	@Override
	public Level getCurrentLevel() {
		return level;
	}


}
