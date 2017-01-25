package commands;

import java.util.Locale;

import common.Direction;
import model.Model;
import view.View;

public class MoveCommand extends SokobanCommand {
		
	public MoveCommand(Model model, View view) {
	
		this.model = model;
		this.view = view;
	}
	@Override
	public void execute() throws Exception {

		//Throwing any error to the client view
		if(this.params.isEmpty()) 
			this.view.displayError("MoveCommand error: Please provide a direction from the following: 'UP', 'DOWN', 'LEFT', 'RIGHT'.");
		else {
			String direction = this.params.getFirst().toUpperCase(Locale.ROOT);
			if(direction.equals("UP") || direction.equals("DOWN") || direction.equals("LEFT") || direction.equals("RIGHT"))
				this.model.move(Direction.valueOf(direction)); 
			else this.view.displayError("MoveCommand error: Please provide a direction from the following: 'up', 'down', 'left', 'right'.");
		}	
	}
}


