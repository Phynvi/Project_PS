package org.rs2server.rs2.model.skills;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.rs2server.rs2.Constants;
import org.rs2server.rs2.clipping.TileControl;
import org.rs2server.rs2.model.pathfinder.DumbPathFinder;
import org.rs2server.rs2.model.Animation;
import org.rs2server.rs2.model.Item;
import org.rs2server.rs2.model.ItemDefinition;
import org.rs2server.rs2.model.Location;
import org.rs2server.rs2.model.Player;
import org.rs2server.rs2.model.Skills;
import org.rs2server.rs2.model.World;
import org.rs2server.rs2.tickable.Tickable;
import org.rs2server.util.Misc;
import org.rs2server.util.XMLController;

/**
 * 
 * @author Killamess
 * 
 */
public class Fishing {

	/**
	 * 
	 * @author Killamess
	 * 
	 */
	public class Catches {

		private int fishingSpot;
		private int animation;
		private int[] catches = new int[3];
		private int[] tools = new int[2];
		private int[] catchLevel = new int[3];
		private int[] toolAmounts = new int[2];

		public Catches() {
		}

		public int getAnimation() {
			return animation;
		}

		public int[] getCatches() {
			return catches;
		}

		public int[] getCatchLevels() {
			return catchLevel;
		}

		public int getFishingSpot() {
			return fishingSpot;
		}

		public int[] getToolAmounts() {
			return toolAmounts;
		}

		public int[] getTools() {
			return tools;
		}
	}

	private static Fishing singleton = null;

	private static final int fishExp(int fish) {
		switch (fish) {
		case 317: // Raw_shrimps
			return 10;
		case 321: // Raw_anchovies
			return 40;
		case 327: // Raw_sardine
			return 20;
		case 331: // Raw_salmon
			return 70;
		case 335: // Raw_trout
			return 50;
		case 338: // Raw_giant_carp
			return 0;
		case 341: // Raw_cod
			return 45;
		case 345: // Raw_herring
			return 30;
		case 349: // Raw_pike
			return 60;
		case 353: // Raw_mackerel
			return 20;
		case 359: // Raw_tuna
			return 80;
		case 363: // Raw_bass
			return 100;
		case 371: // Raw_swordfish
			return 100;
		case 377: // Raw_lobster
			return 90;
		case 7944: // Raw Monkfish
			return 120;
		case 383: // Raw_shark
			return 110;
		case 389: // Raw_manta_ray
			return 150;
		case 395: // Raw_sea_turtle
			return 120;
		default:
			return 30;
		}
	}

	/**
	 * 
	 * Item.getDefinition.getName is not always correct. For example:
	 * "You catch a lobster" is correct. "You catch a raw lobster" is incorrect.
	 * Yours truly, Sneakyhearts.
	 * 
	 * @param fish
	 *            the fish caught or to catch.
	 * @return the correct name to append to string.
	 */
	private static String getFishName(int fish) {
		switch (fish) {
		case 317: // Raw_shrimps
			return "shrimp";
		case 321: // Raw_anchovies
			return "anchovies";
		case 327: // Raw_sardine
			return "sardine";
		case 331: // Raw_salmon
			return "salmon";
		case 335: // Raw_trout
			return "trout";
		case 338: // Raw_giant_carp
			return "giant carp";
		case 341: // Raw_cod
			return "cod";
		case 345: // Raw_herring
			return "herring";
		case 349: // Raw_pike
			return "pike";
		case 353: // Raw_mackerel
			return "mackerel";
		case 359: // Raw_tuna
			return "tuna";
		case 363: // Raw_bass
			return "bass";
		case 371: // Raw_swordfish
			return "swordfish";
		case 377: // Raw_lobster
			return "lobster";
		case 7944: // Raw Monkfish
			return "monkfish";
		case 383: // Raw_shark
			return "shark";
		case 389: // Raw_manta_ray
			return "manta ray";
		case 395: // Raw_sea_turtle
			return "sea turtle";
		default:
			return "if this message appears please make a bug report to administration.";
		}
	}

	private ArrayList<Permit> fishingPermits = new ArrayList<Permit>();

	private ArrayList<Catches> definitions;

	private static final Logger logger = Logger.getLogger(Fishing.class
			.getName());

	public static Fishing getSingleton() {
		if (singleton == null) {
			singleton = new Fishing();
		}
		return singleton;
	}

