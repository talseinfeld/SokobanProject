package commands;

import java.io.IOException;
import java.util.ArrayList;

import level_objects.Square;
import levels.Level;
/**
 * 
 * Printing the current level to the console using ASCII to represent the game objects.
 *
 */
public class ConsoleDisplayer implements Displayer {
	public ConsoleDisplayer() {}
	
	@Override
	public void display(Level level) throws IOException {
		if (level != null && level.getSquares() != null) {
			for (ArrayList<Square> squaresList : level.getSquares()) {
				for (Square square: squaresList)
				{
					System.out.print(square.toString());
				}
				System.out.println();
			}
		}
		else throw new IOException("ConsoleDisplayer error: Level is empty.");
	}
}
