package org.rs2server;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.rs2server.rs2.RS2Server;
import org.rs2server.rs2.model.World;

/**
 * A class to start both the file and game servers.
 * 
 * @author Graham Edgecombe
 * @author Josh Mai
 * 
 */
public class Server {

	/**
	 * The protocol version.
	 */
	public static final int VERSION = 474;
	/**
	 * Logger instance.
	 */
	private static final Logger logger = Logger.getLogger(Server.class
			.getName());
	public static boolean ready = false;

	/**
	 * The entry point of the application.
	 * 
	 * @param args
	 *            The command line arguments.
	 */
	public static void main(String[] args) {
		logger.info("Initializing Hyperion...");
		World.getWorld(); // this starts off background loading
		try {
			while (!ready) {
				Thread.sleep(1000);
			}
			new RS2Server().bind(RS2Server.PORT).start();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Error starting Hyperion.", ex);
			// System.exit(1);
		}
	}
}
