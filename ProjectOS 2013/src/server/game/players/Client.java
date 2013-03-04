package server.game.players;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Future;
import org.apache.mina.common.IoSession;
import server.Config;
import server.Server;
import server.net.HostList;
import server.net.Packet;
import server.net.StaticPacketBuilder;
import server.util.HostBlacklist;
import server.util.Misc;
import server.util.Stream;
import server.game.content.clicking.BankPin;
import server.game.content.clicking.EmoteHandler;
import server.game.content.combat.CombatAssistant;
import server.game.content.combat.magic.Enchanting;
import server.game.content.combat.magic.Magic;
import server.game.content.combat.melee.Specials;
import server.game.content.combat.npcs.AttackNpc;
import server.game.content.minigames.FightPits;
import server.game.content.minigames.PestControl;
import server.game.content.minigames.TradeAndDuel;
import server.game.content.minigames.castlewars.CastleWars;
import server.game.content.music.PlayList;
import server.game.content.music.Sound;
import server.game.content.questhandling.Stages;
import server.game.content.quests.free.CooksAssistant;
import server.game.content.quests.free.DoricsQuest;
import server.game.content.quests.free.ImpCatcher;
import server.game.content.quests.free.PiratesTreasure;
import server.game.content.quests.free.RestlessGhost;
import server.game.content.quests.free.RomeoJuliet;
import server.game.content.quests.free.RuneMysteries;
import server.game.content.quests.free.SheepShearer;
import server.game.content.quests.free.VampyreSlayer;
import server.game.content.quests.free.WitchsPotion;
import server.game.content.quests.member.GertrudesCat;
import server.game.content.random.Holidays;
import server.game.content.skills.SkillInterfaces;
import server.game.content.skills.cooking.Potatoes;
import server.game.content.skills.core.Agility;
import server.game.content.skills.core.Firemaking;
import server.game.content.skills.core.Fletching;
import server.game.content.skills.core.Woodcutting;
import server.game.content.skills.runecrafting.Runecrafting;
import server.game.content.skills.smithing.Smithing;
import server.game.content.skills.smithing.SmithingInterface;
import server.game.content.traveling.Desert;
import server.game.content.tutorial.StagesLoader;
import server.game.content.tutorial.TutorialIsland;
import server.game.dialogues.DialogueHandler;
import server.game.dialogues.FreeDialogues;
import server.game.items.Food;
import server.game.items.ItemAssistant;
import server.game.items.PotionMixing;
import server.game.items.Potions;
import server.game.items.Teles;
import server.game.npcs.NpcsActions;
import server.game.npcs.Pets;
import server.game.objects.ObjectsActions;
import server.game.shops.ShopAssistant;
import server.task.Task;

public class Client extends Player {

	public byte buffer[] = null;
	public Stream inStream = null, outStream = null;
	private IoSession session;
	private ItemAssistant itemAssistant = new ItemAssistant(this);
	private ShopAssistant shopAssistant = new ShopAssistant(this);
	private TradeAndDuel tradeAndDuel = new TradeAndDuel(this);
	private PlayerAssistant playerAssistant = new PlayerAssistant(this);
	private CombatAssistant combatAssistant = new CombatAssistant(this);
	private ObjectsActions actionHandler = new ObjectsActions(this);
	private NpcsActions npcs = new NpcsActions(this);
	private Queue<Packet> queuedPackets = new LinkedList<Packet>();
	private Potions potions = new Potions(this);
	private PotionMixing potionMixing = new PotionMixing(this);
	private Food food = new Food(this);
	private EmoteHandler emoteHandler = new EmoteHandler(this);
	private SkillInterfaces skillInterfaces = new SkillInterfaces(this);
	private Enchanting enchanting = new Enchanting(this);
	private DialogueHandler dialogueHandler = new DialogueHandler(this);
	private Potatoes potatoes = new Potatoes(this);
	private PlayerAction playeraction = new PlayerAction(this);
	private TutorialIsland tutIsland = new TutorialIsland();
	private Desert desert = new Desert();
	private Logs logs = new Logs(this);
	private FreeDialogues dialoguesHandler = new FreeDialogues();
	private Fletching fletching = new Fletching(this);
	private Specials specials = new Specials(this);
	private Sound sound = new Sound(this);
	
