# Object interaction module created by Josh Mai - Sneakyhearts

from org.rs2server.rs2.action.impl import TeleportAction
from org.rs2server.rs2.model import DialogueManager, Location, World
from org.rs2server.rs2.model.container import Bank
from org.rs2server.rs2.model.minigame.impl import FightCaves, FremennikTrials
from org.rs2server.rs2.model.minigame.barrows import Barrows
from org.rs2server.rs2.model.minigame.magetraining import MageTrainingArena, MageTrainingArenaData
from org.rs2server.rs2.model.skills import Mining, Thieving
from org.rs2server.util import Misc



def objectOptionOne_sarcophagus(player, obj): # ahrim's crypt staircase.
	Barrows.getBarrows().openSarcophagus(player, obj.getId())
	
	
	
def objectOptionOne_altar(player, obj):
	player.getSkills().setPrayerPoints(player.getSkills().getLevelForExperience(5), True)
	player.playAnimation(Animation.create(645))
	player.getActionSender().sendMessage('Your prayer has been restored.')
	
def objectOptionOne_lever(player, obj):
	loc = None
	if player.getLocation().getX() > 3089 and player.getLocation().getY() < 3959:
		loc = Location.create(2539, 4712, 0)
		TeleportAction.getTeleportAction(player).castTeleport(loc)
	elif player.getLocation().getX() < 3000 and player.getLocation().getY() > 4700:
		loc = Location.create(3091, 3956, 0)
		TeleportAction.getTeleportAction(player).castTeleport(loc)
			
	

def objectOptionOne_staircase(player, obj):
	oloc = obj.getLocation()
	top = None
	if oloc.getZ() == 3:
		if oloc.getX() == 3558 and oloc.getY() == 9703: # Ahrim
			top = Location.create(Misc.random(3563, 3566), Misc.random(3286, 3290), 0)
			player.setTeleportTarget(top)
		elif oloc.getX() == 3557 and oloc.getY() == 9718: # Dharok
			top = Location.create(Misc.random(3573, 3576), Misc.random(3296, 3300), 0)
			player.setTeleportTarget(top)
		elif oloc.getX() == 3534 and oloc.getY() == 9705: # Guthan
			top = Location.create(Misc.random(3575, 3580), Misc.random(3280, 3285), 0)
			player.setTeleportTarget(top)
		elif oloc.getX() == 3546 and oloc.getY() == 9685: # Karil
			top = Location.create(Misc.random(3564, 3567), Misc.random(3273, 3277), 0)
			player.setTeleportTarget(top)
		elif oloc.getX() == 3565 and oloc.getY() == 9683: # Torag
			top = Location.create(Misc.random(3551, 3556), Misc.random(3281, 3284), 0)
			player.setTeleportTarget(top)
		elif oloc.getX() == 3578 and oloc.getY() == 9703: # Verac
			top = Location.create(Misc.random(3555, 3559), Misc.random(3296, 3300), 0)
			player.setTeleportTarget(top)						
	

def objectOptionOne_cave_entrance(player, obj):
	oloc = obj.getLocation()
	if oloc.getX() == 2437 and oloc.getY() == 5166:
		player.setMinigame(FightCaves(player))
	elif oloc.getX() == 2412 and oloc.getY() == 5118:
		player.getMinigame().quit(player)
			
	
def objectOptionOne_cave_exit(player, obj):
	pass	
	
	
def objectOptionOne_bank_deposit_box(player, obj):
	Bank.openBank(player)

def objectOptionOne_gangplank(player, obj):
	World.getWorld().getPestControl().addWaitingPlayer(player)
	player.setTeleportTarget(Location.create(2661, 2639, 0))	
	
	
def objectOptionOne_10778(player, obj):
	player.setTeleportTarget(MageTrainingArenaData.TELEKENETIC_CHAMBER)
	
	
def objectOptionOne_10779(player, obj):
	player.setTeleportTarget(MageTrainingArenaData.ENCHANTMENT_CHAMBER)
	
	
def objectOptionOne_10780(player, obj):
	player.setTeleportTarget(MageTrainingArenaData.ALCEHMIST_CHAMBER)
	


def objectOptionOne_10781(player, obj):
	player.setTeleportTarget(MageTrainingArenaData.GRAVEYARD_CHAMBER)
	

def objectOptionOne_10782(player, obj):
	player.setTeleportTarget(MageTrainingArenaData.PORTAL_CENTER)
	

def objectOptionOne_pentamid_pile(player, obj):
	MageTrainingArena.getMageTrainingArena().handleObjectOption(player, obj.getId())
	

def objectOptionOne_cylinder_pile(player, obj):
	MageTrainingArena.getMageTrainingArena().handleObjectOption(player, obj.getId())
	

def objectOptionOne_cube_pile(player, obj):
	MageTrainingArena.getMageTrainingArena().handleObjectOption(player, obj.getId())
	
	
