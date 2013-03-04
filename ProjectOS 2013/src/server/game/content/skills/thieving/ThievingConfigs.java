package server.game.content.skills.thieving;

public class ThievingConfigs {
	
	public enum Locks {//id, x, y, h, levreq, xp, lockreq, nextx, nexty
		LOCK1(69, 6969, 6969, 1, 1, 1, false, 6969, 6969);
	
	private int objectId, objectX, objectY, levelReq, xp, nextX, nextY, height;
	private boolean lockReq;
	
	private Locks(int objectId, int objectX, int objectY, int height, int levelReq, int xp, boolean lockReq, int nextX, int nextY) {
		this.objectId = objectId;
		this.objectX = objectX;
		this.objectY = objectY;
		this.height = height;
		this.levelReq = levelReq;
		this.xp = xp;
		this.lockReq = lockReq;
		this.nextX = nextX;
		this.nextY = nextY;
	}
	
	public int getObject() {
		return objectId;
	}
	
	public int getX() {
		return objectX;
	}
	
	public int getY() {
		return objectY;
	}
	
	public int getH() {
		return height;
	}
	
	public int getLevel() {
		return levelReq;
	}
	
	public int getXP() {
		return xp;
	}
	
	public boolean getReq() {
		return lockReq;
	}
	
	public int getNX() {
		return nextX;
	}
	
	public int getNY() {
		return nextY;
	}
	
	public enum Traps { //id, delete, object, lvlreq, xp, rewards
		TRAP1(6969, 1, 1, 1),
		TRAP2(6969, 1, 1, 1);
		
		private int id, levReq, exp;
		private int rewards;
	
	private Traps(int id, int levReq, int exp, int rewards) {
		this.id = id;
		this.levReq = levReq;
		this.exp = exp;
		this.rewards = rewards;
	}
	
	public int getId() {
		return id;
	}
	
	public int getReq() {
		return levReq;
	}
	
	public int getXp() {
		return exp;
	}
	
	public int getRewards() {
		return rewards;
	}
	
		}
	}
}