	public Sound getSound() {
		return sound;
	}

	private final HashMap<String, Object> temporary = new HashMap<String, Object>();

	public Object getTemporary(String name) {
		return temporary.get(name);
	}

	public void addTemporary(String name, Object value) {
		temporary.put(name, value);
	}

	private final PlayList playList = new PlayList(this);

	public PlayList getPlayList() {
		return playList;
	}
	
	public Specials getSA() { 
	    return specials;
	}
	
	public Fletching getFletching() {
		return fletching;
	}

	public TutorialIsland getTut() {
		return tutIsland;
	}
	
	public Potatoes getPTS() {
		return potatoes;
	}
	
	public DialogueHandler getDH() {
		return dialogueHandler;
	}
	
	public FreeDialogues getDI() {
		return dialoguesHandler;
	}
	
	public EmoteHandler getEmoteHandler() { 
		return emoteHandler;
	}
	
	public SkillInterfaces getSI() {
		return skillInterfaces;
	}
	
	public Enchanting getEnchanting() {
		return enchanting;
	}
	
	public PlayerAction getPlayerAction() { 
		return playeraction;
	}
	
	public Logs getLogs() { 
		return logs;
	}
	
	public Desert getDesert() {
		return desert;
	}
	
	private Woodcutting woodcutting = new Woodcutting(this);
	private Agility agility = new Agility(this);
	private Runecrafting runecrafting = new Runecrafting(this);
	private Pets pets = new Pets();
	private Teles teles = new Teles();
	private Firemaking firemaking = new Firemaking();
	private BankPin bankPin = new BankPin(this);
	private StagesLoader stagesLoader = StagesLoader.forId(TutorialIsland.tutorialIslandStage);
	private TutorialIsland tutorialIsland = new TutorialIsland();
	private Smithing smith = new Smithing(this);
	private SmithingInterface smithInt = new SmithingInterface(this);
	
	public Smithing getSmithing() {
		return smith;
	}
	
	public SmithingInterface getSmithingInt() {
		return smithInt;
	}
	
	
	public Woodcutting getWoodcutting() {
		return woodcutting;
	}
	
	public StagesLoader getStages() {
		return stagesLoader;
	}
	
	public Agility getAgility() {
		return agility;
	}
	
	public Runecrafting getRC() {
		return runecrafting;
	}
	
	public Firemaking getFiremaking() {
		return firemaking;
	}

	public Pets getSummon() {
		return pets;
	}
	
	public Teles getTeles() {
		return teles;
	}
	
	public BankPin getBankPin() {
        return bankPin;
    }
	
	public TutorialIsland getNewComersSide() {
		return tutorialIsland;
	}
	
	
	/*/private Fishing fish = new Fishing(this);;/*/
	
	/*/
	 * Quests
	 */

	private ImpCatcher impCatcher = new ImpCatcher(this);
	private CooksAssistant cooksAssistant = new CooksAssistant(this);
	private RomeoJuliet romeoJuliet = new RomeoJuliet(this);
	private DoricsQuest doricsQuest = new DoricsQuest(this);
	private VampyreSlayer vampyreSlayer = new VampyreSlayer(this);
	private RestlessGhost restlessGhost = new RestlessGhost(this);
	private GertrudesCat gertrudesCat = new GertrudesCat(this);
	private SheepShearer sheepShearer = new SheepShearer(this);
	private RuneMysteries runeMysteries = new RuneMysteries(this);
	private WitchsPotion witchsPotion = new WitchsPotion(this);
	private PiratesTreasure piratesTreasure = new PiratesTreasure(this);

	public ImpCatcher getIMP() {
		return impCatcher;
	}
	
	public PiratesTreasure getPT() {
		return piratesTreasure;
	}

	public CooksAssistant getCA() {
		return cooksAssistant;
	}

	public RomeoJuliet getRJ() {
		return romeoJuliet;
	}

	public DoricsQuest getDC() {
		return doricsQuest;
	}

	public VampyreSlayer getVS() {
		return vampyreSlayer;
	}

