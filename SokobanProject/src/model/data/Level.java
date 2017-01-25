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
	private int currentScore = 0;
	private int stepsCounter = 0;
	private int maxY, maxX;
	
	/*Container for all the objects this level has*/
	
	private ArrayList<ArrayList<Square>> squares = new ArrayList<ArrayList<Square>>();
	
	//*********************C'tor****************************//
	
	//Was made for MyTextLevelLoader class that returns ArrayList<ArrayList<Square>>
	public Level(ArrayList<ArrayList<Square>> squares)
	{
		setSquares(squares);
		initLevel();
	}
	public Level() {//default c'tor
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
		for (int y=0;y<this.squares.size();++y) {
			if (y > this.maxY)
				this.maxY = y;
			for (int x=0;x<this.squares.get(y).size();++x) {
				if (x > this.maxX)
					this.maxX = x;
				if (this.squares.get(y).get(x).toString() == new Character().toString())
					this.characterSquare = new Square(new Point(x,y), new Character());
				if (this.squares.get(y).get(x).toString() == new GoalSquare().toString())
					this.goalSquares.add((GoalSquare)this.squares.get(y).get(x));				
			}
		}
	}
	/* UNDER MySokobanPolicy responsibility to check - Took this method to Policy
	public Boolean isWin() {
		for(GoalSquare gs : this.goalSquares) {
			if (gs.getGo() == null || gs.getGo().toString()!=new Box().toString())
				return false;
		}
		return true;
	} */
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
	public Integer getCurrentScore() {
		return currentScore;
	}
	public void setCurrentScore(Integer currentScore) {
		this.currentScore = currentScore;
	}
	public int getStepCounter() {
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
	public void incStepsCounter(int steps) {
		this.stepsCounter+=steps;
	}

	
}
