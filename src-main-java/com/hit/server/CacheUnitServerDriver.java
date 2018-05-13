/**
 * Class 7 - Main. this is a template code given to us
 **/
package com.hit.server;

import com.hit.util.CLI;

public class CacheUnitServerDriver {

	public CacheUnitServerDriver() {
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		CLI cli = new CLI(System.in, System.out);
		Server server = new Server();
		cli.addObserver(server);
		new Thread(cli).start();
	}
}
