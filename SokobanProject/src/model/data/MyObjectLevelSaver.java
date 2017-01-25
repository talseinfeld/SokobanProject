package levels;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MyObjectLevelSaver implements LevelSaver {

	@Override
	public void saveLevel(Level level, OutputStream out) throws IOException {
		if (level == null || level.getSquares() == null)
			throw (new IOException("MyObjectLevelSaver error: Couldn't save level - Empty level. Exisiting."));
		ObjectOutputStream writer = new ObjectOutputStream(out);
		writer.writeObject(level);
		writer.close();
		System.out.println("Level saved as object succesfully.");
	}

}