def objectOptionOne_icosahedron_pile(player, obj):
	MageTrainingArena.getMageTrainingArena().handleObjectOption(player, obj.getId())
		

def objectOptionOne_cupboard(player, obj):	
	MageTrainingArena.getMageTrainingArena().handleObjectOption(player, obj.getId())
	

def objectOptionOne_coin_collector(player, obj):	
	MageTrainingArena.getMageTrainingArena().handleObjectOption(player, obj.getId())
		

def objectOptionTwo_bank_booth(player, obj):
	Bank.openBank(player)
	

def objectOptionTwo_2213(player, obj):
	Bank.openBank(player)
	

def objectOptionTwo_4187(player, obj):
	if player.completedFremennikTrials():
		player.getActionSender().sendMessage('You have already completed the Fremennik Trials minigame.')
	else:
		player.setMinigame(FremennikTrials(player))
		
		
def objectOptionOne_2186(player, obj):
	if(player.getLocation().equals(Location.create(2515, 3161, 0))):
		player.getWalkingQueue().addStep(2515, 3160)
		player.getActionSender().sendMessage('You squeeze through the railing.')
	elif(player.getLocation().equals(Location.create(2515, 3160, 0))):
		player.getWalkingQueue().addStep(2515, 3161)
		player.getActionSender().sendMessage('You squeeze through the railing.')
	
	if(!player.getWalkingQueue().isEmpty()):
		player.getWalkingQueue().finish()
	

def objectOptionOne_26933(player, obj):
	player.setTeleportTarget(Location.create(3097, 9868, 0))
	


def objectOptionOne_14314(player, obj):
	World.getWorld().getPestControl().removeWaitingPlayer(player)
	player.setTeleportTarget(Location.create(2657, 2639, 0))
	

def objectOptionOne_swamp_boaty(player, obj):
	if(obj.getLocation().equals(Location.create(3523, 3284, 0))):
		player.getActionSender().sendMessage('You board the boat...')
		player.setTeleportTarget(Location.create(3500, 3380, 0))
		player.getActionSender().sendMessage('You arrive at the other end of the river.')
		
	elif(obj.getLocation().equals(Location.create(3498, 3377, 0))):
		player.getActionSender().sendMessage('You board the boat...')
		player.setTeleportTarget(Location.create(3522, 3284, 0))
		player.getActionSender().sendMessage('You arrive at the other end of the river.')	
	

def objectOptionOne_6746(player, obj):
	if(obj.getLocation().equals(Location.create(3551, 9688, 0))):
		player.setTeleportTarget(Location.create(3564, 3288, 0))
	
	
def objectOptionOne_6727(player, obj):
	if(obj.getLocation().equals(Location.create(3552, 9688, 0))):
		player.setTeleportTarget(Location.create(3564, 3288, 0))
	
	
def objectOptionOne_1294(player, obj):
	DialogueManager.openDialogue(player, 514)
	

def objectOptionTwo_1294(player, obj):
	DialogueManager.openDialogue(player, 514)
	

def objectOptionOne_1317(player, obj):
	DialogueManager.openDialogue(player, 514)
	

def objectOptionTwo_1317(player, obj):
	DialogueManager.openDialogue(player, 514)
	

def objectOptionOne_2185(player, obj):
	if(player.getQuestStorage().getQuestStage(TreeGnomeVillage.QUEST_ID) >= 8):
		if(player.getLocation().equals(Location.create(2509, 3252, 0))):
			player.setTeleportTarget(Location.create(2509, 3254, 0))
			DialogueManager.openDialogue(player, 469)
		elif(player.getLocation().equals(Location.create(2509, 3254, 0))):
			player.setTeleportTarget(Location.create(2509, 3252, 0))
		

def objectOptionOne_2183(player, obj):
	if(obj.getLocation().equals(Location.create(2506, 3259, 1))):
		if (player.getQuestStorage().getQuestStage(TreeGnomeVillage.QUEST_ID) >= 10):
			DialogueManager.openDialogue(player, 471)
		elif (player.getQuestStorage().getQuestStage(TreeGnomeVillage.QUEST_ID) == 9):
			DialogueManager.openDialogue(player, 471)
	
	
def objectOptionOne_1765(player, obj):
	if(obj.getLocation().equals(Location.create(3017, 3849, 0))):
		player.setTeleportTarget(Location.create(3069, 10255, 0))
	

def objectOptionOne_1530(player, obj):
	if(player.getLocation().getX() > 2505 and player.getLocation().getY() > 3256):
		player.setTeleportTarget(Location.create(2504, 3255, 0))
	elif(player.getLocation().getX() < 2505 and player.getLocation().getY() < 3256):
		player.setTeleportTarget(Location.create(2506, 3257, 0))
	

