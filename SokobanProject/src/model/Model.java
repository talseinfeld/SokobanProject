package model;

import common.Direction;
import model.data.Level;

public interface Model {
	
	public void move(Direction direction);
	public void setLevel(Level level);
	public Level getCurrentLevel();
	public Boolean isWon();
	
}
