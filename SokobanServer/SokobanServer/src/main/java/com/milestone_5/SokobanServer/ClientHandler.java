package com.milestone_5.SokobanServer;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public interface ClientHandler {

	public void handleClient(Socket clientSocket, InputStream inFromClient, OutputStream outToClient);
}
