/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rs2server.rs2.packet;

import org.rs2server.rs2.action.Action;
import org.rs2server.rs2.model.Item;
import org.rs2server.rs2.model.Player;
import org.rs2server.rs2.model.combat.impl.MagicCombatAction;
import org.rs2server.rs2.model.combat.impl.MagicCombatAction.Spell;
import org.rs2server.rs2.net.Packet;

/**
 * 
 * @author Stephen Soltys
 */
public class MagicOnItemPacketHandler implements PacketHandler {

	@Override
	public void handle(final Player player, Packet packet) {
		int interfaceId = packet.getInt();
		short itemId = packet.getLEShort();
		short s2 = packet.getLEShort();
		short slot = packet.getBEShort();
		int spellId = packet.getInt3();
		if (player.getCombatState().isDead()) {
			return;
		}
		final Item item = player.getInventory().get(slot);
		if (item == null || item.getId() != itemId) {
			return;
		}
		if (itemId == 995) {
			player.getActionSender().sendMessage(
					"You can't use that spell on gold!");
			return;
		}
		Action action = null;
		switch (spellId) {
		case 13:// low alch
			MagicCombatAction.executeSpell(Spell.LOW_ALCHEMY, player,
					new Object[] { player, item });
			break;
		case 34:// high alch
			MagicCombatAction.executeSpell(Spell.HIGH_ALCHEMY, player,
					new Object[] { player, item });
			break;
		}
		if (action != null) {
			player.getActionQueue().addAction(action);
		}
	}
}
