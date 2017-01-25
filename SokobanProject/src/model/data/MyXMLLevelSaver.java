package model.data;

import java.beans.XMLEncoder;
import java.io.IOException;
import java.io.OutputStream;

public class MyXMLLevelSaver implements LevelSaver {

	@Override
	public void saveLevel(Level level, OutputStream out) throws IOException {
		if (level == null || level.getSquares() == null)
			throw (new IOException("MyXMLLevelSaver error: Couldn't save level - Empty level. Exisiting."));
		XMLEncoder e = new XMLEncoder(out);
		e.writeObject(level);
		e.close();
	}
}
