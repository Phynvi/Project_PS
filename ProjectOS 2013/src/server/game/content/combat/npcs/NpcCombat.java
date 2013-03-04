package server.game.content.combat.npcs;

import server.Config;
import server.game.content.minigames.FightCaves;
import server.game.npcs.NPCHandler;
import server.game.npcs.NpcData;
import server.game.players.Client;
import server.game.players.PlayerHandler;
import server.util.Misc;

public class NpcCombat {
	
	public static void multiAttackDamage(int i) {
		int max = NPCHandler.getMaxHit(i);
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				if (c.isDead || c.heightLevel != NPCHandler.npcs[i].heightLevel)
					continue;
				if (PlayerHandler.players[j].goodDistance(c.absX,
						c.absY, NPCHandler.npcs[i].absX, NPCHandler.npcs[i].absY, 15)) {
					if (NPCHandler.npcs[i].attackType == 2) {
						if (!c.prayerActive[16]) {
							if (Misc.random(500) + 200 > Misc.random(c
									.getCombat().mageDef())) {
								int dam = Misc.random(max);
								c.dealDamage(dam);
								c.handleHitMask(dam);
							} else {
								c.dealDamage(0);
								c.handleHitMask(0);
							}
						} else {
							c.dealDamage(0);
							c.handleHitMask(0);
						}
					} else if (NPCHandler.npcs[i].attackType == 1) {
						if (!c.prayerActive[17]) {
							int dam = Misc.random(max);
							if (Misc.random(500) + 200 > Misc.random(c
									.getCombat().calculateRangeDefence())) {
								c.dealDamage(dam);
								c.handleHitMask(dam);
							} else {
								c.dealDamage(0);
								c.handleHitMask(0);
							}
						} else {
							c.dealDamage(0);
							c.handleHitMask(0);
						}
					}
					if (NPCHandler.npcs[i].endGfx > 0) {
						c.gfx0(NPCHandler.npcs[i].endGfx);
					}
				}
				c.getPA().refreshSkill(3);
			}
		}
	}
	
	public static void multiAttackGfx(int i, int gfx) {
		if (NPCHandler.npcs[i].projectileId < 0)
			return;
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				if (c.heightLevel != NPCHandler.npcs[i].heightLevel)
					continue;
				if (PlayerHandler.players[j].goodDistance(c.absX,
						c.absY, NPCHandler.npcs[i].absX, NPCHandler.npcs[i].absY, 15)) {
					int nX = NPCHandler.npcs[i].getX() + NPCHandler.offset(i);
					int nY = NPCHandler.npcs[i].getY() + NPCHandler.offset(i);
					int pX = c.getX();
					int pY = c.getY();
					int offX = (nY - pY) * -1;
					int offY = (nX - pX) * -1;
					c.getPA().createPlayersProjectile(nX, nY, offX, offY, 50,
							NPCHandler.getProjectileSpeed(i), NPCHandler.npcs[i].projectileId, 43,
							31, -c.getId() - 1, 65);
				}
			}
		}
	}
	
	public static void attackPlayer(Client c, int i) {
		if (NPCHandler.npcs[i] != null) {
			if (NPCHandler.npcs[i].isDead)
				return;
			if (NPCHandler.npcs[i].npcType == 1532 || NPCHandler.npcs[i].npcType == 1534 || NPCHandler.npcs[i].npcType == 6145 || NPCHandler.npcs[i].npcType == 6144 || NPCHandler.npcs[i].npcType == 6143 || NPCHandler.npcs[i].npcType == 6142) {
				return;
			}
			if (!NPCHandler.npcs[i].inMulti() && NPCHandler.npcs[i].underAttackBy > 0
					&& NPCHandler.npcs[i].underAttackBy != c.playerId) {
				NPCHandler.npcs[i].killerId = 0;
				return;
			}
			if (!NPCHandler.npcs[i].inMulti()
					&& (c.underAttackBy > 0 || (c.underAttackBy2 > 0 && c.underAttackBy2 != i))) {
				NPCHandler.npcs[i].killerId = 0;
				return;
			}
			if (NPCHandler.npcs[i].heightLevel != c.heightLevel) {
				NPCHandler.npcs[i].killerId = 0;
				return;
			}
			NPCHandler.npcs[i].facePlayer(c.playerId);
			boolean special = false;// specialCase(c,i);
			if (NPCHandler.goodDistance(NPCHandler.npcs[i].getX(), NPCHandler.npcs[i].getY(), c.getX(),
					c.getY(), NPCHandler.distanceRequired(i)) || special) {
				if (c.respawnTimer <= 0) {
					NPCHandler.npcs[i].facePlayer(c.playerId);
					NPCHandler.npcs[i].attackTimer = NpcEmotes.getNpcDelay(i);
					NPCHandler.npcs[i].hitDelayTimer = NpcEmotes.getHitDelay(i);
					NPCHandler.npcs[i].attackType = 0;
					if (special)
						loadSpell2(i);
					else
						loadSpell(c, i);
					if (NPCHandler.npcs[i].attackType == 3)
						NPCHandler.npcs[i].hitDelayTimer += 2;
					if (NPCHandler.multiAttacks(i)) {
						multiAttackGfx(i, NPCHandler.npcs[i].projectileId);
						NpcData.startAnimation(NpcEmotes.getAttackEmote(i), i);
						NPCHandler.npcs[i].oldIndex = c.playerId;
						return;
					}
					if (NPCHandler.npcs[i].projectileId > 0) {
						int nX = NPCHandler.npcs[i].getX() + NPCHandler.offset(i);
						int nY = NPCHandler.npcs[i].getY() + NPCHandler.offset(i);
						int pX = c.getX();
						int pY = c.getY();
						int offX = (nY - pY) * -1;
						int offY = (nX - pX) * -1;
						c.getPA().createPlayersProjectile(nX, nY, offX, offY,
								50, NPCHandler.getProjectileSpeed(i),
								NPCHandler.npcs[i].projectileId, 43, 31, -c.getId() - 1,
								65);
					}
					c.underAttackBy2 = i;
					c.singleCombatDelay2 = System.currentTimeMillis();
					NPCHandler.npcs[i].oldIndex = c.playerId;
					NpcData.startAnimation(NpcEmotes.getAttackEmote(i), i);
					c.getPA().removeAllWindows();
				}
			}
		}
	}
	
	public static void loadSpell2(int i) {
		NPCHandler.npcs[i].attackType = 3;
		int random = Misc.random(3);
		if (random == 0) {
			NPCHandler.npcs[i].projectileId = 393; // red
			NPCHandler.npcs[i].endGfx = 430;
		} else if (random == 1) {
			NPCHandler.npcs[i].projectileId = 394; // green
			NPCHandler.npcs[i].endGfx = 429;
		} else if (random == 2) {
			NPCHandler.npcs[i].projectileId = 395; // white
			NPCHandler.npcs[i].endGfx = 431;
		} else if (random == 3) {
			NPCHandler.npcs[i].projectileId = 396; // blue
			NPCHandler.npcs[i].endGfx = 428;
		}
	}

	public static void loadSpell(Client c, int i) {
		switch (NPCHandler.npcs[i].npcType) {
		case 2892:
			NPCHandler.npcs[i].projectileId = 94;
			NPCHandler.npcs[i].attackType = 2;
			NPCHandler.npcs[i].endGfx = 95;
			break;
		case 2894:
			NPCHandler.npcs[i].projectileId = 298;
			NPCHandler.npcs[i].attackType = 1;
			break;
		
		case 134:
		if (c.playerLevel[5] > 0) {
			c.playerLevel[5]--;
			c.getPA().refreshSkill(5);
			c.getPA().appendPoison();
			c.getCombat().resetPlayerAttack();
		}
		break;
		
		case 50:
			int random = Misc.random(4);
			if (random == 0) {
				NPCHandler.npcs[i].projectileId = 393; // red
				NPCHandler.npcs[i].endGfx = 430;
				NPCHandler.npcs[i].attackType = 3;
			} else if (random == 1) {
				NPCHandler.npcs[i].projectileId = 394; //green
				NPCHandler.npcs[i].endGfx = 429;
				NPCHandler.npcs[i].attackType = 3;
			if (c.playerLevel[5] > 0)
				c.playerLevel[5]--;
				c.getPA().refreshSkill(5);
				c.getPA().appendPoison();
				c.getCombat().resetPlayerAttack();
			} else if (random == 2) {
				NPCHandler.npcs[i].projectileId = 395; // white
				NPCHandler.npcs[i].endGfx = 431;
				NPCHandler.npcs[i].attackType = 3;
			} else if (random == 3)
				NPCHandler.npcs[i].projectileId = 396; //blue
				NPCHandler.npcs[i].endGfx = 428;
				NPCHandler.npcs[i].attackType = 3;
				if (c.freezeTimer <= 0) {
					c.freezeTimer = 30;
					c.frozenBy = c.playerId;
	                c.stopMovement();
	                c.getCombat().resetPlayerAttack();
	                c.sendMessage("You have been frozen.");
			} else if (random == 4) {
				NPCHandler.npcs[i].projectileId = -1; // melee
				NPCHandler.npcs[i].endGfx = -1;
				NPCHandler.npcs[i].attackType = 0;
			}
			break;
		// arma npcs
		case 2561:
			NPCHandler.npcs[i].attackType = 0;
			break;
		case 2560:
			NPCHandler.npcs[i].attackType = 1;
			NPCHandler.npcs[i].projectileId = 1190;
			break;
		case 2559:
			NPCHandler.npcs[i].attackType = 2;
			NPCHandler.npcs[i].projectileId = 1203;
			break;
		case 2558:
			random = Misc.random(1);
			NPCHandler.npcs[i].attackType = 1 + random;
			if (NPCHandler.npcs[i].attackType == 1) {
				NPCHandler.npcs[i].projectileId = 1197;
			} else {
				NPCHandler.npcs[i].attackType = 2;
				NPCHandler.npcs[i].projectileId = 1198;
			}
			break;
		// sara npcs
		case 2562: // sara
			random = Misc.random(1);
			if (random == 0) {
				NPCHandler.npcs[i].attackType = 2;
				NPCHandler.npcs[i].endGfx = 1224;
				NPCHandler.npcs[i].projectileId = -1;
			} else if (random == 1)
				NPCHandler.npcs[i].attackType = 0;
			break;
		case 2563: // star
			NPCHandler.npcs[i].attackType = 0;
			break;
		case 2564: // growler
			NPCHandler.npcs[i].attackType = 2;
			NPCHandler.npcs[i].projectileId = 1203;
			break;
		case 2565: // bree
			NPCHandler.npcs[i].attackType = 1;
			NPCHandler.npcs[i].projectileId = 9;
			break;
		// bandos npcs
		case 2550:
			random = Misc.random(2);
			if (random == 0 || random == 1)
				NPCHandler.npcs[i].attackType = 0;
			else {
				NPCHandler.npcs[i].attackType = 1;
				NPCHandler.npcs[i].endGfx = 1211;
				NPCHandler.npcs[i].projectileId = 288;
			}
			break;
		case 2551:
			NPCHandler.npcs[i].attackType = 0;
			break;
		case 2552:
			NPCHandler.npcs[i].attackType = 2;
			NPCHandler.npcs[i].projectileId = 1203;
			break;
		case 2553:
			NPCHandler.npcs[i].attackType = 1;
			NPCHandler.npcs[i].projectileId = 1206;
			break;
		case 2025:
			NPCHandler.npcs[i].attackType = 2;
			int r = Misc.random(3);
			if (r == 0) {
				NPCHandler.npcs[i].gfx100(158);
				NPCHandler.npcs[i].projectileId = 159;
				NPCHandler.npcs[i].endGfx = 160;
			}
			if (r == 1) {
				NPCHandler.npcs[i].gfx100(161);
				NPCHandler.npcs[i].projectileId = 162;
				NPCHandler.npcs[i].endGfx = 163;
			}
			if (r == 2) {
				NPCHandler.npcs[i].gfx100(164);
				NPCHandler.npcs[i].projectileId = 165;
				NPCHandler.npcs[i].endGfx = 166;
			}
			if (r == 3) {
				NPCHandler.npcs[i].gfx100(155);
				NPCHandler.npcs[i].projectileId = 156;
			}
			break;
		case 2881:// supreme
			NPCHandler.npcs[i].attackType = 1;
			NPCHandler.npcs[i].projectileId = 298;
			break;

		case 2882:// prime
			NPCHandler.npcs[i].attackType = 2;
			NPCHandler.npcs[i].projectileId = 162;
			NPCHandler.npcs[i].endGfx = 477;
			break;

		case 2028:
			NPCHandler.npcs[i].attackType = 1;
			NPCHandler.npcs[i].projectileId = 27;
			break;

		case 3200:
			int r2 = Misc.random(1);
			if (r2 == 0) {
				NPCHandler.npcs[i].attackType = 1;
				NPCHandler.npcs[i].gfx100(550);
				NPCHandler.npcs[i].projectileId = 551;
				NPCHandler.npcs[i].endGfx = 552;
			} else {
				NPCHandler.npcs[i].attackType = 2;
				NPCHandler.npcs[i].gfx100(553);
				NPCHandler.npcs[i].projectileId = 554;
				NPCHandler.npcs[i].endGfx = 555;
			}
			break;
		case 2745:
			int r3 = 0;
			if (NPCHandler.goodDistance(NPCHandler.npcs[i].absX, NPCHandler.npcs[i].absY,
					PlayerHandler.players[NPCHandler.npcs[i].spawnedBy].absX,
					PlayerHandler.players[NPCHandler.npcs[i].spawnedBy].absY, 1))
				r3 = Misc.random(2);
			else
				r3 = Misc.random(1);
			if (r3 == 0) {
				NPCHandler.npcs[i].attackType = 2;
				NPCHandler.npcs[i].endGfx = 157;
				NPCHandler.npcs[i].projectileId = 448;
			} else if (r3 == 1) {
				NPCHandler.npcs[i].attackType = 1;
				NPCHandler.npcs[i].endGfx = 451;
				NPCHandler.npcs[i].projectileId = -1;
			} else if (r3 == 2) {
				NPCHandler.npcs[i].attackType = 0;
				NPCHandler.npcs[i].projectileId = -1;
			}
			break;
		case 2743:
			NPCHandler.npcs[i].attackType = 2;
			NPCHandler.npcs[i].projectileId = 445;
			NPCHandler.npcs[i].endGfx = 446;
			break;

		case 2631:
			NPCHandler.npcs[i].attackType = 1;
			NPCHandler.npcs[i].projectileId = 443;
			break;
		}
	}
	
	public static void registerNpcHit(int i) {
		if (NPCHandler.npcs[i] != null) {
			if (PlayerHandler.players[NPCHandler.npcs[i].oldIndex] == null) {
				return;
			}
			if (NPCHandler.npcs[i].isDead)
				return;
			Client c = (Client) PlayerHandler.players[NPCHandler.npcs[i].oldIndex];
			if (NPCHandler.multiAttacks(i)) {
				NpcCombat.multiAttackDamage(i);
				return;
			}
			if (c.playerIndex <= 0 && c.npcIndex <= 0)
				if (c.autoRet == 1)
					c.npcIndex = i;
			if (c.attackTimer <= 3 || c.attackTimer == 0 && c.npcIndex == 0
					&& c.oldNpcIndex == 0) {
				c.startAnimation(c.getCombat().getBlockEmote());
			}
			if (c.respawnTimer <= 0) {
				int damage = 0;
				if (NPCHandler.npcs[i].attackType == 0) {
					damage = Misc.random(NPCHandler.npcs[i].maxHit);
					if (10 + Misc.random(c.getCombat().calcDef()) > Misc
							.random(NPCHandler.npcs[i].attack)) {
						damage = 0;
					}
					if (NpcData.cantKillYou(NPCHandler.npcs[i].npcType)) {
						if (damage >= c.playerLevel[Config.HITPOINTS]) {
							damage = c.playerLevel[Config.HITPOINTS] - 1;
						}
					}
					if (c.prayerActive[18]) { // protect from melee
						damage = 0;
					}
					if (c.playerLevel[3] - damage < 0) {
						damage = c.playerLevel[3];
					}
				}

				if (NPCHandler.npcs[i].attackType == 1) { // range
					damage = Misc.random(NPCHandler.npcs[i].maxHit);
					if (10 + Misc.random(c.getCombat().calculateRangeDefence()) > Misc
							.random(NPCHandler.npcs[i].attack)) {
						damage = 0;
					}
					if (NpcData.cantKillYou(NPCHandler.npcs[i].npcType)) {
						if (damage >= c.playerLevel[Config.HITPOINTS]) {
							damage = c.playerLevel[Config.HITPOINTS] - 1;
						}
					}
					if (c.prayerActive[17]) { // protect from range
						damage = 0;
					}
					if (c.playerLevel[3] - damage < 0) {
						damage = c.playerLevel[3];
					}
				}

				if (NPCHandler.npcs[i].attackType == 2) { // magic
					damage = Misc.random(NPCHandler.npcs[i].maxHit);
					boolean magicFailed = false;
					if (10 + Misc.random(c.getCombat().mageDef()) > Misc
							.random(NPCHandler.npcs[i].attack)) {
						damage = 0;
						magicFailed = true;
					}
					if (NpcData.cantKillYou(NPCHandler.npcs[i].npcType)) {
						if (damage >= c.playerLevel[Config.HITPOINTS]) {
							damage = c.playerLevel[Config.HITPOINTS] - 1;
						}
					}
					if (c.prayerActive[16]) { // protect from magic
						damage = 0;
						magicFailed = true;
					}
					if (c.playerLevel[3] - damage < 0) {
						damage = c.playerLevel[3];
					}
					if (NPCHandler.npcs[i].endGfx > 0
							&& (!magicFailed || FightCaves.isFightCaveNpc(i))) {
						c.gfx100(NPCHandler.npcs[i].endGfx);
					} else {
						c.gfx100(85);
					}
				}

				if (NPCHandler.npcs[i].attackType == 3) { // fire breath
					int anti = c.getPA().antiFire();
					if (anti == 0) {
						damage = Misc.random(30) + 10;
						c.sendMessage("You dragon fire severly injures you!");
					} else if (anti == 1)
						damage = Misc.random(20);
					else if (anti == 2)
						damage = Misc.random(5);
					if (c.playerLevel[3] - damage < 0)
						damage = c.playerLevel[3];
					c.gfx100(NPCHandler.npcs[i].endGfx);
				}
				NPCHandler.handleSpecialEffects(c, i, damage);
				c.logoutDelay = System.currentTimeMillis(); // logout delay
				c.handleHitMask(damage);
				c.playerLevel[3] -= damage;
				c.getPA().refreshSkill(3);
				FightCaves.tzKihEffect(c, i, damage);
				c.updateRequired = true;
			}
		}
	}

}
