package policies;
import java.awt.Point;

import levels.Level;
/**
 * Game's policy interface. This will dictate where can any GameObject move
 * according to each game policy implemented afterwards.
 *  */
public interface Policy {
	
	public void move(Level level, Direction direction);
	public void setPointToADirection(Point p, Direction direction);
}
