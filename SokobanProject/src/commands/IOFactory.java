package commands;

import java.util.HashMap;

import levels.*;
/**
 * 
 * I've used this class in order not to write duplicate code in LoadCommand & SaveCommand.
 * This class has the Level member that will be initiated or saved later on, to implement execute
 * with it's given signature (execute());
 *
 */
public abstract class IOFactory implements Command {

	protected Level level= new Level();
	protected String filePath;
	protected String fileExtension;
	protected HashMap<String, LevelLoader> extLoaders = null;
	protected HashMap<String, LevelSaver> extSavers = null;
	public IOFactory () {}
	
	//Initialize the file path and it's extension
	public IOFactory(String filePath)
	{
		this.filePath = filePath;
		setFileExtension();
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
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileExtension() {
		return fileExtension;
	}
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
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	
}
