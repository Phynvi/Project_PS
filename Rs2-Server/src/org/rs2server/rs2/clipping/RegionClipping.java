package org.rs2server.rs2.clipping;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import org.rs2server.cache.map.LandscapeListener;
import org.rs2server.rs2.ScriptManager;
import org.rs2server.rs2.model.GameObject;
import org.rs2server.rs2.model.Location;

/**
 * 
 * @author Josh Mai
 * 
 */
public class RegionClipping {

	private static final Logger logger = Logger.getLogger(RegionClipping.class
			.getName());
	private static RegionClipping singleton = new RegionClipping();

	public static byte[] getBuffer(File f) throws Exception {
		if (!f.exists()) {
			return null;
		}
		byte[] buffer = new byte[(int) f.length()];
		DataInputStream dis = new DataInputStream(new FileInputStream(f));
		dis.readFully(buffer);
		dis.close();
		byte[] gzipInputBuffer = new byte[999999];
		int bufferlength = 0;
		GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(
				buffer));

		while (bufferlength != gzipInputBuffer.length) {
			int readByte = gzip.read(gzipInputBuffer, bufferlength,
					gzipInputBuffer.length - bufferlength);
			if (readByte == -1) {
				break;
			}
			bufferlength += readByte;
		}
		byte[] inflated = new byte[bufferlength];
		System.arraycopy(gzipInputBuffer, 0, inflated, 0, bufferlength);
		buffer = inflated;
		if (buffer.length < 10) {
			return null;
		}
		return buffer;
	}

	public static RegionClipping getRegionClipping() {
		if (singleton == null) {
			singleton = new RegionClipping();
		}
		return singleton;
	}

	private RegionClipping[] regions;
	private int id;

	private int[][][] clips = new int[4][][];

	private boolean members = false;

	public List<Integer> mapIndices = null;

	public Map<Integer, byte[]> mapBuffer = new HashMap<Integer, byte[]>();

	public RegionClipping() {
	}

	public RegionClipping(int id, boolean members) {
		this.id = id;
		this.members = members;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param height
	 * @param shift
	 */
	private void addClip(int x, int y, int height, int shift) {
		int regionAbsX = (this.id >> 8) * 64;
		int regionAbsY = (this.id & 0xFF) * 64;
		if (this.clips[height] == null) {
			this.clips[height] = new int[64][64];
		}
		if (x < regionAbsX || y < regionAbsY) {
			return;
		}
		this.clips[height][(x - regionAbsX)][(y - regionAbsY)] |= shift;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param height
	 * @param shift
	 */
	public void addClipping(int x, int y, int height, int shift) {
		int regionX = x >> 3;
		int regionY = y >> 3;
		int regionId = (regionX / 8 << 8) + regionY / 8;
		for (RegionClipping r : this.regions) {
			if (r.id() == regionId) {
				r.addClip(x, y, height, shift);
				break;
			}
		}
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param height
	 * @param xLength
	 * @param yLength
	 * @param flag
	 */
	public void addClippingForSolidObject(int x, int y, int height,
			int xLength, int yLength, boolean flag) {
		int clipping = 256;
		if (flag) {
			clipping += 131072;
		}
		for (int i = x; i < x + xLength; i++) {
			for (int i2 = y; i2 < y + yLength; i2++) {
				addClipping(i, i2, height, clipping);
			}
		}
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param height
	 * @param type
	 * @param direction
	 * @param flag
	 */
	public void addClippingForVariableObject(int x, int y, int height,
			int type, int direction, boolean flag) {
		if (type == 0) {
			if (direction == 0) {
				addClipping(x, y, height, 128);
				addClipping(x - 1, y, height, 8);
			} else if (direction == 1) {
				addClipping(x, y, height, 2);
				addClipping(x, y + 1, height, 32);
			} else if (direction == 2) {
				addClipping(x, y, height, 8);
				addClipping(x + 1, y, height, 128);
			} else if (direction == 3) {
				addClipping(x, y, height, 32);
				addClipping(x, y - 1, height, 2);
			}
		} else if ((type == 1) || (type == 3)) {
			if (direction == 0) {
				addClipping(x, y, height, 1);
				addClipping(x - 1, y, height, 16);
			} else if (direction == 1) {
				addClipping(x, y, height, 4);
				addClipping(x + 1, y + 1, height, 64);
			} else if (direction == 2) {
				addClipping(x, y, height, 16);
				addClipping(x + 1, y - 1, height, 1);
			} else if (direction == 3) {
				addClipping(x, y, height, 64);
				addClipping(x - 1, y - 1, height, 4);
			}
		} else if (type == 2) {
			if (direction == 0) {
				addClipping(x, y, height, 130);
				addClipping(x - 1, y, height, 8);
				addClipping(x, y + 1, height, 32);
			} else if (direction == 1) {
				addClipping(x, y, height, 10);
				addClipping(x, y + 1, height, 32);
				addClipping(x + 1, y, height, 128);
			} else if (direction == 2) {
				addClipping(x, y, height, 40);
				addClipping(x + 1, y, height, 128);
				addClipping(x, y - 1, height, 2);
			} else if (direction == 3) {
				addClipping(x, y, height, 160);
				addClipping(x, y - 1, height, 2);
				addClipping(x - 1, y, height, 8);
			}
		}
		if (flag) {
			if (type == 0) {
				if (direction == 0) {
					addClipping(x, y, height, 65536);
					addClipping(x - 1, y, height, 4096);
				} else if (direction == 1) {
					addClipping(x, y, height, 1024);
					addClipping(x, y + 1, height, 16384);
				} else if (direction == 2) {
					addClipping(x, y, height, 4096);
					addClipping(x + 1, y, height, 65536);
				} else if (direction == 3) {
					addClipping(x, y, height, 16384);
					addClipping(x, y - 1, height, 1024);
				}
			}
			if ((type == 1) || (type == 3)) {
				if (direction == 0) {
					addClipping(x, y, height, 512);
					addClipping(x - 1, y + 1, height, 8192);
				} else if (direction == 1) {
					addClipping(x, y, height, 2048);
					addClipping(x + 1, y + 1, height, 32768);
				} else if (direction == 2) {
					addClipping(x, y, height, 8192);
					addClipping(x + 1, y + 1, height, 512);
				} else if (direction == 3) {
					addClipping(x, y, height, 32768);
					addClipping(x - 1, y - 1, height, 2048);
				}
			} else if (type == 2) {
				if (direction == 0) {
					addClipping(x, y, height, 66560);
					addClipping(x - 1, y, height, 4096);
					addClipping(x, y + 1, height, 16384);
				} else if (direction == 1) {
					addClipping(x, y, height, 5120);
					addClipping(x, y + 1, height, 16384);
					addClipping(x + 1, y, height, 65536);
				} else if (direction == 2) {
					addClipping(x, y, height, 20480);
					addClipping(x + 1, y, height, 65536);
					addClipping(x, y - 1, height, 1024);
				} else if (direction == 3) {
					addClipping(x, y, height, 81920);
					addClipping(x, y - 1, height, 1024);
					addClipping(x - 1, y, height, 4096);
				}
			}
		}
	}

	/**
	 * 
	 */
	private void addFixes() {
		removeSquare(Location.create(2753, 3503, 2), 9);
		for (int y = 3503; y <= 3512; y++) {
			remove(2763, y, 2);
		}

		removeSquare(Location.create(3095, 3523, 0), 9);
		for (int x = 3041; x < 3329; x++) {
			addClippingForSolidObject(x, 3521, 0, 1, 2, true);
		}
		// addClippingForSolidObject(3104, 3498, 0, 5, 5, true);
		// addClippingForSolidObject(3093, 3525, 0, 2, 2, true);
		addClippingForSolidObject(3049, 3525, 0, 3, 3, true);
		addClippingForSolidObject(3104, 3494, 0, 2, 2, true);
		addClippingForSolidObject(3105, 3489, 0, 2, 2, true);
		for (int x = 3061; x <= 3066; x++) {
			remove(x, 3520, 0);
		}
		for (int x = 3095; x < 3066; x++) {
			remove(x, 3520, 0);
		}

		addClippingForSolidObject(2865, 5353, 2, 2, 2, true);

		remove(3103, 3493, 0);
		remove(3103, 3494, 0);
		remove(3104, 3493, 0);
		removeSquare(Location.create(3109, 3495, 0), 2);

		remove(3109, 3500, 0);
		remove(3109, 3501, 0);
		removeSquare(Location.create(3055, 3523, 0), 11);
		removeSquare(Location.create(3072, 3515, 0), 4);
		remove(3056, 3523, 0);
		remove(3098, 3523, 0);
		remove(3099, 3523, 0);
		removeSquare(Location.create(3095, 3523, 0), 2);
		removeSquare(Location.create(3045, 3523, 0), 2);
		removeSquare(Location.create(3090, 3503, 0), 2);
		remove(3099, 3497, 0);
		remove(3099, 3498, 0);
		remove(3098, 3497, 0);
		remove(3098, 3498, 0);
		addClippingForSolidObject(3095, 3488, 0, 4, 8, true);
		addClippingForSolidObject(3096, 3501, 0, 2, 2, true);

		/*
		 * Varrock
		 */
		remove(3205, 3432, 0);
		remove(3204, 3432, 0);

		/*
		 * Seers' Village
		 */
		remove(2713, 3483, 0);
		remove(2713, 3462, 0);
		remove(2701, 3477, 0);
		remove(2701, 3476, 0);
		remove(2716, 3472, 0);
		remove(2715, 3472, 0);
		
		/*
		 * Port Sarim
		 */
		remove(3016, 3246, 0);
		remove(3015, 3246, 0);
		remove(3012, 3239, 0);
		remove(3012, 3238, 0);

		/*
		 * Draynor
		 */
		remove(3088, 3250, 0);
		remove(3088, 3251, 0);
		remove(3102, 3258, 0);
		remove(3101, 3258, 0);
		remove(3089, 3258, 0);
		remove(3088, 3258, 0);
		remove(3098, 3271, 0);
		remove(3098, 3270, 0);
		remove(3100, 3276, 0);
		remove(3100, 3277, 0);
		remove(3092, 3269, 0);
		remove(3092, 3268, 0);
		remove(3128, 3247, 0);
		remove(3128, 3246, 0);
		/*
		 * Wizard Tower
		 */
		remove(3109, 3167, 0);
		remove(3109, 3166, 0);

		/*
		 * Lumbridge
		 */
		remove(3215, 3212, 0);
		remove(3215, 3211, 0);
		remove(3226, 3214, 0);
		remove(3227, 3214, 0);
		remove(3226, 3223, 0);
		remove(3227, 3223, 0);
		remove(3234, 3203, 0);
		remove(3233, 3203, 0);
		remove(3234, 3207, 0);
		remove(3233, 3207, 0);
		remove(3237, 3210, 0);
		remove(3238, 3210, 0);
		remove(3217, 3218, 0);
		remove(3216, 3218, 0);
		remove(3217, 3219, 0);
		remove(3216, 3219, 0);
		remove(3208, 3211, 0);
		remove(3208, 3212, 0);
		remove(3230, 3235, 0);
		remove(3230, 3236, 0);
		remove(3228, 3240, 0);
		remove(3229, 3240, 0);
		remove(3246, 3243, 0);
		remove(3246, 3244, 0);
		remove(3244, 3216, 0);
		remove(3244, 3215, 0);
		remove(3243, 3216, 0);
		remove(3243, 3215, 0);
		remove(3246, 3193, 0);
		remove(3247, 3193, 0);
		remove(3213, 3221, 0);
		remove(3212, 3221, 0);
		remove(3213, 3222, 0);
		remove(3212, 3222, 0);
		remove(3207, 3217, 0);
		remove(3207, 3218, 0);
		remove(3207, 3210, 1);
		remove(3208, 3210, 1);
		remove(3207, 3214, 1);
		remove(3208, 3214, 1);
		remove(3207, 3222, 1);
		remove(3208, 3222, 1);
		remove(3207, 3227, 1);
		remove(3208, 3227, 1);
		remove(3253, 3266, 0);
		remove(3252, 3266, 0);
		remove(3253, 3267, 0);
		remove(3252, 3267, 0);
		

		/*
		 * al kahlid
		 */

		remove(3318, 3193, 0);
		remove(3319, 3193, 0);
		remove(3320, 3193, 0);
		remove(3321, 3193, 0);

		remove(3272, 3166, 0);
		remove(3273, 3166, 0);
		remove(3273, 3167, 0);
		remove(3272, 3167, 0);

		remove(3276, 3180, 0);
		remove(3275, 3180, 0);
		remove(3280, 3185, 0);
		remove(3279, 3185, 0);
		remove(3278, 3191, 0);
		remove(3277, 3191, 0);
		remove(3317, 3193, 0);
		remove(3318, 3193, 0);

		/*
		 * Falador
		 */
		remove(2989, 3368, 0);
		remove(2989, 3367, 0);
		remove(3061, 3374, 0);
		remove(3061, 3375, 0);
		remove(2982, 3371, 0);
		remove(2982, 3370, 0);
		remove(2949, 3379, 0);
		remove(2948, 3379, 0);
		remove(2965, 3338, 0);
		remove(2964, 3338, 0);
		remove(2965, 3339, 0);
		remove(2964, 3339, 0);
		remove(2971, 3337, 0);
		remove(2971, 3336, 0);
		remove(2970, 3337, 0);
		remove(2970, 3336, 0);
		remove(2981, 3340, 0);
		remove(2982, 3340, 0);
		remove(2981, 3341, 0);
		remove(2982, 3341, 0);
		remove(2978, 3346, 0);
		remove(2978, 3347, 0);
		remove(2973, 3348, 0);
		remove(2973, 3349, 0);
		remove(2985, 3341, 0);
		remove(2986, 3341, 0);
		
		/*
		 * Taverly & Dorics Place
		 */
		remove(2949, 3450, 0);
		remove(2950, 3450, 0);
		remove(2936, 3451, 0);
		remove(2935, 3451, 0);
		remove(2936, 3450, 0);
		remove(2935, 3450, 0);
		
		/*
		 * Goblin Village
		 */
		remove(2952, 3501, 0);
		remove(2951, 3501, 0);
		remove(2954, 3505, 0);
		remove(2953, 3505, 0);
		remove(2958, 3506, 0);
		remove(2959, 3506, 0);
		
		/*
		 * Tutorial Island
		 */
		remove(3097, 3107, 0);
		remove(3098, 3107, 0);

		/*
		 * Kzhaard Vs. Gnomes
		 */
		remove(2524, 3254, 0);
		remove(2524, 3255, 0);

	}

	/**
	 * 
	 * @param objectId
	 *            the object to spawn.
	 * @param x
	 *            the x coordinate to spawn to.
	 * @param y
	 *            the y coordinate to spawn to.
	 * @param height
	 *            the height coordinate to spawn to.
	 * @param type
	 *            the object type
	 * @param direction
	 *            the direction the object should face.
	 */
	public void addObject(int objectId, int x, int y, int height, int type,
			int direction) {
		ObjectDef def = ObjectDef.getObjectDef(objectId);
		if (def == null) {
			return;
		}
		int xLength;
		int yLength;
		if (direction != 1 && direction != 3) {
			xLength = def.xLength();
			yLength = def.yLength();
		} else {
			xLength = def.yLength();
			yLength = def.xLength();
		}
		if (type == 22) {
			if ((def.hasActions()) && (def.unwalkable())) {
				addClipping(x, y, height, 2097152);
			}
		} else if (type >= 9) {
			if (def.unwalkable()) {
				addClippingForSolidObject(x, y, height, xLength, yLength,
						def.solid());
			}
		} else if ((type >= 0) && (type <= 3) && (def.unwalkable())) {
			addClippingForVariableObject(x, y, height, type, direction,
					def.solid());
		}
	}

	public boolean blockedEast(Location loc) {
		return (getClipping(loc.getX() + 1, loc.getY(), loc.getZ()) & 0x1280180) != 0;
	}

	public boolean blockedNorth(Location loc) {
		return (getClipping(loc.getX(), loc.getY() + 1, loc.getZ()) & 0x1280120) != 0;
	}

	public boolean blockedNorthEast(Location loc) {
		return (getClipping(loc.getX() + 1, loc.getY() + 1, loc.getZ()) & 0x12801E0) != 0;
	}

	public boolean blockedNorthWest(Location loc) {
		return (getClipping(loc.getX() - 1, loc.getY() + 1, loc.getZ()) & 0x1280138) != 0;
	}

	public boolean blockedSouth(Location loc) {
		return (getClipping(loc.getX(), loc.getY() - 1, loc.getZ()) & 0x1280102) != 0;
	}

	public boolean blockedSouthEast(Location loc) {
		return (getClipping(loc.getX() + 1, loc.getY() - 1, loc.getZ()) & 0x1280183) != 0;
	}

	public boolean blockedSouthWest(Location loc) {
		return (getClipping(loc.getX() - 1, loc.getY() - 1, loc.getZ()) & 0x128010E) != 0;
	}

	public boolean blockedWest(Location loc) {
		return (getClipping(loc.getX() - 1, loc.getY(), loc.getZ()) & 0x1280108) != 0;
	}

	/**
	 * 
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param height
	 * @param xLength
	 * @param yLength
	 * @return
	 */
	public boolean canMove(int startX, int startY, int endX, int endY,
			int height, int xLength, int yLength) {
		int diffX = endX - startX;
		int diffY = endY - startY;
		int max = Math.max(Math.abs(diffX), Math.abs(diffY));
		for (int ii = 0; ii < max; ii++) {
			int currentX = endX - diffX;
			int currentY = endY - diffY;
			for (int i = 0; i < xLength; i++) {
				for (int i2 = 0; i2 < yLength; i2++) {
					if (diffX < 0 && diffY < 0) {
						if ((getClipping((currentX + i) - 1,
								(currentY + i2) - 1, height) & 0x128010e) != 0
								|| (getClipping((currentX + i) - 1, currentY
										+ i2, height) & 0x1280108) != 0
								|| (getClipping(currentX + i,
										(currentY + i2) - 1, height) & 0x1280102) != 0) {
							return false;
						}
					} else if (diffX > 0 && diffY > 0) {
						if ((getClipping(currentX + i + 1, currentY + i2 + 1,
								height) & 0x12801e0) != 0
								|| (getClipping(currentX + i + 1,
										currentY + i2, height) & 0x1280180) != 0
								|| (getClipping(currentX + i,
										currentY + i2 + 1, height) & 0x1280120) != 0) {
							return false;
						}
					} else if (diffX < 0 && diffY > 0) {
						if ((getClipping((currentX + i) - 1, currentY + i2 + 1,
								height) & 0x1280138) != 0
								|| (getClipping((currentX + i) - 1, currentY
										+ i2, height) & 0x1280108) != 0
								|| (getClipping(currentX + i,
										currentY + i2 + 1, height) & 0x1280120) != 0) {
							return false;
						}
					} else if (diffX > 0 && diffY < 0) {
						if ((getClipping(currentX + i + 1, (currentY + i2) - 1,
								height) & 0x1280183) != 0
								|| (getClipping(currentX + i + 1,
										currentY + i2, height) & 0x1280180) != 0
								|| (getClipping(currentX + i,
										(currentY + i2) - 1, height) & 0x1280102) != 0) {
							return false;
						}
					} else if (diffX > 0 && diffY == 0) {
						if ((getClipping(currentX + i + 1, currentY + i2,
								height) & 0x1280180) != 0) {
							return false;
						}
					} else if (diffX < 0 && diffY == 0) {
						if ((getClipping((currentX + i) - 1, currentY + i2,
								height) & 0x1280108) != 0) {
							return false;
						}
					} else if (diffX == 0 && diffY > 0) {
						if ((getClipping(currentX + i, currentY + i2 + 1,
								height) & 0x1280120) != 0) {
							return false;
						}
					} else if (diffX == 0
							&& diffY < 0
							&& (getClipping(currentX + i, (currentY + i2) - 1,
									height) & 0x1280102) != 0) {
						return false;
					}
				}

			}

			if (diffX < 0) {
				diffX++;
			} else if (diffX > 0) {
				diffX--;
			}
			if (diffY < 0) {
				diffY++;
			} else if (diffY > 0) {
				diffY--;
			}
		}

		return true;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param height
	 * @return
	 */
	private int getClip(int x, int y, int height) {
		int regionAbsX = (this.id >> 8) * 64;
		int regionAbsY = (this.id & 0xFF) * 64;
		if (this.clips[height] == null) {
			return 0;
		}
		if (regionAbsX > x || regionAbsY > y) {
			return 0;
		}
		if ((x - regionAbsX) > clips[height].length
				|| (y - regionAbsY) > clips[height][(x - regionAbsX)].length) {
			return 0;
		}
		return this.clips[height][(x - regionAbsX)][(y - regionAbsY)];

	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param height
	 * @return
	 */
	public int getClipping(int x, int y, int height) {
		if (height > 3) {
			height = 0;
		}

		int regionX = x >> 3;
		int regionY = y >> 3;
		int regionId = (regionX / 8 << 8) + regionY / 8;
		for (RegionClipping r : this.regions) {
			if (r.id() == regionId) {
				return r.getClip(x, y, height);
			}
		}
		return getClip(x, y, height);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param height
	 * @param check
	 * @return
	 */
	public int getClipping(int x, int y, int height, boolean check) {
		if (height > 3) {
			height = 0;
		}

		int regionX = x >> 3;
		int regionY = y >> 3;
		int regionId = (regionX / 8 << 8) + regionY / 8;
		for (RegionClipping r : this.regions) {
			if (r.id() == regionId) {
				return r.getClip(x, y, height);
			}
		}

		return 0;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param height
	 * @param moveTypeX
	 * @param moveTypeY
	 * @return
	 */
	public boolean getClipping(int x, int y, int height, int moveTypeX,
			int moveTypeY) {
		try {
			if (height > 3) {
				height = 0;
			}
			int checkX = x + moveTypeX;
			int checkY = y + moveTypeY;
			if ((moveTypeX == -1) && (moveTypeY == 0)) {
				return (getClipping(x, y, height) & 0x1280108) == 0;
			}
			if ((moveTypeX == 1) && (moveTypeY == 0)) {
				return (getClipping(x, y, height) & 0x1280180) == 0;
			}
			if ((moveTypeX == 0) && (moveTypeY == -1)) {
				return (getClipping(x, y, height) & 0x1280102) == 0;
			}
			if ((moveTypeX == 0) && (moveTypeY == 1)) {
				return (getClipping(x, y, height) & 0x1280120) == 0;
			}
			if ((moveTypeX == -1) && (moveTypeY == -1)) {
				return ((getClipping(x, y, height) & 0x128010E) == 0)
						&& ((getClipping(checkX - 1, checkY, height) & 0x1280108) == 0)
						&& ((getClipping(checkX, checkY - 1, height) & 0x1280102) == 0);
			}
			if ((moveTypeX == 1) && (moveTypeY == -1)) {
				return ((getClipping(x, y, height) & 0x1280183) == 0)
						&& ((getClipping(checkX + 1, checkY, height) & 0x1280180) == 0)
						&& ((getClipping(checkX, checkY - 1, height) & 0x1280102) == 0);
			}
			if ((moveTypeX == -1) && (moveTypeY == 1)) {
				return ((getClipping(x, y, height) & 0x1280138) == 0)
						&& ((getClipping(checkX - 1, checkY, height) & 0x1280108) == 0)
						&& ((getClipping(checkX, checkY + 1, height) & 0x1280120) == 0);
			}
			if ((moveTypeX == 1) && (moveTypeY == 1)) {
				return ((getClipping(x, y, height) & 0x12801E0) == 0)
						&& ((getClipping(checkX + 1, checkY, height) & 0x1280180) == 0)
						&& ((getClipping(checkX, checkY + 1, height) & 0x1280120) == 0);
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public int getIndexPosition(int id) throws IOException {
		if (mapIndices.contains(id)) {
			for (int i = 0; i < mapIndices.size(); i++) {
				if (mapIndices.get(i) == id) {
					return i;
				}
			}
		}
		return -1;
	}

	public byte[] grabMap(int id) throws IOException {
		if (mapIndices == null) {
			loadIndex2();
		}
		if (mapBuffer.get(id) == null) {
			RandomAccessFile raf_cache = new RandomAccessFile(
					"data/world/MAP_CACHE.dat", "rw");
			RandomAccessFile raf_index = new RandomAccessFile(
					"data/world/MAP_CACHE.idx", "rw");
			int pos = getIndexPosition(id);
			if (pos == -1) {
				raf_cache.close();
				raf_index.close();
				return null;
			}
			raf_index.seek(pos * 12);
			raf_cache.seek(raf_index.readInt());
			byte[] b = new byte[raf_index.readInt()];
			raf_cache.readFully(b);
			b = inflate(b, raf_index.readInt());
			mapBuffer.put(id, b);
			raf_cache.close();
			raf_index.close();
			return mapBuffer.get(id);
		}
		return mapBuffer.get(id);
	}

	public int id() {
		return this.id;
	}

	public byte[] inflate(byte[] b, int l) throws IOException {
		byte[] buf = new byte[l];
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		DataInputStream dis = new DataInputStream(new GZIPInputStream(bais));
		dis.readFully(buf, 0, buf.length);
		dis.close();
		return buf;
	}

	public boolean isMembersArea(int x, int y, int height) {
		if ((x >= 3272) && (x <= 3320) && (y >= 2752) && (y <= 2809)) {
			return false;
		}
		if ((x >= 2640) && (x <= 2677) && (y >= 2638) && (y <= 2679)) {
			return false;
		}
		int regionX = x >> 3;
		int regionY = y >> 3;
		int regionId = (regionX / 8 << 8) + regionY / 8;

		for (RegionClipping r : this.regions) {
			if (r.id() == regionId) {
				return r.members();
			}
		}

		return false;
	}

	public void load(LandscapeListener listener) {
		try {

			logger.info("Loading clipping laws...");

			File f = new File("data/world/map_index");
			if (!f.exists()) {
				logger.info(f.getName() + " file does not exist.");
				return;
			}
			RandomAccessFile in = new RandomAccessFile(f, "rw");
			int size = (int) in.length() / 7;
			this.regions = new RegionClipping[size];
			int[] regionIds = new int[size];
			int[] mapGroundFileIds = new int[size];
			int[] mapObjectsFileIds = new int[size];
			boolean[] isMembers = new boolean[size];
			for (int i = 0; i < size; i++) {
				regionIds[i] = in.readShort();
				mapGroundFileIds[i] = in.readShort();
				mapObjectsFileIds[i] = in.readShort();
				isMembers[i] = in.readByte() == 0;
			}
			in.close();
			for (int i = 0; i < size; i++) {
				this.regions[i] = new RegionClipping(regionIds[i], isMembers[i]);
			}
			boolean errorLoading = false;
			int regionz = 0;
			for (int i = 0; i < size; i++) {
				File file = new File("data/world/map/" + mapObjectsFileIds[i]
						+ ".gz");
				byte[] file1 = file.exists() ? getBuffer(new File(
						"data/world/map/" + mapObjectsFileIds[i] + ".gz"))
						: this.grabMap(mapObjectsFileIds[i]);
				file = new File("data/world/map/" + mapGroundFileIds[i] + ".gz");

				byte[] file2 = file.exists() ? getBuffer(new File(
						"data/world/map/" + mapGroundFileIds[i] + ".gz"))
						: this.grabMap(mapGroundFileIds[i]);
				if ((file1 == null) || (file2 == null)) {
					continue;
				}
				try {
					singleton.loadMaps(regionIds[i], new ByteStream(file1),
							new ByteStream(file2), listener);
					regionz++;
				} catch (Exception e) {
					e.printStackTrace();
					errorLoading = true;
				}
			}
			GameObject go = new GameObject(Location.create(2658, 2639, 0),
					14315, 0, 1, true);
			listener.objectParsed(go);
			if (errorLoading) {
				logger.info("An error had occuried while trying to load world maps.");
				return;
			}
			singleton.addFixes();
			logger.info("Loaded " + regionz + " Regions.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadIndex2() throws IOException {
		mapIndices = new ArrayList<Integer>();
		DataInputStream dis = new DataInputStream(new FileInputStream(
				"data/world/MAP_CACHE.idx2"));
		for (int i = 0; i < (int) new File("data/world/MAP_CACHE.idx2")
				.length() / 2; i++) {
			mapIndices.add((int) dis.readShort());
		}
		dis.close();
	}

	private void loadMaps(int regionId, ByteStream str1, ByteStream str2,
			LandscapeListener listener) {
		int absX = (regionId >> 8) * 64;
		int absY = (regionId & 255) * 64;
		byte[][][] someArray = new byte[4][64][64];

		int objectId;
		int incr;
		int location;
		int incr2;
		for (objectId = 0; objectId < 4; ++objectId) {
			for (incr = 0; incr < 64; ++incr) {
				location = 0;

				while (location < 64) {
					while (true) {
						incr2 = str2.getUByte();
						if (incr2 != 0) {
							if (incr2 != 1) {
								if (incr2 <= 49) {
									str2.skip(1);
								} else if (incr2 <= 81) {
									someArray[objectId][incr][location] = (byte) (incr2 - 49);
								}
								continue;
							}

							str2.skip(1);
						}

						++location;
						break;
					}
				}
			}
		}

		for (objectId = 0; objectId < 4; ++objectId) {
			for (incr = 0; incr < 64; ++incr) {
				for (location = 0; location < 64; ++location) {
					if ((someArray[objectId][incr][location] & 1) == 1) {
						incr2 = objectId;
						if ((someArray[1][incr][location] & 2) == 2) {
							incr2 = objectId - 1;
						}

						if (incr2 >= 0 && incr2 <= 3) {
							addClipping(absX + incr, absY + location, incr2,
									2097152);
						}
					}
				}
			}
		}

		objectId = -1;

		while ((incr = str1.getUSmart()) != 0) {
			objectId += incr;
			location = 0;

			while ((incr2 = str1.getUSmart()) != 0) {
				location += incr2 - 1;
				int localX = location >> 6 & 63;
				int localY = location & 63;
				int height = location >> 12;
				int objectData = str1.getUByte();
				int type = objectData >> 2;
				int direction = objectData & 3;
				GameObject go = new GameObject(Location.create(absX + localX,
						absY + localY, height), objectId, type, direction, true);
				if (localX >= 0 && localX < 64 && localY >= 0 && localY < 64) {
					if ((someArray[1][localX][localY] & 2) == 2) {
						--height;
					}

					if (height >= 0 && height <= 3) {
						addObject(objectId, absX + localX, absY + localY,
								height, type, direction);
						listener.objectParsed(go);
					}
				}
			}
		}
	}

	public boolean members() {
		return this.members;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param height
	 */
	public void remove(int x, int y, int height) {
		removeClip(Location.create(x, y, height));

		int regionX = x >> 3;
		int regionY = y >> 3;
		int regionId = (regionX / 8 << 8) + regionY / 8;
		for (RegionClipping r : this.regions) {
			if (r.id() == regionId) {
				r.removeClip(Location.create(x, y, height));
				break;
			}
		}
	}

	/**
	 * 
	 * @param loc
	 */
	private void removeClip(Location loc) {
		try {
			int regionAbsX = (this.id >> 8) * 64;
			int regionAbsY = (this.id & 0xFF) * 64;
			if (this.clips[loc.getZ()] == null) {
				this.clips[loc.getZ()] = new int[64][64];
			}
			if (loc.getX() < regionAbsX || loc.getY() < regionAbsY) {
				return;
			}
			this.clips[loc.getZ()][(loc.getX() - regionAbsX)][(loc.getY() - regionAbsY)] = 0;
		} catch (Exception localException) {
			// localException.printStackTrace();
		}
	}

	/**
	 * 
	 * @param loc
	 * @param size
	 */
	public void removeSquare(Location loc, int size) {
		for (int i = 0; i < size; i++) {
			for (int i2 = 0; i2 < size; i2++) {
				remove(loc.getX() + i, loc.getY() + i2, loc.getZ());
			}
		}
	}
}
