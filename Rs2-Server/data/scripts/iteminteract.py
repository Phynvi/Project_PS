# Item interaction script written by Josh Mai - Sneakyhearts

from org.rs2server.rs2.model import Animation
from org.rs2server.rs2.model.content import SpinningPlate, ToyHorsey
from org.rs2server.util import Misc

def item_interact_toy_horsey(player, item):
	player.playAnimation(Animation.create(ToyHorsey.getToyHorsey().getAnimation()))
	player.setForceChat(Misc.randomStringFromArray(ToyHorsey.getToyHorsey().getShoutMessages()))
	player.forceChat(player.getForcedChatMessage())
	

def item_interact_spinning_plate(player, item):
	player.playAnimation(Animation.create(SpinningPlate.getSpinningPlate().getSpinningAnimation()))
	
		
