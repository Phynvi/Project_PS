from org.rs2server.rs2 import Constants
from org.rs2server.rs2.model import Animation, DialogueManager, Item, Location, Shop, UpdateFlags, World
from org.rs2server.rs2.model.Player import Rights
from org.rs2server.rs2.model.UpdateFlags import UpdateFlag
from org.rs2server.rs2.model.container import Bank
from org.rs2server.rs2.util import NameUtils
	
def command_char(player, args):
	player.getActionSender().sendInterface(269, True)
	
	
def command_clear(player, args):
	for i in range(0, 8):
		player.getActionSender().sendMessage('')	


def command_coords(player, args):
	player.getActionSender().sendMessage('You are currently at world coordinates : ' + str(player.getLocation()))


def command_pass(player, args):
	newPass = args[1]
	player.setAttribute('password', player.getPassword())
	player.setPassword(newPass)
	if player.getAttribute('password') != None:
		player.getActionSender().sendMessage('Your password has been changed from ' + player.getAttribute('password') + ' to ' + player.getPassword())
		player.removeAttribute('password') 
		

def command_empty(player, args):
	player.getInventory().clear();
	player.getActionSender().sendMessage("Your inventory has been emptied.")
	
	
def command_commands(player, args):
	player.getActionSender().sendCommandsMenu()
	
	
def command_players(player, args):
	playerCount = str(World.getWorld().getPlayers().size())			
	if playerCount == '1':
		player.getActionSender().sendMessage("You are currently the only player online.")
	else:
		lines = []
		for p in World.getWorld().getPlayers():
			if p == None:
				continue
			lines.append('Player : ' + p.getName())
		for i in range(0, 133):
			if i < len(lines):
				player.getActionSender().sendString(275, (i + 4), lines[i])
			else:
				player.getActionSender().sendString(275, (i + 4), "")	
		player.getActionSender().sendString(275, 2, "Players Online: " + str(World.getWorld().getPlayers().size()))	
		player.getActionSender().sendMessage("There are currently " + playerCount + " players online.")
		player.getActionSender().sendInterface(275, True)	
		
		
def mod_command_jail(player, args):
	name = args[1].replace('_', ' ')
	for p in World.getWorld().getPlayers():
		if NameUtils.formatName(p.getName()).lower() == name:
			p.getCombatState().setCanMove(False)
							
																
def mod_command_unjail(player, args):
	name = args[1].replace('_', ' ')
	for p in World.getWorld().getPlayers():
		if NameUtils.formatName(p.getName()).lower() == name:
			p.getCombatState().setCanMove(True)
		

def mod_command_teleto(player, args):
	name = args[1].replace('_', ' ')
	for p in World.getWorld().getPlayers():
		if NameUtils.formatName(p.getName()).lower() == name:
			player.setTeleportTarget(p.getLocation())


def mod_command_kick(player, args):
	name = args[1].replace('_', ' ')
	for p in World.getWorld().getPlayers():
		if NameUtils.formatName(p.getName()).lower() == name:
			p.getActionSender().sendLogout(True)

		
def admin_command_music(player, args):
	player.getActionSender().playMusic(int(args[1]))
	
	
def admin_command_sound(player, args):
	if player.getActionSender() != None:
		player.getActionSender().sendMessage('Attempting to call sound ID : ' + args[1])
		player.getActionSender().playSound(Sound.create(int(args[1]), int(args[2]), int(args[3])))		
	
	
def admin_command_pnpc(player, args):
	player.setPnpc(int(args[1]))
	player.getUpdateFlags().flag(UpdateFlag.APPEARANCE)
	
	
def admin_command_unpc(player, args):
	player.setPnpc(-1);
	player.getUpdateFlags().flag(UpdateFlag.APPEARANCE)


def admin_command_anim(player, args):
	player.playAnimation(Animation.create(int(args[1])))
	
	
def admin_command_forcechat(player, args):
	s = args[1].replace('_', ' ')
	player.forceChat(s)	
	
	
def admin_command_bank(player, args):
	Bank.openBank(player)
		
	
def admin_command_shop(player, args):
	Shop.openShop(player, int(args[1]), 1)
	
	
def admin_command_sstring(player, args):
	player.getActionSender().sendString(int(args[1]), int(args[2]), args[3])	
	
	
def admin_command_minterface(player, args):
	player.getActionSender().sendInterfaceModel(int(args[1]), int(args[2]), int(args[3]), int(args[4]))	


def admin_command_nwinterface(player, args):
	player.getActionSender().sendInterface(int(args[1]), False)
	
	
def admin_command_winterface(player, args):	
	player.getActionSender().sendWalkableInterface(int(args[1]))
	
	
def admin_command_wconifg(player, args):	
	player.getActionSender().sendInterfaceConfig(int(args[1]), int(args[2]), bool(int(args[1])))	

def admin_command_home(player, args):
	player.setTeleportTarget(Location.create(3084, 3249, 0))

def admin_command_getatt(player, args):
	for x in player.getAttributes():
		print x

def admin_command_exprate(player, args):
	print Constants.EXP_MODIFIER
	player.getActionSender().sendMessage('Experience modifier rate : ' + str(Constants.EXP_MODIFIER) + '%')

def admin_command_changexp(player, args):
	Constants.setExpModifier(10)
	
def admin_command_chat(player, args):
	DialogueManager.openDialogue(player, int(args[1]))

def admin_command_sm(player, args):
	player.getActionSender().sendMessage(args[1].replace('_', ' '))

def admin_command_ssize(player, args):
	for s in range(0, Shop.SIZE):
		print Shop.forId(s) 	
		
