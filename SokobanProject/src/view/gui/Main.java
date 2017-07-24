package view.gui;
	
import controller.Controller;
import controller.MySokobanController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.MySokobanModel;
import model.policy.MySokobanPolicy;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			FXMLLoader fxl = new FXMLLoader();
			BorderPane root = fxl.load(getClass().getResource("MainWindow.fxml").openStream());
			MainWindowController mwc=fxl.getController();
			MySokobanModel m=new MySokobanModel(new MySokobanPolicy(), 5555);
			Controller c=new MySokobanController(m, mwc);
			m.addObserver(c);
			mwc.addObserver(c);
			
			Scene scene = new Scene(root,600,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Sokoban");
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setOnCloseRequest((e)->mwc.stop());
		} catch(Exception e) {
			System.out.println("view.gui: Main: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
