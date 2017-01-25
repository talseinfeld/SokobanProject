package commands;

import java.io.FileOutputStream;
import java.io.IOException;

import model.Model;
/**
 * This class will provide the actual Saver for the file requested in O(1) time complexity,
 * using a Factory design pattern.
 * With a given String from the user, the class will recognize the file extension and 
 * will open an appropriate saver accordingly.
 */
public class SaveCommand extends SaveLoadFactory {
	
	public SaveCommand(Model model) {
		this.model = model;
	}

	//saves a level using the file extension from the file path given by the user.
	@Override
	public void execute() throws IOException {
		this.filePath = this.params.getFirst();
		this.setFileExtension();
		if (!extSavers.containsKey(fileExtension))
			throw new IOException("SaveCommand error: Please provide a valid file extension.");
		try {
			extSavers.get(fileExtension).saveLevel(this.model.getCurrentLevel(), new FileOutputStream(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
