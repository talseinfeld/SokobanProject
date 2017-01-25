package view;

import model.data.Level;

public interface View {

	public void displayLevel(Level level);
	public void displayWin();
	public void displayError(String error);
	public void stop();
	
}
