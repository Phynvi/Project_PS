package org.rs2server.rs2.model.skills;

import java.util.HashMap;
import java.util.Random;

import org.rs2server.rs2.Constants;
import org.rs2server.rs2.clipping.RegionClipping;
import org.rs2server.rs2.event.Event;
import org.rs2server.rs2.model.Animation;
import org.rs2server.rs2.model.GameObject;
import org.rs2server.rs2.model.GroundItem;
import org.rs2server.rs2.model.Item;
import org.rs2server.rs2.model.Location;
import org.rs2server.rs2.model.Player;
import org.rs2server.rs2.model.Skills;
import org.rs2server.rs2.model.World;
import org.rs2server.rs2.model.region.Region;
import org.rs2server.rs2.tickable.Tickable;

/**
 * 
 * @author Josh Mai <Sneakyhearts>
 *
 */
public class Firemaking {

	private enum Log {

		NORMAL(1511, 1, 30), OAK(1521, 15, 60), WILLOW(1519, 30, 90), TEAK(
				6333, 35, 105), MAPLE(1517, 45, 135), MAHOGANY(6332, 50, 157.5), YEW(
				1515, 60, 202.5), MAGIC(1513, 75, 303.8);
		private int id, reqLevel;
		private double exp;

		/**
		 * Constructs a new log.
		 * 
		 * @param id
		 *            The item id of the log.
		 * @param reqLevel
		 *            The required level to light the log.
		 * @param exp
		 *            The experience one gains from lighting the log.
		 */
		Log(int id, int reqLevel, double exp) {
			this.id = id;
			this.reqLevel = reqLevel;
			this.exp = exp;
		}

		/**
		 * The experience one gains from lighting the log.
		 * 
		 * @return exp
		 */
		public double getExperience() {
			return exp;
		}

		/**
		 * The log item.
		 * 
		 * @return new Item(id)
		 */
		public Item getItem() {
			return new Item(id);
		}

		/**
		 * The required level to light the log.
		 * 
		 * @return reqLevel
		 */
		public int getRequiredLevel() {
			return reqLevel;
		}
	}

	private static Firemaking singleton = null;

	public static Firemaking getSingleton() {
		if (singleton == null) {
			singleton = new Firemaking();
		}
		return singleton;
	}

	/**
	 * Adds experience to the player for a specific log.
	 * 
	 * @param log
	 *            The log we will add experience for.
	 * @return true if the player has gained a level.
	 */
	private boolean addExperience(Player player, Log log) {
		int beforeLevel = player.getSkills().getLevelForExperience(
				Skills.FIREMAKING);
		player.getSkills().addExperience(Skills.FIREMAKING,
				log.getExperience() * Constants.getExpModifier());
		int afterLevel = player.getSkills().getLevelForExperience(
				Skills.FIREMAKING);
		if (afterLevel > beforeLevel) {
			return true;
		}
		return false;
	}

	/**
	 * Finds the log for an item.
	 * 
	 * @param item
	 *            The log item.
	 * @return The log for the item.
	 */
	public Log findLog(Item item) {
		switch (item.getDefinition().getId()) {
		case 1511:
			return Log.NORMAL;
		case 1521:
			return Log.OAK;
		case 1519:
			return Log.WILLOW;
		case 6333:
			return Log.TEAK;
		case 1517:
			return Log.MAPLE;
		case 6332:
			return Log.MAHOGANY;
		case 1515:
			return Log.YEW;
		case 1513:
			return Log.MAGIC;
		}
		return null;
	}

