package view.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.data.Level;
import model.db.MyDatabaseManager;
import model.entities.EntityDB;
import view.View;

/**
 * 
 * @author Tal Sheinfeld
 * Our GUI Controller. Working on Strategy design pattern principles.
 *
 */

public class MainWindowController extends Observable implements View, Initializable {
	
	private String levelName;
	private Timer timer;
	private int secondsCounter = 0;
	private IntegerProperty currentTimeSeconds;
	private IntegerProperty stepsCounter;
	private Level level;
	private MediaPlayer mPlayer;
	
	private MyDatabaseManager dbm = MyDatabaseManager.getInstance();
	
	@FXML
	MySokobanLevelDisplayer levelDisplayer;
	@FXML
	Label timeLabel;
	@FXML
	Label stepsLabel;
	//***********Scoreboard table************//
	@FXML
	protected TableView<EntityDB> scoreboardTable;
	//***********Table columns**************//
	@FXML
	protected TableColumn<EntityDB, String> levelNameCol;
	@FXML
	protected TableColumn<EntityDB, String> usernameCol;
	@FXML
	protected TableColumn<EntityDB, Integer> totalStepsCol;
	@FXML
	protected TableColumn<EntityDB, Integer> finishTimeCol;

	
	public MainWindowController() {}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//added this method so when we start moving our agent, the focus will stay on the character always
		levelDisplayer.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) 
			{
				Platform.runLater(new Runnable()
				{
					@Override
					public void run() 
					{
						levelDisplayer.requestFocus();
					}
				});                    
			}
		});
		setMediaPlayer();
		this.mPlayer.play();
		bindVariables();
		levelDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->levelDisplayer.requestFocus());
		levelDisplayer.setOnKeyPressed(new EventHandler<KeyEvent>() {
			//TODO - add the feature for clients to choose their own keys
			@Override
			public void handle(KeyEvent event) {
				setStepsCounter(level.getStepsCounter());
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
			List<String> params = new LinkedList<>();
			params.add("load");
			params.add(chosen.getPath());
			this.setChanged();
			notifyObservers(params);
			startTimer();
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
				List<String> params = new LinkedList<>();
				params.add("save");
				params.add(chosen.getPath());
				setChanged();
				notifyObservers(params);
			}
		}
	}
	@Override
	public void setLevelName(String levelName) {
		//LoadCommand calls this function after getting the file's and getting the file name out of it
		this.levelName = levelName;
		//TODO - Platform.runLater(()->levelNameLabel.setText(levelName);
	}
	public void setMoveCommand(String direction) {
		LinkedList<String> params = new LinkedList<String>();
		String[] commandArr = direction.split(" ");
		for(String s : commandArr)
			params.add(s);
		setChanged();
		notifyObservers(params);
	}


	private void bindVariables() {
		currentTimeSeconds = new SimpleIntegerProperty(0);
		stepsCounter = new SimpleIntegerProperty(0);
		timeLabel.textProperty().bind(currentTimeSeconds.asString());
		stepsLabel.textProperty().bind(stepsCounter.asString());
	}
	private void startTimer() {
		secondsCounter = 0;
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						secondsCounter++;
						currentTimeSeconds.set(secondsCounter);						
					}
				});
			}
		}, 0, 1000);
	}
	@Override
	public void displayLevel(Level level) {
		//sending the level to MySokobanLevelDisplayer to initialize and then draw
		try {
			levelDisplayer.setLevel(level);
			this.level=level;
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void displayWin() {
		try {
			levelDisplayer.drawLevelWon();
			this.usernameDialog();
			timer.cancel();
			level.setTimeCounter(currentTimeSeconds.get());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void displayError(Exception e) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Error dialog");
				alert.setHeaderText("Attention");
				alert.setContentText(e.getMessage());
				e.printStackTrace();
				alert.showAndWait();				
			}
		});
	}

	//Safely exiting the program
	@Override
	public void stop() {
		setChanged();
		List<String> params = new LinkedList<>();
		params.add("exit");
		notifyObservers(params);
		Platform.exit();
	}
	//TODO - take this hard coded Mp3 setter and put in the FXML
	public void setMediaPlayer() {
		this.mPlayer = new MediaPlayer(new Media(new File("./resources/5yt.mp3").toURI().toString()));
		this.mPlayer.setOnEndOfMedia(new Runnable() {

			@Override
			public void run() {
				mPlayer.seek(Duration.ZERO);			
			}
		});
	}
	
	public void openScoreboard() {
		if (levelName == null) {
			displayError(new Exception("Please load a level before pressing the 'Highscore' button"));
			return;
		}
        try {
        	FXMLLoader fxmlLoader = new FXMLLoader();
        	Parent root1 = fxmlLoader.load(getClass().getResource("/view/gui/Scoreboard.fxml").openStream());
        	ScoreboardController controller = fxmlLoader.getController();
        	controller.setLevelName(levelName);
        	controller.initSearch();
        	
        	 Stage stage = new Stage();

             stage.setTitle("Scoreboard for level "+levelName);
             stage.setScene(new Scene(root1));
             stage.show();
             
		} catch (Exception e) {
			displayError(e);
		}
	}

	/**
	 * Opens a dialog window to the user, to enter his username,
	 * if the user won the level.
	 * Then this data will be saved in the database.
	 */
	@Override
	public void usernameDialog() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				TextInputDialog dialog = new TextInputDialog("Username");
				dialog.setTitle("You're the winner!");
				dialog.setHeaderText("Congratulations, you've just won "+levelName);
				dialog.setContentText("Please enter your username:");
				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()) {
					setChanged();
					List<String> params = new LinkedList<>();
					params.add("username");
					params.add(levelName);
					params.add(result.get());
					notifyObservers(params);
				}
			}
		});			
	}
	
	public void setStepsCounter(Integer stepsCounter) {
		this.stepsCounter.set(stepsCounter);
	}
}
