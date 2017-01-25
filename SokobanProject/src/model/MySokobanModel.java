package model;

import java.util.LinkedList;
import java.util.Observable;

import common.Direction;
import model.data.Level;
import model.policy.Policy;

public class MySokobanModel extends Observable implements Model {

	private Level level;
	private Policy policy;
		
	public MySokobanModel(Policy policy) {
		
		this.policy = policy;
	}

	@Override
	public Boolean isWon() {
		//isWin(Level level) is defined in Policy class - Policy dictates the game rules
		return this.policy.isWon(this.level);
	}
	
	@Override
	public void move(Direction direction) {
		
		LinkedList<String> params = new LinkedList<String>();
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
	@Override
	public void setLevel(Level level) {
		this.level = level;
		//Checking if user loaded a level that is already won
		if (this.isWon()) {
			LinkedList<String> params = new LinkedList<String>();
			params.add("win");
			this.notifyObservers(params);
		}
		LinkedList<String> arg = new LinkedList<String>();
		arg.add("display");
		this.setChanged();
		this.notifyObservers(arg);		
	}
	
	//========Setters&Getters========//
	@Override
	public Level getCurrentLevel() {
		return level;
	}
}
