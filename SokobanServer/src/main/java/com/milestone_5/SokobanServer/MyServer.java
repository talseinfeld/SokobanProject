package com.milestone_5.SokobanServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyServer {

	private int port;
	private ClientHandler ch;
	private boolean stop = false;
	private ExecutorService threadPool;
	private static final int THREADS_NUM = 30;

	public MyServer(int port, ClientHandler ch) {
		this.port = port;
		this.ch = ch;
		threadPool = Executors.newFixedThreadPool(THREADS_NUM);
	}

	private void runServer() throws Exception {
		ServerSocket server = new ServerSocket(port);
		server.setSoTimeout(10000);
		while(!stop) {
			try {
				Socket aClient = server.accept();
				threadPool.execute(new Runnable() {
					@Override
					public void run() {
						try {
							ch.handleClient(aClient, aClient.getInputStream(), aClient.getOutputStream());
						} catch (IOException e) {
							// client disconnected
							// TODO - TAKE CARE OF CLIENT SOCKET IN YOUR PROTOCOL!!!
						}

					}
				});
			} catch (Exception e) {
				// timeout exception - no one connected
			}
		}
		server.close();
		System.out.println("Server closed.");
	}
	
	public void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					runServer();
				} catch (Exception e) {
					System.out.println("Server listening thread died.");
				}
			}
		}).start();
	}
	
	public void stop() {
		threadPool.shutdown();
		try {
			threadPool.awaitTermination(2, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.out.println("Closed all connections.");
		} finally {
			stop = true;
		}
	}
}
