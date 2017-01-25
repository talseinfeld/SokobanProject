package view;

/**
 * 
 * Our Command Line Interface:
 * Here we will show the user a simple console menu with a detailed explanation.
 * We assume the user will use a keyboard to type a desired textual command from the set:
 * load or save followed by the file path, move {up,down,left,right}, display, exit.
 */
public class CLI { 
	
	/*
	private Command command = null;
	private Level level = new Level();
	private String[] commandGiven;
	//private String filePath;

	
	public CLI() {
		this.help();
		this.start();
	}
	public void start() {
		//TODO - run setCommand as a thread
		do {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.nextLine();
		try {
			this.setCommand(choice);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		} while(true);
	}
	public void setCommand(String args) throws IOException, InterruptedException {
		
		this.commandGiven = args.split(" ");
		this.commandGiven[0] = this.commandGiven[0].toLowerCase(Locale.ROOT);
		try {
		//if (this.commandGiven[0].equals("load") || this.commandGiven[0].equals("save"))
	//		this.filePath = this.commandGiven[1];
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		switch (this.commandGiven[0]) {
		
		case "display":
			//this.command = new DisplayCommand(this.level, new ConsoleDisplayer());
			this.command.execute();
			break;
		case "exit":
			System.out.print("Exiting");
			for (int i=0;i<3;++i) {
				System.out.print(".");
				TimeUnit.SECONDS.sleep(i);
			}
			//this.command = new SafeExitCommand(new ConsoleExitor());
			this.command.execute();
			break;
		case "move":
			try {
				this.commandGiven[1]=this.commandGiven[1].toUpperCase();
				//this.command = new MoveCommand(this.level, new MySokobanPolicy(), Direction.valueOf(this.commandGiven[1]));
				this.command.execute();
				//showing level after movement
				//this.command = new DisplayCommand(this.level, new ConsoleDisplayer());
				this.command.execute();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
				
				break;	
		case "load":
			try {
				//this.command = new LoadCommand(this.filePath);
				this.command.execute();
			//	this.level = ((LoadCommand) this.command).getLevel();
				//if (this.level.isWin())
				//{
					//System.out.println("You've Won!");
				//}
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "save":
			try {
			//this.command = new SaveCommand(this.filePath);
			//((SaveCommand) (this.command)).setLevel(this.level);
			this.command.execute();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "help":
			this.help();
			break;
		default:
			System.out.println("Please enter a command from the menu,"
					+" according to the instructions given.");
			System.out.println("Type Help to get the help menu.");
			break; 
		}

	}
	//Printing a help menu to the user
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

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public String[] getCommandGiven() {
		return commandGiven;
	}

	public void setCommandGiven(String[] commandGiven) {
		this.commandGiven = commandGiven;
	}
*/	
}