	private Catches fish(int id) {
		for (Catches c : definitions) {
			if (c.getFishingSpot() == id) {
				return c;
			}
		}
		return null;
	}

	private boolean generateSuccess(int level, int levelReq) {
		if (level - 25 > levelReq) {
			return true;
		}
		if (level - 20 > levelReq) {
			return Misc.random(1) == 0;
		}
		if (level - 15 > levelReq) {
			return Misc.random(2) == 0;
		}
		if (level - 10 > levelReq) {
			return Misc.random(3) == 0;
		}
		if (level - 5 > levelReq) {
			return Misc.random(4) == 0;
		}
		return Misc.random(5) == 0;
	}

	public void init() throws IOException {
		if (definitions != null) {
			throw new IllegalStateException(
					"Fishing definitions already loaded.");
		}
		logger.info("Loading fishing definitions...");
		try {
			File file = new File("data/fish.xml");
			if (file.exists()) {
				definitions = XMLController.readXML(file);
				logger.info("Loaded " + definitions.size()
						+ " fishing definitions.");
			} else {
				logger.info("Fishing definitions not found!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean loop(Permit fishingPermit) {
		Player player = null;

		if (fishingPermit.getMob().isPlayer()) {
			player = (Player) fishingPermit.getMob();
		}
		if (player == null || player.getAttribute("busy") != null) {
			return false;
		}
		if (DumbPathFinder.standingDiagonal(player)
				|| TileControl.calculateDistance(player.getLocation(),
						fishingPermit.getLocation()) != 1) {
			return false;
		}
		if (player.getInventory().freeSlots() == 0) {
			player.getActionSender().sendMessage(
					"You do not have enough space in your inventory.");
			return false;
		}
		int index = fishingPermit.getValue();
		int mainTool = fish(index).getTools()[0];
		int nextTool = fish(index).getTools()[1];

		if (!player.getInventory().contains(mainTool)) {
			player.getActionSender()
					.sendMessage(
							"You need a "
									+ ItemDefinition.getDefinitions()[mainTool]
											.getName().toLowerCase()
									+ " to fish here.");
			return false;
		}
		if (nextTool != -1 && !player.getInventory().contains(nextTool)) {
			player.getActionSender()
					.sendMessage(
							"You need a "
									+ ItemDefinition.getDefinitions()[nextTool]
											.getName().toLowerCase()
									+ " to fish here.");
			return false;
		}
		if (player.getSkills().getLevel(Skills.FISHING) < fish(index)
				.getCatchLevels()[0]) {
			player.getActionSender().sendMessage(
					"You need a fishing level of "
							+ fish(index).getCatchLevels()[0]
							+ " to fish here.");
			return false;
		}
		int count = -1;

		if (player.getSkills().getLevel(Skills.FISHING) >= fish(index)
				.getCatchLevels()[0] && fish(index).getCatchLevels()[0] != -1) {
			count++;
		}
		if (player.getSkills().getLevel(Skills.FISHING) >= fish(index)
				.getCatchLevels()[1] && fish(index).getCatchLevels()[1] != -1) {
			count++;
		}
		if (player.getSkills().getLevel(Skills.FISHING) >= fish(index)
				.getCatchLevels()[2] && fish(index).getCatchLevels()[2] != -1) {
			count++;
		}
		if (generateSuccess(player.getSkills().getLevel(Skills.FISHING),
				fish(index).getCatchLevels()[count])) {
			reward(fishingPermit, count);
		} else {
			player.playAnimation(Animation
					.create(fish(index).getAnimation(), 0));
		}
		if (player.getInventory().freeSlots() == 0) {
			player.getActionSender().sendMessage(
					"You do not have enough space in your inventory.");
			return false;
		}
		if (!player.getInventory().contains(mainTool)) {
			player.getActionSender()
					.sendMessage(
							"You need a "
									+ ItemDefinition.getDefinitions()[mainTool]
											.getName().toLowerCase()
									+ " to fish here.");
			return false;
		}
		if (nextTool != -1 && !player.getInventory().contains(nextTool)) {
			player.getActionSender()
					.sendMessage(
							"You need a "
									+ ItemDefinition.getDefinitions()[nextTool]
											.getName().toLowerCase()
									+ " to fish here.");
			return false;
		}
		return true;
	}

	private void reward(Permit fishingPermit, int theCount) {
		if (theCount < 0 || theCount >= 3) {
			return;
		}

		Player player = null;

		if (fishingPermit.getMob().isPlayer()) {
			player = (Player) fishingPermit.getMob();
		}
		int index = fishingPermit.getValue();

		if (player == null || fish(index) == null) {
			return;
		}
		int startOn = 0;
		int endOn = (startOn + Misc.random(theCount));

		while (fish(index).getCatches()[endOn] == -1) {
			if (endOn > 0) {
				endOn--;
			}
		}

		Item item = new Item(fish(index).getCatches()[endOn]);
		player.getInventory().add(item);

		String word = "a";
		if (getFishName(fish(index).getCatches()[endOn]).endsWith("s")) {
			word = "some";
		}
		player.getActionSender().sendMessage(
				"You catch " + word + " "
						+ getFishName(fish(index).getCatches()[endOn]) + ".");
		player.playAnimation(Animation.create(fish(index).getAnimation()));

		int toolId = fish(index).getTools()[1];
		int toolAmount = fish(index).getToolAmounts()[1];

		if (toolAmount > 0 && toolId > 0) {
			Item remove = new Item(fish(index).getTools()[1], fish(index)
					.getToolAmounts()[1]);
			if (remove != null) {
				player.getInventory().remove(remove);
			}
		}
		player.getSkills().addExperience(
				Skills.FISHING,
				Constants.getExpModifier()
						* fishExp(fish(index).getCatches()[endOn]));
	}

	private void start(final Permit fishingPermit) {
		Player player = null;

		if (fishingPermit.getMob().isPlayer()) {
			player = (Player) fishingPermit.getMob();
		}
		if (player == null || player.getAttribute("busy") != null) {
			return;
		}
		if (DumbPathFinder.standingDiagonal(player)
				|| TileControl.calculateDistance(player.getLocation(),
						fishingPermit.getLocation()) != 1) {
			if (DumbPathFinder.standingDiagonal(player)) {
				DumbPathFinder.getInAttackablePosition(player);
			}
			World.getWorld().submit(new Tickable(1) {

				@Override
				public void execute() {
					if (fishingPermit.isActive()) {
						start(fishingPermit);
					} else {
						fishingPermits.remove(fishingPermit);
					}
					this.stop();
				}
			});
			return;
		}
		player.face(fishingPermit.getLocation());
		player.playAnimation(Animation.create(-1));

		if (player.getInventory().freeSlots() == 0) {
			player.getActionSender().sendMessage(
					"You do not have enough space in your inventory.");
			return;
		}
		int mainTool = fish(fishingPermit.getValue()).getTools()[0];
		int nextTool = fish(fishingPermit.getValue()).getTools()[1];

		if (!player.getInventory().contains(mainTool)) {
			player.getActionSender()
					.sendMessage(
							"You need a "
									+ ItemDefinition.getDefinitions()[mainTool]
											.getName().toLowerCase()
									+ " to fish here.");
			return;
		}
		if (nextTool != -1 && !player.getInventory().contains(nextTool)) {
			player.getActionSender()
					.sendMessage(
							"You need some "
									+ ItemDefinition.getDefinitions()[nextTool]
											.getName().toLowerCase()
									+ " to fish here.");
			return;
		}
		if (player.getSkills().getLevel(Skills.FISHING) < fish(
				fishingPermit.getValue()).getCatchLevels()[0]) {
			player.getActionSender()
					.sendMessage(
							"You need a fishing level of "
									+ fish(fishingPermit.getValue())
											.getCatchLevels()[0]
									+ " to fish here.");
			return;
		}
		for (Permit p : fishingPermits) {
			if (p.getMob() == player) {
				p.disablePermit();
			}
		}
		fishingPermits.add(fishingPermit);
		player.playAnimation(Animation.create(fish(fishingPermit.getValue())
				.getAnimation(), 0));

		World.getWorld().submit(new Tickable(6) {

			@Override
			public void execute() {
				if (!fishingPermit.isActive()) {
					fishingPermits.remove(fishingPermit);
					this.stop();
					return;
				}
				if (!loop(fishingPermit)) {
					fishingPermit.getMob().playAnimation(Animation.create(-1));
					fishingPermits.remove(fishingPermit);
					this.stop();
					return;
				}
			}
		});
	}

	public void start(final Player player, final int index,
			final Location fishingLocation) {
		for (Permit p : fishingPermits) {
			if (p.getMob() == player) {
				p.disablePermit();
			}
		}
		Permit permit = new Permit(player, index, fishingLocation, true);

		if (permit != null) {
			start(permit);
		}
	}

}
