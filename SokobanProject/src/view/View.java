package view;

import model.data.Level;

public interface View {

	public void displayLevel(Level level);
	public void setLevelName(String levelName);
	public void displayWin();
	public void displayError(Exception e);
	public void stop();
	public void usernameDialog();
	public void solve();
	public void hint();
	
	
}
