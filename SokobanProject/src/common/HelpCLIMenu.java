package common;

public class HelpCLIMenu {

	public void help() {
		System.out.println("==================================");
		System.out.println("|      Sokoban Console Menu       |");
		System.out.println("==================================");
		System.out.println("|            Options:             |");
		System.out.println("|>Load <file path>                |");
		System.out.println("|   e.g: Load C:/level1.obj       |");
		System.out.println("|>Save <file path>             	  |");
		System.out.println("|   e.g: Save level.xml        	  |");
		System.out.println("|>Display                      	  |");
		System.out.println("|>Exit                            |");
		System.out.println("|>Help (Will bring up this menu)  |");
		System.out.println("==================================");
	}
}
