package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;

import controller.server.ClientHandler;
import model.data.Level;
import model.data.Square;

public class MySokobanClientHandler extends Observable implements ClientHandler, View {

	private BufferedReader serverInFromClient = null;
	private PrintWriter serverOutToClient = null;
	private boolean isStopped = false;
	
	public MySokobanClientHandler() {}
	
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) throws IOException {
		
		serverInFromClient = new BufferedReader(new InputStreamReader(inFromClient));
		serverOutToClient= new PrintWriter(outToClient);
		
		//Avoiding NullPointerExceptions
		if (serverInFromClient == null || serverOutToClient == null) {
			
			serverInFromClient.close();
			serverOutToClient.close();
		}
		else {
			do {
				//TODO - send the client commands menu
				serverOutToClient.write("Enter command:");
				serverOutToClient.println();
				serverOutToClient.flush();
				//TODO - fix lowercase/uppercase
				String[] commandArr = serverInFromClient.readLine().split(" ");
				LinkedList<String> params = new LinkedList<String>();
				for (String s: commandArr)
					params.add(s);
				/* We'd like to end this loop
				 * and to send the controller "exit" string; So it
				 * will close all the existing threads safely.
				 */
				setChanged();
				notifyObservers(params);

			} while(!isStopped);
			//TODO - Check if needed to close these locals, while the server handles those references
			this.serverInFromClient.close();
			this.serverOutToClient.close();
		}
	}

	@Override
	public void stop() {
		isStopped = true;		
	}

	@Override
	public void displayLevel(Level level) throws IOException {
		
		if (level != null && level.getSquares() != null) {
			for (ArrayList<Square> squaresList : level.getSquares()) {
				for (Square square: squaresList)
				{
					serverOutToClient.write(square.toString());
					serverOutToClient.flush();
				}
				serverOutToClient.println();
				serverOutToClient.flush();
			}
			
		}
		else throw new IOException("MySokobanClientHandler error: Level is empty.");
	}

	@Override
	public void displayWin() {
		serverOutToClient.write("Congratualtions! You've Won!");
		serverOutToClient.flush();
		
	}

	@Override
	public void displayError(String error) {
		serverOutToClient.write("Error: "+error);
		serverOutToClient.flush();
	}
	

}
