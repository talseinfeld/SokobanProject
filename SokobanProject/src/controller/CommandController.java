package controller;

import java.io.IOException;
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
	
	public void insertCommand(Command command) throws InterruptedException {
		
		this.cQueue.put(command);
	}
	
	public void start() {
		
		Thread thread = new Thread(new Runnable() {
			
			public void run() {
				while(!isStopped) {
					try {
						Command c = cQueue.poll(5, TimeUnit.SECONDS);
						if (c!=null)
							c.execute();
					} catch (InterruptedException | IOException e) {
						System.out.println(e.getMessage());
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
