package commands;

import java.io.FileInputStream;
import java.io.IOException;

public class LoadCommand extends IOFactory {
	/**
	 * This class will provide the actual Loader for the file requested in O(1) time complexity,
	 * using a Factory design pattern.
	 * With a given String from the user, the class will recognize the file extension and 
	 * will open an appropriate loader accordingly.
	 */
	public LoadCommand() {}
	
	public LoadCommand(String filePath) {//Initialize path from super class
		super(filePath);
	}
	
	//loads a level using the file extension from the file path given by the user.
	@Override
	public void execute() throws IOException {
		if (!extLoaders.containsKey(fileExtension))
			throw new IOException("LoadCommand error: Please provied a valid file extension.");
		try {
			this.level = extLoaders.get(fileExtension).loadLevel(new FileInputStream(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
