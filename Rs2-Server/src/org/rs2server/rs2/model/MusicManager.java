/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rs2server.rs2.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.rs2server.util.XMLController;

/**
 * 
 * @author Stephen Soltys
 */
public class MusicManager {

	private static final Logger logger = Logger.getLogger(Door.class.getName());
	private static List<MusicTrack> tracks;

	public static List<MusicTrack> getTracks() {
		return tracks;
	}

	public static void init() {
		logger.info("Loading music...");
		try {
			/**
			 * Load music
			 */
			File file = new File("data/music.xml");
			tracks = new ArrayList<MusicTrack>();

			if (file.exists()) {
				tracks = XMLController.readXML(file);
				logger.info("Loaded " + tracks.size() + " music tracks.");
			} else {
				logger.info("Music not found!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
