package controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;

import commands.Command;
import commands.DisplayCommand;
import commands.LoadCommand;
import commands.MoveCommand;
import commands.SafeExitCommand;
import commands.SaveCommand;
import commands.WinCommand;
import controller.server.Server;
import model.Model;
import view.View;

public class MySokobanController implements Controller {

	private Server server;
	private Model model;
	private View view;
	private Controller controller;
	private CommandController cc;
	private HashMap<String, Command> commands;
	
	public MySokobanController(Model m, View v) {
		
		this.controller = this;
		this.model = m;
		this.view = v;
		this.setHashKeys();
		this.cc = new CommandController();
		this.cc.start();
	
	}

	public MySokobanController(Model m, View v, Server s) {
		
		this.controller = this;
		this.model = m;
		this.view = v;
		this.server = s;
		this.setHashKeys();
		this.cc = new CommandController();
		this.cc.start();
		
	}
	protected void setHashKeys() {
		
		this.commands = new HashMap<String, Command>();
		this.commands.put("move", new MoveCommand(this.model, this.view));
		this.commands.put("display", new DisplayCommand(this.model, this.view));
		this.commands.put("load", new LoadCommand(this.model, this.view));
		this.commands.put("save", new SaveCommand(this.model, this.view));
		this.commands.put("exit", new SafeExitCommand(controller));
		this.commands.put("win", new WinCommand(this.model, this.view));
	}
	
	@Override
	public CommandController getCommandController() {
		return cc;
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		@SuppressWarnings("unchecked")
		LinkedList<String> params = (LinkedList<String>) arg1;
		String commandKey = params.removeFirst();
		Command c = commands.get(commandKey);
		if (c == null) {
			view.displayError("Command " + commandKey + " not found");
			return;
		}
		c.setParams(params);
		cc.insertCommand(c);
	}
	@Override
	public void startTheServer() {
		this.server.start();
	}
	@Override
	public void stopTheServer() {
		this.server.stop();		
	}
	@Override
	public Server getServer() {
		return this.server;
	}

}
