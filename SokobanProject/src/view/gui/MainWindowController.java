package view.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.data.Level;
import view.View;

public class MainWindowController extends Observable implements View, Initializable {

	private Level level;
	private int timerCounter;
	
	@FXML
	MySokobanLevelDisplayer levelDisplayer;
	@FXML
	Text stepsCount;
	@FXML
	Text timer;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
		levelDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->levelDisplayer.requestFocus());
		levelDisplayer.setOnKeyPressed(new EventHandler<KeyEvent>() {

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
	public void setGameObjectsPath() {
		levelDisplayer.setWallFilename("./resources/Wall_Brown.png");
		levelDisplayer.setCharacterFilename("./resources/Character4.png");
		levelDisplayer.setBoxFilename("./resources/Crate_Brown.png");
		levelDisplayer.setGoalSquareFilename("./resources/EndPoint_Red.png");
		levelDisplayer.setFloorFilename("./resources/GroundGravel_Sand.png");
		levelDisplayer.setWinFilename("./resources/win.jpg");
	}
	public void openFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Open a Sokoban level file");
		fc.setInitialDirectory(new File("./resources"));
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Level files (.txt .xml *.obj)","*.txt", "*.xml", "*.obj"));
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Object files (*.obj)", "*.obj"));
		File chosen = fc.showOpenDialog(null);
		if (chosen != null) {
			//setGameObjectsPath();
			LinkedList<String> params = new LinkedList<String>();
			params.add("load");
			params.add(chosen.getPath());
			timerCounter=0;
			startTimer();
			//TODO - start music
			this.setChanged();
			notifyObservers(params);
		}
	}
	public void save() {
		if(this.level!=null) {
			FileChooser fc = new FileChooser();
			fc.setTitle("Save a Sokoban level file");
			fc.setInitialDirectory(new File("./resources"));
			FileChooser.ExtensionFilter extFilters = new FileChooser.ExtensionFilter("XML files *.xml", "Text files *.txt", "Object files *.obj");
			fc.getExtensionFilters().add(extFilters);
			File chosen = fc.showOpenDialog(null);
			if (chosen != null) {
				LinkedList<String> params = new LinkedList<String>();
				params.add("save");
				params.add(chosen.getPath());
				//TODO - stop music
				setChanged();
				notifyObservers(params);
			}
		}
	}
	public void startTimer() {
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				timerCounter++;
				timer.setText(String.valueOf(timerCounter));
				
			}
		}, 0, 1000);
	}
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


	
}