def objectOptionOne_1766(player, obj):
	if(obj.getLocation().equals(Location.create(3069, 10256, 0))):
		player.setTeleportTarget(Location.create(3017, 3848, 0))
	

def objectOptionOne_1816(player, obj):
	if(obj.getLocation().equals(Location.create(3067, 10253, 0))):
		player.setTeleportTarget(Location.create(2271, 4680, 0))
	

def objectOptionOne_1817(player, obj):
	if(obj.getLocation().equals(Location.create(2271, 4680, 0))):
		player.setTeleportTarget(Location.create(3067, 10253, 0))
	

def objectOptionTwo_2181(player, obj):
	DialogueManager.openDialogue(player, 465)
	

def objectOptionOne_10284(player, obj):
	print 'open barrows chest'
	


def objectOptionOne_4493(player, obj):
	player.setTeleportTarget(Location.create(3433, 3537, 1))
	

def objectOptionOne_2634(player, obj):
	player.getActionSender().sendMessage('These rocks are in the way.')
	player.getActionSender().sendMessage('I need 50 mining to get past them.')
	

def objectOptionOne_6707(player, obj):
	if(obj.getLocation().equals(Location.create(3578,9703,3))):
		player.setTeleportTarget(Location.create(3557, 3297, 0))	
	
	
def objectOptionOne_6703(player, obj):
	player.setTeleportTarget(Location.create(3576, 3297))
	

def objectOptionOne_6704(player, obj):
	player.setTeleportTarget(Location.create(3577, 3282))
	

def objectOptionOne_6702(player, obj):
	player.setTeleportTarget(Location.create(3565, 3288))
	

def objectOptionOne_6706(player, obj):
	player.setTeleportTarget(Location.create(3553, 3282))
	

def objectOptionOne_6705(player, obj):
	player.setTeleportTarget(Location.create(3566, 3275))



def objectOptionTwo_2634(player, obj):
	mining = Mining(player, obj)
	if(mining.canHarvest()):
		if(player.getSkills().getLevelForExperience(Skills.MINING) >= 50):
			player.getActionSender().sendMessage('You mine through the rocks.')
			if(player.getLocation().getX() == 2837):
				player.setTeleportTarget(Location.create(2840, player.getLocation().getY(), 0))
			elif(player.getLocation().getX() == 2840):
				player.setTeleportTarget(Location.create(2837, player.getLocation().getY(), 0))
			
	else:
		player.getActionSender().sendMessage('You need a mining level of 50 to do that.')	
	

def objectOptionOne_2631(player, obj):
	if(player.getInventory().contains(1591)):
		if(player.getLocation().equals(
			Location.create(2931, 9690, 0))):
			player.getWalkingQueue().addStep(2931, 9689)
		elif(player.getLocation().equals(
			Location.create(2931, 9689, 0))):
			player.getWalkingQueue().addStep(2931, 9690)
		
		if(!player.getWalkingQueue().isEmpty()):
			player.getWalkingQueue().finish()
		
	
def objectOptionOne_2623(player, obj):
	if(player.getInventory().contains(1590)):
		if(player.getLocation().equals(Location.create(2924, 9803, 0))):
			player.getWalkingQueue().addStep(2923, 9803)
		elif(player.getLocation().equals(Location.create(2923, 9803, 0))):
			player.getWalkingQueue().addStep(2924, 9803)
		
		if(!player.getWalkingQueue().isEmpty()):
			player.getWalkingQueue().finish()
		

def objectOptionOne_3463(player, obj):
	if(player.getInventory().contains(2945)):
		if(player.getLocation().equals(
			Location.create(3415, 3489, 2))):
			player.getWalkingQueue().addStep(3416, 3489)
		elif(player.getLocation().equals(
			Location.create(3416, 3489, 2))):
			player.getWalkingQueue().addStep(3415, 3489)
		
		if(!player.getWalkingQueue().isEmpty()):
			player.getWalkingQueue().finish()
		
	
def objectOptionOne_16154(player, obj):
	player.setTeleportTarget(Location.create(1860, 5244, 0))


def objectOptionOne_4487(player, obj):
	if(player.getLocation().equals(Location.create(3428, 3536, 0))):
		player.setTeleportTarget(Location.create(3428, 3535, 0))
	elif(player.getLocation().equals(Location.create(3428, 3535, 0))):
		player.setTeleportTarget(Location.create(3428, 3536, 0))
	

def objectOptionOne_4490(player, obj):
	if(player.getLocation().equals(Location.create(3429, 3536, 0))):
		player.setTeleportTarget(Location.create(3429, 3535, 0))
	elif(player.getLocation().equals(Location.create(3429, 3535, 0))):
		player.setTeleportTarget(Location.create(3429, 3536, 0))
	

def objectOptionOne_16149(player, obj):
	player.setTeleportTarget(Location.create(2042, 5245, 0))


