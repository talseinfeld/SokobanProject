package commands;

import model.Model;
import view.View;
/**
 * 
 * @author Tal Sheinfeld
 * Displaying the current level to the client.
 *
 */
public class DisplayCommand extends SokobanCommand {
		
	public DisplayCommand(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	@Override
	public void execute() throws Exception {
		if (this.model.getCurrentLevel()==null || this.model.getCurrentLevel().getSquares() == null) {
			Exception e = new Exception("DisplayCommand error: There is no level to display.");
			this.view.displayError(e);
		}
		this.view.displayLevel(this.model.getCurrentLevel());
	}

}
