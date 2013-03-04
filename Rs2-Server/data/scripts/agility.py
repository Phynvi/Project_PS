from org.rs2server.rs2.model import *
from org.rs2server.rs2.model.skills import Agility
from java.util import Random

def varrockFence(player, obstacle, obj):
	y = -2
	direction = 2
	if player.getLocation().getY() < 3335:
		y = 2
		direction = 4
	forceMovementVars =  [ 0, 0, 0, y, 33, 60, direction, 2 ]
	Agility.forceMovement(player, Animation.create(6132), forceMovementVars, 1, True)
	

def wildernessDitch(player, obstacle, obj):
	y = 3
	dir = 0
	if(player.getLocation().getY() > 3520):
		y = -3
		dir = 2
	
	forceMovementVars =  [ 0, 0, 0, y, 33, 60, dir, 2 ]
	Agility.forceMovement(player, Animation.create(6132), forceMovementVars, 1, True)


def faladorCrumblingWall(player, obstacle, obj):
	x = 2
	dir = 1
	if(player.getLocation().getX() >= 2936):
		x = -2
		dir = 3
	
	forceMovementVars =  [ 0, 0, x, 0, 20, 60, dir, 2 ]
	Agility.forceMovement(player, Animation.create(839), forceMovementVars, 1, True)
	player.getSkills().addExperience(Skills.AGILITY, obstacle.getExperience())


def gnomeLogBalance(player, obstacle, obj):
	if(player.getLocation().getX() != 2474):
		player.removeAttribute('busy')
		return
	
	gnomeAgilityCourseLvl = player.getAttribute('gnomeAgilityCourse')
	if(gnomeAgilityCourseLvl == None):
		player.setAttribute('gnomeAgilityCourse', 1)
	
	Agility.setRunningToggled(player, False, 7)
	Agility.forceWalkingQueue(player, Animation.create(762), 2474, 3429, 0, 7, True)
	player.getSkills().addExperience(Skills.AGILITY, obstacle.getExperience())


def gnomeObstacleNet(player, obstacle, obj):
	player.face(Location.create(player.getLocation().getX(), 0, 0))
	gnomeAgilityCourseLvl = player.getAttribute('gnomeAgilityCourse')
	if gnomeAgilityCourseLvl == 1:
		player.setAttribute('gnomeAgilityCourse', 2)
	
	Agility.forceTeleport(player, Animation.create(828), Location.create(player.getLocation().getX(), 3424, 1), 0, 2)
	player.getSkills().addExperience(Skills.AGILITY, obstacle.getExperience())


def gnomeTreeBranch(player, obstacle, obj):
	gnomeAgilityCourseLvl = player.getAttribute('gnomeAgilityCourse')
	if gnomeAgilityCourseLvl == 2:
		player.setAttribute('gnomeAgilityCourse', 3)
	
	Agility.forceTeleport(player, Animation.create(828), Location.create(2473, 3420, 2), 0, 2)
	player.getSkills().addExperience(Skills.AGILITY, obstacle.getExperience())


def gnomeBalanceRope(player, obstacle, obj):
	if !player.getLocation().equals(Location.create(2477, 3420, 2)):
		player.removeAttribute('busy')
		return
	
	gnomeAgilityCourseLvl = player.getAttribute('gnomeAgilityCourse')
	if(gnomeAgilityCourseLvl == 3):
		player.setAttribute('gnomeAgilityCourse', 4)
	
	Agility.setRunningToggled(player, False, 7)
	Agility.forceWalkingQueue(player, Animation.create(762), 2483, 3420, 0, 7, True)
	player.getSkills().addExperience(Skills.AGILITY, obstacle.getExperience())


def gnomeTreeBranch2(player, obstacle, obj):
	gnomeAgilityCourseLvl = player.getAttribute('gnomeAgilityCourse')
	if(gnomeAgilityCourseLvl == 4):
		player.setAttribute('gnomeAgilityCourse', 5)
	
	Agility.forceTeleport(player, Animation.create(828), Location.create(2485, 3419, 0), 0, 2)
	player.getSkills().addExperience(Skills.AGILITY, obstacle.getExperience())


def gnomeObstacleNet2(player, obstacle, obj):
	if(player.getLocation().getY() != 3425):
		player.removeAttribute('busy')
		player.getActionSender().sendMessage("You can't go over the net from here.")
		return
	
	player.face(Location.create(player.getLocation().getX(), 9999, 0))
	gnomeAgilityCourseLvl = player.getAttribute('gnomeAgilityCourse')
	if(gnomeAgilityCourseLvl == 5):
		player.setAttribute('gnomeAgilityCourse', 6)
	
	Agility.forceTeleport(player, Animation.create(828), Location.create(player.getLocation().getX(), 3427, 0), 0, 2)
	player.getSkills().addExperience(Skills.AGILITY, obstacle.getExperience())


