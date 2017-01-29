package view.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.data.Level;
import model.data.Square;

public class MySokobanLevelDisplayer extends Canvas {
	
	private Level level;
	private StringProperty wallFilename=new SimpleStringProperty();
	private StringProperty boxFilename=new SimpleStringProperty();
	private StringProperty characterFilename=new SimpleStringProperty();
	private StringProperty goalSquareFilename = new SimpleStringProperty();
	private StringProperty floorFilename = new SimpleStringProperty();
	private StringProperty winFilename = new SimpleStringProperty();
	
	
	
	public void redraw() throws FileNotFoundException {
		ArrayList<ArrayList<Square>> squares = this.level.getSquares();
		if(squares != null) {
			double W = this.getWidth();
			double H = this.getHeight();
			double w = W/this.level.getMaxX();
			double h = H/this.level.getMaxY();
			GraphicsContext gc = getGraphicsContext2D();
			//TODO - catch exception in MainWindowController
			Image wall = new Image(new FileInputStream(getWallFilename()));
			Image box = new Image(new FileInputStream(getBoxFilename()));
			Image floor = new Image(new FileInputStream(getFloorFilename()));
			Image character = new Image(new FileInputStream(getCharacterFilename()));
			Image goal = new Image(new FileInputStream(getGoalSquareFilename()));
			gc.clearRect(0, 0, W, H);
			for(int y=0;y<squares.size();y++) {
				for(int x=0;x<squares.get(y).size();x++) {
					switch (squares.get(y).get(x).toString()) {
					case "#":
						gc.drawImage(wall, x*w, y*h, w, h);				
						break;
					case "@":
						gc.drawImage(box, x*w, y*h, w, h);
						break;
					case " ":
						gc.drawImage(floor, x*w, y*h, w, h);
						break;
					case "A":
						gc.drawImage(character, x*w, y*h, w, h);
						break;
					case "o":
						gc.drawImage(goal, x*w, y*h, w, h);
						break;
					default:
						break;
					}
				}
			}
			
			
		}
	}
	public void drawLevelWon() throws FileNotFoundException {
		GraphicsContext gc = getGraphicsContext2D();
		Image win = new Image(new FileInputStream(getWinFilename()));
		gc.drawImage(win, 0, 0, getWidth(), getHeight());
		
	}
	//===============Setters&Getters=================//
	public String getWallFilename() {
		return wallFilename.get();
	}
	public void setWallFilename(String wallFilename) {
		this.wallFilename.set(wallFilename);
	}
	public String getBoxFilename() {
		return boxFilename.get();
	}
	public void setBoxFilename(String boxFilename) {
		this.boxFilename.set(boxFilename);
	}
	public String getCharacterFilename() {
		return characterFilename.get();
	}
	public void setCharacterFilename(String characterFilename) {
		this.characterFilename.set(characterFilename);
	}
	public String getGoalSquareFilename() {
		return goalSquareFilename.get();
	}
	public void setGoalSquareFilename(String goalSquareFilename) {
		this.goalSquareFilename.set(goalSquareFilename);
	}
	public String getFloorFilename() {
		return floorFilename.get();
	}
	public void setFloorFilename(String floorFilename) {
		this.floorFilename.set(floorFilename);
	}
	public void setLevel(Level level) throws FileNotFoundException {
		this.level = level;
		redraw();
	}
	public String getWinFilename() {
		return winFilename.get();
	}
	public void setWinFilename(String winFilename) {
		this.winFilename.set(winFilename);
	}
	
	
}