def objectOptionOne_16148(player, obj):
	player.setTeleportTarget(Location.create(3081, 3421, 0))


def objectOptionOne_16078(player, obj):
	player.setTeleportTarget(Location.create(1859, 5243, 0))


def objectOptionOne_16080(player, obj):
	player.setTeleportTarget(Location.create(1859, 5243, 0))


def objectOptionOne_16065(player, obj):
	SecurityStronghold.getSingleton().openDoor(player, obj)


def objectOptionOne_16066(player, obj):
	SecurityStronghold.getSingleton().openDoor(player, obj)


def objectOptionOne_16123(player, obj):
	SecurityStronghold.getSingleton().openDoor(player, obj)


def objectOptionOne_16124(player, obj):
	SecurityStronghold.getSingleton().openDoor(player, obj)


def objectOptionOne_2406(player, obj):
	DialogueManager.openDialogue(player, 310)


def objectOptionOne_1568(player, obj):
	if(obj.getLocation().equals(Location.create(3405, 3507, 0))):
		player.setTeleportTarget(Location.create(3405, 9906, 0))
	


def objectOptionOne_25939(player, obj):
	if(obj.getLocation().equals(Location.create(2715, 3470, 1))):
		player.setTeleportTarget(Location.create(2714, 3470, 0))
	


def objectOptionOne_25938(player, obj):
	if(obj.getLocation().equals(Location.create(2715, 3470, 0))):
		player.setTeleportTarget(Location.create(2714, 3470, 1))
	

def objectOptionOne_1292(player, obj):
	DialogueManager.openDialogue(player, 309)


def objectOptionTwo_25824(player, obj):
	player.getActionSender().sendInterface(459, True)


def objectOptionOne_12047(player, obj):
	if(player.getLocation().equals(
		Location.create(2465, 4433, 0))):
		player.getWalkingQueue().addStep(2465, 4434)
	elif(player.getLocation().equals(
		Location.create(2470, 4437, 0))):
		player.getWalkingQueue().addStep(2469, 4437)
	
	if(!player.getWalkingQueue().isEmpty()):
		player.getWalkingQueue().finish()
	


def objectOptionOne_3444(player, obj):
	if(player.getLocation().equals(
		Location.create(3405, 9895, 0))):
		player.getWalkingQueue().addStep(3405, 9894)
	elif(player.getLocation().equals(
		Location.create(3405, 9894, 0))):
		player.getWalkingQueue().addStep(3405, 9895)
	
	if(!player.getWalkingQueue().isEmpty()):
		player.getWalkingQueue().finish()
	


def objectOptionOne_12045(player, obj):
	if(player.getLocation().equals(
		Location.create(2466, 4433, 0))):
		player.getWalkingQueue().addStep(2466, 4434)
	elif(player.getLocation().equals(
		Location.create(2470, 4438, 0))):
		player.getWalkingQueue().addStep(2469, 4438)
	
	if(!player.getWalkingQueue().isEmpty()):
		player.getWalkingQueue().finish()
	

def objectOptionOne_3198(player, obj):
	if(player.getLocation().equals(
		Location.create(3312, 3235, 0))):
		player.getActionSender().sendMessage('Dueling is disabled for now.')
		#player.getWalkingQueue().addStep(3311, 3235)
	elif(player.getLocation().equals(
		Location.create(3311, 3235, 0))):
		player.getActionSender().sendMessage('Dueling is disabled for now.')
		#player.getWalkingQueue().addStep(3312, 3235)
	
	if(!player.getWalkingQueue().isEmpty()):
		player.getWalkingQueue().finish()
	

def objectOptionOne_3197(player, obj):
	if(player.getLocation().equals(
		Location.create(3312, 3234, 0))):
		player.getActionSender().sendMessage('Dueling is disabled for now.')
		#player.getWalkingQueue().addStep(3311, 3234)
	elif(player.getLocation().equals(
		Location.create(3311, 3234, 0))):
		player.getActionSender().sendMessage('Dueling is disabled for now.')
		#player.getWalkingQueue().addStep(3312, 3234)
	
	if(!player.getWalkingQueue().isEmpty()):
		player.getWalkingQueue().finish()
	


def objectOptionOne_1553(player, obj):
	if(player.getLocation().equals(
		Location.create(3253, 3267, 0))):
		player.getWalkingQueue().addStep(3252, 3267)
	elif(player.getLocation().equals(
		Location.create(3252, 3267, 0))):
		player.getWalkingQueue().addStep(3253, 3267)
	elif(player.getLocation().equals(
		Location.create(3237, 3295, 0))):
		player.getWalkingQueue().addStep(3236, 3295)
	elif(player.getLocation().equals(
		Location.create(3236, 3295, 0))):
		player.getWalkingQueue().addStep(3237, 3295)
	
	if(!player.getWalkingQueue().isEmpty()):
		player.getWalkingQueue().finish()
	