	public RestlessGhost getRG() {
		return restlessGhost;
	}

	public GertrudesCat getGC() {
		return gertrudesCat;
	}

	public SheepShearer getSS() {
		return sheepShearer;
	}

	public RuneMysteries getRM() {
		return runeMysteries;
	}
	
	public WitchsPotion getWP() {
		return witchsPotion;
	}

	/*/
	 * End of Quests
	 * 
	 */

	public int lowMemoryVersion = 0;
	public int timeOutCounter = 0;
	public int returnCode = 2;
	private Future<?> currentTask;

	public Client(IoSession s, int _playerId) {
		super(_playerId);
		this.session = s;
		synchronized (this) {
			outStream = new Stream(new byte[Config.BUFFER_SIZE]);
			outStream.currentOffset = 0;
		}
		inStream = new Stream(new byte[Config.BUFFER_SIZE]);
		inStream.currentOffset = 0;
		buffer = new byte[Config.BUFFER_SIZE];
	}
	
	/**
     * Shakes the player's screen.
     * Parameters 1, 0, 0, 0 to reset.
     * @param verticleAmount How far the up and down shaking goes (1-4).
     * @param verticleSpeed How fast the up and down shaking is.
     * @param horizontalAmount How far the left-right tilting goes.
     * @param horizontalSpeed How fast the right-left tiling goes..
     */
    public void shakeScreen(int verticleAmount, int verticleSpeed, int horizontalAmount, int horizontalSpeed) {
            outStream.createFrame(35); // Creates frame 35.
            outStream.writeByte(verticleAmount);
            outStream.writeByte(verticleSpeed);
            outStream.writeByte(horizontalAmount);
            outStream.writeByte(horizontalSpeed);
    }
    
    /**
     * Resets the shaking of the player's screen.
     */
    public void resetShaking() {
            shakeScreen(1, 0, 0, 0);
    }
    
    public void puzzleBarrow(Client c){
    	getPA().sendFrame246(4545, 250, 6833);
    	getPA().sendFrame126("1.", 4553);
    	getPA().sendFrame246(4546, 250, 6832);
    	getPA().sendFrame126("2.", 4554);
    	getPA().sendFrame246(4547, 250, 6830);
    	getPA().sendFrame126("3.", 4555);
    	getPA().sendFrame246(4548, 250, 6829);
    	getPA().sendFrame126("4.", 4556);
    	getPA().sendFrame246(4550, 250, 3454);
    	getPA().sendFrame246(4551, 250, 8746);
    	getPA().sendFrame246(4552, 250, 6830);
    	getPA().showInterface(4543);
    }

	public void flushOutStream() {
		if (disconnected || outStream.currentOffset == 0)
			return;
		synchronized (this) {
			StaticPacketBuilder out = new StaticPacketBuilder().setBare(true);
			byte[] temp = new byte[outStream.currentOffset];
			System.arraycopy(outStream.buffer, 0, temp, 0, temp.length);
			out.addBytes(temp);
			session.write(out.toPacket());
			outStream.currentOffset = 0;
		}
	}

	public void sendClan(String name, String message, String clan, int rights) {
		outStream.createFrameVarSizeWord(217);
		outStream.writeString(name);
		outStream.writeString(message);
		outStream.writeString(clan);
		outStream.writeWord(rights);
		outStream.endFrameVarSize();
	}

