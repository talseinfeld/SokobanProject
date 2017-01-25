package level_objects;

import java.awt.Point;
import java.io.Serializable;

public class GoalSquare extends Square implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	public GoalSquare() {}
	public GoalSquare(Point p) {
		super(p);
	}
	
	//"*" stands for a GoalSquare that contains a Box
	//"a" stands for a GoalSquare that contains a Character
	//a convenient way to manually QA the game
	@Override
	public String toString(){
		if (this.getGo()!=null) {
			if (this.getGo().toString() == new Box().toString())
				return "*";
			if(this.getGo().toString() == new Character().toString())
				return "a";
		}
		return "o";
	}

}