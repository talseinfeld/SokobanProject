package commands;
/**
 * 
 * In this Milestone, we just close the running CLI thread using this class.
 *
 */
public class ConsoleExitor implements Exit {

	
	@Override
	public void exit() {
		System.exit(0);

	}

}
