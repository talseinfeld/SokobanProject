package view.gui;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.db.MyDatabaseManager;
import model.entities.EntityDB;
import model.entities.ScoreboardDB;

public class UserScoreController implements Initializable {

	MyDatabaseManager dbm = MyDatabaseManager.getInstance();

	@FXML
	private TableView<EntityDB> usersTable;

	@FXML
	private TableColumn<EntityDB, String> usernameCol;
	@FXML
	private TableColumn<EntityDB, String> levelNameCol;
	@FXML
	private TableColumn<EntityDB, Integer> totalStepsCol;
	@FXML
	private TableColumn<EntityDB, Integer> finishTimeCol;

	//getting the username from the Scoreboard Table
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public UserScoreController() {}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initTableColumns();
		initSearch();
	}

	@SuppressWarnings("unchecked")
	public void initSearch() {
		ObservableList<EntityDB> data = FXCollections.observableArrayList();
		ScoreboardDB db = new ScoreboardDB();
		db.setUsername(username);
		String hql = "from ScoreboardDB s where s.username=:username";
		List<?> list = dbm.getListByQueryAndEntityProperties(hql, db);
		Iterator<?> i = list.iterator();
		while (i.hasNext()) {
			ScoreboardDB sdb = (ScoreboardDB) i.next();
			data.add(sdb);
		}
		usersTable.getItems().clear();
		usersTable.setItems(data);
		usersTable.getSortOrder().setAll(totalStepsCol,finishTimeCol);

	}

	protected void initTableColumns() {

		// binding the columns to the model object
		usernameCol.setCellValueFactory(param -> 
		new SimpleObjectProperty<>(param.getValue().getUsername()));
		levelNameCol.setCellValueFactory(param -> 
		new SimpleObjectProperty<>(param.getValue().getLevelName()));
		totalStepsCol.setCellValueFactory(param -> 
		new SimpleObjectProperty<>(param.getValue().getTotalSteps()));
		finishTimeCol.setCellValueFactory(param -> 
		new SimpleObjectProperty<>(param.getValue().getFinishTime()));
	}

}
