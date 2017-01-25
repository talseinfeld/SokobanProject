package commands;

import java.io.IOException;

import model.Model;
import view.View;

public class DisplayCommand extends SokobanCommand implements Command {
	
	public DisplayCommand() {}//default C'tor
	
	public DisplayCommand(Model m, View v) {
		
		this.model = m;
		this.view = v;

	}
	@Override
	public void execute() throws IOException {
		
		this.view.displayLevel(this.model.getCurrentLevel());
	}

}
