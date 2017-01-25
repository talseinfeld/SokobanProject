package commands;

import java.io.IOException;

import controller.Controller;

public class SafeExitCommand extends SokobanCommand {
	private Controller c;
	
	public SafeExitCommand(Controller c) {
		this.c = c;
	}
	@Override
	public void execute() throws IOException {
		if(c.getServer()!=null) {
			c.getServer().getClientHandler().stop();
			c.stopTheServer();
		}
		c.getCommandController().stop();
		
	}
}
