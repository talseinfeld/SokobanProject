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
/**
 * 
 * @author Tal Sheinfeld
 * Our server's protocol. This is how our Sokoban server will handle it's client.
 * Very simple TCP/IP on console while running the server as a thread.
 *
 */
public class MySokobanClientHandler extends Observable implements ClientHandler, View {

	private BufferedReader serverInFromClient = null;
	private PrintWriter serverOutToClient = null;
	private boolean stop = false;
	
	public MySokobanClientHandler() {}
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {
		serverInFromClient = (new BufferedReader(new InputStreamReader(inFromClient)));
		serverOutToClient= new PrintWriter(outToClient);
		//writing the help menu to client
		help();
		while(!stop) {
			try {
				serverOutToClient.println("Enter command: ");
				serverOutToClient.flush();
				String[] commandArr = serverInFromClient.readLine().split(" ");
				//stringCmdToLowerCase(commandArr);
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
			catch (Exception e) {
				System.out.println("MySokobanClientHandler: Client disconnected. "+e.getMessage());
			}
		} 
	}
	//changing command given by user to lower case (not touching file path)
public void stringCmdToLowerCase(String[] cmd) {
		if (cmd[0]!=null)
			cmd[0].toLowerCase();
		if (cmd[0].equals("move") && cmd[1]!=null)
			cmd[1].toLowerCase();
	}
	//Help menu for client's console
	//TODO - change help menu to be a HelpCommand that gets an OutputStream to write to
	public void help() {
		if (serverOutToClient!=null) {
			serverOutToClient.println("==================================");
			serverOutToClient.flush();
			serverOutToClient.println("|      Sokoban Console Menu       |");
			serverOutToClient.flush();
			serverOutToClient.println("==================================");
			serverOutToClient.flush();
			serverOutToClient.println("|            Options:             |");
			serverOutToClient.flush();
			serverOutToClient.println("|>Load <file path>                |");
			serverOutToClient.flush();
			serverOutToClient.println("|   e.g: Load C:/level1.obj       |");
			serverOutToClient.flush();
			serverOutToClient.println("|>Save <file path>                |");
			serverOutToClient.flush();
			serverOutToClient.println("|   e.g: Save level.xml           |");
			serverOutToClient.flush();
			serverOutToClient.println("|>Display                         |");
			serverOutToClient.flush();
			serverOutToClient.println("|>Exit                            |");
			serverOutToClient.flush();
			serverOutToClient.println("==================================");
			serverOutToClient.flush();
			
		}
	}

	@Override
	public void stop() {
		stop = true;		
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
	public void displayError(Exception e) {
		serverOutToClient.write("Error: "+e.getMessage());
		serverOutToClient.println();
		serverOutToClient.flush();
	}
	@Override
	public void usernameDialog() {
		// TODO ASCII username winning dialog 
		
	}
	@Override
	public void setLevelName(String levelName) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void solve() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hint() {
		// TODO Auto-generated method stub
		
	}
	
}
