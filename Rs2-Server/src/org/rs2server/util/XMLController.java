package org.rs2server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.rs2server.rs2.model.Animation;
import org.rs2server.rs2.model.CombatNPCDefinition;
import org.rs2server.rs2.model.Door;
import org.rs2server.rs2.model.GameObject;
import org.rs2server.rs2.model.ItemSpawn;
import org.rs2server.rs2.model.NPCDrop;
import org.rs2server.rs2.model.NPCSpawn;
import org.rs2server.rs2.model.Sound;
import org.rs2server.rs2.model.CombatNPCDefinition.Skill;
import org.rs2server.rs2.model.boundary.Boundary;
import org.rs2server.rs2.model.equipment.EquipmentDefinition;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.Sun14ReflectionProvider;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Controls all the XML File types in the server
 * 
 * @author Sir Sean Warning! XML can be slow so don't bulk up with useless stuff
 *         :)
 * 
 */
public class XMLController {

	/**
	 * The XStream Instance We don't have to implement any drivers as we use the
	 * default set one, which is very fast
	 * 
	 * Before you cry, i'm not making the XStream instance constant as it looks
	 * ugly
	 */
	private static final XStream xstream = new XStream(new Sun14ReflectionProvider(), new DomDriver());

	/**
	 * All the xstream alias in here I am not importing it into an another xml
	 * as if we need to refactor it will not edit it
	 */
	static {
		/**
		 * Sets the <code>org.hyperion.rs2.boundary.Boundary.class</code> class
		 * to boundary
		 */
		xstream.alias("animation", Animation.class);
		xstream.alias("item", org.rs2server.rs2.model.Item.class);
		xstream.alias("npcSpawn", NPCSpawn.class);
		xstream.alias("equipmentDef",
				org.rs2server.rs2.model.equipment.EquipmentDefinition.class);
		xstream.alias("equipmentType",
				org.rs2server.rs2.model.container.Equipment.EquipmentType.class);
		xstream.alias("weaponStyle",
				org.rs2server.rs2.model.equipment.WeaponStyle.class);
		xstream.alias("shop", org.rs2server.rs2.model.Shop.class);
		xstream.alias("CombatDefinition", CombatNPCDefinition.class);
		xstream.alias("skill", Skill.class);
		xstream.alias("boundary", Boundary.class);
		xstream.alias("npcdrop", NPCDrop.class);
		xstream.alias("gameObject", GameObject.class);
		xstream.alias("door", Door.class);
		xstream.alias("skillRequirement", EquipmentDefinition.Skill.class);
		xstream.alias("itemSpawn", ItemSpawn.class);
		xstream.alias("soundEffect", Sound.class);
		xstream.alias("food", org.rs2server.rs2.model.skills.Cooking.Food.class);
		xstream.alias("catch",
				org.rs2server.rs2.model.skills.Fishing.Catches.class);
		/**
		 * Sets the <code>org.hyperion.rs2.model.Location.class</code> class to
		 * location
		 */
		xstream.alias("location", org.rs2server.rs2.model.Location.class);
	}

	/**
	 * @return the xstream
	 */
	public static XStream getXstream() {
		return xstream;
	}

	/**
	 * Reads an object from an XML file.
	 * 
	 * @author Graham Edgecombe
	 * @param file
	 *            The file.
	 * @return The object.
	 * @throws IOException
	 *             if an I/O error occurs. Edit Sir Sean: Now uses generic's
	 */
	@SuppressWarnings("unchecked")
	public static <T> T readXML(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		try {
			return (T) xstream.fromXML(in);
		} finally {
			in.close();
		}
	}

	/**
	 * Reads an object from an XML string.
	 * 
	 * @author Graham Edgecombe
	 * @param s
	 *            The XML.
	 * @return The object. Edit Sir Sean: Now uses generic's
	 */
	@SuppressWarnings("unchecked")
	public static <T> T readXML(String s) {
		return (T) xstream.fromXML(s);
	}

	/**
	 * Writes the XML file, using try and finally will allow the file output to
	 * close if an exception is thrown (will stop memory leaks)
	 * 
	 * @param object
	 *            The object getting written
	 * @return The XML
	 */
	public static String writeXML(Object object) {
		return xstream.toXML(object);
	}

	/**
	 * Writes the XML file, using try and finally will allow the file output to
	 * close if an exception is thrown (will stop memory leaks)
	 * 
	 * @param object
	 *            The object getting written
	 * @param file
	 *            The file area and name
	 * @throws IOException
	 */
	public static void writeXML(Object object, File file) throws IOException {
		FileOutputStream out = new FileOutputStream(file);
		try {
			xstream.toXML(object, out);
			out.flush();
		} finally {
			out.close();
		}
	}
}