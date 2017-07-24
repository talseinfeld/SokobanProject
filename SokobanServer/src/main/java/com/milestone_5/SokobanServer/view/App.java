package com.milestone_5.SokobanServer.view;

import com.milestone_5.SokobanServer.MyServer;
import com.milestone_5.SokobanServer.SokobanClientHandler;
import com.milestone_5.SokobanServer.model.AdminModel;
import com.milestone_5.SokobanServer.view_model.DashboardViewModel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	final static MyServer server = new MyServer(5555, new SokobanClientHandler());
	public static void main( String[] args )
	{
		try {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						server.start();
					} catch (Exception e) {}
				}
			}).start();
		} catch (Exception e) {}

		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		AdminModel model = AdminModel.getInstance();
		DashboardViewModel vm = new DashboardViewModel(model);
		model.addObserver(vm);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboard.fxml"));

		Parent root = (Parent) loader.load();	
		DashboardController controller = loader.getController();
		controller.setViewModel(vm);

		Scene scene = new Scene(root, 180, 325);

		stage.setTitle("Admin Dashboard");
		stage.setScene(scene);
		stage.show();
		stage.setOnCloseRequest((e)-> { server.stop(); });
	}

}
