package controller;

import java.util.Observer;

import controller.server.Server;

public interface Controller extends Observer {

	public CommandController getCommandController();
	public Server getServer();
	public void startTheServer();
	public void stopTheServer();
	
	
}