	/**
	 * Lights a log.
	 * 
	 * @param log
	 *            The log to light.
	 */
	public void light(final Player player, final Log log) {
		if (player.getAttribute("busy") != null) {
			return;
		}
		player.getWalkingQueue().reset();
		player.getActionSender().resetMapFlag();

		player.getAttributes().put("busy", true);
		player.getAttributes().put("skilling", true);

		Item item = log.getItem();

		if (item == null) {
			return;
		}

		if (log.getRequiredLevel() > player.getSkills().getLevel(
				Skills.FIREMAKING)) {
			String vowels[] = { "a", "e", "i", "o", "u" };
			for (String vowel : vowels) {
				String itemName = item.getDefinition().getName().trim()
						.replaceAll("_", " ");
				player.getActionSender().sendMessage(
						"You must have a Firemaking level of "
								+ log.getRequiredLevel() + " to light "
								+ (itemName.startsWith(vowel) ? "an" : "a")
								+ " " + itemName + ".");
				player.getAttributes().remove("busy");
				player.getAttributes().remove("skilling");
				break;
			}
			return;
		}

		final HashMap<Integer, Boolean> moves = new HashMap<Integer, Boolean>();

		moves.put(
				0,
				!RegionClipping.getRegionClipping().getClipping(
						player.getLocation().getX(),
						player.getLocation().getY(),
						player.getLocation().getZ(),
						player.getLocation().getX(),
						player.getLocation().getY() + 1));
		moves.put(
				1,
				!RegionClipping.getRegionClipping().getClipping(
						player.getLocation().getX(),
						player.getLocation().getY(),
						player.getLocation().getZ(),
						player.getLocation().getX() + 1,
						player.getLocation().getY()));
		moves.put(
				2,
				!RegionClipping.getRegionClipping().getClipping(
						player.getLocation().getX(),
						player.getLocation().getY(),
						player.getLocation().getZ(),
						player.getLocation().getX(),
						player.getLocation().getY() - 1));
		moves.put(
				3,
				!RegionClipping.getRegionClipping().getClipping(
						player.getLocation().getX(),
						player.getLocation().getY(),
						player.getLocation().getZ(),
						player.getLocation().getX() - 1,
						player.getLocation().getY()));

		if (!moves.containsValue(true)) {
			player.getActionSender().sendMessage(
					"You cannot light a fire here.");
			player.getAttributes().remove("busy");
			player.getAttributes().remove("skilling");
			return;
		}
		for (GameObject obj : player.getRegion().getGameObjects()) {
			if (obj != null && obj.getDefinition() != null
					&& obj.getDefinition().getId() == 2732
					&& obj.getLocation().equals(player.getLocation())) {
				player.getActionSender().sendMessage(
						"You cannot light a fire here.");
				player.getAttributes().remove("busy");
				player.getAttributes().remove("skilling");
				return;
			}
		}
		player.playAnimation(Animation.create(733));
		final GroundItem groundItem = new GroundItem(player.getName(), item,
				player.getLocation());

		player.getInventory().remove(item);
		World.getWorld().createGroundItem(groundItem, player);

		player.getActionSender().sendMessage("You attempt to light the logs.");

		World.getWorld().submit(new Event(lightDelay(player, log)) {

			@Override
			public void execute() {
				player.playAnimation(Animation.create(-1));

				if (groundItem.equals(null)
						|| player.getAttribute("skilling") == null
						|| player.getAttribute("busy") == null) {
					moves.clear();
					this.stop();
					return;
				}
				final GameObject fire = new GameObject(player.getLocation(),
						2732, 10, 0, false);

				World.getWorld().unregister(groundItem);
				World.getWorld().register(fire);

				player.getActionSender().sendMessage(
						"The fire catches and the logs begin to burn.");
				addExperience(player, log);

				World.getWorld().submit(new Tickable(180) {

					@Override
					public void execute() {
						for (Player players : player.getRegion().getPlayers()) {
							if (players != null) {
								World.getWorld().register(
										new GroundItem(players.getName(),
												new Item(592), fire
														.getLocation()),
										players);
							}
						}
						Region[] regions = World.getWorld().getRegionManager()
								.getSurroundingRegions(fire.getLocation());
						for (Region r : regions) {
							fire.removeFromRegion(r);
							for (Player p : r.getPlayers()) {
								p.getActionSender().removeObject(fire);
							}
						}
						Region region = World
								.getWorld()
								.getRegionManager()
								.getRegion(fire.getLocation().getRegionX(),
										fire.getLocation().getRegionY());
						fire.removeFromRegion(region);
						this.stop();
					}
				});
				Location loc = player.getLocation();

				if (moves.get(3).booleanValue()) {
					loc = Location.create(player.getLocation().getX() - 1,
							player.getLocation().getY(), loc.getZ());
				} else if (moves.get(1).booleanValue()) {
					loc = Location.create(player.getLocation().getX() + 1,
							player.getLocation().getY(), loc.getZ());
				} else if (moves.get(0).booleanValue()) {
					loc = Location.create(player.getLocation().getX(), player
							.getLocation().getY() + 1, loc.getZ());
				} else if (moves.get(2).booleanValue()) {
					loc = Location.create(player.getLocation().getX(), player
							.getLocation().getY() - 1, loc.getZ());
				}
				player.getWalkingQueue().findRoute(loc.getX(), loc.getY(),
						false, 1, 1);
				if (!player.getWalkingQueue().isEmpty()) {
					player.getWalkingQueue().finish();
				}
				player.face(fire.getLocation());
				moves.clear();

				World.getWorld().submit(new Tickable(2) {

					@Override
					public void execute() {
						player.getAttributes().remove("busy");
						player.getAttributes().remove("skilling");
						this.stop();
					}
				});
				this.stop();
			}
		});
	}

	/**
	 * Light delay for a specific log.
	 * 
	 * @param log
	 *            The log.
	 * @return The light delay.
	 */
	private int lightDelay(Player player, Log log) {
		return random(800,
				(int) ((Math.sqrt(log.getRequiredLevel() * 2500) * (99 - player
						.getSkills().getLevel(Skills.FIREMAKING)))));
	}

	/**
	 * Returns a random integer with min as the inclusive lower bound and max as
	 * the exclusive upper bound.
	 * 
	 * @param min
	 *            The inclusive lower bound.
	 * @param max
	 *            The exclusive upper bound.
	 * @return Random integer min <= n < max.
	 */
	private int random(int min, int max) {
		Random random = new Random();
		int n = Math.abs(max - min);
		return Math.min(min, max) + (n == 0 ? 0 : random.nextInt(n));
	}
}
