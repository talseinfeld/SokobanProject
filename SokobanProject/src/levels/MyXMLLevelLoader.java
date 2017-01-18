package levels;

import java.beans.XMLDecoder;
import java.io.IOException;
import java.io.InputStream;

public class MyXMLLevelLoader implements LevelLoader {

	@SuppressWarnings("resource")
	@Override
	public Level loadLevel(InputStream in) throws IOException {
		Level level = ((Level) new XMLDecoder(in).readObject());
		if (level == null || level.getSquares() == null)
			throw (new IOException("Couldn't read from XML, level is empty. Exiting")); 
		return level;
	}

}
