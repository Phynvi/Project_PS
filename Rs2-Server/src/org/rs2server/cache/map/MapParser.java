package org.rs2server.cache.map;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.rs2server.rs2.net.Packet;
import org.rs2server.rs2.net.Packet.Type;

/**
 * A class which parses map files in the game cache.
 * 
 * @author Graham Edgecombe
 * 
 */
public class MapParser {

	public static class TileRegionData {

		private int id, x, y, groundFile, objFile;

		public TileRegionData(int id, int objFile, int groundFile, int x, int y) {
			this.id = id;
			this.objFile = objFile;
			this.groundFile = groundFile;
			this.x = x;
			this.y = y;
		}

		public int getGroundFile() {
			return groundFile;
		}

		public int getId() {
			return id;
		}

		public int getObjectFile() {
			return objFile;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
	}

	private static List<MapParser.TileRegionData> tileRegionData = new ArrayList<MapParser.TileRegionData>();

	public static MapParser.TileRegionData getDataForArea(int area) {
		for (MapParser.TileRegionData data : tileRegionData) {
			if (data.getId() == area) {
				return data;
			}
		}
		return null;
	}

	public static MapParser.TileRegionData getDataForObjectFile(int area) {
		for (MapParser.TileRegionData data : tileRegionData) {
			if (data.getObjectFile() == area) {
				return data;
			}
		}
		return null;
	}

	public static MapParser.TileRegionData getDataForRegionCoords(int x, int y) {
		for (MapParser.TileRegionData data : tileRegionData) {
			if (data.getX() == x && data.getY() == y) {
				return data;
			}
		}
		return null;
	}

	public static List<MapParser.TileRegionData> getTileRegionData() {
		return tileRegionData;
	}

	public static void loadTileRegions(MapListener listener) {
		try {
			RandomAccessFile file = new RandomAccessFile(new File(
					"./data/cache/MAP_DUMP.dat"), "rw");
			while (file.getFilePointer() < file.length()) {
				tileRegionData.add(new MapParser.TileRegionData(file.readInt(),
						file.readInt(), file.readInt(), file.readInt(), file
								.readInt()));
			}
			file.close();
			// Clipping.regions = new Clipping[tileRegionData.size()];
			// for (int i = 0; i < Clipping.regions.length; i++) {
			// Clipping.regions[i] = new Clipping(tileRegionData.get(i).getId(),
			// false);
			// }
			for (MapParser.TileRegionData data : tileRegionData) {
				new MapParser(data.getId(), listener).parse();
			}
		} catch (Exception ex) {
			Logger.getLogger(MapParser.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}

	/**
	 * The area id.
	 */
	private int area;

	/**
	 * The map listener.
	 */
	private MapListener listener;

	/**
	 * Creates the map parser.
	 * 
	 * @param cache
	 *            The cache.
	 * @param area
	 *            The area id.
	 * @param listener
	 *            The listener.
	 */
	public MapParser(int area, MapListener listener) {
		this.area = area;
		this.listener = listener;
	}

	/**
	 * @return the area
	 */
	public int getArea() {
		return area;
	}

	/**
	 * @return the listener
	 */
	public MapListener getListener() {
		return listener;
	}

	/**
	 * Parses the map file.
	 */
	public void parse() {
		/*
		 * TileRegionData data = this.getDataForArea(area); if (data == null ||
		 * area == -1) { return; } int i_10_ = (data.getX() + (data.modifier >>
		 * -964192152) * 64); int i_11_ = (data.getY() + 64 * (0xff &
		 * data.modifier)); Packet packet = null; try { packet =
		 * readRegionFile(); } catch (IOException ex) {
		 * Logger.getLogger(MapParser.class.getName()).log(Level.SEVERE, null,
		 * ex); } int[][][] someArray = new int[4][64][64]; for (int i_12_ = 0;
		 * i_12_ < 4; i_12_++) { for (int i_13_ = 0; (i_13_ ^ 0xffffffff) > -65;
		 * i_13_++) { for (int i_14_ = 0; (i_14_ ^ 0xffffffff) > -65; i_14_++) {
		 * someArray[i_12_][i_13_][i_14_] = readTile(i_13_ + i_10_, i_14_ +
		 * i_11_, packet); //listener.tileParsed(readTile(i_13_ + i_10_, i_14_ +
		 * i_11_, packet), // Location.create(data.getX() + i_13_, //
		 * data.getY() + i_14_, i_12_)); } } } for (int i = 0; i < 4; i++) { for
		 * (int i2 = 0; i2 < 64; i2++) { for (int i3 = 0; i3 < 64; i3++) { if
		 * ((someArray[i][i2][i3] & 1) == 1) { int height = i; if
		 * ((someArray[1][i2][i3] & 2) == 2) { height--; } if (height >= 0 &&
		 * height <= 3) { // Clipping.addClipping(data.getX() + i2, data.getY()
		 * + i3, height, 0x200000); } } } } }
		 */
	}

	private Packet readRegionFile() throws IOException {
		FileInputStream stream = new FileInputStream("./data/cache/tiles/"
				+ area + ".dat");
		byte[] b = new byte[stream.available()];
		stream.read(b);
		stream.close();
		return new Packet(-1, Type.FIXED, IoBuffer.wrap(b));
	}

	private int readTile(int localX, int localY, Packet stream) {
		int i = -1;
		if (localX >= 0 && localX < 104 && localY >= 0 && localY < 104) {
			for (;;) {
				int i_6_ = stream.getUnsignedByte();
				if ((i_6_ ^ 0xffffffff) == -1) {
					break;
				}
				if (i_6_ == 1) {
					stream.getUnsignedByte();
					break;
				}
				if ((i_6_ ^ 0xffffffff) >= -50) {
					stream.getUnsignedByte();
				} else if (i_6_ <= 81) {
					i = i_6_ - 49;
				}
			}
		} else {
			for (;;) {
				int i_8_ = stream.getUnsignedByte();
				if (i_8_ == 0) {
					break;
				}
				if ((i_8_ ^ 0xffffffff) == -2) {
					stream.getUnsignedByte();
					break;
				}
				if ((i_8_ ^ 0xffffffff) >= -50) {
					stream.getUnsignedByte();
				}
			}
		}
		return i;
	}
}
