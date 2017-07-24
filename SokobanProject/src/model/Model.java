package model;

import common.Direction;
import model.data.Level;

public interface Model {
	
	public void move(Direction direction);
	public void setLevel(Level level);
	public void saveToDb(String levelName, String username);
	public Level getCurrentLevel();
	public Boolean isWon();	
	public void solveLevel();
	public void hint();
	
}