	public static final int PACKET_SIZES[] = { 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, // 0
			0, 0, 0, 0, 8, 0, 6, 2, 2, 0, // 10
			0, 2, 0, 6, 0, 12, 0, 0, 0, 0, // 20
			0, 0, 0, 0, 0, 8, 4, 0, 0, 2, // 30
			2, 6, 0, 6, 0, -1, 0, 0, 0, 0, // 40
			0, 0, 0, 12, 0, 0, 0, 8, 8, 12, // 50
			8, 8, 0, 0, 0, 0, 0, 0, 0, 0, // 60
			6, 0, 2, 2, 8, 6, 0, -1, 0, 6, // 70
			0, 0, 0, 0, 0, 1, 4, 6, 0, 0, // 80
			0, 0, 0, 0, 0, 3, 0, 0, -1, 0, // 90
			0, 13, 0, -1, 0, 0, 0, 0, 0, 0,// 100
			0, 0, 0, 0, 0, 0, 0, 6, 0, 0, // 110
			1, 0, 6, 0, 0, 0, -1, 0, 2, 6, // 120
			0, 4, 6, 8, 0, 6, 0, 0, 0, 2, // 130
			0, 0, 0, 0, 0, 6, 0, 0, 0, 0, // 140
			0, 0, 1, 2, 0, 2, 6, 0, 0, 0, // 150
			0, 0, 0, 0, -1, -1, 0, 0, 0, 0,// 160
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 170
			0, 8, 0, 3, 0, 2, 0, 0, 8, 1, // 180
			0, 0, 12, 0, 0, 0, 0, 0, 0, 0, // 190
			2, 0, 0, 0, 0, 0, 0, 0, 4, 0, // 200
			4, 0, 0, 0, 7, 8, 0, 0, 10, 0, // 210
			0, 0, 0, 0, 0, 0, -1, 0, 6, 0, // 220
			1, 0, 0, 0, 6, 0, 6, 8, 1, 0, // 230
			0, 4, 0, 0, 0, 0, -1, 0, -1, 4,// 240
			0, 0, 6, 6, 0, 0, 0 // 250
	};

	public void destruct() {
		if (session == null)
			return;
		if (CastleWars.isInCwWait(this)) {
		    CastleWars.leaveWaitingRoom(this);
		}
		if (CastleWars.isInCw(this)) {
		    CastleWars.removePlayerFromCw(this);
		}
		if (FightPits.getState(this) != null) {
		    FightPits.removePlayer(this, true);
		}
		if (PestControl.isInPcBoat(this)) {
	        PestControl.removePlayerGame(this);
	        getPA().movePlayer(2440, 3089, 0);
	    }
	    if (hasNpc == true)
			getSummon().pickUpClean(this, summonId);
		Misc.println("[DEREGISTERED]: " + playerName + "");
		HostList.getHostList().remove(session);
		disconnected = true;
		session.close();
		session = null;
		inStream = null;
		outStream = null;
		isActive = false;
		buffer = null;
		super.destruct();
	}

	public void sendMessage(String s) {
		synchronized (this) {
			if (getOutStream() != null) {
				outStream.createFrameVarSize(253);
				outStream.writeString(s);
				outStream.endFrameVarSize();
			}
		}
	}

	public void setSidebarInterface(int menuId, int form) {
		synchronized (this) {
			if (getOutStream() != null) {
				outStream.createFrame(71);
				outStream.writeWord(form);
				outStream.writeByteA(menuId);
			}
		}
	}
	
	  public void playMusic(int song) {
		  if (musicOn) {
	            outStream.createFrame(74);
	            outStream.writeWordBigEndian(song);
	        }
	    }
	
	public void flashSideBarIcon(int barId) {//not sure if works
		outStream.createFrame(3);
		outStream.createFrame(24);
		outStream.writeByte(-barId);
	}

