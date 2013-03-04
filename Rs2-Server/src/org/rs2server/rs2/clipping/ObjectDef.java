package org.rs2server.rs2.clipping;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 
 * @author Josh Mai
 * 
 */
public final class ObjectDef {

	public static byte[] getBuffer(String s) {
		try {
			File f = new File("data/world/object/" + s);
			if (!f.exists()) {
				return null;
			}
			byte[] buffer = new byte[(int) f.length()];
			DataInputStream dis = new DataInputStream(new FileInputStream(f));
			dis.readFully(buffer);
			dis.close();
			return buffer;
		} catch (Exception localException) {
		}
		return null;
	}

	public static void loadConfig() {
		archive = new MemoryArchive(new ByteStream(getBuffer("loc.dat")),
				new ByteStream(getBuffer("loc.idx")));
		cache = new ObjectDef[20];
		for (int k = 0; k < 20; k++) {
			cache[k] = new ObjectDef();
		}
	}

	public boolean aBoolean736;
	public String name;
	public int xSize;
	public int ySize;
	public int anInt746;
	private int[] originalModelColors;
	public int anInt749;
	public static boolean lowMem;
	public int type;
	public boolean aBoolean757;
	public int anInt758;
	public int[] childrenIDs;
	public boolean flatTerrain;
	public boolean aBoolean764;
	public boolean unwalkable;
	public int anInt768;
	private static int cacheIndex;
	private int[] anIntArray773;
	public int anInt774;
	public int anInt775;
	private int[] anIntArray776;
	public byte[] description;
	public boolean hasActions;
	public boolean solid;
	public int anInt781;
	private static ObjectDef[] cache;
	private int[] modifiedModelColors;

	public String[] actions;

	private static MemoryArchive archive;

	public static ObjectDef getObjectDef(int i) {
		for (int j = 0; j < 20; j++) {
			if (cache[j].type == i) {
				return cache[j];
			}
		}
		cacheIndex = (cacheIndex + 1) % 20;
		ObjectDef class46 = cache[cacheIndex];
		class46.type = i;
		class46.setDefaults();
		byte[] buffer = archive.get(i);
		if ((buffer != null) && (buffer.length > 0)) {
			class46.readValues(new ByteStreamExt(buffer));
		}
		return class46;
	}

	private ObjectDef() {
		this.type = -1;
	}

	public boolean hasActions() {
		return this.hasActions;
	}

	public boolean hasName() {
		return (this.name != null) && (this.name.length() > 1);
	}

