package commands;

import java.io.FileInputStream;

import model.Model;
import view.View;

public class LoadCommand extends SaveLoadFactory {
	/** @author Tal Sheinfeld
	 * This class will provide the actual Loader for the file requested in O(1) time complexity,
	 * using a Factory design pattern.
	 * With a given String from the user, the class will recognize the file extension and 
	 * will open an appropriate loader accordingly.
	 */
	public LoadCommand(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	//loads a level using the file extension from the file path given by the user.
	@Override
	public void execute() throws Exception {
		//Throwing any exception we might get to to the main class so we can print the error to the client
		if (this.params.isEmpty())
			this.view.displayError("LoadCommand error: You didn't provide a file path, i.e: C:\\Levels\\level1.txt");
		this.filePath = this.params.getFirst();
		this.setFileExtension();
		if (!(extLoaders.containsKey(fileExtension)))
			this.view.displayError("LoadCommand error: Please provied a file extension from the following: 'txt', 'obj', 'xml'.");
		this.model.setLevel(extLoaders.get(fileExtension).loadLevel(new FileInputStream(filePath)));
	
	}
}