	public void initialize() {
		synchronized (this) { //i tried a few different clients but any suggestions on a new client to try? Galkons refractored find dis shit
			outStream.createFrame(249);
			outStream.writeByteA(0);
			outStream.writeWordBigEndianA(playerId);
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (j == playerId)
					continue;
				if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].playerName.equalsIgnoreCase(playerName))
					disconnected = true;
				}
			}
			if(hasNpc == true) {
				if (summonId > 0) {
					Server.npcHandler.spawnNpc3(this, summonId, absX, absY-1, heightLevel, 0, 120, 25, 200, 200, true, false, true);	
				}
			}
			if (playerHitpoints < 0) {
				isDead = true;
			}
			for (int i = 0; i < 25; i++) {
				getPA().setSkillLevel(i, playerLevel[i], playerXP[i]);
				getPA().refreshSkill(i);
			}
			for (int p = 0; p < PRAYER.length; p++) { // reset prayer glows
				prayerActive[p] = false;
				getPA().sendFrame36(PRAYER_GLOW[p], 0);
			}
			if (splitChat == true) {
				getPA().sendFrame36(502, 1);
				getPA().sendFrame36(287, 1);
			} else {
				getPA().sendFrame36(502, 0);
				getPA().sendFrame36(287, 0);
			}
			if (playerRights == 4 || playerRights < 1) {
				Config.BANK_SIZE = 352;
			} else {
				Config.BANK_SIZE = 100;
			}
			TutorialIsland.tutorialIslandStage = 66;
			if(TutorialIsland.tutorialIslandStage < 65) {
				getNewComersSide().setTutorialIslandStage(this, TutorialIsland.getTutorialIslandStage(), true);
			}
			if (addStarter && TutorialIsland.getTutorialIslandStage() > 65) {
				TutorialIsland.addStarterItems(this);
			}
			if (TutorialIsland.getTutorialIslandStage() == 0) {
				getTut().startTutorial(this);
				getTut().setTutorialIslandStage(this, TutorialIsland.getTutorialIslandStage(), true);
			if (StagesLoader.forId(TutorialIsland.getTutorialIslandStage()) != null)
				TutorialIsland.enableSideBarInterfaces(this, StagesLoader.forId(TutorialIsland.getTutorialIslandStage()).getSideBar());
			} else {
				TutorialIsland.showAllSideBars(this);
				getItems().sendWeapon(playerEquipment[playerWeapon], getItems().getItemName(playerEquipment[playerWeapon]));
			}
			sendMessage("Welcome to ProjectOS.");
			sendMessage("Magic teleport requirements have been removed for a while.");
			sendMessage("Visit our forums at projectos.forum.st & apply for staff!");
			if (!hasBankpin) {
				sendMessage("You do not have a bank pin. It is highly recommeneded that you get one.");
			}
			if (playerRights > 1 && Config.HOLIDAYS) {
				Holidays.message(this);
				Config.HOLIDAYS = false;
			}
			getPlayList().fixAllColors();
			attackTimer(this);
			getPlayerAction().setAction(false);
			getPlayerAction().canWalk(true);
			loadQuests(this);
			HostBlacklist.loadBlacklist();
			getPA().handleWeaponStyle();
			Magic.handleLoginText(this);
			accountFlagged = getPA().checkForFlags();
			getPA().sendFrame36(108, 0);// resets autocast button
			getPA().sendFrame36(172, 1);
			getPA().sendFrame107(); // reset screen
			getPA().setChatOptions(0, 0, 0); // reset private messaging options
			correctCoordinates();
			getPA().showOption(4, 0, "Trade With", 3);
			getPA().showOption(5, 0, "Follow", 4);
			getItems().resetItems(3214);
			getItems().resetBonus();
			getItems().getBonus();
			getItems().writeBonus();
			getItems().setEquipment(playerEquipment[playerHat], 1, playerHat);
			getItems().setEquipment(playerEquipment[playerCape], 1, playerCape);
			getItems().setEquipment(playerEquipment[playerAmulet], 1,
					playerAmulet);
			getItems().setEquipment(playerEquipment[playerArrows],
					playerEquipmentN[playerArrows], playerArrows);
			getItems().setEquipment(playerEquipment[playerChest], 1,
					playerChest);
			getItems().setEquipment(playerEquipment[playerShield], 1,
					playerShield);
			getItems().setEquipment(playerEquipment[playerLegs], 1, playerLegs);
			getItems().setEquipment(playerEquipment[playerHands], 1,
					playerHands);
			getItems().setEquipment(playerEquipment[playerFeet], 1, playerFeet);
			getItems().setEquipment(playerEquipment[playerRing], 1, playerRing);
			getItems().setEquipment(playerEquipment[playerWeapon],
					playerEquipmentN[playerWeapon], playerWeapon);
			getCombat().getPlayerAnimIndex();
			getPA().logIntoPM();
			getItems().addSpecialBar(playerEquipment[playerWeapon]);
			saveTimer = Config.SAVE_TIMER;
			saveCharacter = true;
			Misc.println("[REGISTERED]: " + playerName + "");
			handler.updatePlayer(this, outStream);
			handler.updateNPC(this, outStream);
			flushOutStream();
			getPA().resetFollow();
			if (autoRet == 1)
				getPA().sendFrame36(172, 1);
			else
				getPA().sendFrame36(172, 0);
		}
	}

	public void update() {
		synchronized (this) {
			handler.updatePlayer(this, outStream);
			handler.updateNPC(this, outStream);
			flushOutStream();
		}
	}

	public void logout() {
		synchronized (this) {
			if (CastleWars.isInCw(this)) {
                CastleWars.removePlayerFromCw(this);
            }
            if (CastleWars.isInCwWait(this)) {
                CastleWars.leaveWaitingRoom(this);
            }
            if (FightPits.getState(this) != null) {
    		    FightPits.removePlayer(this, true);
    		}
            if (PestControl.isInPcBoat(this)) {
                PestControl.removePlayerGame(this);
                getPA().movePlayer(2440, 3089, 0);
            }
			if (hasNpc == true)
				getSummon().pickUpClean(this, summonId);
			if (System.currentTimeMillis() - logoutDelay > 10000) {
				outStream.createFrame(109);
				properLogout = true;
			} else {
				sendMessage("You must wait a few seconds from being out of combat to logout.");
			}
		}
	}
	
	public int packetSize = 0, packetType = -1;
	public boolean WildernessWarning = false;
	
	public void antiFirePotion(){
		Server.getTaskScheduler().schedule(new Task(200) {
			@Override
			protected void execute() {
				antiFirePot = false;	
				sendMessage("Your resistance to dragon fire has worn off.");
				stop();
			}
		});
	}
	
				public void loadQuests(final Client c) {
				Server.getTaskScheduler().schedule(new Task(1) {
				@Override
				protected void execute() {
				if (c.disconnected) {
					stop();
					return;
				}
				Stages.stage(c);
				}
			});
		}
				
				public void attackTimer(final Client c) {
					Server.getTaskScheduler().schedule(new Task(1) {
						@Override
						protected void execute() {
					if (attackTimer == 1) {
						if (npcIndex > 0 && clickNpcType == 0) {
							AttackNpc.attackNpc(c, npcIndex);
						}
						if (playerIndex > 0) {
							getCombat().attackPlayer(playerIndex);
						}
					} else if (attackTimer <= 0 && (npcIndex > 0 || playerIndex > 0)) {
						if (npcIndex > 0) {
							attackTimer = 0;
							AttackNpc.attackNpc(c, npcIndex);
						} else if (playerIndex > 0) {
							attackTimer = 0;
							getCombat().attackPlayer(playerIndex);
						}
					}
				}
			});
		}
						
				public boolean isBusy = false;
				public int ismemb;
				
				public boolean checkBusy() {
					return isBusy;
				}

				public void setBusy(boolean isBusy) {
					this.isBusy = isBusy;
				}

				public boolean isBusy() {
					return isBusy;
				}
				
				public void updateWalkEntities() {	
					if(inWild() && !inCw()) {
						int modY = absY > 6400 ?  absY - 6400 : absY;
						wildLevel = (((modY - 3520) / 8) + 1);
						getPA().walkableInterface(197);
						if(Config.SINGLE_AND_MULTI_ZONES) {
							if(inMulti()) {
								getPA().sendFrame126("@yel@Level: "+wildLevel, 199);
							} else {
								getPA().sendFrame126("@yel@Level: "+wildLevel, 199);
							}
						} else {
							getPA().multiWay(-1);
							getPA().sendFrame126("@yel@Level: "+wildLevel, 199);
						}
						getPA().showOption(3, 0, "Attack", 1);
						if (WildernessWarning == false) {
							resetWalkingQueue();
							WildernessWarning = true;
							getPA().sendFrame126("WARNING!", 6940);
							getPA().showInterface(1908);
						}
					} else if (inDuelArena()) {
						getPA().walkableInterface(201);
						if (duelStatus == 5) {
							getPA().showOption(3, 0, "Attack", 1);
						} else {
							//getPA().showOption(3, 0, "Challenge", 1);
						}
					} else if (inPits) {
					    getPA().showOption(3, 0, "Attack", 1);      
					} else if (getPA().inPitsWait()) {
					    getPA().showOption(3, 0, "Null", 1);
					} else if (inBarrows()) {
						getPA().sendFrame126("Kill Count: " + barrowsKillCount, 4536);
						getPA().walkableInterface(4535);
				    } else if (inCw()) {
				            getPA().showOption(3, 0, "Attack", 1);
				    } else {
				    	getPA().sendFrame99(0);
						getPA().walkableInterface(-1);
						getPA().showOption(3, 0, "Null", 1);
					}
				}

	public void process() {
		
		if (runEnergy < 100) {
			if (System.currentTimeMillis() > Agility.getAgilityRunRestore(this) + lastRunRecovery) {
				runEnergy++;
				lastRunRecovery = System.currentTimeMillis();
				getPA().sendFrame126(runEnergy+"%", 149);
			}
		}

		if (System.currentTimeMillis() - specDelay > Config.INCREASE_SPECIAL_AMOUNT) {
			specDelay = System.currentTimeMillis();
			if (specAmount < 10) {
				specAmount += .5;
				if (specAmount > 10)
					specAmount = 10;
				getItems().addSpecialBar(playerEquipment[playerWeapon]);
			}
		}

		if (followId > 0) {
			getPA().followPlayer();
		} else if (followId2 > 0) {
			getPA().followNpc();
		}

		getCombat().handlePrayerDrain();

		if (System.currentTimeMillis() - singleCombatDelay > 3300) {
			underAttackBy = 0;
		}
		if (System.currentTimeMillis() - singleCombatDelay2 > 3300) {
			underAttackBy2 = 0;
		}

		if (System.currentTimeMillis() - restoreStatsDelay > 60000) {
			restoreStatsDelay = System.currentTimeMillis();
			for (int level = 0; level < playerLevel.length; level++) {
				if (playerLevel[level] < getLevelForXP(playerXP[level])) {
					if (level != 5) { // prayer doesn't restore
						playerLevel[level] += 1;
						getPA().setSkillLevel(level, playerLevel[level],
								playerXP[level]);
						getPA().refreshSkill(level);
					}
				} else if (playerLevel[level] > getLevelForXP(playerXP[level])) {
					playerLevel[level] -= 1;
					getPA().setSkillLevel(level, playerLevel[level],
							playerXP[level]);
					getPA().refreshSkill(level);
				}
			}
		}
		
		if (!hasMultiSign && inMulti()) {
			hasMultiSign = true;
			getPA().multiWay(1);
		}

		if (hasMultiSign && !inMulti()) {
			hasMultiSign = false;
			getPA().multiWay(-1);
		}

		if (skullTimer > 0) {
			skullTimer--;
			if (skullTimer == 1) {
				isSkulled = false;
				attackedPlayers.clear();
				headIconPk = -1;
				skullTimer = -1;
				getPA().requestUpdates();
			}
		}

		if (isDead && respawnTimer == -6) {
			getPA().applyDead();
		}

		if (respawnTimer == 7) {
			respawnTimer = -6;
			getPA().giveLife();
		} else if (respawnTimer == 12) {
			respawnTimer--;
			startAnimation(0x900);
			poisonDamage = -1;
		}

		if (respawnTimer > -6) {
			respawnTimer--;
		}
		if (freezeTimer > -6) {
			freezeTimer--;
			if (frozenBy > 0) {
				if (PlayerHandler.players[frozenBy] == null) {
					freezeTimer = -1;
					frozenBy = -1;
				} else if (!goodDistance(absX, absY,
						PlayerHandler.players[frozenBy].absX,
						PlayerHandler.players[frozenBy].absY, 20)) {
					freezeTimer = -1;
					frozenBy = -1;
				}
			}
		}

		if (hitDelay > 0) {
			hitDelay--;
		}

		if (teleTimer > 0) {
			teleTimer--;
			if (!isDead) {
				if (teleTimer == 1 && newLocation > 0) {
					teleTimer = 0;
					getPA().changeLocation();
				}
				if (teleTimer == 5) {
					teleTimer--;
					getPA().processTeleport();
				}
				if (teleTimer == 9 && teleGfx > 0) {
					teleTimer--;
					gfx100(teleGfx);
				}
			} else {
				teleTimer = 0;
			}
		}

		if (hitDelay == 1) {
			if (oldNpcIndex > 0) {
				getCombat().delayedHit(oldNpcIndex);
			}
			if (oldPlayerIndex > 0) {
				getCombat().playerDelayedHit(oldPlayerIndex);
			}
		}

		if (attackTimer > 0) {
			attackTimer--;
		}

		if (timeOutCounter > Config.TIMEOUT) {
			disconnected = true;
		}

		timeOutCounter++;
	}

	public void setCurrentTask(Future<?> task) {
		currentTask = task;
	}

	public Future<?> getCurrentTask() {
		return currentTask;
	}

	public synchronized Stream getInStream() {
		return inStream;
	}

	public synchronized int getPacketType() {
		return packetType;
	}

	public synchronized int getPacketSize() {
		return packetSize;
	}

	public synchronized Stream getOutStream() {
		return outStream;
	}

	public ItemAssistant getItems() {
		return itemAssistant;
	}

	public PlayerAssistant getPA() {
		return playerAssistant;
	}

	public ShopAssistant getShops() {
		return shopAssistant;
	}

	public TradeAndDuel getTradeAndDuel() {
		return tradeAndDuel;
	}

	public CombatAssistant getCombat() {
		return combatAssistant;
	}

	public ObjectsActions getObjects() {
		return actionHandler;
	}
	
	public NpcsActions getNpcs() {
		return npcs;
	}

	public IoSession getSession() {
		return session;
	}

	public Potions getPotions() {
		return potions;
	}

	public PotionMixing getPotMixing() {
		return potionMixing;
	}

	public Food getFood() {
		return food;
	}
	

	/**
	 * Skill Constructors
	 */
	
	/*/
	public Cooking getCooking() {
		return cooking;
	}

	public Fishing getFishing() {
		return fish;
	}

	public Crafting getCrafting() {
		return crafting;
	}

	public Thieving getThieving() {
		return thieving;
	}

	public Herblore getHerblore() {
		return herblore;
	}

	public SmithingInterface getSmithingInt() {
		return smithInt;
	}

	public Prayer getPrayer() {
		return prayer;
	}

	public Fletching getFletching() {
		return fletching;
	}/*/

	/**
	 * End of Skill Constructors
	 */

	public void queueMessage(Packet arg1) {
		// synchronized(queuedPackets) {
		// if (arg1.getId() != 41)
		queuedPackets.add(arg1);
		// else
		// processPacket(arg1);
		// }
	}

	public synchronized boolean processQueuedPackets() {
		Packet p = null;
		synchronized (queuedPackets) {
			p = queuedPackets.poll();
		}
		if (p == null) {
			return false;
		}
		inStream.currentOffset = 0;
		packetType = p.getId();
		packetSize = p.getLength();
		inStream.buffer = p.getData();
		if (packetType > 0) {
			// sendMessage("PacketType: " + packetType);
			PacketHandler.processPacket(this, packetType, packetSize);
		}
		timeOutCounter = 0;
		return true;
	}

	public synchronized boolean processPacket(Packet p) {
		synchronized (this) {
			if (p == null) {
				return false;
			}
			inStream.currentOffset = 0;
			packetType = p.getId();
			packetSize = p.getLength();
			inStream.buffer = p.getData();
			if (packetType > 0) {
				// sendMessage("PacketType: " + packetType);
				PacketHandler.processPacket(this, packetType, packetSize);
			}
			timeOutCounter = 0;
			return true;
		}
	}

	public void correctCoordinates() {
		if (inPcGame()) {
			getPA().movePlayer(2657, 2639, 0);
			if (FightPitsArea()) {
	            getPA().movePlayer(2399, 5178, 0);
			if (inFightCaves()) {
				getPA().movePlayer(absX, absY, playerId * 4);
				sendMessage("Your wave will start in 10 seconds.");
				  Server.getTaskScheduler().schedule(new Task(16) {
                      @Override
                      protected void execute() {
						Server.fightCaves.spawnNextWave((Client)PlayerHandler.players[playerId]);
						stop();
                      }
				  });
				}
			}
		}
	}
}
