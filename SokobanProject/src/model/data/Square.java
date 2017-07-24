package model.data;

import java.awt.Point;
import java.io.Serializable;

/*Each square in the game and what it represents*/
public class Square implements Serializable{

	private static final long serialVersionUID = 1L;
	private GameObject go = null;
	protected Point position;
	
	public Square() {} //default C'tor
	public Square(Square square) {//Copy C'tor
		this.setGo(square.getGo());
		this.setPosition(square.getPosition());
	}
	
	//Convenient way to initialize each square through the loaders
	public Square(Point p,GameObject go)
	{
		this.position = p;
		this.setGo(go);
	}
	public Square(Point p)
	{
		this.position=p;
	}

	public GameObject getGo() {
		return go;
	}

	public void setGo(GameObject go) {
		this.go = go;
	}
	@Override
	public String toString()
	{
		if (go == null)
			return " ";
		return go.toString();
	}
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		this.position = position;
	}
	
	@Override
	public boolean equals(Object o) {
		Square s = (Square) o;
		return position.equals(s.position);
	}
	
	@Override
	public int hashCode() {
		return position.hashCode();
	}
	
}
