package server.game.npcs;

import java.util.ArrayList;

import server.game.content.clipping.clip.region.Region;
import server.game.content.minigames.FightCaves;
import server.game.players.PlayerHandler;
import server.util.Misc;

public class NpcData {
	
	public static final int[] npcsOnlyMage = {907, 908, 909, 910, 911, 912, 913, 914};//done
	public static final int[][] transformNpc = {{3223, 6006}, {3224, 6007}, {3225, 6008}, {3226, 6009}};//done
	public static final int[] npcsCantKillYou = {41, 951, 1017, 1401, 1402, 1692, 2313, 2314, 2315};//done
	public static final int[] npcCantAttack = {1532, 1533, 1534, 1535};
	public static final int[] npcDontGiveXp = {2459, 2460, 2461, 2462};
	

	public static boolean cantKillYou(int npcType) {
		for (int n : npcsCantKillYou) {
			if (n == npcType) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean onlyMage(int npcType) {
		for (int element : npcsOnlyMage) {
			if (npcType == element) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean cantAttack(int npcType) {
		for (int n : npcCantAttack) {
			if (n == npcType) {
				return true;
			}
		}
		return false;
	}

	public static boolean dontGiveXp(int npcType) {
		for (int n : npcDontGiveXp) {
			if (n == npcType) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isAggressive(int i) {
		if (NPCHandler.npcs[i].aggressive && !onlyMage(NPCHandler.npcs[i].npcType)) {
			return true;
		}
		if (NPCHandler.npcs[i].inWild() && NPCHandler.npcs[i].MaxHP > 0 && !onlyMage(NPCHandler.npcs[i].npcType)) {
			return true;
		}
		return false;
	}
	
	public static int getNpcKillerId(int npcId) {
		int oldDamage = 0;
		int killerId = 0;
		 for (int p = 1; p < PlayerHandler.players.length; p++) {
			if (PlayerHandler.players[p] != null) {
				if (PlayerHandler.players[p].lastNpcAttacked == npcId) {
					if (PlayerHandler.players[p].totalDamageDealt > oldDamage) {
						oldDamage = PlayerHandler.players[p].totalDamageDealt;
						killerId = p;
					}
					PlayerHandler.players[p].totalDamageDealt = 0;
				}
			}
		}
		return killerId;
	}
	
	public static int getCloseRandomPlayer(int i) {
		ArrayList<Integer> players = new ArrayList<Integer>();
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (NPCHandler.goodDistance(PlayerHandler.players[j].absX,
						PlayerHandler.players[j].absY, NPCHandler.npcs[i].absX,
						NPCHandler.npcs[i].absY, 2 + NPCHandler.distanceRequired(i)
								+ NPCHandler.followDistance(i))
						|| FightCaves.isFightCaveNpc(i)) {
					if ((PlayerHandler.players[j].underAttackBy <= 0 && PlayerHandler.players[j].underAttackBy2 <= 0)
							|| PlayerHandler.players[j].inMulti())
						if (PlayerHandler.players[j].heightLevel == NPCHandler.npcs[i].heightLevel)
							players.add(j);
				}
			}
		}
		if (players.size() > 0)
			return players.get(Misc.random(players.size() - 1));
		else
			return 0;
	}
	
	public static void startAnimation(int animId, int i) {
		NPCHandler.npcs[i].animNumber = animId;
		NPCHandler.npcs[i].animUpdateRequired = true;
		NPCHandler.npcs[i].updateRequired = true;
	}
	
	public static void handleClipping(int i) {
		NPC npc = NPCHandler.npcs[i];
			if(npc.moveX == 1 && npc.moveY == 1) {
					if((Region.getClipping(npc.absX + 1, npc.absY + 1, npc.heightLevel) & 0x12801e0) != 0)  {
							npc.moveX = 0; npc.moveY = 0;
							if((Region.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) == 0)
								npc.moveY = 1;
							else 
								npc.moveX = 1; 				
							}
					} else if(npc.moveX == -1 && npc.moveY == -1) {
						if((Region.getClipping(npc.absX - 1, npc.absY - 1, npc.heightLevel) & 0x128010e) != 0)  {
							npc.moveX = 0; npc.moveY = 0;
							if((Region.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) == 0)
								npc.moveY = -1;
							else
								npc.moveX = -1; 		
					}
					} else if(npc.moveX == 1 && npc.moveY == -1) {
							if((Region.getClipping(npc.absX + 1, npc.absY - 1, npc.heightLevel) & 0x1280183) != 0)  {
							npc.moveX = 0; npc.moveY = 0;
							if((Region.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) == 0)
								npc.moveY = -1;
							else
								npc.moveX = 1; 
							}
					} else if(npc.moveX == -1 && npc.moveY == 1) {
						if((Region.getClipping(npc.absX - 1, npc.absY + 1, npc.heightLevel) & 0x128013) != 0)  {
							npc.moveX = 0; npc.moveY = 0;
							if((Region.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) == 0)
								npc.moveY = 1;
							else
								npc.moveX = -1; 
										}
					} //Checking Diagonal movement. 
					
			if (npc.moveY == -1 ) {
				if ((Region.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) != 0)
                    npc.moveY = 0;
			} else if( npc.moveY == 1) {
				if((Region.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) != 0)
					npc.moveY = 0;
			} //Checking Y movement.
			if(npc.moveX == 1) {
				if((Region.getClipping(npc.absX + 1, npc.absY, npc.heightLevel) & 0x1280180) != 0) 
					npc.moveX = 0;
				} else if(npc.moveX == -1) {
				 if((Region.getClipping(npc.absX - 1, npc.absY, npc.heightLevel) & 0x1280108) != 0)
					npc.moveX = 0;
			} //Checking X movement.
	}

}
