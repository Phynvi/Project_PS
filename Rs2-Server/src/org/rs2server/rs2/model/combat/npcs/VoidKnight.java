/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rs2server.rs2.model.combat.npcs;

import org.rs2server.rs2.model.*;
import org.rs2server.rs2.model.combat.impl.AbstractCombatAction;

/**
 * 
 * @author Steve
 */
public class VoidKnight extends AbstractCombatAction {

	@Override
	public int distance(Mob victim, Mob attacker) {
		return 2;
	}

	@Override
	public void hit(final Mob attacker, final Mob victim) {
		super.hit(attacker, victim);

		if (attacker.isNPC()) {
			NPC npc = (NPC) attacker;
			npc.getCombatState().setCanMove(false);
		}
	}

}
