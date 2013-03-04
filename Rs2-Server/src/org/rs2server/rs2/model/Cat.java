package org.rs2server.rs2.model;

import org.rs2server.rs2.model.combat.CombatAction;
import org.rs2server.rs2.model.combat.impl.MagicCombatAction.Spell;
import org.rs2server.rs2.model.container.Container;
import org.rs2server.rs2.model.region.Region;
import org.rs2server.rs2.net.ActionSender;

/**
 * A mobile entity for summoned cats.
 * 
 * @author Canownueasy <tgpn1996@hotmail.com>
 * 
 */

public class Cat extends NPC {

	/**
	 * The size of the cat.
	 */
	private CatSize catSize;

	/**
	 * The owner of the cat mob. Note that the cat will nearly always be
	 * following this mob.
	 */
	public Player owner;

	public Cat(NPCDefinition definition, Location spawnLocation,
			Location minLocation, Location maxLocation, int direction) {
		super(definition, spawnLocation, minLocation, maxLocation, direction);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addToRegion(Region region) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canHit(Mob victim, boolean messages) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void dropLoot(Mob mob) {
		// TODO Auto-generated method stub

	}

	public void followOwner() {
		// this.followPlayer(owner);
	}

	@Override
	public ActionSender getActionSender() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Animation getAttackAnimation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Spell getAutocastSpell() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Relays the cat size.
	 * 
	 * @return The cat size.
	 */
	public CatSize getCatSize() {
		return catSize;
	}

	@Override
	public Location getCentreLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getClientIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCombatCooldownDelay() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Animation getDeathAnimation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CombatAction getDefaultCombatAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Animation getDefendAnimation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefinedName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Graphic getDrawbackGraphic() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public InterfaceState getInterfaceState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Container getInventory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getProjectileId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getProjectileLockonIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getProtectionPrayerModifier() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Skills getSkills() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUndefinedName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isAutoRetaliating() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNPC() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isObject() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPlayer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeFromRegion(Region region) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAutocastSpell(Spell spell) {
		// TODO Auto-generated method stub

	}

	/**
	 * Set's the cat to a certain size. This will be used when updating the
	 * appearance of the cat.
	 * 
	 * @param catSize
	 *            The new cat size.
	 */
	public void setCatSize(CatSize catSize, Player owner) {
		this.catSize = catSize;
		this.owner = owner;
	}

	@Override
	public void setDefaultAnimations() {
		// TODO Auto-generated method stub

	}

}
