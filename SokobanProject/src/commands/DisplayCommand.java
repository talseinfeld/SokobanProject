package commands;

import model.Model;
import view.View;
/**
 * 
 * @author Tal Sheinfeld
 * Displaying the current level to the client.
 *
 */
public class DisplayCommand extends SokobanCommand implements Command {
		
	public DisplayCommand(Model m, View v) {
		
		this.model = m;
		this.view = v;

	}
	@Override
	public void execute() throws Exception {
		if (this.model.getCurrentLevel()==null || this.model.getCurrentLevel().getSquares() == null)
			this.view.displayError("DisplayCommand error: There is no level to display.");
		this.view.displayLevel(this.model.getCurrentLevel());
	}

}
