package org.rs2server.rs2.model.skills;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.rs2server.rs2.Constants;
import org.rs2server.rs2.action.Action;
import org.rs2server.rs2.clipping.TileControl;
import org.rs2server.rs2.model.Animation;
import org.rs2server.rs2.model.Item;
import org.rs2server.rs2.model.Location;
import org.rs2server.rs2.model.Player;
import org.rs2server.rs2.model.Skills;
import org.rs2server.rs2.model.World;
import org.rs2server.rs2.tickable.Tickable;
import org.rs2server.util.Misc;
import org.rs2server.util.XMLController;

/**
 * 
 * @author killamess
 * 
 */
public class Cooking {

	public class Food {

		private int rawType;
		private int cookedType;
		private int burntType;
		private int level;
		private int xp;

		public Food() {
		}

		public int getBurntType() {
			return burntType;
		}

		public int getCookedType() {
			return cookedType;
		}

		public int getLevel() {
			return level;
		}

		public int getRawType() {
			return rawType;
		}

		public int getXp() {
			return xp;
		}
	}

	private static Cooking singleton = null;
	private ArrayList<Permit> cookingPermits = new ArrayList<Permit>();
	private ArrayList<Food> definitions;

	private static final Logger logger = Logger.getLogger(Cooking.class
			.getName());

	public static Cooking getSingleton() {
		if (singleton == null) {
			singleton = new Cooking();
		}
		return singleton;
	}

	private final int[][] COOKING_OBJECTS = {
			{ 114, 2728, 2729, 2730, 2731, 2859, 3039, 4172, 5275, 8750, 9682,
					9683, 12102, 9085, 9086, 9087, 9088, 12269, 13539, 13540,
					13541, 13542, 13543, 13544, 14919, 12312 }, { 2732 } };

	public int cookingObject(int id) {

		for (int i = 0; i < COOKING_OBJECTS.length; i++) {
			if (COOKING_OBJECTS[0].length <= i) {
				continue;
			}
			if (COOKING_OBJECTS[0][i] == id) { // Oven Objects
				return 1;
			}
			if (COOKING_OBJECTS[1].length <= i) {
				continue;
			}
			if (COOKING_OBJECTS[1][i] == id) { // Fire objects
				return 2;
			}
		}
		return 0; // Not a cooking object.
	}

	private boolean generateCookSuccess(int level, int levelReq) {
		if (level - 20 > levelReq) {
			return true;
		}
		if (level - 15 > levelReq) {
			return Misc.random(4) != 0;
		}
		if (level - 10 > levelReq) {
			return Misc.random(3) != 0;
		}
		if (level - 5 > levelReq) {
			return Misc.random(2) != 0;
		}
		return Misc.random(2) != 0;
	}

