package server.game.players.packets;

import server.game.content.skills.SkillHandler;
import server.game.content.skills.core.Fishing;
import server.game.content.traveling.Desert;
import server.game.dialogues.FreeDialogues;
import server.game.players.Client;
import server.game.players.PacketType;
import server.game.players.PlayerHandler;

/**
 * Walking packet
 **/
public class Walking implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.getSmithing().resetVariables();
		if (packetType == 248 || packetType == 164) {
			c.faceUpdate(0);
			c.npcIndex = 0;
			c.playerIndex = 0;
			if (c.isWoodcutting == true) {
				c.isWoodcutting = false;
			}
			c.clickNpcType = 0;//proccess update
			c.clickObjectType = 0;//process update
			if (c.isSmelting == true) {
				c.isSmelting = true;
			}
			if (c.nextChat > 0) {
				FreeDialogues.handledialogue(c, 0, c.npcType);
			}
			if (c.isBanking == true) {
				c.isBanking = false;
			}
			if (c.playerStun == true) {
				return;
			}
			if(c.stopPlayerSkill) {
			    SkillHandler.resetPlayerSkillVariables(c);
			    Fishing.resetFishing(c);
			    c.stopPlayerSkill = false;    
			}
			if (c.followId > 0 || c.followId2 > 0)
				c.getPA().resetFollow();
			if (c.getPlayerAction().checkWalking() == false)
				return;
		}
		if (c.getNewComersSide().isInTutorialIslandStage()) {
			c.getNewComersSide().updateInterface(c, false);
		}
		
		if (c.duelRule[1] && c.duelStatus == 5) {
			if (PlayerHandler.players[c.duelingWith] != null) {
				if (!c.goodDistance(c.getX(), c.getY(),
						PlayerHandler.players[c.duelingWith].getX(),
						PlayerHandler.players[c.duelingWith].getY(), 1)
						|| c.attackTimer == 0) {
					c.sendMessage("Walking has been disabled in this duel!");
				}
			}
			c.playerIndex = 0;
			return;
		}
		
		if(c.stopPlayerSkill) {
			SkillHandler.resetPlayerSkillVariables(c);
			c.stopPlayerSkill = false;
		}

		if (c.freezeTimer > 0) {
			if (PlayerHandler.players[c.playerIndex] != null) {
				if (c.goodDistance(c.getX(), c.getY(),
						PlayerHandler.players[c.playerIndex].getX(),
						PlayerHandler.players[c.playerIndex].getY(), 1)
						&& packetType != 98) {
					c.playerIndex = 0;
					return;
				}
			}
			if (packetType != 98) {
				c.sendMessage("A magical force stops you from moving.");
				c.playerIndex = 0;
			}
			return;
		}

		if (System.currentTimeMillis() - c.lastSpear < 4000) {
			c.sendMessage("You have been stunned.");
			c.playerIndex = 0;
			return;
		}

		if (packetType == 98) {
			c.mageAllowed = true;
		}

		if ((c.duelStatus >= 1 && c.duelStatus <= 4) || c.duelStatus == 6) {
			if (c.duelStatus == 6) {
				c.getTradeAndDuel().claimStakedItems();
			}
			return;
		}
		c.getPA().removeAllWindows();

		
		if(c.inDesert() && !c.getDesert().isEventExecuted()) {
			 Desert.callHeat(c);
		     c.getDesert().setEventExecuted(true);
		}

		if (c.respawnTimer > 3) {
			return;
		}
		if (c.inTrade) {
			return;
		}
		if (packetType == 248) {
			packetSize -= 14;
		}
		c.newWalkCmdSteps = (packetSize - 5) / 2;
		if (++c.newWalkCmdSteps > c.walkingQueueSize) {
			c.newWalkCmdSteps = 0;
			return;
		}

		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;

		int firstStepX = c.getInStream().readSignedWordBigEndianA()
				- c.getMapRegionX() * 8;
		for (int i = 1; i < c.newWalkCmdSteps; i++) {
			c.getNewWalkCmdX()[i] = c.getInStream().readSignedByte();
			c.getNewWalkCmdY()[i] = c.getInStream().readSignedByte();
		}

		int firstStepY = c.getInStream().readSignedWordBigEndian()
				- c.getMapRegionY() * 8;
		c.setNewWalkCmdIsRunning(c.getInStream().readSignedByteC() == 1);
		for (int i1 = 0; i1 < c.newWalkCmdSteps; i1++) {
			c.getNewWalkCmdX()[i1] += firstStepX;
			c.getNewWalkCmdY()[i1] += firstStepY;
		}
	}

}

