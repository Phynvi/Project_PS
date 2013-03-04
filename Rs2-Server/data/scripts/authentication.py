from org.rs2server.rs2 import Constants
from org.rs2server.rs2.model import Location
from org.rs2server.rs2.model.Player import Rights
from org.rs2server.rs2.model.quest.impl import TutorialIsland

# called when a returning player logs in.
def login(player):
	serverName = Constants.SERVER_NAME	
	
	if player.getName().lower() == 'sneaky' or player.getName().lower() == 'sneakyhearts':
		player.setRights(Rights.ADMINISTRATOR)
		player.setAttribute('sneaky', True)
	# send login block below.
	player.setActive(True)
	player.getActionSender().sendMapRegion()
	player.getActionSender().sendLoginInterface()
	player.getActionSender().sendDefaultChatbox()
	player.getPrivateChat().updateFriendList(True)
	player.getActionSender().sendFriends()
	player.getActionSender().sendIgnores()
	player.getActionSender().sendDetails()
	player.getActionSender().sendSkills()
	player.getActionSender().updateRunningConfig()
	player.getActionSender().sendRunEnergy()
	player.getActionSender().updateBrightnessConfig()
	player.getActionSender().updateSplitPrivateChatConfig()
	player.getActionSender().updateAcceptAidConfig()
	player.getActionSender().updateMouseButtonsConfig()
	player.getActionSender().updateChatEffectsConfig()
	player.getActionSender().updateBankConfig()
	player.getActionSender().updateAutoRetaliateConfig()
	player.getCombatState().calculateBonuses()
	player.getActionSender().sendBonuses()
	player.getActionSender().sendConfig(313, 255) # SkillCape Off + First Part Of Emotes
	player.getActionSender().sendConfig(465, 511) # 2nd part Of Emotes
	player.getActionSender().sendConfig(802, 511) # 3rd Part Of Emotes
	player.getActionSender().sendConfig(1085, 700) # 4th Part Of Emotes
	player.getActionSender().sendInterfaceConfig(274, 11, True) # Quest interface
	player.getActionSender().sendInterfaceConfig(589, 18, True) # Clan chat interface
	player.getActionSender().sendString(550, 2, "Friends List - World 2")
	player.getActionSender().sendSidebarInterfaces()
	player.getActionSender().sendInteractionOption('null', 1, True) # null or fight
	player.getActionSender().sendInteractionOption('null', 2, False) # challenge = duel arena only
	player.getActionSender().sendInteractionOption('Follow', 3, False)
	player.getActionSender().sendInteractionOption('Trade with', 4, False)
	player.getActionSender().sendAreaInterface(None, player.getLocation())
	player.getActionSender().sendMessage('Welcome to ' + serverName + '.')
	player.checkForSkillcapes()

# called when a new player logs in.	
def initial_login(player):
	player.getActionSender().sendInterface(269, True)
	player.getInventory().add(Item(1351, 1))
	player.getInventory().add(Item(590, 1))
	player.getInventory().add(Item(303, 1))
	player.getInventory().add(Item(315, 2))
	player.getInventory().add(Item(1925, 1))
	player.getInventory().add(Item(1931, 1))
	player.getInventory().add(Item(2309, 1))
	player.getInventory().add(Item(1265, 1))
	player.getInventory().add(Item(1205, 1))
	player.getInventory().add(Item(1277, 1))
	player.getInventory().add(Item(1171, 1))
	player.getInventory().add(Item(841, 1))
	player.getInventory().add(Item(882, 10))
	player.getInventory().add(Item(556, 25))
	player.getInventory().add(Item(558, 15))
	player.getInventory().add(Item(555, 6))
	player.getInventory().add(Item(557, 4))
	player.getInventory().add(Item(559, 2))
	#TutorialIsland.getTutorialIsland().start(player)


# called when a player logs out.
def logout(player):
	pass
