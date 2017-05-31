package commands;

import java.io.FileInputStream;

import org.apache.commons.io.FilenameUtils;

import model.Model;
import view.View;
/** @author Tal Sheinfeld
 * This class will provide the actual Loader for the file requested in O(1) time complexity,
 * using a Factory design pattern.
 * With a given String from the user, the class will recognize the file extension and 
 * will open an appropriate loader accordingly.
 */
public class LoadCommand extends SaveLoadFactory {
	
	public LoadCommand(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	//loads a level using the file extension from the file path given by the user.
	@Override
	public void execute() throws Exception {
		//Throwing any exception we might get to to the main class so we can print the error to the client
		if (this.params.isEmpty()) {
			Exception e = new Exception("LoadCommand error: You didn't provide a file path, i.e: C:\\Levels\\level1.txt");
			this.view.displayError(e);
		}
		this.filePath = this.params.getFirst();
		this.setFileExtension();
		if (!(extLoaders.containsKey(fileExtension))) {
			Exception e = new Exception("LoadCommand error: Please provied a file extension from the following: 'txt', 'obj', 'xml'.");
			this.view.displayError(e);
		}
		this.model.setLevel(extLoaders.get(fileExtension).loadLevel(new FileInputStream(filePath)));
		if (model.getCurrentLevel().getLevelName()==null) {
			//TODO - normalizeNoEndSeperators
			String levelName = FilenameUtils.getName(filePath);
			levelName = FilenameUtils.removeExtension(levelName);
			model.getCurrentLevel().setLevelName(levelName); 
		}
		view.setLevelName(model.getCurrentLevel().getLevelName());
	}
}
