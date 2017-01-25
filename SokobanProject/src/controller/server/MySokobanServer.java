package controller.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author Tal Sheinfeld
 * Our SokobanServer class.
 * Because we segregated the protocol (ClientHandler) and the server, technically it will be wiser for the 
 * ClientHandler to extend Observable. But - I don't know if each client handler I'll have in the future, will
 * be an Observable type, therefore the ClientHandler interface will be our facade for our protocols.
 * Our specific SokobanClientHandler will extend an Observable type, and will transfer the client's commands to
 * our Controller.
 *
 */
public class MySokobanServer implements Server {

	private int port;
	private ClientHandler ch;
	private volatile boolean stop;
	
	public MySokobanServer(int port, ClientHandler ch) {
		
		this.port = port;
		this.ch = ch;
		stop = false;
	}

	private void runServer() throws Exception {
		ServerSocket server=new ServerSocket(port, 1);
		System.out.println("Server is alive and listenig.");
		server.setSoTimeout(10000);
		while(!stop){
			try{
				Socket aClient=server.accept(); // blocking call
				System.out.println("User connected.");
				ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
				aClient.getInputStream().close();
				aClient.getOutputStream().close();
				aClient.close();
			}
			catch (IOException e) {
				System.out.println("MySokobanServer: "+e.getMessage());
			}
		}
		try {
			System.out.println("Server is shutting down...");
			server.close();
			System.out.println("Server is offline.");
		}
		catch (Exception e) {
			System.out.println("Error in closing the server: "+e.getMessage());
		}
	}

	@Override
	public void start() {
		new Thread(new Runnable() {
			public void run() {
				try{
					runServer();
				}
				catch (Exception e) {
					System.out.println("Server thread closed.");
				}
			}
		}).start();
	}
	@Override
	public void stop() {
		this.stop = true;
	}
	@Override
	public ClientHandler getClientHandler() {
		return ch;
	} 
}
