package commands;

import java.io.IOException;

import common.Direction;
import model.Model;

public class MoveCommand extends SokobanCommand {
	
	public MoveCommand() {}
	
	public MoveCommand(Model model) {
	
		this.model = model;
	}
	@Override
	public void execute() throws IOException {
		String ways = this.params.getFirst().toUpperCase();
		if (ways.equals("UP") ||ways.equals("DOWN") ||ways.equals("LEFT") ||ways.equals("RIGHT"))
			this.model.move(Direction.valueOf(ways));
	}

}
