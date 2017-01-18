package commands;

import java.io.IOException;

import levels.Level;

public interface Displayer {
	public void display(Level level) throws IOException;
}
