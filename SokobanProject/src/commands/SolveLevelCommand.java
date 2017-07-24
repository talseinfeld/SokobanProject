package commands;

import model.Model;
import view.View;

public class SolveLevelCommand extends SokobanCommand implements Command {

	public SolveLevelCommand(Model model, View view) {
		this.model=model;
		this.view=view;
	}
	@Override
	public void execute() throws Exception {
		model.solveLevel();
	}

}
