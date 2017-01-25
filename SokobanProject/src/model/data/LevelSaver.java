package model.data;

import java.io.OutputStream;

public interface LevelSaver {
	public void saveLevel(Level level, OutputStream out) throws Exception;
}
