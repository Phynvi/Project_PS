package server.game.content.combat.magic;

/**
 * Obleisk
 * @author Andrew
 */

public class Charge {
	
	/*/
	public enum Obleisks {
		AIR(2152, 66, 76, 150, 573),
		WATER(2151, 56, 66, 149, 571),
		FIRE(2150, 63, 173, 152, 569),
		EARTH(2149, 60, 70, 151, 575);
	
	int objectId, level, exp, gfx, newItem;
	
	private Obleisks(int objectId, int level, int exp, int gfx, int newItem) {
		this.objectId = objectId;
		this.level = level;
		this.exp = exp;
		this.gfx = gfx;
		this.newItem = newItem;
	}
	
	public int getObject() {
		return objectId;
	}
	
	public int getNew() {
		return newItem;
	}
	
	public int getExp() {
		return exp;
	}
	
	public int getGfx() {
		return gfx;
	}
	
	public int getLevel() {
		return level;
	}
}
	
	public static void activeateObleisk(Client c, int objectId) {
		for (Obleisks o: Obleisks.values()) {
			String newItem = c.getItems().getItemName(o.getNew()).toLowerCase();
			int amount = (c.getItems().getItemCount(567));
			if (!rightObject(c, objectId)) {
				return;
			}
			if (!rightRunes(c)) {
				c.sendMessage("You don't have the required runes to charge your orb.");
				return;
			}
			if (!c.getItems().playerHasItem(567)) {
				c.sendMessage("You don't have an orb to charge.");
				return;
			}
			if (c.playerLevel[6] < o.getLevel()) {
				c.sendMessage("You need a " + o.getLevel() + " to charge your orb!");
				return;
			}
			c.getPA().addSkillXP(o.getExp(), c.playerMagic);
			c.getItems().deleteItem(567, c.getItems().getItemCount(amount));
			c.getItems().addItem(o.getNew(), c.getItems().getItemCount(amount));
			c.gfx0(o.getGfx());
			c.sendMessage("You turn your unpowered orb into a " + newItem + ".");
		}
	}
	
	public static boolean rightRunes(Client c) {
		for (Obleisks o: Obleisks.values()) {
			if (o.getObject() == 2152 && c.getItems().playerHasItem(4151, 1)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean rightObject(Client c, int objectId) {
		for (Obleisks o: Obleisks.values()) {
			if (objectId == o.getObject()) {
				return true;
			}
		}
		return false;
	}/*/
}