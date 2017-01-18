package commands;

import java.io.IOException;

import levels.Level;

public class DisplayCommand implements Command {
	private Level level = new Level();
	private Displayer displayer = null;
	public DisplayCommand() {}
	public DisplayCommand(Level level, Displayer displayer) {
		
		this.displayer = displayer;
		this.level = level;
	}
	@Override
	public void execute() throws IOException {
		this.displayer.display(this.level);		
	}
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}	
}
