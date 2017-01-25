package controller.server;

import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {

	public void handleClient(InputStream inFromClient, OutputStream outToClient) throws Exception;
	public void stop();
}
