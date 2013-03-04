from org.rs2server.rs2.model.minigame.magetraining import MageTrainingArenaData

def alchemy_interface(player):
	cItems = MageTrainingArenaData.cupboardItems
	boots = getTotalItems(player, cItems[0])
	kite = getTotalItems(player, cItems[1])
	helm = getTotalItems(player, cItems[2])
	emerald = getTotalItems(player, cItems[3])
	longsword = getTotalItems(player, cItems[4])
	player.getActionSender().sendWalkableInterface(194)
	player.getActionSender().sendString(194, 3, str(getPizazzPoints(player)))
	player.getActionSender().sendString(194, 9, str(boots))
	player.getActionSender().sendString(194, 10, str(kite))
	player.getActionSender().sendString(194, 11, str(helm))
	player.getActionSender().sendString(194, 12, str(emerald))
	player.getActionSender().sendString(194, 13, str(longsword))
	
def enchantment_interface(player):
	player.getActionSender().sendWalkableInterface(195)
	player.getActionSender().sendString(195, 3, str(getPizazzPoints(player)))		
	
def graveyard_interface(player):
	player.getActionSender().sendWalkableInterface(196)
	player.getActionSender().sendString(196, 3, str(getPizazzPoints(player)))	

def telekenetic_interface(player):
	player.getActionSender().sendWalkableInterface(198)
	player.getActionSender().sendString(198, 3, str(getPizazzPoints(player)))		
	
def getTotalItems(player, itemId):
	total = 0
	items = []
	if player.getInventory().contains(itemId):
		for i in range(0, 28):
			if player.getInventory().get(i) != None:
				items.append(player.getInventory().get(i).getId())
		for x in items:
			if x == itemId:
				total = total + 1	
	return total
	
def getPizazzPoints(player):
	return player.getPizazzPoints()				
			