def objectOptionOne_1551(player, obj):
	if(player.getLocation().equals(
		Location.create(3253, 3266, 0))):
		player.getWalkingQueue().addStep(3252, 3266)
	elif(player.getLocation().equals(
		Location.create(3252, 3266, 0))):
		player.getWalkingQueue().addStep(3253, 3266)
	elif(player.getLocation().equals(
		Location.create(3237, 3296, 0))):
		player.getWalkingQueue().addStep(3236, 3296)
	elif(player.getLocation().equals(
		Location.create(3236, 3296, 0))):
		player.getWalkingQueue().addStep(3237, 3296)
	
	if(!player.getWalkingQueue().isEmpty()):
		player.getWalkingQueue().finish()
	


def objectOptionOne_11993(player, obj):
	if(player.getLocation().equals(
		Location.create(3108, 3162, 0))):
		player.getWalkingQueue().addStep(3107, 3162)
		player.getWalkingQueue().addStep(3106, 3161)
	elif(player.getLocation().equals(
		Location.create(3107, 3163, 0))):
		player.getWalkingQueue().addStep(3107, 3162)
		player.getWalkingQueue().addStep(3106, 3161)
	elif(player.getLocation().equals(
		Location.create(3108, 3163, 0))):
		player.getWalkingQueue().addStep(3107, 3162)
		player.getWalkingQueue().addStep(3106, 3161)
	elif(player.getLocation().equals(
		Location.create(3106, 3162, 0))):
		player.getWalkingQueue().addStep(3107, 3162)
		player.getWalkingQueue().addStep(3108, 3163)
	elif(player.getLocation().equals(
		Location.create(3107, 3161, 0))):
		player.getWalkingQueue().addStep(3107, 3162)
		player.getWalkingQueue().addStep(3108, 3163)
	elif(player.getLocation().equals(
		Location.create(3106, 3161, 0))):
		player.getWalkingQueue().addStep(3107, 3162)
		player.getWalkingQueue().addStep(3108, 3163)
	
	if(!player.getWalkingQueue().isEmpty()):
		player.getWalkingQueue().finish()
	


def objectOptionOne_3443(player, obj):
	if(player.getQuestStorage().getQuestStage(PriestInPeril.QUEST_ID) == 7):
		player.setTeleportTarget(Location.create(3423, 3485, 0))
	


def objectOptionOne_3432(player, obj):
	if(player.getQuestStorage().getQuestStage(PriestInPeril.QUEST_ID) == 7):
		player.setTeleportTarget(Location.create(3440, 9887, 0))
	


def objectOptionOne_3445(player, obj):
	if(player.getLocation().equals(
		Location.create(3432, 9897, 0))):
		player.getWalkingQueue().addStep(3431, 9897)
	elif(player.getLocation().equals(
		Location.create(3431, 9897, 0))):
		player.getWalkingQueue().addStep(3432, 9897)
	
	if(!player.getWalkingQueue().isEmpty()):
		player.getWalkingQueue().finish()
	


def objectOptionOne_2409(player, obj): 
	DialogueManager.openDialogue(player, 283)


def objectOptionOne_2147(player, obj):
	player.setTeleportTarget(Location.create(3104, 9576, 0))


def objectOptionOne_2148(player, obj):
	player.setTeleportTarget(Location.create(3105, 3162, 0))


def objectOptionOne_1733(player, obj):
	player.setTeleportTarget(Location.create(3058, 9776, 0))


def objectOptionOne_1734(player, obj):
	player.setTeleportTarget(Location.create(3061, 3376, 0))


def objectOptionOne_2882(player, obj):
	if(player.getLocation().equals(obj.getLocation())):
		player.getWalkingQueue().addStep(3267, 3227)
	else:
		player.getWalkingQueue().addStep(3268, 3227)
	player.getWalkingQueue().finish()


def objectOptionOne_2883(player, obj):
	if(player.getLocation().equals(obj.getLocation())):
		player.getWalkingQueue().addStep(3267, 3228)
	else:
		player.getWalkingQueue().addStep(3268, 3228)
	player.getWalkingQueue().finish()


def objectOptionTwo_2781(player, obj):
	Smelting.showSmeltingInterface(player)


def objectOptionTwo_11666(player, obj):
	Smelting.showSmeltingInterface(player)


def objectOptionOne_1740(player, obj):
	if(obj.getLocation().getX() == 3417 and obj.getLocation().getY() == 3493):
		player.setTeleportTarget(Location.create(3416, 3485, 0))
	elif(obj.getLocation().getX() == 3417 and obj.getLocation().getY() == 3485):
		player.setTeleportTarget(Location.create(3417, 3485, 0))
	else:
		player.setTeleportTarget(Location.create(3205, 3209, 1))
	


