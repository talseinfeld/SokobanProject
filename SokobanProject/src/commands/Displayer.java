package commands;

import java.io.IOException;

import model.data.Level;

public interface Displayer {
	public void display(Level level) throws IOException;
}
