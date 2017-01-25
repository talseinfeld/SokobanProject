package commands;

import java.util.LinkedList;
/**
 * @author Tal Sheinfeld
 * I've used Strategy design pattern in all of the ConcreteCommands, so later
 * when we'd like to use a different command (e.g: GUIDisplayer, SafeExitor, etc..)
 * it will be really convenient.
 *
 */
public interface Command {
	public void execute() throws Exception;
	public void setParams(LinkedList<String> params);
}
