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
	private boolean isStopped = false;
	
	public MySokobanClientHandler() {}
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) throws Exception  {
		serverInFromClient = (new BufferedReader(new InputStreamReader(inFromClient)));
		serverOutToClient= new PrintWriter(outToClient);
		//writing the help menu to client
		help();
		while(!isStopped) {
				serverOutToClient.println("Enter command: ");
				serverOutToClient.flush();
				String[] commandArr = serverInFromClient.readLine().split(" ");
				stringCmdToLowerCase(commandArr);
				LinkedList<String> params = new LinkedList<String>();
				for (String s: commandArr) 
					params.add(s);
				//writing the help menu to client if requested
				if (params.getFirst().equals("help"))
					help();
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
			serverOutToClient.write("==================================");
			serverOutToClient.flush();
			serverOutToClient.write("|      Sokoban Console Menu       |");
			serverOutToClient.flush();
			serverOutToClient.write("==================================");
			serverOutToClient.flush();
			serverOutToClient.write("|            Options:             |");
			serverOutToClient.flush();
			serverOutToClient.write("|>Load <file path>                |");
			serverOutToClient.flush();
			serverOutToClient.write("|   e.g: Load C:/level1.obj       |");
			serverOutToClient.flush();
			serverOutToClient.write("|>Save <file path>                |");
			serverOutToClient.flush();
			serverOutToClient.write("|   e.g: Save level.xml           |");
			serverOutToClient.flush();
			serverOutToClient.write("|>Display                         |");
			serverOutToClient.flush();
			serverOutToClient.write("|>Exit                            |");
			serverOutToClient.flush();
			serverOutToClient.write("|>Help (Will bring up this menu)  |");
			serverOutToClient.flush();
			serverOutToClient.write("==================================");
			serverOutToClient.flush();
		}
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
