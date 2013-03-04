package server.game.content.skills.content.slayer;

/**
 * Slayer need to finish
 * @author Andrew
 */

public class SlayerInfo {
	
	public static int taskAmount;
	public static String slayerMaster = "none";
	public static String taskMonster = "none";
	public static String Shilo = "Shilo Village";
	public static String Zanaris = "Lost City";
	
	  public static final String BURTHORPE_ASSIGNMENTS[] = {
	        "banshee", "bat", "bear", "bird", "cave bug", "cave crawler", "cave slime", "cave crawler", "cow", "crawling hand",
	        "dog", "dwarf", "ghost", "goblin", "kalphite", "monkey", "moss giant", "skeleton", "spider", "werewolf",
	        "wolf", "zombie", "rat", "scorpion"
	    };
	    public static final String CANIFIS_ASSIGNMENTS[] = {
	        "killerwatt", "banshee", "basilisk", "bat", "bloodveld", "blue dragon", "bronze dragon", "cave bug", "cave crawler", "cave slime",
	        "cockatrice", "crawling hand", "dagannoth", "desert lizard", "dog", "ghoul", "green dragon", "earth warrior", "elf", "hellhound",
	        "hobgoblin", "ice giant", "infernal mage", "jelly", "kalphite", "lesser demon", "moss giant", "mogre", "ogre", "pyrefiend",
	        "rockslug", "shadow warrior", "skeleton", "shade", "troll", "vampire", "werewolf"
	    };
	    public static final String EDGEVILLE_DUNGEON_ASSIGNMENTS[] = {
	        "zygomite", "aberrant specter", "banshee", "basilisk", "blue dragon", "bloodveld", "bronze dragon", "cave bug", "cockatrice", "crawling hand",
	        "crocodile", "dagannoth", "desert lizard", "dust devil", "earth warrior", "elf", "fire giant", "goblin", "green dragon", "ghoul",
	        "harpie bug swarm", "hellhound", "hill giant", "ice giant", "ice warrior", "infernal mage", "jelly", "kalphite", "kurask", "lesser demon",
	        "moss giant", "ogre", "pyrefiend", "rockslug", "shade", "shadow warrior", "troll", "turoth", "vampire", "wall beast",
	        "werewolf"
	    };
	    public static final String ZANARIS_ASSIGNMENTS[] = {
	        "aberrant specter", "abyssal demon", "banshee", "basilisk", "black demon", "bronze dragon", "bloodveld", "blue dragon", "cave bug", "cave crawler",
	        "cave slime", "cockatrice", "crawling hand", "dagannoth", "desert lizard", "dust devil", "elf", "fever spider", "fire giant", "gargoyle",
	        "greater demon", "hellhound", "iron dragon", "infernal mage", "jelly", "kalphite", "kurask", "lesser demon", "nechryael", "pyrefiend",
	        "rockslug", "shadow warrior", "steel dragon", "troll", "turoth", "wall beast"
	    };
	    public static final String SHILO_VILLAGE_ASSIGNMENTS[] = {
	        "skeletal wyvern", "dark beast", "aberrant specter", "abyssal demon", "banshee", "basilisk", "black demon", "black dragon", "bloodveld", "bronze dragon",
	        "cave crawler", "cockatrice", "desert lizard", "dust devil", "elf", "fever spider", "gargoyle", "greater demon", "infernal mage", "iron dragon",
	        "jelly", "kalphite", "kurask", "lesser demon", "nechryael", "pyrefiend", "steel dragon", "troll", "turoth"
	    };
	
	public enum SlayerMasters {
	//name, level, quest, hardness, task
	TURAEL("Turael", 1, 1, 100, SlayerInfo.BURTHORPE_ASSIGNMENTS);
	
	private String name;
	private String[] tasks;
	private int level, quest, hardness;
	
	private SlayerMasters(String name, int level, int quest, int hardness, String[] tasks) {
		this.name = name;
		this.level = level;
		this.tasks = tasks;
		this.quest = quest;
		this.hardness = hardness;
	}
	
	public int getHard() {
		return hardness;
	}
	
	public int getQuest() {
		return quest;
	}
	
	public String[] getTask() {
		return tasks;
	}
	
	public String getName() {
		return name;
	}
	
	public int getLevel() {
		return level;
	}
}
	
	public enum SlayerTasks {
			//name, level
			CRAWLING_HANDS("crawling hand", 5),
	        CAVE_BUGS("cave bug", 7),
	        CAVE_CRAWLER("cave crawler", 10),
	        BANSHEE("banshee", 15),
	        CAVE_SLIME("cave slime", 17),
	        ROCKSLUG("rockslug", 20),
	        DESERT_LIZARD("desert lizard", 22),
	        COCKATRICE("cockatrice", 25),
	        PYREFIEND("pyrefiend", 30),
	        MOGRE("mogre", 32),
	        HARPIE_BUG_SWARM("harpie bug swarm", 33),
	        WALL_BEAST("wall beast", 35),
	        KILLERWATT("killerwatt", 37),
	        BASILISK("basilisk", 40),
	        FEVER_SPIDER("fever spider", 42),
	        INFERNAL_MAGE("infernal mage", 45),
	        BLOODVELD("bloodveld", 50),
	        JELLY("jelly", 52),
	        TUROTH("turoth", 55),
	        ZYGOMITE("zygomite", 57),
	        ABERRANT_SPECTER("aberrant specter", 60),
	        DUST_DEVIL("dust devil", 65),
	        KURASK("kurask", 70),
	        SKELETAL_WYVERNS("skeletal wyvern", 72),
	        GARGOYLE("gargoyle", 75),
	        NECHRYAL("nechryael", 80),
	        ABYSSAL_DEMON("abyssal demon", 85),
	        DARK_BEAST("dark beast", 90);
	        
			private String monsterName;		
	        private int levelReq;
	        
	        private SlayerTasks(String monsterName, int levelReq) {
	        	this.monsterName = monsterName;
	        	this.levelReq = levelReq;
	        }
	        
	        public String getMonster() {
	        	return monsterName;
	        }
	        
	        public int getLevel() {
	        	return levelReq;
	        }
		}
}