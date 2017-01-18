package level_objects;

import java.io.Serializable;

public class Box extends GameObject implements Serializable {

	private static final long serialVersionUID = 1L;
	public Box() {}
	@Override
	public String toString()
	{
		return "@";
	}
}
