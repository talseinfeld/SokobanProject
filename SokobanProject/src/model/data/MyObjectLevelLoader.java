package levels;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class MyObjectLevelLoader implements LevelLoader {

	@Override
	public Level loadLevel(InputStream in) throws IOException, ClassNotFoundException {
		Level level =  (Level) new ObjectInputStream(in).readObject();
		if (level == null || level.getSquares()==null)
		{
			throw (new IOException("ObjectLevelLoader error: Couldn't load Level object, level is empty."));
		}
		else 
			{
				return level;
			}
	}

}
