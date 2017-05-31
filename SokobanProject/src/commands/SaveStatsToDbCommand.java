package commands;

import model.Model;
import view.View;

public class SaveStatsToDbCommand extends SokobanCommand implements Command {

	
	public SaveStatsToDbCommand(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	@Override
	public void execute() throws Exception {
		//first taking the level name, then the username
		//both are sent from the View
		this.model.saveToDb(params.remove(), params.remove());

	}

}
