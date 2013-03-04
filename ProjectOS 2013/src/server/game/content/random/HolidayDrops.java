package server.game.content.random;
	
	public enum HolidayDrops {
		EASTER(1961, "Easter", false),
		HALLOWEEN(1959, "Halloween", true),
		CHRISTMAS(962, "Christmas", false);
	
	private final int item;
	private final String name;
	private final boolean whichHoliday;
	public int count = 0;//count starts at 0 and ends at 400 can be changed
	public final int drops = 7;//this is just for random number of the drops
	
	private HolidayDrops(int item, String name, boolean whichHoliday) {
		this.item = item;
		this.name = name;
		this.whichHoliday = whichHoliday;
	}
	
	public int getItem() {
		return item;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getHoliday() {
		return whichHoliday;
	}
}