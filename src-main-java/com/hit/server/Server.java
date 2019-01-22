
package com.hit.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import com.hit.services.CacheUnitController;


@SuppressWarnings("deprecation")
public class Server implements Observer, Runnable {

	ServerSocket server;
	
	boolean isServerUp;
	
	CacheUnitController cacheUnitController;
	
	Server() throws IOException {
		this.isServerUp = false;
		this.cacheUnitController = new CacheUnitController<String>();
	}

	@Override
	public void update(Observable o, Object arg) {
		String argString = (String)arg;
		
		if (argString.equals("start")) {
			if (this.isServerUp) {
				System.out.println("Server is alerdy up!");
			}
			else {
				// we want this.start() but in a different thread so:
				new Thread(this).start();
			}
		} else if (argString.equals("shutdown")) {
			if (this.isServerUp) {
				this.stop();
			} else {
				System.out.println("Server is alredy down!");
			}
		}
	}

	@Override
	public void run() {
		this.start();
	}	
	
	private void start() {

		this.isServerUp = true;

		try {
			this.server = new ServerSocket(12345);
			
			while (this.isServerUp) {
				Socket socket = this.server.accept();
				new Thread(new HandleRequest<String>(socket, this.cacheUnitController)).start();
			}
			
		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void stop() {
		try {
			this.isServerUp = false;
			this.server.close();
			System.out.println("Server is down");
			System.out.println("Exiting Program");
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
}
