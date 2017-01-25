package commands;

import java.io.IOException;
import java.util.LinkedList;
/**
 * 
 * I've used Strategy design pattern in all of the ConcreteCommands, so later
 * when we'd like to use a different command (e.g: GUIDisplayer, SafeExitor, etc..)
 * it will be really convenient.
 *
 */
public interface Command {
	public void execute() throws IOException;
	public void setParams(LinkedList<String> params);
}
