package commands;

import model.Model;
import view.View;
/**
 * 
 * @author Tal Sheinfeld
 * Notifying the client if the level had been won
 *
 */
public class WinCommand extends SokobanCommand {
	
	public WinCommand(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	@Override
	public void execute() throws Exception {
		view.displayLevel(model.getCurrentLevel());
		view.displayWin();
	}

}
