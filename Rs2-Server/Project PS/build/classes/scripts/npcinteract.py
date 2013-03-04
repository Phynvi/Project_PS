from org.rs2server.rs2.model import DialogueManager, Shop
from org.rs2server.rs2.model.skills import *

def talkTo477(player, npc):
	DialogueManager.openDialogue(player, 489)


def talkTo470(player, npc):
	DialogueManager.openDialogue(player, 415)


def talkTo482(player, npc):
	DialogueManager.openDialogue(player, 438)


def talkTo483(player, npc):
	DialogueManager.openDialogue(player, 446)


def talkTo481(player, npc):
	DialogueManager.openDialogue(player, 451)


def talkTo469(player, npc):
	DialogueManager.openDialogue(player, 394)


def talkTo70(player, npc):
	DialogueManager.openDialogue(player, 377)


def talkTo798(player, npc):
	DialogueManager.openDialogue(player, 384)


def talkTo1596(player, npc):
	DialogueManager.openDialogue(player, 381)


def talkTo1597(player, npc):
	DialogueManager.openDialogue(player, 393)


def optionThree1597(player, npc):
	Shop.openShop(player, 26, 1)



def optionThree1596(player, npc):
	Shop.openShop(player, 26, 1)


def optionThree70(player, npc):
	Shop.openShop(player, 26, 1)


def talkTo1049(player, npc):
	DialogueManager.openDialogue(player, 353)


def talkTo648(player, npc):
	DialogueManager.openDialogue(player, 316)


def talkTo33(player, npc):
	DialogueManager.openDialogue(player, 312)


def talkTo656(player, npc):
	DialogueManager.openDialogue(player, 304)


def talkTo657(player, npc):
	DialogueManager.openDialogue(player, 296)


def talkTo658(player, npc):
	DialogueManager.openDialogue(player, 296)


def talkTo650(player, npc):
	DialogueManager.openDialogue(player, 269)


def talkTo553(player, npc):
	DialogueManager.openDialogue(player, 256)


def talkTo300(player, npc):
	DialogueManager.openDialogue(player, 249)


def talkTo2253(player, npc):
	DialogueManager.openDialogue(player, 238)


def talkTo376(player, npc):
	DialogueManager.openDialogue(player, 239)
 

def tradeWith376(player, npc):
	DialogueManager.openDialogue(player, 240)


def talkTo741(player, npc):
	DialogueManager.openDialogue(player, 243)


def talkTo377(player, npc):
	DialogueManager.openDialogue(player, 239)


def tradeWith377(player, npc):
	DialogueManager.openDialogue(player, 240)


def talkTo378(player, npc):
	DialogueManager.openDialogue(player, 239)


def tradeWith378(player, npc):
	DialogueManager.openDialogue(player, 240)


def talkTo970(player, npc):
	DialogueManager.openDialogue(player, 237)


def tradeWith564(player, npc):
	Shop.openShop(player, 25, 1)

def tradeWith548(player, npc):
	Shop.openShop(player, 22, 1)

def tradeWith549(player, npc):
	Shop.openShop(player, 16, 1)	

def tradeWith970(player, npc):
	Shop.openShop(player, 6, 1)


def talkTo948(player, npc):
	Shop.openShop(player, 6, 1)
	
def talkTo2574(player, npc):
	DialogueManager.openDialogue(player, 1000)

def tradeWith804(player, npc):
	player.getActionSender().sendInterface(324, True)
	player.getActionSender().sendInterfaceModel(324, 100, 175, 1739)
	player.getActionSender().sendInterfaceModel(324, 101, 175, 1739)
	player.getActionSender().sendInterfaceModel(324, 102, 175, 6289)
	player.getActionSender().sendInterfaceModel(324, 103, 175, 6289)
	player.getActionSender().sendInterfaceModel(324, 104, 175, 1753)
	player.getActionSender().sendInterfaceModel(324, 105, 175, 1751)
	player.getActionSender().sendInterfaceModel(324, 106, 175, 1749)
	player.getActionSender().sendInterfaceModel(324, 107, 175, 1747)
        player.getActionSender().sendString(324, 108, "Soft Leather")
        player.getActionSender().sendString(324, 116, "0 gp")
        player.getActionSender().sendString(324, 109, "Hard Leather")
        player.getActionSender().sendString(324, 117, "0 gp")
        player.getActionSender().sendString(324, 110, "Snakeskin")
        player.getActionSender().sendString(324, 118, "20 gp")
        player.getActionSender().sendString(324, 111, "Snakeskin")
        player.getActionSender().sendString(324, 119, "20 gp")
        player.getActionSender().sendString(324, 112, "Green d'hide")
        player.getActionSender().sendString(324, 120, "20 gp")
        player.getActionSender().sendString(324, 113, "Blue d'hide")
        player.getActionSender().sendString(324, 121, "20 gp")
        player.getActionSender().sendString(324, 114, "Red d'hide")
        player.getActionSender().sendString(324, 122, "20 gp")
        player.getActionSender().sendString(324, 115, "Black d'hide")
        player.getActionSender().sendString(324, 123, "20 gp")
 

def optionThree553(player, npc):
	if(player.getQuestStorage().hasFinished(QuestRepository.get(27))):
		player.setTeleportTarget(Location.create(2889, 4814, 0))
	


def talkTo308(player, npc):
	Shop.openShop(player, 4, 1)


def tradeWith550(player, npc):
	Shop.openShop(player, 8, 1)


def tradeWith579(player, npc):
	Shop.openShop(player, 21, 1)


