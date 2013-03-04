package org.rs2server.util;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import org.rs2server.rs2.model.Location;
import org.rs2server.rs2.model.Mob;

public class Misc {
	
	

	public static byte directionDeltaX[] = new byte[] { 0, 1, 1, 1, 0, -1, -1,
			-1 };

	public static byte directionDeltaY[] = new byte[] { 1, 1, 0, -1, -1, -1, 0,
			1 };
	
	public static int firstDigit(int n) {
		  while (n < -9 || 9 < n) n /= 10;
		  return Math.abs(n);
		}

	public static int booleanToInt(boolean b) {
		return b ? 1 : 0;
	}

	public static int calcJaghash(String n) {
		int count = 0;
		byte[] characters = n.getBytes();
		for (int i = 0; i < n.length(); i++) {
			count = (characters[i] & 0xff) + ((count << -1325077051) + -count);
		}
		return count;
	}

	public static int direction(int srcX, int srcY, int destX, int destY) {
		int dx = destX - srcX, dy = destY - srcY;

		if (dx < 0) {
			if (dy < 0) {
				if (dx < dy) {
					return 11;
				} else if (dx > dy) {
					return 9;
				} else {
					return 10;
				}
			} else if (dy > 0) {
				if (-dx < dy) {
					return 15;
				} else if (-dx > dy) {
					return 13;
				} else {
					return 14;
				}
			} else {
				return 12;
			}
		} else if (dx > 0) {
			if (dy < 0) {
				if (dx < -dy) {
					return 7;
				} else if (dx > -dy) {
					return 5;
				} else {
					return 6;
				}
			} else if (dy > 0) {
				if (dx < dy) {
					return 1;
				} else if (dx > dy) {
					return 3;
				} else {
					return 2;
				}
			} else {
				return 4;
			}
		} else {
			if (dy < 0) {
				return 8;
			} else if (dy > 0) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	/**
	 * @return Returns the distance between two positions.
	 */
	public static int getDistance(int coordX1, int coordY1, int coordX2,
			int coordY2) {
		int deltaX = coordX2 - coordX1;
		int deltaY = coordY2 - coordY1;
		return ((int) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)));
	}

	public static int getDistance(Location loc1, Location loc2) {
		int deltaX = loc2.getX() - loc1.getX();
		int deltaY = loc2.getY() - loc1.getY();
		return ((int) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)));
	}

	public static boolean intToBoolean(int i) {
		return i > 0 ? true : false;
	}

	public static boolean isClose(Mob mob1, Mob mob2) {
		return getDistance(mob1.getLocation().getX(),
				mob1.getLocation().getY(), mob2.getLocation().getX(), mob2
						.getLocation().getY()) <= 1;
	}

	public static int random(int range) {
		return (int) (Math.random() * (range + 1));
	}

	/**
	 * Returns a random integer with min as the inclusive lower bound and max as
	 * the exclusive upper bound.
	 * 
	 * @param min
	 *            The inclusive lower bound.
	 * @param max
	 *            The exclusive upper bound.
	 * @return Random integer min <= n < max.
	 */
	public static int random(int min, int max) {
		Random random = new Random();
		int n = Math.abs(max - min);
		return Math.min(min, max) + (n == 0 ? 0 : random.nextInt(n));
	}

	public static int random2(int range) {
		return (int) Math.ceil(Math.random() * range);
	}

	public static int randomIntFromArray(int[] list) {
		int randomInt = 0;
		Random random = new Random();
		try {
			int randomIndex = random.nextInt(list.length);
			randomInt = list[randomIndex];
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return randomInt;
	}

	public static int randomSlayer(int range) {
		return (int) (Math.random() * (range + 1)) == 0 ? (int) (Math.random() * (range + 1)) + 1
				: (int) (Math.random() * (range + 1) + 1);
	}

	public static String randomStringFromArray(String[] list) {
		String randomString = "";
		Random random = new Random();
		try {
			int randomIndex = random.nextInt(list.length);
			randomString = list[randomIndex];
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return randomString;
	}

	public static byte[] readFile(String s) throws IOException {
		File file = new File(s);
		int i = (int) file.length();
		byte abyte0[] = new byte[i];
		DataInputStream datainputstream = new DataInputStream(
				new BufferedInputStream(new FileInputStream(s)));
		try {
			datainputstream.readFully(abyte0, 0, i);
		} finally {
			datainputstream.close();
		}
		return abyte0;
	}

	public static int round(double x) {
		return (int) Math.round(x);
	}

	public static double round10(double x) {
		return Math.round(10 * x) / (double) 10;
	}

	public static double round100(double x) {
		return Math.round(100 * x) / (double) 100;
	}

	public static double round1000(double x) {
		return Math.round(1000 * x) / (double) 1000;
	}
}
