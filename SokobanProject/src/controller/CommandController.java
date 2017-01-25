package controller;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import commands.Command;

public class CommandController {

	private BlockingQueue<Command> cQueue;
	private Boolean isStopped = false;
	
	public CommandController() {
		this.cQueue = new ArrayBlockingQueue<Command>(10);
	}
	
	public void insertCommand(Command command)  {
		try {
			this.cQueue.put(command);
		}
		catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void start() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!isStopped) {
					try {
						Command cmd = cQueue.poll(1, TimeUnit.SECONDS);
						if (cmd != null)
							cmd.execute();
					} catch (Exception e) {
						System.out.println("CommandController exception: "+e.getMessage());
					}
				}
				
			}
		});
		thread.start();
	}

	public void stop() {
		this.isStopped = true;
	}
}
