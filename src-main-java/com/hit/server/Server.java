/**
 * Class 2 
 * in the start() method we need to instantiate all server components
	and listen to requests
	and pass incoming requests to a new thread
	to the classes that are responsible for them (HandleRequest) (Controller class to pass to HandleRequest)
		
	we need to have HandleRequest object that will get the socket
	object and read from it the Request object
 * */
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
	
	Server() {
		this.isServerUp = false;
	}

	@Override
	public void update(Observable o, Object arg) {
		String argString = (String)arg;
		
		//TODO refactor the direct string
		if (argString.equals("start")) {
			if (this.isServerUp) {
				System.out.println("Server is alerdy up!");
			}
			else {
				// this.start();
				// we want start() in a different thread:
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
				new Thread(new HandleRequest<String>(socket, new CacheUnitController<String>())).start();
			}
		
			System.out.println("server is down");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void stop() {
		try {
			this.isServerUp = false;
			this.server.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
