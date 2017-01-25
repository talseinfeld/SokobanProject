package view;

import java.io.IOException;

import model.data.Level;

public interface View {

	public void displayLevel(Level level) throws IOException;
	public void displayWin();
	public void displayError(String error);
	public void stop();
	
}