def objectOptionOne_1747(player, obj):
	if(obj.getLocation().getX() == 3410 and obj.getLocation().getY() == 3485):
		player.setTeleportTarget(Location.create(3410, 3486, 2))
	elif(obj.getLocation().getX() == 2503 and obj.getLocation().getY() == 3252):
		player.setTeleportTarget(Location.create(2503, 3253, 1))
	


def objectOptionOne_1746(player, obj):
	if(obj.getLocation().getX() == 3410 and obj.getLocation().getY() == 3485):
		player.setTeleportTarget(Location.create(3410, 3486, 1))
	elif(obj.getLocation().getX() == 2503 and obj.getLocation().getY() == 3252):
		player.setTeleportTarget(Location.create(2503, 3253, 0))
	


def objectOptionOne_3490(player, obj):
	if(player.getQuestStorage().getQuestStage(PriestInPeril.QUEST_ID) >= 4):
		if(player.getLocation().getX() == 3408):
			player.getWalkingQueue().addStep(3409, 3488)
			player.getWalkingQueue().finish()
		
		if(player.getLocation().getX() == 3409):
			player.getWalkingQueue().addStep(3408, 3488)
			player.getWalkingQueue().finish()
		
	


def objectOptionTwo_3489(player, obj):
	if(player.getQuestStorage().getQuestStage(PriestInPeril.QUEST_ID) == 1):
		DialogueManager.openDialogue(player, 327)
	elif(player.getQuestStorage().getQuestStage(PriestInPeril.QUEST_ID) == 3):
		DialogueManager.openDialogue(player, 338)
	


def objectOptionTwo_3490(player, obj):
	if(player.getQuestStorage().getQuestStage(PriestInPeril.QUEST_ID) == 1):
		DialogueManager.openDialogue(player, 327)
	elif(player.getQuestStorage().getQuestStage(PriestInPeril.QUEST_ID) == 3):
		DialogueManager.openDialogue(player, 338)
	


def objectOptionOne_3489(player, obj):
	if(player.getQuestStorage().getQuestStage(PriestInPeril.QUEST_ID) >= 4):
		if(player.getLocation().getX() == 3408):
			player.getWalkingQueue().addStep(3409, 3489)
			player.getWalkingQueue().finish()
		
		if(player.getLocation().getX() == 3409):
			player.getWalkingQueue().addStep(3408, 3489)
			player.getWalkingQueue().finish()
		
	


def objectOptionOne_1739(player, obj):
	DialogueManager.openDialogue(player, 232)


def objectOptionTwo_1739(player, obj):
	player.setTeleportTarget(Location.create(3205, 3209, 2))


def objectOptionThree1739(player, obj):
	player.setTeleportTarget(Location.create(3205, 3209, 0))


def objectOptionOne_1738(player, obj):
	if(obj.getLocation().getX() == 3417 and obj.getLocation().getY() == 3492):
		player.setTeleportTarget(Location.create(3417, 3492, 1))
	elif(obj.getLocation().getX() == 3417 and obj.getLocation().getY() == 3484):
		player.setTeleportTarget(Location.create(3417, 3484, 1))
	elif(obj.getLocation().getX() == 3204 and obj.getLocation().getY() == 3207):
		player.setTeleportTarget(Location.create(3205, 3209, 1))
	


def objectOptionOne_733(player, obj):
	if(player.getLocation().getY() == 9898):
		player.getWalkingQueue().addStep(3210, 9899)
		player.getWalkingQueue().finish()
		player.getActionSender().sendMessage('You walk through the web.')
	
	if(player.getLocation().getY() == 9899):
		player.getWalkingQueue().addStep(3210, 9898)
		player.getWalkingQueue().finish()
		player.getActionSender().sendMessage('You walk through the web.')
	

def objectOptionOne_7129(player, obj):
	player.setTeleportTarget(Location.create(2584, 4836, 0))


def objectOptionOne_7130(player, obj):
	player.setTeleportTarget(Location.create(2660, 4839, 0))


def objectOptionOne_7131(player, obj):
	player.setTeleportTarget(Location.create(2527, 4833, 0))


def objectOptionOne_7133(player, obj):
	player.setTeleportTarget(Location.create(2398, 4841, 0))


def objectOptionOne_7134(player, obj):
	player.setTeleportTarget(Location.create(2269, 4843, 0))


def objectOptionOne_7136(player, obj):
	player.setTeleportTarget(Location.create(2207, 4836, 0))


def objectOptionOne_7137(player, obj):
	player.setTeleportTarget(Location.create(2713, 4836, 0))


def objectOptionOne_7139(player, obj):
	player.setTeleportTarget(Location.create(2846, 4836, 0))


def objectOptionOne_7140(player, obj):
	player.setTeleportTarget(Location.create(2784, 4836, 0))


