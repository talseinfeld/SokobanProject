package com.milestone_5.SokobanServer.model;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class AdminModel extends Observable {

	private Map<String, Socket> connectedClients =
			new HashMap<String, Socket>();
	private static int clientCount = 0;
	private static final AdminModel instance = new AdminModel();

	private AdminModel() {}

	public static AdminModel getInstance() {
		return instance;
	}

	public void addClient(Socket socket) {
		if (!socket.isClosed()) {
			clientCount++;
			String userName = "Client_"+clientCount;
			connectedClients.put(userName, socket);
			setChanged();
			List<String> params = new LinkedList<String>();
			params.add("Add");
			params.add(userName);
			notifyObservers(params);
		}
	}

	public void removeClient(String userName) {
		connectedClients.remove(userName);
		clientCount--;
	}

	public void disconnectClient(String userName) {
		Socket socket = connectedClients.get(userName);
		try {
			socket.close();
			connectedClients.remove(userName);
			setChanged();
			List<String> params = new LinkedList<String>();
			params.add("Remove");
			params.add(userName);
			notifyObservers(params);
		} catch (IOException e) {}
	}
}