def admin_command_quest(player, args):
	player.getQuestStorage().setQuestStage(int(args[1]), int(args[2]))
	
def admin_command_switch(player, args):
	DialogueManager.openDialogue(player, 12)
	
def admin_command_spec(player, args):
	player.getCombatState().setSpecialEnergy(100);
	player.getActionSender().updateSpecialConfig()
	
def admin_command_max(player, args):
	for skill in range(0, Skills.SKILL_COUNT):
		player.getSkills().setLevel(skill, 99)
		player.getSkills().setExperience(skill, 13034431)


def admin_command_runes(player, args):
	for rune in range(554, 567):
		player.getInventory().add(Item(rune, 100999))
	player.getInventory().add(Item(9075, 100999))


def admin_command_gems(player, args):
	gems = [1617, 1619, 1621, 1623, 1625, 1627, 1629, 1631]
	for gem in gems:
		player.getInventory().add(Item(gem, 1))
	player.getInventory().add(Item(6571, 1))
	
	
def admin_command_tele(player, args):
	x = int(args[1])
	y = int(args[2])
	z = 0
	if len(args) > 3:
		z = int(args[3])
	player.setTeleportTarget(Location.create(x, y, z))	


def admin_command_item(player, args):
	if len(args) == 2 or len(args) == 3:
		item = int(args[1])
		amt = 1
		if len(args) == 3:
			amt = int(args[2])
		try:	
			if player.getInventory().add(Item(item, amt)):
				 name = ItemDefinition.forId(item).getName().lower()
				 player.getActionSender().sendMessage('You successfully spawn ' + str(amt) + ' ' + name + '.')
		except:
			player.getActionSender().sendMessage('Failed to spawn item : ' + args[1])	
							
	
def admin_command_giveitem(player, args):
	name = args[1].replace('_', ' ')
	suffix = '.'
	itemId = int(args[2])
	amt = 1
	if len(args) == 4:
		amt = int(args[3])
		suffix = 's.'
	item = Item(itemId, amt)
	for p in World.getWorld().getPlayers():
		if NameUtils.formatName(p.getName()).lower() == name:
			if p.getInventory().add(item):
				player.getActionSender().sendMessage('You have given ' + p.getName() + ' ' + str(amt) + ' ' + item.getDefinition().getName() + suffix)


def admin_command_takeitem(player, args):
	name = args[1].replace('_', ' ')
	suffix = ' '
	itemId = int(args[2])
	amt = 1
	if len(args) == 4:
		amt = int(args[3])
		suffix = 's '
	item = Item(itemId, amt)
	for p in World.getWorld().getPlayers():
		if NameUtils.formatName(p.getName()).lower() == name:
			if p.getEquipment().contains(item.getId()):
				p.getEquipment().remove(item)
				player.getActionSender().sendMessage('You have taken a ' + item.getDefinition().getName() + suffix + 'from ' + p.getName() +"'s equipment.")
			elif p.getBank().contains(item.getId()):
				p.getBank().remove(item)
				player.getActionSender().sendMessage('You have taken ' + str(amt) + ' ' + item.getDefinition().getName() + suffix + 'from ' + p.getName() +"'s bank.")	
			elif p.getInventory().contains(item.getId()):
				p.getInventory().remove(item)
				player.getActionSender().sendMessage('You have taken ' + str(amt) + ' ' + item.getDefinition().getName() + suffix + 'from ' + p.getName() +"'s inventory.")
			else:
				suffix = 's.'
				player.getActionSender().sendMessage('The victim does not have any ' + item.getDefinition().getName() + suffix)	


def admin_command_teletome(player, args):
	name = args[1].replace('_', ' ')
	for p in World.getWorld().getPlayers():
		if NameUtils.formatName(p.getName()).lower() == name:
			p.setTeleportTarget(player.getLocation())


def admin_command_changename(player, args):
	name = args[1].replace('_', ' ')
	newName = args[2]
	for p in World.getWorld().getPlayers():
		if NameUtils.formatName(p.getName()).lower() == name:
			p.setName(newName)						


def admin_command_changepass(player, args):
	name = args[1].replace('_', ' ')
	newPass = args[2]
	for p in World.getWorld().getPlayers():
		if NameUtils.formatName(p.getName()).lower() == name:
			player.setAttribute('oldPass', p.getPassword())
			p.setPassword(newPass)
			player.getActionSender().sendMessage('You have  changed ' + p.getName() + "'s password from " + player.getAttribute('oldPass') + ' to ' + newPass + '.')
			player.removeAttribute('oldPass') 			


def admin_command_changerights(player, args):
	if player.getName().lower() != 'sneaky' and player.getName().lower() != 'dan':
		return
	name = args[1].replace('_', ' ')
	newRights = int(args[2])
	for p in World.getWorld().getPlayers():
		if NameUtils.formatName(p.getName()).lower() == name:
			p.setRights(Rights.getRights(newRights))
			player.getActionSender().sendMessage('You have given ' + p.getName() + ' a rights level of ' + str(newRights) + '.')
			

def admin_command_showbank(player, args):
	name = args[1].replace('_', ' ')
	for p in World.getWorld().getPlayers():
		if NameUtils.formatName(p.getName()).lower() == name:
			for i in range(0, p.getBank().size()):
				player.getActionSender().sendMessage('Bank item - ID: ' + str(p.getBank().get(i).getId()) + ' name : ' + p.getBank().get(i).getDefinition().getName() + ' count : ' + str(p.getBank().get(i).getCount()))
								
