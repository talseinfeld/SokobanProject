package model.data;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public class Level implements Serializable {
	
	/*** Default Serializable version added	 */
	private static final long serialVersionUID = 1L;
	/***************************************/
	
	//**********Level Members********************/
	
	private ArrayList<GoalSquare> goalSquares = new ArrayList<>();
	private Square characterSquare = new Square();
	private String levelName;
	private int stepsCounter, timeCounter;
	private int maxY, maxX;
	private boolean wonFlag = false;
	
	/*Container for all the objects this level has*/
	
	private ArrayList<ArrayList<Square>> squares = new ArrayList<ArrayList<Square>>();
	
	//*********************C'tor****************************//
	
	//Was made for MyTextLevelLoader class that returns ArrayList<ArrayList<Square>>
	public Level(ArrayList<ArrayList<Square>> squares)
	{
		stepsCounter = 0; setTimeCounter(0);
		setSquares(squares);
		initLevel();
	}
	public Level() {//default c'tor
		stepsCounter = 0; setTimeCounter(0);
		initLevel();
	}

	//moving a game object from a position to another position
	public void moveToSquare(Point from, Point to) { 
		
		this.squares.get(to.y).get(to.x).setGo(this.squares.get(from.y).get(from.x).getGo());
		this.squares.get(to.y).get(to.x).setPosition(to);
		this.squares.get(from.y).get(from.x).setGo(null);
	}

	
	/*  going through the entire level's "Squares" and
	 *  targeting all the important objects for later use.
	 *  ATM: checks where all the goal squares are and where is the player */
	private void initLevel() {
		this.maxY=this.squares.size();
		for (int y=0;y<this.squares.size();y++) {
			for (int x=0;x<this.squares.get(y).size();x++) {
				if ((x+1) > this.maxX)
					this.maxX = x+1;
				if (this.squares.get(y).get(x).toString() == new Character().toString())
					this.characterSquare = new Square(new Point(x,y), new Character());
				if (this.squares.get(y).get(x).toString() == new GoalSquare().toString())
					this.goalSquares.add((GoalSquare)this.squares.get(y).get(x));				
			}
		}
	}

	//*****Setters & Getters********//
	public ArrayList<ArrayList<Square>> getSquares() {
		return this.squares;
	}
	public void setSquares(ArrayList<ArrayList<Square>> squares) {
		this.squares = squares;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	int getStepCounter() {
		return stepsCounter;
	}
	public void setStepCounter(int stepsCounter) {
		this.stepsCounter = stepsCounter;
	}
	public int getMaxX() {
		return maxX;
	}
	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}
	public int getMaxY() {
		return maxY;
	}
	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}
	public Square getCharacterSquare() {
		return characterSquare;
	}
	public void setCharacterSquare(Square characterSquare) {
		this.characterSquare = characterSquare;
	}
	//setting CharacterSquare with a given point
	public void setCharacterSquareAtPoint(Point p) {
		this.characterSquare.setPosition(p);
	}
	//returns a Square object in a given point
	public Square getSquareAtPoint(Point p) {
		return this.squares.get(p.y).get(p.x);
	}
	public ArrayList<GoalSquare> getGoalSquares() {
		return goalSquares;
	}
	public void setGoalSquares(ArrayList<GoalSquare> goalSquares) {
		this.goalSquares = goalSquares;
	}
	public int getStepsCounter() {
		return stepsCounter;
	}
	public void incStepsCounter(int steps) {
		this.stepsCounter+=steps;
	}
	public boolean getWonFlag() {
		return wonFlag;
	}
	public void setWonFlag(boolean wonFlag) {
		this.wonFlag = wonFlag;
	}
	public int getTimeCounter() {
		return timeCounter;
	}
	public void setTimeCounter(int timeCounter) {
		this.timeCounter = timeCounter;
	}
	
}
