
package com.hit.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Scanner;

@SuppressWarnings("deprecation")
public class CLI extends Observable implements Runnable {

	static final String CMD_START = "start";
	static final String CMD_SHUTDOWN = "shutdown";

	private Scanner in;
	private PrintWriter out;
	private boolean isCmdUp;
	
	
	public CLI(InputStream in, OutputStream out) {
		this.in = new Scanner(in);
		this.out = new PrintWriter(out);
		this.isCmdUp = true;
	}

	@Override
	public void run() {
		try {
			while (this.isCmdUp) {
				this.readInput();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readInput() throws IOException {
		this.write("Enter start / shutdown");
		String s = this.in.next().toLowerCase();

		if (this.checkIfValidInput(s)) {
			this.setChanged();
			this.notifyObservers(s);
		} else {
			this.write("Invalid value...");
		}
	}

	public boolean checkIfValidInput(String inputString) {
		boolean isValid = false;
		
		switch (inputString) {
		case CMD_START:
			isValid = true;
			this.write("Starting the server...");
			break;
		case CMD_SHUTDOWN:
			this.write("Stopping the server...");
			isValid = true;
			this.isCmdUp = false;
			break;
		default:
			break;
		}
		
		return isValid;
	}

	public void write(String string) {
		out.println(string);
		out.flush();
	}
}
