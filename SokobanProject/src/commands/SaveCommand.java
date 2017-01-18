package commands;

import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class will provide the actual Saver for the file requested in O(1) time complexity,
 * using a Factory design pattern.
 * With a given String from the user, the class will recognize the file extension and 
 * will open an appropriate saver accordingly.
 */
public class SaveCommand extends IOFactory {
	
	public SaveCommand() {}
	
	
	public SaveCommand(String filePath) {
		super(filePath);	
	}

	//saves a level using the file extension from the file path given by the user.
	@Override
	public void execute() throws IOException {
		if (!extSavers.containsKey(fileExtension))
			throw new IOException("SaveCommand error: Please provide a valid file extension.");
		try {
			extSavers.get(fileExtension).saveLevel(this.level, new FileOutputStream(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
