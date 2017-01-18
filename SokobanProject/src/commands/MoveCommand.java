package commands;

import java.io.IOException;

import levels.Level;
import policies.Direction;
import policies.Policy;

public class MoveCommand implements Command {
	private Direction direction = null;
	private Policy policy = null;
	private Level level = new Level();
	public MoveCommand() {}
	
	public MoveCommand(Level level, Policy policy, Direction direction) {
		
		this.level.setSquares(level.getSquares());
		this.level.setCharacterSquare(level.getCharacterSquare());
		this.policy = policy;
		this.direction = direction;
	}
	@Override
	public void execute() throws IOException {
		policy.move(this.level, direction);

	}

}
