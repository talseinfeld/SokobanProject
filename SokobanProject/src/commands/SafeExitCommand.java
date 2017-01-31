package commands;

import controller.Controller;

public class SafeExitCommand extends SokobanCommand {
	
	private Controller c;
	
	public SafeExitCommand(Controller c) {
		this.c = c;
	}
	@Override
	public void execute() throws Exception {
		if(c.getServer()!=null) {
			c.getServer().getClientHandler().stop();
			c.stopTheServer();
		}		
		c.getCommandController().stop();
		System.exit(0);
	}
}
