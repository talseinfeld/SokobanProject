package policies;

public enum Direction {
	UP, DOWN, LEFT, RIGHT;
	
	Direction() {}
	@Override //TODO - check if deleteable 
	public String toString() {
		switch (this) {
		case UP:
			return "up";
		case DOWN:
			return "down";
		case LEFT:
			return "left";
		case RIGHT:
			return "right";
		}
		return null;
	}
}