def objectOptionOne_7141(player, obj):
	player.setTeleportTarget(Location.create(2142, 4830, 0))


def objectOptionOne_2492(player, obj):
	player.setTeleportTarget(Location.create(3253, 3400, 0))


def objectOptionOne_2473(player, obj): # air altar portal
	player.setTeleportTarget(Location.create(2985, 3294, 0))


def objectOptionOne_2475(player, obj):
	player.setTeleportTarget(Player.DEFAULT_LOCATION)


def objectOptionOne_2466(player, obj):
	player.setTeleportTarget(Player.DEFAULT_LOCATION)


def objectOptionOne_2467(player, obj): # water altar portal
	player.setTeleportTarget(Location.create(3184, 3163, 0))


def objectOptionOne_2468(player, obj): # earth altar portal
	player.setTeleportTarget(Location.create(3304, 3474, 0))


def objectOptionOne_2469(player, obj): # fire altar portal
	player.setTeleportTarget(Location.create(3311, 3257, 0))


def objectOptionOne_2470(player, obj):
	player.setTeleportTarget(Player.DEFAULT_LOCATION)


def objectOptionOne_4494(player, obj):
	player.setTeleportTarget(Location.create(3438, 3538, 0))



def objectOptionOne_4495(player, obj):
	player.setTeleportTarget(Location.create(3417, 3540, 2))



def objectOptionOne_4496(player, obj):
	player.setTeleportTarget(Location.create(3412, 3540, 1))



def objectOptionOne_881(player, obj):
	player.setTeleportTarget(Location.create(3237, 9858, 0))



def objectOptionOne_24366(player, obj):
	player.setTeleportTarget(Location.create(3236, 3458, 0))


def objectOptionOne_3193(player, obj):
	Bank.openBank(player)


def objectOptionTwo_11402(player, obj):
	Bank.openBank(player)


def objectOptionTwo_26972(player, obj):
	Bank.openBank(player)


def objectOptionTwo_10517(player, obj):
	Bank.openBank(player)


def objectOptionTwo_2213(player, obj):
	Bank.openBank(player)


def objectOptionTwo_16700(player, obj):
	Bank.openBank(player)


def objectOptionTwo_25808(player, obj):
	Bank.openBank(player)


def objectOptionTwo_11758(player, obj):
	Bank.openBank(player)


def objectOptionOne_1733(player, obj):
	player.setTeleportTarget(Location.create(3058, 9776, 0))


def objectOptionOne_1734(player, obj):
	player.setTeleportTarget(Location.create(3061, 3376, 0))


def objectOptionOne_1755(player, obj):
	if obj.getLocation().equals(Location.create(2884, 9797, 0)):
		player.setTeleportTarget(Location.create(player.getLocation().getX(), player.getLocation().getY() - 6400, 0))
	elif player.getLocation().getY() <= 9740 and player.getLocation().getY() >= 9738 and player.getLocation().getX() >= 3018 and player.getLocation().getX() <= 3020:
		player.setTeleportTarget(Location.create(player.getLocation().getX(), player.getLocation().getY() - 6400, 0))
	elif obj.getLocation().equals(Location.create(3019, 9850, 0)):
		player.setTeleportTarget(Location.create(3018, 3450, 0))
	elif obj.getLocation().equals(Location.create(2547, 9951, 0)):
		player.setTeleportTarget(Location.create(2548, 3551, 0))
	elif obj.getLocation().equals(Location.create(3405, 9907, 0)):
		player.setTeleportTarget(Location.create(3405, 3506, 0))
	elif obj.getLocation().equals(Location.create(3097, 9867, 0)):
		player.setTeleportTarget(Location.create(3096, 3468, 0))
	


def objectOptionOne_11867(player, obj):
	player.setTeleportTarget(Location.create(3020, 9850, 0))


def objectOptionOne_2112(player, obj):
	if(player.getLocation().getY() >= 9757):
		if(player.getSkills().getLevelForExperience(Skills.MINING) < 60):
			player.getActionSender().sendMessage('You need a Mining level of 60 to enter the Mining Guild.')
			return
		
		player.setTeleportTarget(Location.create(3046, 9756, 0))
	else:
		player.setTeleportTarget(Location.create(3046, 9757, 0))
		


def objectOptionOne_2113(player, obj):
	if(player.getSkills().getLevelForExperience(Skills.MINING) < 60):
		player.getActionSender().sendMessage('You need a Mining level of 60 to enter the Mining Guild.')
		return
	
	player.setTeleportTarget(Location.create(player.getLocation().getX(), player.getLocation().getY() + 6400, 0))


def objectOptionOne_11844(player, obj):
	if(player.getLocation().getX() <= 2935):
		player.setTeleportTarget(Location.create(2936, 3355, 0))
	else:
		player.setTeleportTarget(Location.create(2934, 3355, 0))
	


