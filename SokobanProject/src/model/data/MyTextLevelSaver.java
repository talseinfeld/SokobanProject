package model.data;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MyTextLevelSaver implements LevelSaver {

	@Override
	public void saveLevel(Level level, OutputStream out) throws IOException {
		//Checks if current level is empty or has no 'Square' types
		if (level == null || level.getSquares() == null)
			throw (new IOException("MyTextLevelSaver error: Couldn't save level - Empty level. Exisiting."));
		//TODO - check if can be changed
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out)));
		for (ArrayList<Square> squaresList : level.getSquares()) {
			for (Square square: squaresList)
			{
				writer.print(square.toString());
			}
			writer.println();
		}
		writer.close();	
	}
}
