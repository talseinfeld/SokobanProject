package boot;

import controller.Controller;
import controller.MySokobanController;
import controller.server.MySokobanServer;
import controller.server.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.MySokobanModel;
import model.policy.MySokobanPolicy;
import view.MySokobanClientHandler;
import view.gui.MainWindowController;
/**
 * 
 * @author Tal Sheinfeld
 *
 */

public class Run extends Application {

	public static void main(String[] args) {
		if(args.length==2 && args[0].equals("-server")) {
			//Initializing server's protocol - Our new CLI
			MySokobanClientHandler ch = new MySokobanClientHandler();
			//Initializing our server with the port we will get in the command console
			Server server = new MySokobanServer(Integer.parseInt(args[1]), ch);
			MySokobanModel model = new MySokobanModel(new MySokobanPolicy());
			Controller controller = new MySokobanController(model, ch, server);
			model.addObserver(controller);
			ch.addObserver(controller);
			try{
				controller.startTheServer();
			}
			catch (Exception e){
				ch.displayError("Main: "+e.getMessage());
			}
		}
		else { 
			if(args.length==0) {
				launch(args);
			}
			else {
				System.out.println("1) To run the server: In the command line type the string '-server' followed by the server's port");
				System.out.println("2) To run the GUI - Don't add any parameters. Just run the program.");
			}
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader fxl = new FXMLLoader();
			BorderPane root = fxl.load(getClass().getResource("/view/gui/MainWindow.fxml").openStream());
			MainWindowController mwc=fxl.getController();
			MySokobanModel m=new MySokobanModel(new MySokobanPolicy());
			Controller c=new MySokobanController(m, mwc);
			m.addObserver(c);
			mwc.addObserver(c);

			Scene scene = new Scene(root,600,600);
			scene.getStylesheets().add(getClass().getResource("/view/gui/application.css").toExternalForm());
			primaryStage.setTitle("Sokoban");
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setOnCloseRequest((e)->mwc.stop());
		} catch(Exception e) {
			System.out.println("Run.start: "+e.getMessage());
			e.printStackTrace();
		}
	}
}