def objectOptionOne_1759(player, obj):
	if(obj.getLocation().equals(Location.create(2547, 3551, 0))):
		player.setTeleportTarget(Location.create(2548, 9951, 0))
	else:
		player.setTeleportTarget(Location.create(2884, player.getLocation().getY() + 6400, 0))
	


def objectOptionOne_4188(player, obj):
	player.setTeleportTarget(Location.create(2666, 3694, 0))


def objectOptionOne_492(player, obj):
	player.setTeleportTarget(Location.create(2856, 9570, 0))


def objectOptionOne_1764(player, obj):
	player.setTeleportTarget(Location.create(2856, 3167, 0))


def objectOptionOne_9358(player, obj):
	player.setTeleportTarget(Location.create(2480, 5175, 0))


def objectOptionOne_9359(player, obj):
	player.setTeleportTarget(Location.create(2862, 9572, 0))


def objectOptionOne_9368(player, obj):
	if(player.getLocation().equals(Location.create(2399, 5167, 0))):
		if(player.getMinigame() != None):
			player.getMinigame().quit(player)
		
	elif(player.getLocation().equals(Location.create(2399, 5169, 0))):
		player.getActionSender().sendMessage('The heat of the barrier prevents you from walking through.')
		


def objectOptionOne_9369(player, obj):
	if(player.getLocation().equals(Location.create(2399, 5177, 0))):
		World.getWorld().getFightPits().addWaitingPlayer(player)
		player.getWalkingQueue().reset()
		player.getWalkingQueue().addStep(2399, 5175)
		player.getWalkingQueue().finish()
	elif(player.getLocation().equals(Location.create(2399, 5175, 0))):
		player.getWalkingQueue().reset()
		player.getWalkingQueue().addStep(2399, 5177)
		player.getWalkingQueue().finish()
		


def objectOptionOne_9391(player, obj):
	if World.getWorld().getFightPits().getParticipants() <= 1:
		player.getActionSender().sendMessage("There isn't a game currently running that you can view.")
	else:
		World.getWorld().getFightPits().removeWaitingPlayer(player)
		player.setInterfaceAttribute('fightPitOrbs', True)
		player.setTeleportTarget(FightPits.CENTRE_ORB)
		player.getActionSender().sendInterfaceInventory(374)
		player.setPnpc(5135)
		player.getUpdateFlags().flag(UpdateFlags.UpdateFlag.APPEARANCE)


def objectOptionOne_26305(player, obj):
	if(obj.getLocation().equals(Location.create(3008, 3462, 0))):
		player.setTeleportTarget(Location.create(2899, 3713, 0))
	elif(obj.getLocation().equals(Location.create(2900, 3713, 0))):
		player.setTeleportTarget(Location.create(3008, 3461, 0))
	


def objectOptionOne_26338(player, obj):
	if(player.getLocation().getY() <= 3715):
		if(player.getSkills().getLevel(Skills.STRENGTH) < 60):
			player.getActionSender().sendMessage('You need a Strength level of 60 to move this boulder.')
			return
		
		player.setTeleportTarget(Location.create(player.getLocation().getX(), player.getLocation().getY() + 4, 0))
	else:
		player.setTeleportTarget(Location.create(player.getLocation().getX(), player.getLocation().getY() - 4, 0))
	


def objectOptionOne_26341(player, obj):
	player.setTeleportTarget(Location.create(2881, 5310, 2))


def objectOptionOne_26293(player, obj):
	player.setTeleportTarget(Location.create(2916, 3747, 0))


def objectOptionOne_26439(player, obj):
	if(obj.getLocation().getY() == 5344):
		player.setTeleportTarget(Location.create(player.getLocation().getX(), 5333, 2))
	elif(obj.getLocation().getY() == 5333):
		player.setTeleportTarget(Location.create(player.getLocation().getX(), 5344, 2))
		player.getSkills().setLevel(5, 0)
		player.getCombatState().setSpecialEnergy(0)
		player.getActionSender().sendMessage('The extreme evil of this area leaves your Prayer drained.')
	

def objectOptionTwo_26299(player, obj):
	player.setTeleportTarget(Location.create(2914, 5300, 1))


def objectOptionOne_26425(player, obj):
	if(player.getLocation().getX() == 2863):
		player.setTeleportTarget(Location.create(player.getLocation().getX() + 1, 5354, 2))
	elif(player.getLocation().getX() == 2864):
		player.setTeleportTarget(Location.create(player.getLocation().getX() - 1, 5354, 2))
		
def objectOptionTwo_gem_stall(player, obj):
	player.getActionSender().sendMessage('Not finished yet - scripted message')

def objectOptionTwo_fish_stall(player, obj):
	Thieving.getSingleton().stallAction(player, obj.getId(), obj)
	
def objectOptionTwo_24914(player, obj):
	Bank.openBank(player)
						
