package model.data;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;



//Returns a level from a text file input stream//
public class MyTextLevelLoader implements LevelLoader {

	@Override
	public Level loadLevel(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		ArrayList<ArrayList<Square>> squares = new ArrayList<>();
		squares.add(new ArrayList<Square>());
		/*coordinates for the game*/
		int y=0,x=0;
		int c;//flag and a variable for casting		
		while ((c=reader.read()) != -1) //while it's not the end of the text
		{
			char ch = (char)c; //casting for previous 'c' mount in the loop
			switch (ch) {
			case ' ':
				squares.get(y).add(x,new Square(new Point(x,y)));
				x++;
				break;
			case '#':
				squares.get(y).add(x, new Square(new Point(x,y),new Wall()));
				x++;
				break;
			case '@':
				squares.get(y).add(x, new Square(new Point(x,y), new Box()));
				x++;
				break;
			case 'A':
				squares.get(y).add(x, new Square(new Point(x,y),new Character()));
				x++;
				break;
			case 'o':
				squares.get(y).add(x, new GoalSquare(new Point(x,y)));
				x++;
				break;
			case '\n':
			//case '\r':
				y++;
				x=0;
				squares.add(new ArrayList<Square>());
				break;
			default:
				break;
			}
		}  
		return new Level(squares);
		}
	}

