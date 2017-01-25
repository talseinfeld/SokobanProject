package view;

import java.io.BufferedReader;
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
	//TODO - check why won't catch here
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) throws Exception  {
		serverInFromClient = (new BufferedReader(new InputStreamReader(inFromClient)));
		serverOutToClient= new PrintWriter(outToClient);
		while(!isStopped) {
				//TODO - send the client commands menu
				serverOutToClient.println("Enter command: ");
				serverOutToClient.flush();
				String[] commandArr = serverInFromClient.readLine().split(" ");
				LinkedList<String> params = new LinkedList<String>();
				for (String s: commandArr) 
					params.add(s);
				//Checking if client wants to disconnect
				if (params.getFirst().equals("exit")) {
					serverOutToClient.write("bye");
					serverOutToClient.println();
					serverOutToClient.flush();
					setChanged();
					notifyObservers(params);
				}else {
					setChanged();
					notifyObservers(params);
				}
		}
		this.serverInFromClient.close();
		this.serverOutToClient.close();
	}

	@Override
	public void stop() {
		isStopped = true;		
	}

	@Override
	public void displayLevel(Level level) {
		try {
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
		catch (Exception e) {
			serverOutToClient.println(e.getMessage());
			serverOutToClient.flush();
		}
	}

	@Override
	public void displayWin() {
		serverOutToClient.write("Congratualtions! You've Won!\n");
		serverOutToClient.flush();
		
	}

	@Override
	public void displayError(String error) {
		serverOutToClient.write("Error: "+error);
		serverOutToClient.println();
		serverOutToClient.flush();
	}
	

}
