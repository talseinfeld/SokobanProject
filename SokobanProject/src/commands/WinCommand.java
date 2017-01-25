package commands;

import java.io.IOException;

import view.View;

public class WinCommand extends SokobanCommand implements Command {
	
	public WinCommand(View view) {
		
		this.view = view;
				
	}
	@Override
	public void execute() throws IOException {
		
		view.displayWin();

	}

}