def gnomeObstaclePipe(player, obstacle, obj):
	if(!player.getLocation().equals(Location.create(2484, 3430, 0)) and !player.getLocation().equals(Location.create(2487, 3430, 0))):
		player.removeAttribute('busy')
		return
	
	if(player.getAttribute('gnomeAgilityCourse') != None):
		courseLevel = player.getAttribute('gnomeAgilityCourse')
		if(courseLevel == 6):
			player.getActionSender().sendMessage('You completed the course!')
			player.getSkills().addExperience(Skills.AGILITY, 40)
			player.removeAttribute('gnomeAgilityCourse')
		
	
	forceMovementVars =  [ 0, 2, 0, 5, 45, 100, 0, 3 ]
	forceMovementVars2 =  [ 0, 0, 0, 2, 0, 15, 0, 1 ]
	Agility.forceMovement(player, Animation.create(746), forceMovementVars, 1, False)
	Agility.forceMovement(player, Animation.create(748), forceMovementVars2, 5, True)
	player.getSkills().addExperience(Skills.AGILITY, obstacle.getExperience())


def barbarianObstaclePipe(player, obstacle, obj):
	if(!player.getLocation().equals(Location.create(2552, 3561, 0)) and !player.getLocation().equals(Location.create(2552, 3558, 0))):
		player.removeAttribute('busy')
		return
	
	y = -3
	dir = 2
	if(player.getLocation().getY() <= 3558):
		y = 3
		dir = 0
	
	forceMovementVars =  [ 0, 0, 0, y, 0, 60, dir, 2 ]
	Agility.forceMovement(player, Animation.create(749), forceMovementVars, 1, True)
	player.getSkills().addExperience(Skills.AGILITY, obstacle.getExperience())


def barbarianRopeSwing(player, obstacle, obj):
	if(!player.getLocation().equals(Location.create(2551, 3554, 0))):
		player.removeAttribute('busy')
		return
	random = Random()
	random= random.nextInt(player.getSkills().getLevel(Skills.AGILITY))
	success = True
	if(random< 20):
		success = False
	Agility.animateObject(obj, Animation.create(54), 0)
	Agility.animateObject(obj, Animation.create(55), 2)
	if(success):
		barbAgilityCourseLvl = player.getAttribute('barbarianAgilityCourse')
		if(barbAgilityCourseLvl == None):
			player.setAttribute('barbarianAgilityCourse', 1)
		
		forceMovementVars =  [ 0, 0, 0, -5, 30, 50, 2, 2 ]
		Agility.forceMovement(player, Animation.create(751), forceMovementVars, 1, True)
		player.getSkills().addExperience(Skills.AGILITY, obstacle.getExperience())
	else:
		forceMovementVars =  [ 0, 0, 0, -3, 30, 50, 2, 2 ]
		Agility.forceMovement(player, Animation.create(751), forceMovementVars, 1, True)
		Agility.forceTeleport(player, Animation.create(766), Location.create(2551, 9951, 0), 3, 6)
		Agility.forceWalkingQueue(player, None, 2549, 9951, 7, 2, True)
		Agility.setRunningToggled(player, False, 9)
		Agility.damage(player, 5, 7)
	


def barbarianLogBalance(player, obstacle, obj):
	if(player.getLocation().getY() != 3546):
		player.removeAttribute('busy')
		return
		
	random = Random()
	random= random.nextInt(player.getSkills().getLevel(Skills.AGILITY))
	success = True
	
	if(random< 20):
		success = False
	
	
	if(success):
		barbAgilityCourseLvl = player.getAttribute('barbarianAgilityCourse')
		if(barbAgilityCourseLvl == 1):
			player.setAttribute('barbarianAgilityCourse', 2)
		
		Agility.setRunningToggled(player, False, 12)
		Agility.forceWalkingQueue(player, Animation.create(762), 2541, 3546, 0, 11, True)
		player.getSkills().addExperience(Skills.AGILITY, obstacle.getExperience())
	else:
		player.face(Location.create(0, player.getLocation().getY(),0))
			
		Agility.forceTeleport(player, Animation.create(773), Location.create(2545, 3547, 0), 10, 12)
		Agility.setRunningToggled(player, False, 16)
		Agility.forceWalkingQueue(player, Animation.create(772), 2545, 3549, 12, 4, False)
		Agility.forceWalkingQueue(player, Animation.create(772), 2546, 3550, 13, 3, False)
		
		Agility.forceWalkingQueue(player, Animation.create(762), 2545, 3546, 0, 7, False)		
		
		forceMovementVars =  [ 0, 0, 0, 1, 25, 30, 3, 2 ]
		Agility.forceMovement(player, Animation.create(771), forceMovementVars, 8, False)
		
		Agility.forceTeleport(player, None, Location.create(2546, 3550, 0), 16, 16)		
	


