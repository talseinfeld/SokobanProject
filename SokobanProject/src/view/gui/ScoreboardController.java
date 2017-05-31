package view.gui;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.db.MyDatabaseManager;
import model.entities.EntityDB;
import model.entities.ScoreboardDB;

public class ScoreboardController implements Initializable {
	
	
	private MyDatabaseManager dbm = MyDatabaseManager.getInstance();
	
	//getting the levelName from the MainWindowController when building this window
	private String levelName;
	
	@FXML
	private TextField levelNameTextBox;
	@FXML
	private TextField usernameTextBox;
	
	@FXML
	private TableView<EntityDB> scoreboardTable;
	@FXML
	private TableColumn<EntityDB, Integer> idCol;
	@FXML
	private TableColumn<EntityDB, String> levelNameCol;
	@FXML
	private TableColumn<EntityDB, String> usernameCol;
	@FXML
	private TableColumn<EntityDB, Integer> totalStepsCol;
	@FXML
	private TableColumn<EntityDB, Integer> finishTimeCol;
	

	public ScoreboardController() {}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		initColumns();
		initSearch();
		
		scoreboardTable.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->scoreboardTable.requestFocus());
		scoreboardTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount()==2 && 
						scoreboardTable.getSelectionModel().getSelectedItem()!=null) {
					
					
					try {
						String selectedUsername = scoreboardTable.getSelectionModel().getSelectedItem().getUsername();
						FXMLLoader fxmlLoader = new FXMLLoader();
						Parent root2 = fxmlLoader.load(getClass().getResource("/view/gui/UserScore.fxml").openStream());
						//passing the name to the UserScoreController
						UserScoreController controller = fxmlLoader.getController();
						controller.setUsername(selectedUsername);
						controller.initSearch();

						Stage stage = new Stage();
						stage.setTitle("Scoreboard for player \"" + selectedUsername + "\"");
						stage.setScene(new Scene(root2));
						stage.show();

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public void searchByUsername() {
		ObservableList<EntityDB> data = FXCollections.observableArrayList();
		ScoreboardDB db = new ScoreboardDB();
		db.setUsername(usernameTextBox.getText());
		String hql = "from ScoreboardDB s where s.username=:username";
		List<?> list = dbm.getListByQueryAndEntityProperties(hql, db);
		Iterator<?> i = list.iterator();
		while (i.hasNext()) {
			ScoreboardDB sdb = (ScoreboardDB) i.next();
			data.add(sdb);
		}
		scoreboardTable.getItems().clear();
		scoreboardTable.setItems(data);
		scoreboardTable.getSortOrder().setAll(totalStepsCol, finishTimeCol);			
	}
	
	@SuppressWarnings("unchecked")
	public void searchByLevelName() {
		ObservableList<EntityDB> data = FXCollections.observableArrayList();
		ScoreboardDB db = new ScoreboardDB();
		db.setLevelName(levelName);
		String hql = "from ScoreboardDB s where s.levelName=:levelName";
		List<?> list = dbm.getListByQueryAndEntityProperties(hql, db);
		Iterator<?> i = list.iterator();
		while (i.hasNext()) {
			ScoreboardDB sdb = (ScoreboardDB) i.next();
			data.add(sdb);
		}
		
		scoreboardTable.getItems().clear();
		scoreboardTable.setItems(data);
		
		scoreboardTable.getSortOrder().setAll(totalStepsCol, finishTimeCol);
	}
	protected void initColumns() {

		//binding all the table columns to our model's object (ScoreboardDB)
		idCol.setCellValueFactory(columnVal ->
		new SimpleObjectProperty<>(columnVal.getValue().getId()));
		levelNameCol.setCellValueFactory(columnVal->
		new SimpleStringProperty(columnVal.getValue().getLevelName()));
		usernameCol.setCellValueFactory(columnVal->
		new SimpleStringProperty(columnVal.getValue().getUsername()));
		totalStepsCol.setCellValueFactory(columnVal->
		new SimpleObjectProperty<>(columnVal.getValue().getTotalSteps()));
		finishTimeCol.setCellValueFactory(columnVal->
		new SimpleObjectProperty<>(columnVal.getValue().getFinishTime()));
		
	}
	
	@SuppressWarnings("unchecked")
	protected void initSearch() {
		
		ObservableList<EntityDB> data = FXCollections.observableArrayList();
		String hql = "from ScoreboardDB s where s.levelName = :levelName";
		ScoreboardDB db = new ScoreboardDB();
		db.setLevelName(levelName);
		List<?> res = dbm.getListByQueryAndEntityProperties(hql, db);
		@SuppressWarnings("rawtypes")
		Iterator i = res.iterator();
		while(i.hasNext()) {
			ScoreboardDB sdb = (ScoreboardDB) i.next();
			data.add(sdb);
		}
		
		scoreboardTable.getItems().clear();
		scoreboardTable.setItems(data);
		
		scoreboardTable.getSortOrder().setAll(totalStepsCol, finishTimeCol);
				
	}


	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

}
