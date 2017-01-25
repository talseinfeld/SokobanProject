package boot;

import controller.Controller;
import controller.MySokobanController;
import controller.server.MySokobanServer;
import model.MySokobanModel;
import model.policy.MySokobanPolicy;
import view.MySokobanClientHandler;
/**
 * 
 * @author Tal Sheinfeld
 *
 */
public class Run {

	public static void main(String[] args) {
		MySokobanClientHandler ch = new MySokobanClientHandler();
		MySokobanServer server = new MySokobanServer(55555, ch);
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
}