def tradeWith532(player, npc):
	Shop.openShop(player, 10, 1)


def tradeWith545(player, npc):
	Shop.openShop(player, 1, 1)


def tradeWith541(player, npc):
	Shop.openShop(player, 11, 1)



def tradeWith553(player, npc):
	Shop.openShop(player, 7, 1)


def tradeWith523(player, npc):
	Shop.openShop(player, 5, 1)


def tradeWith522(player, npc):
	Shop.openShop(player, 2, 1)


def tradeWith521(player, npc):
	Shop.openShop(player, 1, 1)


def tradeWith546(player, npc):
	Shop.openShop(player, 9, 1)


def talkTo520(player, npc):
	DialogueManager.openDialogue(player, 57)


def tradeWith520(player, npc):
	Shop.openShop(player, 2, 1)


def tradeWith519(player, npc):
	Shop.openShop(player, 0, 1)


def talkTo1513(player, npc):
	DialogueManager.openDialogue(player, 218)


def talkTo3097(player, npc):
	DialogueManager.openDialogue(player, 214)


def talkTo550(player, npc):
	DialogueManager.openDialogue(player, 158)


def tradeWith552(player, npc):
	Shop.openShop(player, 2, 1)


def talkTo598(player, npc):
	DialogueManager.openDialogue(player, 124)


def tradeWith598(player, npc):
	DialogueManager.openDialogue(player, 126)


def talkTo599(player, npc):
	DialogueManager.openDialogue(player, 133)


def talkTo736(player, npc):
	DialogueManager.openDialogue(player, 137)


def talkTo577(player, npc):
	DialogueManager.openDialogue(player, 140)


def tradeWith577(player, npc):
	Shop.openShop(player, 4, 1)


def talkTo580(player, npc):
	DialogueManager.openDialogue(player, 143)


def tradeWith580(player, npc):
	Shop.openShop(player, 5, 1)


def talkTo581(player, npc):
	DialogueManager.openDialogue(player, 146)


def tradeWith581(player, npc):
	Shop.openShop(player, 6, 1)


def talkTo594(player, npc):
	DialogueManager.openDialogue(player, 149)


def tradeWith594(player, npc):
	Shop.openShop(player, 3, 1)


def talkTo579(player, npc):
	DialogueManager.openDialogue(player, 152)

def talkTo583(player, npc):
	DialogueManager.openDialogue(player, 155)


def talkTo558(player, npc):
	DialogueManager.openDialogue(player, 161)


def tradeWith558(player, npc):
	Shop.openShop(player, 23, 1)


def talkTo557(player, npc):
	DialogueManager.openDialogue(player, 164)


def tradeWith557(player, npc):
	Shop.openShop(player, 12, 1)


def tradeWith583(player, npc):
	Shop.openShop(player, 24, 1)


def talkTo380(player, npc):
	DialogueManager.openDialogue(player, 171)


def tradeWith380(player, npc):
	DialogueManager.openDialogue(player, 172)


def talkTo1303(player, npc):
	if(player.completedFremennikTrials()):
		
		DialogueManager.openDialogue(player, 174)
	else:
		DialogueManager.openDialogue(player, 181)
		
		
def tradeWith1303(player, npc):
	if player.completedFremennikTrials():
		Shop.openShop(player, 13, 1)
	else:
		player.getActionSender().sendMessage("You must complete the Fremennik Trials minigame before accessing this shop.")
	

def talkTo1369(player, npc):
	DialogueManager.openDialogue(player, 177)


def tradeWith1369(player, npc):
	Shop.openShop(player, 14, 1)


def talkTo1301(player, npc):
	DialogueManager.openDialogue(player, 188)


def tradeWith1301(player, npc):
	Shop.openShop(player, 15, 1)


def talkTo549(player, npc):
	DialogueManager.openDialogue(player, 191)


def tradeWith549(player, npc):
	Shop.openShop(player, 16, 1)


def talkTo379(player, npc):
	DialogueManager.openDialogue(player, 197)


def talkTo568(player, npc):
	DialogueManager.openDialogue(player, 201)


def tradeWith568(player, npc):
	Shop.openShop(player, 18, 1)


def talkTo2620(player, npc):
	DialogueManager.openDialogue(player, 204)


def tradeWith2620(player, npc):
	Shop.openShop(player, 19, 1)


def talkTo2623(player, npc):
	DialogueManager.openDialogue(player, 207)


def tradeWith2623(player, npc):
	Shop.openShop(player, 20, 1)


def talkTo2619(player, npc):
	DialogueManager.openDialogue(player, 210)

def fishTwo334(player, npc):
	player.getActionQueue().addAction_Fishing(player, Fishing.Fish.SHARK, npc)


def fishTwo333(player, npc):
	player.getActionQueue().addAction(Fishing(player, Fishing.Fish.SWORDFISH, npc))


def fishTwo330(player, npc):
	player.getActionQueue().addAction(Fishing(player, Fishing.Fish.HERRING, npc))


def fish333(player, npc):
	player.getActionQueue().addAction(Fishing(player, Fishing.Fish.LOBSTER, npc))


def fish330(player, npc):
	player.getActionQueue().addAction(Fishing(player, Fishing.Fish.SHRIMP, npc))


def fish329(player, npc):
	player.getActionQueue().addAction(Fishing(player, Fishing.Fish.SALMON, npc))


def fishTwo329(player, npc):
	player.getActionQueue().addAction(Fishing(player, Fishing.Fish.PIKE, npc))
