package level_objects;

import java.io.Serializable;

public class Wall extends GameObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Wall() {}
	
	@Override
	public String toString()
	{
		return "#";
	}
}