	public void init() throws IOException {
		if (definitions != null) {
			throw new IllegalStateException(
					"Cooking definitions already loaded.");
		}
		logger.info("Loading cooking definitions...");
		try {
			File file = new File("data/food.xml");
			if (file.exists()) {
				definitions = XMLController.readXML(file);
				logger.info("Loaded " + definitions.size()
						+ " cooking definitions.");
			} else {
				logger.info("Cooking definitions not found!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean loop(Permit cookingPermit) {

		Player player = (Player) cookingPermit.getMob();

		if (player == null) {
			return false;
		}

		int id = cookingPermit.getValue();

		if (rawInput(id) == null) {
			return false;
		}
		if (rawInput(id).getLevel() > player.getSkills().getLevel(
				Skills.COOKING)) {
			player.getActionSender().sendMessage(
					"You need a cooking level of " + rawInput(id).getLevel()
							+ " to cook this.");
			return false;
		}
		if (!player.getInventory().contains(rawInput(id).getRawType())) {
			return false;
		}
		if (player.getInventory().contains(rawInput(id).getRawType())) {
			player.getInventory()
					.remove(new Item(rawInput(id).getRawType(), 1));
			if (generateCookSuccess(
					player.getSkills().getLevel(Skills.COOKING), rawInput(id)
							.getLevel())) {
				player.getInventory().add(
						new Item(rawInput(id).getCookedType(), 1));
				player.getSkills().addExperience(Skills.COOKING,
						Constants.getExpModifier() * rawInput(id).getXp());
			} else {
				player.getInventory().add(
						new Item(rawInput(id).getBurntType(), 1));
			}
		} else {
			return false;
		}
		return true;
	}

	public Food rawInput(int id) {
		for (Food f : definitions) {
			if (f.getRawType() == id) {
				return f;
			}
		}
		return null;
	}

	public void showCookingInterface(Player player) {
		if (player.getAttribute("permit") == null) {
			return;
		}
		Permit permit = (Permit) player.getAttribute("permit");
		player.getActionSender().sendInterfaceModel(307, 2, 160,
				permit.getValue());
		player.getActionSender().sendChatboxInterface(307);
	}

	private void start(final Permit cookingPermit, final int amount) {

		final Player player = (Player) cookingPermit.getMob();

		if (player == null) {
			return;
		}
		int id = cookingPermit.getValue();
		int cookSpeed;
		if (rawInput(id) == null
				|| player.getAttribute("cookingObject") == null) {
			return;
		}
		if (rawInput(id).getLevel() > player.getSkills().getLevel(
				Skills.COOKING)) {
			player.getActionSender().sendMessage(
					"You need a cooking level of " + rawInput(id).getLevel()
							+ " to cook this.");
			return;
		}
		if (!player.getInventory().contains(rawInput(id).getRawType())) {
			return;
		}
		cookSpeed = cookingObject((Integer) player
				.getAttribute("cookingObject")) == 2 ? 3 : 5;
		player.face(cookingPermit.getLocation());

		if (TileControl.calculateDistance(player.getLocation(),
				cookingPermit.getLocation()) > 1) {

			World.getWorld().submit(new Tickable(1) {

				@Override
				public void execute() {
					if (cookingPermit.isActive()) {
						start(cookingPermit, amount);
					} else {
						cookingPermits.remove(cookingPermit);
					}
					this.stop();
				}
			});
			return;
		}
		for (Permit p : cookingPermits) {
			if (p.getMob() == player) {
				p.disablePermit();
			}
		}
		cookingPermits.add(cookingPermit);

		int e = 0;
		switch (cookingObject((Integer) player.getAttribute("cookingObject"))) {
		case 2:
			e = 897;
			break;

		default:
			e = 883;
			break;
		}
		player.setAttribute("cookingObject", null);
		final int emote = e;

		player.playAnimation(Animation.create(emote, 0));

		player.getActionQueue().addAction(new Action(player, cookSpeed) {

			int ticks = amount;

			@Override
			public void execute() {
				if (!cookingPermit.isActive() || ticks <= 0) {
					cookingPermits.remove(cookingPermit);
					player.setAttribute("permit", null);
					this.stop();
					return;
				}
				if (!loop(cookingPermit)) {
					// cookingPermit.getMob().playAnimation(Animation.create(-1));
					cookingPermits.remove(cookingPermit);
					player.setAttribute("permit", null);
					this.stop();
					return;

				} else if (ticks - 1 >= 0) {
					if (player.getInventory().contains(
							rawInput(cookingPermit.getValue()).getRawType())) {
						player.playAnimation(Animation.create(emote, 0));
					} else {
						cookingPermits.remove(cookingPermit);
						player.setAttribute("permit", null);
						this.stop();
						return;
					}
				}
				ticks--;
			}

			@Override
			public AnimationPolicy getAnimationPolicy() {
				return AnimationPolicy.RESET_ALL;
			}

			@Override
			public CancelPolicy getCancelPolicy() {
				return CancelPolicy.ALWAYS;
			}

			@Override
			public StackPolicy getStackPolicy() {
				return StackPolicy.NEVER;
			}
		});
	}

	public void start(final Player player, final int index,
			final Location cookingLocation, int amount) {
		start(new Permit(player, index, cookingLocation, true), amount);
	}
}