	private void readValues(ByteStreamExt stream) {
		int flag = -1;
		while (true) {
			int type = stream.readUnsignedByte();
			if (type == 0) {
				break;
			}
			if (type == 1) {
				int len = stream.readUnsignedByte();
				if (len <= 0) {
					continue;
				}
				if ((this.anIntArray773 == null) || (lowMem)) {
					this.anIntArray776 = new int[len];
					this.anIntArray773 = new int[len];
					for (int k1 = 0; k1 < len; k1++) {
						this.anIntArray773[k1] = stream.readUnsignedWord();
						this.anIntArray776[k1] = stream.readUnsignedByte();
					}
					continue;
				}

				stream.currentOffset += len * 3;
				continue;
			}

			if (type == 2) {
				this.name = stream.readNewString();
				continue;
			}
			if (type == 5) {
				int len = stream.readUnsignedByte();
				if (len <= 0) {
					continue;
				}
				if ((this.anIntArray773 == null) || (lowMem)) {
					this.anIntArray776 = null;
					this.anIntArray773 = new int[len];
					for (int l1 = 0; l1 < len; l1++) {
						this.anIntArray773[l1] = stream.readUnsignedWord();
					}
					continue;
				}

				stream.currentOffset += len * 2;
				continue;
			}

			if (type == 14) {
				this.xSize = stream.readUnsignedByte();
				continue;
			}
			if (type == 15) {
				this.ySize = stream.readUnsignedByte();
				continue;
			}
			if (type == 17) {
				this.unwalkable = false;
				continue;
			}
			if (type == 18) {
				this.aBoolean757 = false;
				continue;
			}
			if (type == 19) {
				this.hasActions = (stream.readUnsignedByte() == 1);
				continue;
			}
			if (type == 21) {
				this.flatTerrain = true;
				continue;
			}
			if (type == 22) {
				continue;
			}
			if (type == 23) {
				this.aBoolean764 = true;
				continue;
			}
			if (type == 24) {
				this.anInt781 = stream.readUnsignedWord();
				if (this.anInt781 == 65535) {
					this.anInt781 = -1;
					continue;
				}
			}
			if (type == 27) {
				continue;
			}
			if (type == 28) {
				this.anInt775 = stream.readUnsignedByte();
				continue;
			}
			if (type == 29) {
				stream.readSignedByte();
				continue;
			}
			if (type == 39) {
				stream.readSignedByte();
				continue;
			}
			if ((type >= 30) && (type < 39)) {
				if (this.actions == null) {
					this.actions = new String[5];
				}
				this.actions[(type - 30)] = stream.readNewString();
				this.hasActions = true;
				if (this.actions[(type - 30)].equalsIgnoreCase("hidden")) {
					this.actions[(type - 30)] = null;
					continue;
				}
			}
			if (type == 40) {
				int i1 = stream.readUnsignedByte();
				this.modifiedModelColors = new int[i1];
				this.originalModelColors = new int[i1];
				for (int i2 = 0; i2 < i1; i2++) {
					this.modifiedModelColors[i2] = stream.readUnsignedWord();
					this.originalModelColors[i2] = stream.readUnsignedWord();
				}
				continue;
			}

			if (type == 41) {
				int l = stream.readUnsignedByte();
				stream.skip(l * 4);
				continue;
			}
			if (type == 42) {
				int l = stream.readUnsignedByte();
				stream.skip(l);
				continue;
			}
			if (type == 60) {
				this.anInt746 = stream.readUnsignedWord();
				continue;
			}
			if (type == 62) {
				continue;
			}
			if (type == 64) {
				this.solid = false;
				continue;
			}
			if (type == 65) {
				stream.readUnsignedWord();
				continue;
			}
			if (type == 66) {
				stream.readUnsignedWord();
				continue;
			}
			if (type == 67) {
				stream.readUnsignedWord();
				continue;
			}
			if (type == 68) {
				this.anInt758 = stream.readUnsignedWord();
				continue;
			}
			if (type == 69) {
				this.anInt768 = stream.readUnsignedByte();
				continue;
			}
			if (type == 70) {
				stream.readSignedWord();
				continue;
			}
			if (type == 71) {
				stream.readSignedWord();
				continue;
			}
			if (type == 72) {
				stream.readSignedWord();
				continue;
			}
			if (type == 73) {
				this.aBoolean736 = true;
				continue;
			}
			if (type != 74) {
				if (type == 75) {
					stream.readUnsignedByte();
					continue;
				}
				if ((type == 77) || (type == 92)) {
					this.anInt774 = stream.readUnsignedWord();
					if (this.anInt774 == 65535) {
						this.anInt774 = -1;
					}
					this.anInt749 = stream.readUnsignedWord();
					if (this.anInt749 == 65535) {
						this.anInt749 = -1;
					}
					int endChild = -1;
					if (type == 92) {
						endChild = stream.readUnsignedWord();
						if (endChild == 65535) {
							endChild = -1;
						}
					}
					int j1 = stream.readUnsignedByte();
					this.childrenIDs = new int[j1 + 2];
					for (int j2 = 0; j2 <= j1; j2++) {
						this.childrenIDs[j2] = stream.readUnsignedWord();
						if (this.childrenIDs[j2] == 65535) {
							this.childrenIDs[j2] = -1;
						}
					}
					this.childrenIDs[(j1 + 1)] = endChild;
					continue;
				}
				if (type == 78) {
					stream.skip(3);
					continue;
				}
				if (type == 79) {
					stream.skip(5);
					int l = stream.readUnsignedByte();
					stream.skip(l * 2);
					continue;
				}
				if (type == 81) {
					stream.skip(1);
					continue;
				}
				if ((type == 82) || (type == 88) || (type == 89)
						|| (type == 90) || (type == 91) || (type == 94)
						|| (type == 95) || (type == 96) || (type == 97)) {
					continue;
				}
				if (type == 93) {
					stream.skip(2);
					continue;
				}
				if (type == 249) {
					int l = stream.readUnsignedByte();
					for (int ii = 0; ii < l; ii++) {
						boolean b = stream.readUnsignedByte() == 1;
						stream.skip(3);
						if (b) {
							stream.readNewString();
						} else {
							stream.skip(4);
						}
					}
					continue;
				}

				// System.out.println("Unknown config: " + type);
			}
		}
		if (flag == -1) {
			this.hasActions = ((this.anIntArray773 != null) && ((this.anIntArray776 == null) || (this.anIntArray776[0] == 10)));
			if (this.actions != null) {
				this.hasActions = true;
			}
		}
	}

	private void setDefaults() {
		this.anIntArray773 = null;
		this.anIntArray776 = null;
		this.name = null;
		this.description = null;
		this.modifiedModelColors = null;
		this.originalModelColors = null;
		this.xSize = 1;
		this.ySize = 1;
		this.unwalkable = true;

		this.hasActions = false;
		this.flatTerrain = false;

		this.aBoolean764 = false;
		this.aBoolean736 = false;

		this.aBoolean757 = true;

		this.anInt781 = -1;
		this.anInt775 = 16;
		this.actions = null;
		this.anInt746 = -1;
		this.anInt758 = -1;
		this.solid = true;
		this.anInt768 = 0;

		this.anInt774 = -1;
		this.anInt749 = -1;
		this.childrenIDs = null;
	}

	public boolean solid() {
		return this.solid;
	}

	public boolean unwalkable() {
		return this.unwalkable;
	}

	public int xLength() {
		return this.xSize;
	}

	public int yLength() {
		return this.ySize;
	}
}
