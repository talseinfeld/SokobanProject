package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import model.data.Level;

public class Client {
	
	private String solution;
	
	public Client() {}
	
	public void start(String ip, int port, Level game) {
		try {			
			Socket socket = new Socket(ip, port);
			System.out.println("connected to server");

			BufferedReader serverInput = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			ObjectOutputStream outToServer = new ObjectOutputStream(
					socket.getOutputStream());

			outToServer.writeObject(game);
			outToServer.flush();
			String solution = serverInput.readLine();
			this.setSolution(solution);
			System.out.println("Solution received from server: " + solution);

			serverInput.close();
			outToServer.close();
			socket.close();
		} 
		catch (UnknownHostException e) {}
		catch (IOException e) {}
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}
}
