package commands;

import java.io.FileOutputStream;

import model.Model;
import view.View;
/**
 * This class will provide the actual Saver for the file requested in O(1) time complexity,
 * using a Factory design pattern.
 * With a given String from the user, the class will recognize the file extension and 
 * will open an appropriate saver accordingly.
 */
public class SaveCommand extends SaveLoadFactory {
	
	public SaveCommand(Model model, View view) {
		
		this.model = model;
		this.view = view;
	}

	//saves a level using the file extension from the file path given by the user.
	@Override
	public void execute() throws Exception {
		//Throwing any exception we might get to to the main class so we can print the error to the client
		if (this.params.isEmpty()) {
			Exception e = new Exception("SaveCommand error: You didn't provide a file path, i.e: C:\\Levels\\myLevel.xml");
			this.view.displayError(e);
		}
		this.filePath = this.params.getFirst();
		this.setFileExtension();
		if (!extSavers.containsKey(fileExtension)) {
			Exception e = new Exception("SaveCommand error: Please provied a file extension from the following: 'txt', 'obj', 'xml'.");
			this.view.displayError(e);
		}
		extSavers.get(fileExtension).saveLevel(this.model.getCurrentLevel(), new FileOutputStream(filePath));
	}
}
