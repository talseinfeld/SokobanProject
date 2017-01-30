package view.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Observable;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import model.data.Level;
import view.View;
/**
 * 
 * @author Tal Sheinfeld
 * Our GUI Controller. Working on Strategy design pattern principles.
 *
 */
public class MainWindowController extends Observable implements View, Initializable {

	private Level level;
	private MediaPlayer mPlayer;
	
	@FXML
	MySokobanLevelDisplayer levelDisplayer;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//added this method so when we start moving our agent, the focus will stay on the character always
		levelDisplayer.focusedProperty().addListener(new ChangeListener<Boolean>()
		  {
		            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) 
		            {
		                Platform.runLater(new Runnable()
		                {
		                    public void run() 
		                    {
		                     levelDisplayer.requestFocus();
		                    }
		                });                    
		            }
		        });
		setMediaPlayer();
		this.mPlayer.play();
		levelDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->levelDisplayer.requestFocus());
		levelDisplayer.setOnKeyPressed(new EventHandler<KeyEvent>() {
			//TODO - add the feature for clients to choose their own keys
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode()==KeyCode.UP)
					setMoveCommand("move up");
				if(event.getCode()==KeyCode.DOWN)
					setMoveCommand("move down");
				if(event.getCode()==KeyCode.RIGHT)
					setMoveCommand("move right");
				if(event.getCode()==KeyCode.LEFT)
					setMoveCommand("move left");
			}
		});
		
	}
	public void openFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Open a Sokoban level file");
		fc.setInitialDirectory(new File("./resources"));
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Object files (*.obj)", "*.obj"));
		File chosen = fc.showOpenDialog(null);
		if (chosen != null) {
			LinkedList<String> params = new LinkedList<String>();
			params.add("load");
			params.add(chosen.getPath());
			this.setChanged();
			notifyObservers(params);
		}
	}
	public void save() {
		if(this.level!=null) {
			FileChooser fc = new FileChooser();
			fc.setTitle("Save a Sokoban level file");
			fc.setInitialDirectory(new File("./resources"));
			fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
			fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
			fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Object files (*.obj)", "*.obj"));
			File chosen = fc.showSaveDialog(null);
			if (chosen != null) {
				LinkedList<String> params = new LinkedList<String>();
				params.add("save");
				params.add(chosen.getPath());
				setChanged();
				notifyObservers(params);
			}
		}
	}
	//TODO - add a working timer and a step counter for each level.

	public void setMoveCommand(String direction) {
		LinkedList<String> params = new LinkedList<String>();
		String[] commandArr = direction.split(" ");
		for(String s : commandArr)
			params.add(s);
		setChanged();
		notifyObservers(params);
	}
	@Override
	public void displayLevel(Level level) {
		//sending the level to MySokobanLevelDisplayer to initialize and then draw
		try {
			levelDisplayer.setLevel(level);
			this.level=level;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void displayWin() {
		try {
			levelDisplayer.drawLevelWon();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void displayError(String error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		Platform.exit();
		setChanged();
		LinkedList<String> params = new LinkedList<String>();
		params.add("exit");
		notifyObservers(params);
		
	}
	//TODO - take this hard coded Mp3 setter and put in the FXML; Fix SceneBuilder bug opening this MainWindow.fxml
	public void setMediaPlayer() {
		this.mPlayer = new MediaPlayer(new Media(new File("./resources/5yt.mp3").toURI().toString()));
		this.mPlayer.setOnEndOfMedia(new Runnable() {
			
			@Override
			public void run() {
				mPlayer.seek(Duration.ZERO);			
			}
		});
	}	
}