def barbarianObstacleNet(player, obstacle, obj):
	if(player.getLocation().getX() != 2539):
		player.removeAttribute('busy')
		return
	
	if(player.getLocation().getY() >= 3547):
		player.removeAttribute('busy')
		return
	
	player.face(Location.create(0, player.getLocation().getY(), 0))
	barbAgilityCourseLvl = player.getAttribute('barbarianAgilityCourse')
	if(barbAgilityCourseLvl == 2):
		player.setAttribute('barbarianAgilityCourse', 3)
	
	Agility.forceTeleport(player, Animation.create(828), Location.create(player.getLocation().getX() - 2, 3546, 1), 0, 2)
	player.getSkills().addExperience(Skills.AGILITY, obstacle.getExperience())


def barbarianLedge(player, obstacle, obj):
	if(player.getLocation().getY() != 3547):
		player.removeAttribute('busy')
		return
	
	
	random = Random()
	random= random.nextInt(player.getSkills().getLevel(Skills.AGILITY))
	success = True
	
	if(random< 20):
		success = False
	
		
	player.face(Location.create(0, player.getLocation().getY(), 0))
	
	if(success):
		barbAgilityCourseLvl = player.getAttribute('barbarianAgilityCourse')
		if(barbAgilityCourseLvl == 2):
			player.setAttribute('barbarianAgilityCourse', 3)
		
		player.playAnimation(Animation.create(753))
		Agility.setRunningToggled(player, False, 8)
		Agility.forceWalkingQueue(player, None, 2532, 3546, 4, 2, False)
		Agility.forceWalkingQueue(player, Animation.create(756), 2532, 3547, 0, 4, False)	
		Agility.forceTeleport(player, Animation.create(828), Location.create(2532, 3546, 0), 7, 8)
		player.getSkills().addExperience(Skills.AGILITY, obstacle.getExperience())
	else:
		Agility.setRunningToggled(player, False, 8)
		Agility.forceTeleport(player, None, Location.create(2534, 3546, 1), 3, 3)
		Agility.forceWalkingQueue(player, None, 2536, 3547, 6, 3, True)
		Agility.forceTeleport(player, Animation.create(766), Location.create(2534, 3546, 0), 3, 5)
		Agility.forceWalkingQueue(player, Animation.create(756), 2534, 3547, 0, 2, False)
		Agility.damage(player, 5, 6)		
	


def barbarianCrumblingWall1(player, obstacle, obj):
	if(!player.getLocation().equals(Location.create(2535, 3553, 0))):
		player.removeAttribute('busy')
		return
	
	forceMovementVars =  [ 0, 0, 2, 0, 0, 60, 1, 2 ]
	Agility.forceMovement(player, Animation.create(839), forceMovementVars, 1, True)
	player.getSkills().addExperience(Skills.AGILITY, obstacle.getExperience())
	barbAgilityCourseLvl = player.getAttribute('barbarianAgilityCourse')
	if(barbAgilityCourseLvl == 3):
		player.setAttribute('barbarianAgilityCourse', 4)
	


def barbarianCrumblingWall2(player, obstacle, obj):
	if(!player.getLocation().equals(Location.create(2538, 3553, 0))):
		player.removeAttribute('busy')
		return
	
	forceMovementVars =  [ 0, 0, 2, 0, 0, 60, 1, 2 ]
	Agility.forceMovement(player, Animation.create(839), forceMovementVars, 1, True)
	player.getSkills().addExperience(Skills.AGILITY, obstacle.getExperience())
	barbAgilityCourseLvl = player.getAttribute('barbarianAgilityCourse')
	if(barbAgilityCourseLvl == 4):
		player.setAttribute('barbarianAgilityCourse', 5)
	


def barbarianCrumblingWall3(player, obstacle, obj):
	if(!player.getLocation().equals(Location.create(2541, 3553, 0))):
		player.removeAttribute('busy')
		return
	
	forceMovementVars =  [ 0, 0, 2, 0, 0, 60, 1, 2 ]
	Agility.forceMovement(player, Animation.create(839), forceMovementVars, 1, True)
	player.getSkills().addExperience(Skills.AGILITY, obstacle.getExperience())
	barbAgilityCourseLvl = player.getAttribute('barbarianAgilityCourse')
	if(barbAgilityCourseLvl == 5):
		player.getActionSender().sendMessage('You completed the course!')
		player.getSkills().addExperience(Skills.AGILITY, 46.2)
		player.removeAttribute('barbarianAgilityCourse')
	

