package commands;

import java.util.LinkedList;

import model.Model;
import view.View;

public abstract class SokobanCommand implements Command {
	
	protected Model model;
	protected View view;
	protected LinkedList<String> params;
	
	@Override
	public void setParams(LinkedList<String> params) {
		this.params = params;
	}

}
