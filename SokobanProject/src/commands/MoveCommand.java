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
		if(this.params.isEmpty()) {
			Exception e = 
					new Exception("MoveCommand error: Please provide a direction from the following: 'UP', 'DOWN', 'LEFT', 'RIGHT'.");
			this.view.displayError(e);
		}
		if(this.model.isWon())
			return;
		else {
			String direction = this.params.getFirst().toUpperCase(Locale.ROOT);
			if(direction.equals("UP") || direction.equals("DOWN") || direction.equals("LEFT") || direction.equals("RIGHT"))
				this.model.move(Direction.valueOf(direction)); 
			else {
				Exception e = 
						new Exception("MoveCommand error: Please provide a direction from the following: 'UP', 'DOWN', 'LEFT', 'RIGHT'.");
				this.view.displayError(e);
			}
		}	
	}
}


