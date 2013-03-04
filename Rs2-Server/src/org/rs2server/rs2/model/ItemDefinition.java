package org.rs2server.rs2.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.logging.Logger;

import org.apache.mina.core.buffer.IoBuffer;
import org.rs2server.rs2.util.IoBufferUtils;
import org.rs2server.util.Buffers;

/**
 * The item definition manager.
 * 
 * @author Vastico
 * @author Graham Edgecombe
 * 
 */
public class ItemDefinition {

	/**
	 * Logger instance.
	 */
	private static final Logger logger = Logger.getLogger(ItemDefinition.class
			.getName());
	/**
	 * The definition array.
	 */
	private static ItemDefinition[] definitions;

	/**
	 * Untradable items
	 */
	public static int[] untradableItems = new int[] { 6570, 2677 };

	/**
	 * Dumps the item definitions.
	 * 
	 * @throws IOException
	 *             if an I/O error occurs.
	 * @throws IllegalStateException
	 *             if the definitions have been loaded already.
	 */
	public static void dump() {
		try {
			logger.info("Dumping item definitions...");
			OutputStream os = new FileOutputStream(
					"data/itemDefinitions-weights.bin");
			IoBuffer buf = IoBuffer.allocate(1024);
			buf.setAutoExpand(true);

			ItemDefinition item = null;
			for (ItemDefinition itemDef : definitions) {
				if (itemDef != null) {
					if (item == null || itemDef.getId() > item.getId()) {
						item = itemDef;
					}
				}
			}

			buf.putShort((short) item.getId());
			for (int i = 0; i < definitions.length; i++) {
				item = definitions[i];
				if (item != null) {
					buf.putShort((short) item.getId());
					IoBufferUtils.putRS2String(buf, item.getName());
					IoBufferUtils.putRS2String(buf, item.getDescription());
					buf.put((byte) (item.isNoted() ? 1 : 0));
					buf.putShort((short) item.getNormalId());
					buf.put((byte) (item.isNoteable() ? 1 : 0));
					buf.putShort((short) item.getNotedId());
					buf.put((byte) (item.isStackable() ? 1 : 0));
					buf.put((byte) (item.isMembersOnly() ? 1 : 0));
					buf.putInt(item.getValue());
					buf.putDouble(item.getWeight());
				} else {
					buf.putShort((short) -1);
				}
			}
			buf.flip();
			byte[] data = new byte[buf.limit()];
			buf.get(data);
			os.write(data);
			os.flush();
			os.close();
			logger.info("Item definitions dumped successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}

	/**
	 * Gets a definition for the specified id.
	 * 
	 * @param id
	 *            The id.
	 * @return The definition.
	 */
	public static ItemDefinition forId(int id) {
		return definitions[id];
	}

	public static int forName(String text) {
		for (ItemDefinition d : definitions) {
			if (d.name.equalsIgnoreCase(text)) {
				return d.id;
			}
		}
		return -1;
	}

	/**
	 * Gets the definition array.
	 * 
	 * @return The definition array
	 */
	public static ItemDefinition[] getDefinitions() {
		return definitions;
	}

	/**
	 * Loads the item definitions.
	 * 
	 * @throws IOException
	 *             if an I/O error occurs.
	 * @throws IllegalStateException
	 *             if the definitions have been loaded already.
	 */
	public static void init() throws IOException {
		if (definitions != null) {
			throw new IllegalStateException("Item definitions already loaded.");
		}
		logger.info("Loading item definitions...");
		RandomAccessFile raf = new RandomAccessFile("data/itemDefinitions.bin",
				"r");
		try {
			ByteBuffer buffer = raf.getChannel().map(MapMode.READ_ONLY, 0,
					raf.length());
			int count = buffer.getShort() + 1;// +1 because arrays start at 0
			definitions = new ItemDefinition[11791];
			for (int i = 0; i < count; i++) {
				int id = buffer.getShort();
				if (id == -1) {
					continue;
				}
				String name = Buffers.readString(buffer).replace("(+)", "(p+)")
						.replace("(s)", "(p++)");
				String examine = Buffers.readString(buffer);
				boolean noted = buffer.get() == 1 ? true : false;
				int parentId = buffer.getShort() & 0xFFFF;
				if (parentId == 65535) {
					parentId = -1;
				}
				boolean noteable = buffer.get() == 1 ? true : false;
				int notedId = buffer.getShort() & 0xFFFF;
				if (notedId == 65535) {
					notedId = -1;
				}
				boolean stackable = buffer.get() == 1 ? true : false;
				boolean members = buffer.get() == 1 ? true : false;
				int shopValue = buffer.getInt();
				double weight = buffer.getDouble();

				definitions[id] = new ItemDefinition(id, name, examine, noted,
						noteable, stackable, parentId, notedId, members,
						shopValue, weight);
			}
			logger.info("Loaded " + definitions.length + " item definitions.");

		} finally {
			raf.close();
		}
	}

	/**
	 * Id.
	 */
	private final int id;
	/**
	 * Name.
	 */
	private final String name;
	/**
	 * Description.
	 */
	private final String examine;
	/**
	 * Noted flag.
	 */
	private final boolean noted;
	/**
	 * Noteable flag.
	 */
	private final boolean noteable;
	/**
	 * Stackable flag.
	 */
	private final boolean stackable;
	/**
	 * Non-noted id.
	 */
	private final int parentId;
	/**
	 * Noted id.
	 */
	private final int notedId;
	/**
	 * Members flag.
	 */
	private final boolean members;
	/**
	 * Shop value.
	 */
	private int shopValue;

	/**
	 * Weight.
	 */
	private final double weight;

	/**
	 * Creates the item definition.
	 * 
	 * @param id
	 *            The id.
	 * @param name
	 *            The name.
	 * @param examine
	 *            The description.
	 * @param noted
	 *            The noted flag.
	 * @param noteable
	 *            The noteable flag.
	 * @param stackable
	 *            The stackable flag.
	 * @param parentId
	 *            The non-noted id.
	 * @param notedId
	 *            The noted id.
	 * @param members
	 *            The members flag.
	 * @param shopValue
	 *            The shop price.
	 * @param highAlcValue
	 *            The high alc value.
	 * @param lowAlcValue
	 *            The low alc value.
	 */
	private ItemDefinition(int id, String name, String examine, boolean noted,
			boolean noteable, boolean stackable, int parentId, int notedId,
			boolean members, int shopValue, double weight) {
		this.id = id;
		this.name = name;
		this.examine = examine;
		this.noted = noted;
		this.noteable = noteable;
		this.stackable = stackable;
		this.parentId = parentId;
		this.notedId = notedId;
		this.members = members;
		this.shopValue = shopValue;
		this.weight = weight;
	}

	/**
	 * Gets the description.
	 * 
	 * @return The description.
	 */
	public String getDescription() {
		return examine;
	}

	/**
	 * Gets the id.
	 * 
	 * @return The id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the name.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the normal id.
	 * 
	 * @return The normal id.
	 */
	public int getNormalId() {
		return parentId;
	}

	/**
	 * Gets the noted id.
	 * 
	 * @return The noted id.
	 */
	public int getNotedId() {
		return notedId;
	}

	/**
	 * Gets the value.
	 * 
	 * @return The value.
	 */
	public int getValue() {
		return shopValue;
	}

	/**
	 * Gets the weight.
	 * 
	 * @return The weight.
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * Gets the members only flag.
	 * 
	 * @return The members only flag.
	 */
	public boolean isMembersOnly() {
		return members;
	}

	/**
	 * Gets the noteable flag.
	 * 
	 * @return The noteable flag.
	 */
	public boolean isNoteable() {
		return noteable;
	}

	/**
	 * Gets the noted flag.
	 * 
	 * @return The noted flag.
	 */
	public boolean isNoted() {
		return noted;
	}

	/**
	 * Gets the stackable flag.
	 * 
	 * @return The stackable flag.
	 */
	public boolean isStackable() {
		return stackable || noted;
	}
}
