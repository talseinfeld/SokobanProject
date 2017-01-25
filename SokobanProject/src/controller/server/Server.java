package controller.server;

public interface Server {
	
	public void start();
	public void stop();
	public ClientHandler getClientHandler();
	
}
