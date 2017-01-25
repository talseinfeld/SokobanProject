package commands;

import java.io.FileInputStream;
import java.io.IOException;

import model.Model;

public class LoadCommand extends SaveLoadFactory {
	/**
	 * This class will provide the actual Loader for the file requested in O(1) time complexity,
	 * using a Factory design pattern.
	 * With a given String from the user, the class will recognize the file extension and 
	 * will open an appropriate loader accordingly.
	 */
	public LoadCommand(Model model) {
		this.model = model;
	}
	
	//loads a level using the file extension from the file path given by the user.
	@Override
	public void execute() throws IOException {
		this.filePath = this.params.getFirst();
		this.setFileExtension();
		if (!extLoaders.containsKey(fileExtension))
			throw new IOException("LoadCommand error: Please provied a valid file extension.");
		try {
			this.model.setLevel(extLoaders.get(fileExtension).loadLevel(new FileInputStream(filePath)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
