package org.rs2server.rs2.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.rs2server.rs2.util.NameUtils;

public class PrivateChat {

	public enum ClanRank {
		NOT_IN_CLAN(-1),

		FRIEND(0),

		RECRUIT(1),

		CORPORAL(2),

		SERGEANT(3),

		LIEUTENANT(4),

		CAPTAIN(5),

		GENERAL(6),

		OWNER(7);

		/**
		 * The list of clan ranks.
		 */
		private static List<ClanRank> clanRanks = new ArrayList<ClanRank>();

		static {
			for (ClanRank clanRank : ClanRank.values()) {
				clanRanks.add(clanRank);
			}
		}

		public static ClanRank forId(int id) {
			for (ClanRank clanRank : clanRanks) {
				if (clanRank.getId() == id) {
					return clanRank;
				}
			}
			return null;
		}

		/**
		 * The id of this rank.
		 */
		private int id;

		ClanRank(int id) {
			this.id = id;
		}

		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}
	}

	public enum EntryRank {
		ANYONE(-1, "Anyone"),

		ANY_FRIENDS(0, "Any friends"),

		RECRUIT(1, "Recruit+"),

		CORPORAL(2, "Corporal+"),

		SERGEANT(3, "Sergeant+"),

		LIEUTENANT(4, "Leiutenant+"),

		CAPTAIN(5, "Captain+"),

		GENERAL(6, "General+"),

		ONLY_ME(7, "Only me");

		/**
		 * The list of clan entry ranks.
		 */
		private static List<EntryRank> entryRanks = new ArrayList<EntryRank>();

		static {
			for (EntryRank entryRank : EntryRank.values()) {
				entryRanks.add(entryRank);
			}
		}

		public static EntryRank forId(int id) {
			for (EntryRank entryRank : entryRanks) {
				if (entryRank.getId() == id) {
					return entryRank;
				}
			}
			return null;
		}

		/**
		 * The id of this rank.
		 */
		private int id;

		/**
		 * The text displayed on the clan setup interface.
		 */
		private String text;

		EntryRank(int id, String text) {
			this.id = id;
			this.text = text;
		}

		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}

		/**
		 * @return the text
		 */
		public String getText() {
			return text;
		}
	}

	public enum KickRank {
		ANYONE(-1, "Anyone"),

		ANY_FRIENDS(0, "Any friends"),

		RECRUIT(1, "Recruit+"),

		CORPORAL(2, "Corporal+"),

		SERGEANT(3, "Sergeant+"),

		LIEUTENANT(4, "Leiutenant+"),

		CAPTAIN(5, "Captain+"),

		GENERAL(6, "General+"),

		ONLY_ME(7, "Only me");

		/**
		 * The list of clan kick ranks.
		 */
		private static List<KickRank> kickRanks = new ArrayList<KickRank>();

		static {
			for (KickRank kickRank : KickRank.values()) {
				kickRanks.add(kickRank);
			}
		}

		public static KickRank forId(int id) {
			for (KickRank kickRank : kickRanks) {
				if (kickRank.getId() == id) {
					return kickRank;
				}
			}
			return null;
		}

		/**
		 * The id of this rank.
		 */
		private int id;

		/**
		 * The text displayed on the clan setup interface.
		 */
		private String text;

		KickRank(int id, String text) {
			this.id = id;
			this.text = text;
		}

		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}

		/**
		 * @return the text
		 */
		public String getText() {
			return text;
		}
	}

	public enum TalkRank {
		ANYONE(-1, "Anyone"),

		ANY_FRIENDS(0, "Any friends"),

		RECRUIT(1, "Recruit+"),

		CORPORAL(2, "Corporal+"),

		SERGEANT(3, "Sergeant+"),

		LIEUTENANT(4, "Leiutenant+"),

		CAPTAIN(5, "Captain+"),

		GENERAL(6, "General+"),

		ONLY_ME(7, "Only me");

		/**
		 * The list of clan talk ranks.
		 */
		private static List<TalkRank> talkRanks = new ArrayList<TalkRank>();

		static {
			for (TalkRank talkRank : TalkRank.values()) {
				talkRanks.add(talkRank);
			}
		}

		public static TalkRank forId(int id) {
			for (TalkRank talkRank : talkRanks) {
				if (talkRank.getId() == id) {
					return talkRank;
				}
			}
			return null;
		}

		/**
		 * The id of this rank.
		 */
		private int id;

		/**
		 * The text displayed on the clan setup interface.
		 */
		private String text;

		TalkRank(int id, String text) {
			this.id = id;
			this.text = text;
		}

		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}

		/**
		 * @return the text
		 */
		public String getText() {
			return text;
		}
	}

	/**
	 * The player whos friends list this is.
	 */
	private String owner;

	/**
	 * The player whos friends list this is.
	 */
	private Player player;

	/**
	 * A map of friend names, stored as a long.
	 */
	private Map<Long, ClanRank> friends = new HashMap<Long, ClanRank>(200);

	/**
	 * A list of ignored names, stored as a long.
	 */
	private List<Long> ignores = new ArrayList<Long>(100);

	/**
	 * A list of clan members.
	 */
	private List<Player> members = new ArrayList<Player>(100);

	/**
	 * The entry rank required.
	 */
	private EntryRank entryRank = EntryRank.ANYONE;

	/**
	 * The talk rank required.
	 */
	private TalkRank talkRank = TalkRank.ANYONE;

	/**
	 * The kick rank required.
	 */
	private KickRank kickRank = KickRank.CORPORAL;

	/**
	 * The amount of private messages sent.
	 */
	private int lastMessageIndex = 1;

	/**
	 * The channel name.
	 */
	private String channelName = "";

	/**
	 * The random number generator.
	 */
	private Random random = new Random();

	public PrivateChat(String owner, String channelName) {
		this.owner = owner;
		this.channelName = channelName;
		Player ownPlayer = World.getWorld().getPlayerNames()
				.get(NameUtils.nameToLong(owner));
		if (ownPlayer != null) {
			this.player = ownPlayer;
			for (long name : friends.keySet()) {
				if (World.getWorld().getPlayerNames().containsKey(name)) {
					player.getActionSender().sendFriend(name, 1,
							friends.get(name).getId());
				}
			}

		}
	}

	public boolean addClanMember(Player applicant) {
		if (channelName.length() < 1) {
			applicant.getActionSender().sendMessage(
					"The channel you tried to join does not exist.:clan:");
			return false;
		}
		if (members.size() >= 100) {
			applicant.getActionSender().sendMessage(
					"This clan chat is full.:clan:");
			return false;
		}
		if (ignores.contains(applicant.getNameAsLong())) {
			applicant
					.getActionSender()
					.sendMessage(
							"You do not have a high enough rank to join this clan channel.:clan:");
			return false;
		}
		if (getEntryRank() != EntryRank.ANYONE
				&& !applicant.getName().equals(owner)) {
			if (!friends.containsKey(applicant.getNameAsLong())) {
				applicant
						.getActionSender()
						.sendMessage(
								"You do not have a high enough rank to join this clan channel.:clan:");
				return false;
			}
			if (friends.get(applicant.getNameAsLong()).getId() < getEntryRank()
					.getId()) {
				applicant
						.getActionSender()
						.sendMessage(
								"You do not have a high enough rank to join this clan channel.:clan:");
				return false;
			}
		}
		applicant
				.getActionSender()
				.sendMessage(
						"Now talking in clan channel " + channelName
								+ ".:clan:")
				.sendMessage(
						"To talk, start each line of chat with the / symbol.:clan:");
		members.add(applicant);
		applicant.getInterfaceState().setClan(owner);
		updateClanMembers();
		return true;
	}

	public void addFriend(long nameAsLong, ClanRank clanRank) {
		if (friends.size() >= 200) {
			if (player != null) {
				player.getActionSender().sendMessage(
						"Your friends list is full.");
			}
			return;
		}
		if (friends.containsKey(nameAsLong)) {
			if (player != null) {
				player.getActionSender().sendMessage(
						NameUtils.longToName(nameAsLong)
								+ " is already on your friends list.");
			}
			return;
		}
		if (ignores.contains(nameAsLong)) {
			if (player != null) {
				player.getActionSender().sendMessage(
						"Please remove " + NameUtils.longToName(nameAsLong)
								+ " from your ignore list first.");
			}
			return;
		}
		friends.put(nameAsLong, clanRank);
		if (player != null) {
			Player p = World.getWorld().getPlayerNames().get(nameAsLong);
			if (p != null) {
				if ((p.getInterfaceState().getPrivateChat() == 0 || p
						.getInterfaceState().getPrivateChat() == 1
						&& p.getPrivateChat().getFriends()
								.containsKey(player.getNameAsLong()))
						&& !p.getPrivateChat().getIgnores()
								.contains(player.getNameAsLong())) {
					player.getActionSender().sendFriend(p.getNameAsLong(), 1,
							friends.get(p.getNameAsLong()).getId());
				}
				if (p.getPrivateChat().getFriends()
						.containsKey(player.getNameAsLong())
						&& player.getInterfaceState().getPrivateChat() == 1) { // if
																				// we've
																				// added
																				// them,
																				// but
																				// we
																				// had
																				// private
																				// set
																				// to
																				// 'friends',
																				// we
																				// need
																				// to
																				// tell
																				// them
																				// we
																				// are
																				// now
																				// online
																				// because
																				// we
																				// are
																				// friends
																				// with
																				// them
					p.getActionSender().sendFriend(
							player.getNameAsLong(),
							1,
							p.getPrivateChat().getFriends()
									.get(player.getNameAsLong()).getId());
				}
				if (members.contains(p)) {
					updateClanMembers();
				}
			}
		}
	}

	public void addIgnore(long nameAsLong) {
		if (ignores.size() >= 100) {
			if (player != null) {
				player.getActionSender().sendMessage(
						"Your ignore list is full.");
			}
			return;
		}
		if (ignores.contains(nameAsLong)) {
			if (player != null) {
				player.getActionSender().sendMessage(
						NameUtils.longToName(nameAsLong)
								+ " is already on your ignore list.");
			}
			return;
		}
		if (friends.containsKey(nameAsLong)) {
			if (player != null) {
				player.getActionSender().sendMessage(
						"Please remove " + NameUtils.longToName(nameAsLong)
								+ " from your friends list first.");
			}
			return;
		}
		ignores.add(nameAsLong);
		if (player != null) {
			Player p = World.getWorld().getPlayerNames().get(nameAsLong);
			if (p != null) {
				if (player.getInterfaceState().getPrivateChat() != 2 // no point
																		// telling
																		// the
																		// client
																		// again
																		// if we
																		// are
																		// already
																		// offline
						&& p.getPrivateChat().getFriends()
								.containsKey(player.getNameAsLong()) // if they
																		// have
																		// us on
																		// their
																		// friends
																		// list,
																		// we
																		// need
																		// to
																		// update
																		// them
																		// to
																		// offline
																		// now
						&& !(player.getInterfaceState().getPrivateChat() == 1 && !friends
								.containsKey(p.getNameAsLong()))) { // don't
																	// send
																	// offline
																	// if we
																	// already
																	// have
																	// private
																	// set to
																	// 'friends'
																	// and they
																	// aren't
																	// our
																	// friend
					p.getActionSender().sendFriend(
							player.getNameAsLong(),
							0,
							p.getPrivateChat().getFriends()
									.get(player.getNameAsLong()).getId());
				}

			}
		}
	}

	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}

	/**
	 * @return the entryRank
	 */
	public EntryRank getEntryRank() {
		return entryRank;
	}

	/**
	 * @return the friends
	 */
	public Map<Long, ClanRank> getFriends() {
		return friends;
	}

	/**
	 * @return the ignores
	 */
	public List<Long> getIgnores() {
		return ignores;
	}

	/**
	 * @return the kickRank
	 */
	public KickRank getKickRank() {
		return kickRank;
	}

	/**
	 * @return lastMessageIndex
	 */
	public int getLastMessageIndex() {
		return (lastMessageIndex++) + random.nextInt();
	}

	/**
	 * @return the members
	 */
	public List<Player> getMembers() {
		return members;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return the talkRank
	 */
	public TalkRank getTalkRank() {
		return talkRank;
	}

	public void removeClanMember(Player applicant) {
		if (members.contains(applicant)) {
			members.remove(applicant);
		}
		applicant.getInterfaceState().setClan("");
		applicant.getActionSender().sendClanChannel("", "", false, null, null);
		updateClanMembers();
	}

	public void removeFriend(long nameAsLong) {
		friends.remove(nameAsLong);
		Player p = World.getWorld().getPlayerNames().get(nameAsLong);
		if (p != null) {
			if (player != null) {
				if (player.getInterfaceState().getPrivateChat() == 1
						&& p.getPrivateChat().getFriends()
								.containsKey(player.getNameAsLong())) {
					p.getActionSender().sendFriend(
							player.getNameAsLong(),
							0,
							p.getPrivateChat().getFriends()
									.get(player.getNameAsLong()).getId());
				}
			}
			if (members.contains(p)) {
				updateClanMembers();
			}
		}
	}

	public void removeIgnore(long nameAsLong) {
		ignores.remove(nameAsLong);
		Player p = World.getWorld().getPlayerNames().get(nameAsLong);
		if (p != null) {
			if (player.getInterfaceState().getPrivateChat() != 2 // no point
																	// telling
																	// the
																	// client
																	// again if
																	// we are
																	// already
																	// offline
					&& p.getPrivateChat().getFriends()
							.containsKey(player.getNameAsLong()) // if they have
																	// us on
																	// their
																	// friends
																	// list, we
																	// need to
																	// update
																	// them to
																	// offline
																	// now
					&& !(player.getInterfaceState().getPrivateChat() == 1 && !friends
							.containsKey(p.getNameAsLong()))) { // don't send
																// offline if we
																// already have
																// private set
																// to 'friends'
																// and they
																// aren't our
																// friend
				p.getActionSender().sendFriend(
						player.getNameAsLong(),
						1,
						p.getPrivateChat().getFriends()
								.get(player.getNameAsLong()).getId());
			}
		}
	}

	/**
	 * Sends a message to a recipient.
	 * 
	 * @param nameAsLong
	 *            The recipients name as a long.
	 * @param message
	 *            The message bytes.
	 * @param unpacked
	 *            The unpacked message.
	 */
	public void sendMessage(long to, byte[] message) {
		Player p = World.getWorld().getPlayerNames().get(to);
		if (p != null) {
			player.getActionSender().sendSentPrivateMessage(p.getNameAsLong(),
					message);
			p.getActionSender().sendRecievedPrivateMessage(
					player.getNameAsLong(), player.getRights().toInteger(),
					message);
		}
	}

	public void sendMessage(Player p, byte[] message) {
		if (getTalkRank() != TalkRank.ANYONE && !p.getName().equals(owner)) {
			if (!friends.containsKey(p.getNameAsLong())) {
				p.getActionSender()
						.sendMessage(
								"You do not have a high enough rank to talk in this clan channel.:clan:");
				return;
			}
			if (friends.get(p.getNameAsLong()).getId() < getTalkRank().getId()) {
				p.getActionSender()
						.sendMessage(
								"You do not have a high enough rank to talk in this clan channel.:clan:");
				return;
			}
		}
		for (Player p2 : getMembers()) {
			p2.getActionSender().sendClanMessage(p.getName(), channelName,
					message, p.getRights().toInteger());
		}
	}

	/**
	 * @param channelName
	 *            the channelName to set
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * @param entryRank
	 *            the entryRank to set
	 */
	public void setEntryRank(EntryRank entryRank) {
		this.entryRank = entryRank;
		if (getEntryRank() != EntryRank.ANYONE && getMembers().size() > 0) {
			for (Player p : getMembers()) {
				if (!p.getName().equals(owner)) {
					if (!friends.containsKey(p.getNameAsLong())) {
						p.getActionSender()
								.sendMessage(
										"You do not have a high enough rank to remain in this clan channel1.");
						removeClanMember(p);
					} else if (friends.get(p.getNameAsLong()).getId() < getEntryRank()
							.getId()) {
						p.getActionSender()
								.sendMessage(
										"You do not have a high enough rank to remain in this clan channel1.");
						removeClanMember(p);
					}
				}
			}
		}
	}

	/**
	 * @param friends
	 *            the friends to set
	 */
	public void setFriends(Map<Long, ClanRank> friends) {
		this.friends = friends;
	}

	/**
	 * @param ignores
	 *            the ignores to set
	 */
	public void setIgnores(List<Long> ignores) {
		this.ignores = ignores;
	}

	/**
	 * @param kickRank
	 *            the kickRank to set
	 */
	public void setKickRank(KickRank kickRank) {
		this.kickRank = kickRank;
	}

	/**
	 * @param player
	 *            the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @param talkRank
	 *            the talkRank to set
	 */
	public void setTalkRank(TalkRank talkRank) {
		this.talkRank = talkRank;
	}

	public void updateClanMembers() {
		if (channelName.length() < 1) {
			return;
		}
		if (members.size() < 1) {
			return;
		}
		for (Player p : getMembers()) {
			p.getActionSender().sendClanChannel(owner, channelName, true,
					members, friends);
		}
	}

	public void updateFriendList(boolean online) {
		player.getActionSender().sendFriendServer(2);
		if (player.getInterfaceState().getPrivateChat() == 2) {
			online = false; // appearing offline
		}
		for (Player p : World.getWorld().getPlayers()) {
			if (p.getPrivateChat().getFriends()
					.containsKey(player.getNameAsLong())
					&& !ignores.contains(p.getNameAsLong())) { // don't send
																// that the
																// player is
																// logged in if
																// they are on
																// our ignore
																// list
				boolean onlineTemp = online;
				if (player.getInterfaceState().getPrivateChat() == 1
						&& !friends.containsKey(p.getNameAsLong())) {
					onlineTemp = false;
				}
				p.getActionSender().sendFriend(
						player.getNameAsLong(),
						onlineTemp ? 1 : 0,
						p.getPrivateChat().getFriends()
								.get(player.getNameAsLong()).getId());
			}
		}
	}
}
