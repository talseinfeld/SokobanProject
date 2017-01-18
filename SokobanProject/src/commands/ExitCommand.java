package commands;

import java.io.IOException;

public class ExitCommand implements Command {
	private Exit exitor = null;
	public ExitCommand(Exit exitor) {
		this.exitor = exitor;
	}
	@Override
	public void execute() throws IOException {
		exitor.exit();

	}
}
