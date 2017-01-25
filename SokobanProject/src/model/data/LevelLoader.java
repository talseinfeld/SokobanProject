package model.data;

import java.io.InputStream;
/**<h3><font color="blue">Answers to the Serialization questions:</font></h3></br>
 * A: The "Creator" is our LevelLoader (any kind of it). After our creator creates a level from a given path, it sends our Level class that data. Our level class only "holds" this data.</br></br>
 * B: If in the future we would like to add a different loader for different files (.bat, .exe, etc) we just need to override this interface' method and not change any existing code.</br></br>
 * C: It lets us use a Polymorphic approach, we can always promise that each class we have or will have with a member of a LoadLevel - we can use it and its method purpose is known to us.</br></br>
 * D: Because we want to use Liskov's Substitution Principle, as we detailed in C and also to use all the implementation of the Java libraries that implemented InputStream.
 */
public interface LevelLoader {
	public Level loadLevel(InputStream in) throws Exception;
}
