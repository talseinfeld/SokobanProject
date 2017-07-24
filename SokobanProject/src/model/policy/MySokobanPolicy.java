package model.policy;

import java.awt.Point;
import java.util.ArrayList;

import common.Direction;
import model.data.Box;
import model.data.Character;
import model.data.GameObject;
import model.data.GoalSquare;
import model.data.Level;
/** 
 * Our Sokoban policy class. This class is defining our game's rules; 
 * In this class, we will give a specific order to Level class whether to move our player
 * or not. 
 * In our Sokoban game, we assume that each movement is only one step at a time and our player
 * cannot push two boxes, nor go through walls. On top of that, we cannot pull back boxes - 
 * only moving one box at a time, with the same given direction, only if the Square after that 
 * box has nothing in it (meaning: The next Square has no GameObject in it).
 * */
public class MySokobanPolicy implements Policy {

	private Level level;

	public MySokobanPolicy() {}

	public MySokobanPolicy(Level level) {
		this.level = level;
	}
	//Assuming each step is only one step
	@Override
	public void setPointToADirection(Point p, Direction direction) {
		switch(direction) {
		case UP:
			p.setLocation(p.x,p.y-1);
			break;
		case DOWN:
			p.setLocation(p.x, p.y+1);
			break;
		case LEFT:
			p.setLocation(p.x-1, p.y);
			break;
		case RIGHT:
			p.setLocation(p.x+1, p.y);
			break;
		default:
			break;
		}
	}


	@Override
	public boolean move(Level level, Direction direction) {

		Point from = level.getCharacterSquare().getPosition();
		//setting the position from our Player according to a given direction
		Point to = new Point(from);
		setPointToADirection(to, direction);

		//Checking if the Square in the given direction has nothing in it.
		//In other words: It's either a blank or a GoalSquare with no Boxes in it.
		if (level.getSquareAtPoint(to).getGo() == null) {
			level.moveToSquare(from, to);
			level.setCharacterSquareAtPoint(to);
			level.incStepsCounter(1);
			return true;
		}
		//Checking if the Square in the given direction has Box in it
		if (level.getSquareAtPoint(to).getGo().toString() == new Box().toString()) {
			//Now we need to check if the Square ahead is free as well:
			//First, we'll check the next Square after 'to':
			Point oneAheadfromTo = new Point(to);
			setPointToADirection(oneAheadfromTo, direction);

			if (level.getSquareAtPoint(oneAheadfromTo).getGo() == null) {
				//If this square is free, first we need to push the box to this location.
				level.moveToSquare(to, oneAheadfromTo);
				//Then we move the box we encountered first
				level.moveToSquare(from, to);
				level.setCharacterSquareAtPoint(to);
				level.incStepsCounter(1);
				return true;
			}
		}
		return false;

	}

	@Override
	public Boolean isWon(Level level) {
		ArrayList<GoalSquare> goalSquares = level.getGoalSquares();
		for(GoalSquare gs : goalSquares) {
			if (gs.getGo() == null || gs.getGo().toString()!=new Box().toString())
				return false;
		}
		level.setWonFlag(true);		
		return true;
	}

	@Override
	public boolean isGameObjectMovabaleTo(Point from, Direction direction) {

		GameObject go = level.getSquareAtPoint(from).getGo();
		if (go != null) {
			if (go.toString().equals(new Character().toString())) {
				Point to = new Point(from);
				setPointToADirection(to, direction);
				if (level.getSquareAtPoint(to).getGo() == null) 
					return true;
			}

			else if (go.toString().equals(new Box().toString())) {
				Point to = new Point(from);
				setPointToADirection(to, direction);
				Point oneBeforeFrom = new Point(from);
				switch (direction.toString()) {
				case "UP":
					setPointToADirection(oneBeforeFrom, Direction.DOWN);
					if (level.getSquareAtPoint(oneBeforeFrom).getGo() == null &&
							level.getSquareAtPoint(to).getGo() == null)
						return true;
				case "DOWN":
					setPointToADirection(oneBeforeFrom, Direction.UP);
					if (level.getSquareAtPoint(oneBeforeFrom).getGo() == null &&
							level.getSquareAtPoint(to).getGo() == null)
						return true;
				case "LEFT":
					setPointToADirection(oneBeforeFrom, Direction.RIGHT);
					if (level.getSquareAtPoint(oneBeforeFrom).getGo() == null &&
							level.getSquareAtPoint(to).getGo() == null)
						return true;
				case "RIGHT":
					setPointToADirection(oneBeforeFrom, Direction.LEFT);
					if (level.getSquareAtPoint(oneBeforeFrom).getGo() == null &&
							level.getSquareAtPoint(to).getGo() == null)
						return true;
				}
			}
		}
		return false;
	}


}

