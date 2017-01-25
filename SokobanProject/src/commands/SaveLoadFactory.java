package commands;

import java.util.HashMap;

import model.data.LevelLoader;
import model.data.LevelSaver;
import model.data.MyObjectLevelLoader;
import model.data.MyObjectLevelSaver;
import model.data.MyTextLevelLoader;
import model.data.MyTextLevelSaver;
import model.data.MyXMLLevelLoader;
import model.data.MyXMLLevelSaver;
/**
 * 
 * I've used this class in order not to write duplicate code in LoadCommand & SaveCommand.
 * This class has the Level member that will be initiated or saved later on, to implement execute
 * with it's given signature (execute());
 *
 */
public abstract class SaveLoadFactory extends SokobanCommand {

	protected String filePath;
	protected String fileExtension;
	protected HashMap<String, LevelLoader> extLoaders = null;
	protected HashMap<String, LevelSaver> extSavers = null;
	
	
	//Initialize the file path and it's extension
	public SaveLoadFactory()
	{
		setHashKeys();
	}	
	public void setHashKeys(){
		extLoaders = new HashMap<>();
		extLoaders.put("txt",new MyTextLevelLoader());
		extLoaders.put("obj",new MyObjectLevelLoader());
		extLoaders.put("xml",new MyXMLLevelLoader());

		extSavers = new HashMap<>();
		extSavers.put("txt", new MyTextLevelSaver());
		extSavers.put("obj", new MyObjectLevelSaver());
		extSavers.put("xml", new MyXMLLevelSaver());
	}
	//===========Setters&Getters===========//
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	//Setting the file extension according to the full path given
	//ASSUMING: filepath will be like: C:/Documents/SomeLevel.extension
	public void setFileExtension() {

		int c = filePath.indexOf('.',0);
		this.fileExtension = filePath.substring(c+1,filePath.length());
	}
	public HashMap<String, LevelLoader> getExtLoaders() {
		return extLoaders;
	}
	public void setExtLoaders(HashMap<String, LevelLoader> extLoaders) {
		this.extLoaders = extLoaders;
	}
	public HashMap<String, LevelSaver> getExtSavers() {
		return extSavers;
	}
	public void setExtSavers(HashMap<String, LevelSaver> extSavers) {
		this.extSavers = extSavers;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	
}
