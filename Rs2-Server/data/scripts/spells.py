# Spells module created by Josh Mai - Sneakyhearts

from org.rs2server.rs2 import Constants
from org.rs2server.rs2.action.impl import TeleportAction
from org.rs2server.rs2.model import Animation, Graphic, Item, Location, Projectile, Skills, Sound
from org.rs2server.rs2.model.equipment import PoisonType
from org.rs2server.rs2.model.minigame.magetraining import MageTrainingArena
from org.rs2server.rs2.model.combat.impl import MagicCombatAction
from org.rs2server.util import Misc

def varrockTeleport(mob):
	TeleportAction.getTeleportAction(mob).castTeleport(TeleportAction.getTeleportAction(mob).getVarrockLocation())
	

def lumbridgeTeleport(mob):
	TeleportAction.getTeleportAction(mob).castTeleport(TeleportAction.getTeleportAction(mob).getLumbridgeLocation())
	


def faladorTeleport(mob):
	TeleportAction.getTeleportAction(mob).castTeleport(TeleportAction.getTeleportAction(mob).getFaladorLocation())
	


def camelotTeleport(mob):
	TeleportAction.getTeleportAction(mob).castTeleport(TeleportAction.getTeleportAction(mob).getCamelotLocation())
	


def ardougneTeleport(mob):
	TeleportAction.getTeleportAction(mob).castTeleport(TeleportAction.getTeleportAction(mob).getArdougneLocation())
	


def watchtowerTeleport(mob):
	TeleportAction.getTeleportAction(mob).castTeleport(TeleportAction.getTeleportAction(mob).getWatchtowerLocation())
	


def trollheimTeleport(mob):
	TeleportAction.getTeleportAction(mob).castTeleport(TeleportAction.getTeleportAction(mob).getTrollheimLocation())
	
	
def apeAtollTeleport(mob):
	TeleportAction.getTeleportAction(mob).castTeleport(TeleportAction.getTeleportAction(mob).getApeAtollLocation())
		

def paddewwaTeleport(mob):
	TeleportAction.getTeleportAction(mob).castTeleport(TeleportAction.getTeleportAction(mob).getPaddewwaLocation())
	


def senntistenTeleport(mob):
	TeleportAction.getTeleportAction(mob).castTeleport(TeleportAction.getTeleportAction(mob).getSenntistenLocation())
	


def kharyrllTeleport(mob):
	TeleportAction.getTeleportAction(mob).castTeleport(TeleportAction.getTeleportAction(mob).getKharyrllLocation())
	


def lassarTeleport(mob):
	TeleportAction.getTeleportAction(mob).castTeleport(TeleportAction.getTeleportAction(mob).getLassarLocation())
	


def dareeyakTeleport(mob):
	TeleportAction.getTeleportAction(mob).castTeleport(TeleportAction.getTeleportAction(mob).getDareeyakLocation())
	


def carrallangarTeleport(mob):
	TeleportAction.getTeleportAction(mob).castTeleport(TeleportAction.getTeleportAction(mob).getCarrallangarLocation())
	


def annakarlTeleport(mob):
	TeleportAction.getTeleportAction(mob).castTeleport(TeleportAction.getTeleportAction(mob).getAnnakarlLocation())
	


def ghorrockTeleport(mob):
	TeleportAction.getTeleportAction(mob).castTeleport(TeleportAction.getTeleportAction(mob).getGhorrockLocation())
	
	
	
def windStrike(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	anim = 1162 if bool(autocast) else 711
	attacker.playAnimation(Animation.create(anim))
	attacker.playGraphics(Graphic.create(90, 0, 100))
	attacker.playSound(Sound.create(220, 1, 0))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 91, 60, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(92, gfxDelay, 100), PoisonType.NONE, False, 2, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 5.5)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)
	
def waterStrike(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	anim = 1162 if bool(autocast) else 711
	attacker.playAnimation(Animation.create(anim))
	attacker.playGraphics(Graphic.create(93, 0, 100))
	attacker.playSound(Sound.create(211, 1, 0))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 94, 60, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(95, gfxDelay, 100), PoisonType.NONE, False, 4, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 7.5)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def earthStrike(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	anim = 1162 if bool(autocast) else 711
	attacker.playAnimation(Animation.create(anim))
	attacker.playGraphics(Graphic.create(96, 0, 100))
	attacker.playSound(Sound.create(132, 1, 0))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 97, 60, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(98, gfxDelay, 100), PoisonType.NONE, False, 6, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 9.5)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def fireStrike(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	anim = 1162 if bool(autocast) else 711
	attacker.playAnimation(Animation.create(anim))
	attacker.playGraphics(Graphic.create(99, 0, 100))
	attacker.playSound(Sound.create(160, 1, 0))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 100, 60, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(101, gfxDelay, 100), PoisonType.NONE, False, 8, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 11.5)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def windBolt(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	anim = 1162 if bool(autocast) else 711
	attacker.playAnimation(Animation.create(anim))
	attacker.playGraphics(Graphic.create(117, 0, 100))
	attacker.playSound(Sound.create(218, 1, 0))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 118, 60, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(119, gfxDelay, 100), PoisonType.NONE, False, 9, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 13.5)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def waterBolt(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	anim = 1162 if bool(autocast) else 711
	attacker.playAnimation(Animation.create(anim))
	attacker.playGraphics(Graphic.create(120, 0, 100))
	attacker.playSound(Sound.create(209, 1, 0))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 121, 60, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(122, gfxDelay, 100), PoisonType.NONE, False, 10, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 16.5)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def earthBolt(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	anim = 1162 if bool(autocast) else 711
	attacker.playAnimation(Animation.create(anim))
	attacker.playGraphics(Graphic.create(123, 0, 100))
	attacker.playSound(Sound.create(130, 1, 0))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 124, 60, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(125, gfxDelay, 100), PoisonType.NONE, False, 11, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 19.5)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def fireBolt(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	anim = 1162 if bool(autocast) else 711
	attacker.playAnimation(Animation.create(anim))
	attacker.playGraphics(Graphic.create(126, 0, 100))
	attacker.playSound(Sound.create(157, 1, 0))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 127, 60, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(128, gfxDelay, 100), PoisonType.NONE, False, 12, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 21.5)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def windBlast(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	anim = 1162 if bool(autocast) else 711
	attacker.playAnimation(Animation.create(anim))
	attacker.playGraphics(Graphic.create(132, 0, 100))
	attacker.playSound(Sound.create(216, 1, 0))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 133, 60, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(134, gfxDelay, 100), PoisonType.NONE, False, 13, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 25.5)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def waterBlast(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	anim = 1162 if bool(autocast) else 711
	attacker.playAnimation(Animation.create(anim))
	attacker.playGraphics(Graphic.create(135, 0, 100))
	attacker.playSound(Sound.create(207, 1, 0))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 136, 60, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(137, gfxDelay, 100), PoisonType.NONE, False, 14, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 28.5)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def earthBlast(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	anim = 1162 if bool(autocast) else 711
	attacker.playAnimation(Animation.create(anim))
	attacker.playGraphics(Graphic.create(138, 0, 100))
	attacker.playSound(Sound.create(128, 1, 0))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 139, 60, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(140, gfxDelay, 100), PoisonType.NONE, False, 15, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 31.5)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def fireBlast(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	anim = 1162 if bool(autocast) else 711
	attacker.playAnimation(Animation.create(anim))
	attacker.playGraphics(Graphic.create(129, 0, 100))
	attacker.playSound(Sound.create(155, 1, 0))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 130, 60, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(131, gfxDelay, 100), PoisonType.NONE, False, 16, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 34.5)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def windWave(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	anim = 1167 if bool(autocast) else 727
	attacker.playSound(Sound.create(222, 1, 0))
	attacker.playAnimation(Animation.create(anim))
	attacker.playGraphics(Graphic.create(158, 0, 100))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 159, 45, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 10, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(160, gfxDelay, 100), PoisonType.NONE, False, 17, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 36)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def waterWave(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	anim = 1167 if bool(autocast) else 727
	attacker.playSound(Sound.create(213, 1, 0))
	attacker.playAnimation(Animation.create(anim))
	attacker.playGraphics(Graphic.create(161, 0, 100))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 162, 45, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 10, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(163, gfxDelay, 100), PoisonType.NONE, False, 18, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 37.5)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def earthWave(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	anim = 1167 if bool(autocast) else 727
	attacker.playSound(Sound.create(134, 1, 0))
	attacker.playAnimation(Animation.create(anim))
	attacker.playGraphics(Graphic.create(164, 0, 100))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 165, 45, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 10, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(166, gfxDelay, 100), PoisonType.NONE, False, 19, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 40)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def fireWave(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	anim = 1167 if bool(autocast) else 727
	attacker.playSound(Sound.create(162, 1, 0))
	attacker.playAnimation(Animation.create(anim))
	attacker.playGraphics(Graphic.create(155, 0, 100))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 156, 45, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 10, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(157, gfxDelay, 100), PoisonType.NONE, False, 20, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 42.5)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def saradominStrike(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	chargedbonus = 10 if attacker.getCombatState().isCharged() else 0
	attacker.playAnimation(Animation.create(811))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(76, 60, 100), PoisonType.NONE, False, 20 + chargedbonus, 3, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 35)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def clawsOfGuthix(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	chargedbonus = 10 if attacker.getCombatState().isCharged() else 0
	attacker.playAnimation(Animation.create(811))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(77, 60, 100), PoisonType.NONE, False, 20 + chargedbonus, 3, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 35)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def flamesOfZamorak(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	chargedbonus = 10 if attacker.getCombatState().isCharged() else 0
	attacker.playAnimation(Animation.create(811))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(78, 60, 0), PoisonType.NONE, False, 20 + chargedbonus, 3, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 35)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def ibanBlast(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playAnimation(Animation.create(708))
	attacker.playGraphics(Graphic.create(87, 0, 100))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 88, 80, 50, clientSpeed, 50, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(89, gfxDelay, 100), PoisonType.NONE, False, 25, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 30)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def magicDart(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playAnimation(Animation.create(1576))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 328, 60, 50, clientSpeed, 48, 32, victim.getProjectileLockonIndex(), 5, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(329, gfxDelay, 100), PoisonType.NONE, False, 10 + (attacker.getSkills().getLevelForExperience(Skills.MAGIC) / 10), delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 30)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def crumbleUndead(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	anim = 1166 if bool(autocast) else 724
	attacker.playAnimation(Animation.create(anim))
	attacker.playGraphics(Graphic.create(145, 0, 100))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 146, 60, 50, clientSpeed, 40, 32, victim.getProjectileLockonIndex(), 5, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(147, gfxDelay, 100), PoisonType.NONE, False, 15, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 24.5)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)
	
def teleBlock(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playAnimation(Animation.create(1819))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 344, 60, 50, clientSpeed, 40, 32, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(345, gfxDelay, 0), PoisonType.NONE, False, 3, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 80)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)


def bind(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playAnimation(Animation.create(710))
	attacker.playGraphics(Graphic.create(177, 0, 100))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 178, 80, 50, clientSpeed + 20, 43, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(181, gfxDelay + 20, 100), PoisonType.NONE, False, 0, delay, 8)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 30)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)


def snare(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playAnimation(Animation.create(710))
	attacker.playGraphics(Graphic.create(177, 0, 100))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 178, 80, 50, clientSpeed + 20, 43, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(180, gfxDelay + 20, 100), PoisonType.NONE, False, 3, delay, 17)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 60.5)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)


def entangle(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playAnimation(Animation.create(710))
	attacker.playGraphics(Graphic.create(177, 0, 100))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 178, 80, 50, clientSpeed + 20, 43, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(179, gfxDelay + 20, 100), PoisonType.NONE, False, 5, delay, 25)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 91)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)


def confuse(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playAnimation(Animation.create(716))
	attacker.playGraphics(Graphic.create(102, 0, 100))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 103, 60, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(104, gfxDelay, 100), PoisonType.NONE, False, 0, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 13)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)


def weaken(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playAnimation(Animation.create(717))
	attacker.playGraphics(Graphic.create(105, 0, 100))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 106, 60, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(107, gfxDelay, 100), PoisonType.NONE, False, 0, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 21)
	attacker.geyCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)


def curse(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playAnimation(Animation.create(718))
	attacker.playGraphics(Graphic.create(108, 0, 100))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 109, 60, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(110, gfxDelay, 100), PoisonType.NONE, False, 0, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 29)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def vulnerability(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playAnimation(Animation.create(718))
	attacker.playGraphics(Graphic.create(167, 0, 100))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 168, 60, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(169, gfxDelay, 100), PoisonType.NONE, False, 0, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 76)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)


def enfeeble(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playAnimation(Animation.create(728))
	attacker.playGraphics(Graphic.create(170, 0, 100))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 171, 60, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(172, gfxDelay, 100), PoisonType.NONE, False, 0, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 83)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)


def stun(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playAnimation(Animation.create(729))
	attacker.playGraphics(Graphic.create(173, 0, 100))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 174, 60, 50, clientSpeed, 43, 35, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(175, gfxDelay, 100), PoisonType.NONE, False, 0, delay, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 90)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def smokeRush(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playSound(Sound.create(176, 1, 20))
	attacker.playAnimation(Animation.create(1978))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 384, 70, 50, 80, 48, 20, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(385, 80, 100), PoisonType.POISON, False, 13, 4, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 30)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def shadowRush(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playSound(Sound.create(178, 1, 20))
	attacker.playAnimation(Animation.create(1978))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 378, 70, 50, 80, 48, 0, victim.getProjectileLockonIndex(), 0, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(379, 80, 0), PoisonType.NONE, False, 14, 4, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 31)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)


def bloodRush(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playSound(Sound.create(108, 1, 20))
	attacker.playAnimation(Animation.create(1978))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(373, 80, 0), PoisonType.NONE, False, 15, 4, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 33)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)


def iceRush(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playSound(Sound.create(171, 1, 20))
	attacker.playAnimation(Animation.create(1978))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 360, 70, 50, 80, 43, 0, victim.getProjectileLockonIndex(), 0, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(361, 80, 0), PoisonType.NONE, False, 17, 4, 8)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 34)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)


def smokeBurst(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playSound(Sound.create(179, 1, 20))
	attacker.playAnimation(Animation.create(1979))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(389, 80, 0), PoisonType.POISON, True, 17, 4, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 35.5)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)


def shadowBurst(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playSound(Sound.create(175, 1, 20))
	attacker.playAnimation(Animation.create(1979))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(382, 80, 0), PoisonType.NONE, True, 18, 4, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 37)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)


def bloodBurst(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playSound(Sound.create(107, 1, 20))
	attacker.playAnimation(Animation.create(1979))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(376, 80, 0), PoisonType.NONE, True, 21, 4, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 39)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)


def iceBurst(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playSound(Sound.create(171, 1, 20))
	attacker.playAnimation(Animation.create(1979))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(363, 80, 0), PoisonType.NONE, True, 22, 4, 17)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 40)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)


def smokeBlitz(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playSound(Sound.create(183, 1, 20))
	attacker.playAnimation(Animation.create(1978))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 386, 70, 50, 80, 48, 20, victim.getProjectileLockonIndex(), 15, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(387, 80, 100), PoisonType.SUPER_POISON, False, 23, 4, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 42)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)


def shadowBlitz(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playSound(Sound.create(175, 1, 20))
	attacker.playAnimation(Animation.create(1978))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 380, 70, 50, 80, 48, 0, victim.getProjectileLockonIndex(), 0, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(381, 80, 0), PoisonType.NONE, False, 24, 4, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 43)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)

def bloodBlitz(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playSound(Sound.create(108, 1, 20))
	attacker.playAnimation(Animation.create(1978))
	attacker.playProjectile(Projectile.create(attacker.getCentreLocation(), victim.getCentreLocation(), 374, 70, 50, 80, 48, 0, victim.getProjectileLockonIndex(), 0, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(375, 80, 0), PoisonType.NONE, False, 25, 4, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 45)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)
	
	
def iceBlitz(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playSound(Sound.create(171, 1, 20))
	attacker.playAnimation(Animation.create(1978))
	attacker.playGraphics(Graphic.create(366))
	if victim.getSprites().getPrimarySprite() != -1 or victim.getSprites().getSecondarySprite() != -1:
		attacker.playProjectile(Projectile.create(victim.getCentreLocation(), victim.getCentreLocation(), 366, 70, 50, 90, 0, 0, victim.getProjectileLockonIndex(), 0, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(367, 80, 0), PoisonType.NONE, False, 26, 6, 25)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 46)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)	

def smokeBarrage(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playSound(Sound.create(179, 1, 20))
	attacker.playAnimation(Animation.create(1979))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(390, 80, 100), PoisonType.SUPER_POISON, True, 27, 4, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 48)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)


def shadowBarrage(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playSound(Sound.create(175, 1, 20))
	attacker.playAnimation(Animation.create(1979))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(383, 80, 0), PoisonType.NONE, True, 28, 4, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 49)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)


def bloodBarrage(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playSound(Sound.create(107, 1, 20))
	attacker.playAnimation(Animation.create(1979))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(377, 80, 0), PoisonType.NONE, True, 29, 4, 0)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 51)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)
	
def iceBarrage(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playAnimation(Animation.create(1979))
	attacker.playSound(Sound.create(171, 1, 0))
	if victim.getSprites().getPrimarySprite() != -1 or victim.getSprites().getSecondarySprite() != -1:
		attacker.playProjectile(Projectile.create(victim.getCentreLocation(), victim.getCentreLocation(), 368, 70, 50, 90, 0, 0, victim.getProjectileLockonIndex(), 0, 48))
	magicCombatAction.hitEnemy(attacker, victim, spell, Graphic.create(369, 60, 0), PoisonType.NONE, True, 30, 4, 33)
	attacker.getSkills().addExperience(Skills.MAGIC, Constants.EXP_MODIFIER * 52)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(6)


def bandosSpiritualMage(attacker, victim, spell, autocast, clientSpeed, magicCombatAction, gfxDelay, delay):
	attacker.playAnimation(Animation.create(4320))
	magicCombatAction.hitEnemy(attacker, victim, spell, null, PoisonType.NONE, False, 4, 1, 0)
	attacker.getCombatState().setSpellDelay(6)
	attacker.getCombatState().setAttackDelay(4)
	
	
def vengeance(mob):
	mob.playAnimation(Animation.create(4410))
	mob.playGraphics(Graphic.create(726, 0, 100))
	mob.getCombatState().setVengeance(True)
	mob.getCombatState().setCanVengeance(False)
	mob.getCombatState().setCanVengeance(50)
	
def vengeanceOther(mob, target):
	mob.playAnimation(Animation.create(4411))
	target.playGraphics(Graphic.create(725, 0, 100))
	target.getCombatState().setVengeance(True)
	mob.getCombatState().setCanVengeance(False)
	mob.getCombatState().setCanVengeance(50)	
	
	
def spellbookSwap(mob, spellBook):
	mob.playAnimation(Animation.create(6299))
	mob.playGraphics(Graphic.create(1062))
	mob.getCombatState().setCanSpellbookSwap(False)
	mob.getCombatState().setCanSpellbookSwap(200)
	mob.getActionSender().sendSidebarInterface(105, MagicCombatAction.SpellBook.forId(spellBook).getInterfaceId())
	mob.getCombatState().setSpellBook(spellBook)
	
	
def lowAlch(mob, item):
	amt = int(item.getDefinition().getValue() * 0.6)
	mob.getInventory().remove(Item(item.getId(), 1))
	mob.getInventory().add(Item(995, amt))
	mob.playAnimation(Animation.create(712))
	mob.playGraphics(Graphic.create(112, 0, 100))
	mob.playSound(Sound.create(98, 1, 20))
	mob.getSkills().addExperience(Skills.MAGIC, 25 * Constants.EXP_MODIFIER)
	
	
def highAlch(mob, item):
	amt = int(item.getDefinition().getValue() * .75)
	cItems = MageTrainingArena.getCupboarditems()
	xmin = 1
	xmax = 30
	currency = 995
	for i in cItems:
		if item.getId() == i:
			amt = Misc.random(xmin, xmax)
			currency = 8890
	mob.getInventory().remove(Item(item.getId(), 1))
	mob.getInventory().add(Item(currency, amt))
	mob.playAnimation(Animation.create(713))
	mob.playGraphics(Graphic.create(113, 0, 100))
	mob.playSound(Sound.create(97, 1, 20))
	mob.getSkills().addExperience(Skills.MAGIC, 50 * Constants.EXP_MODIFIER)
		
		
def charge(mob):
	if mob.getActionSender() != None:
		mob.getActionSender().sendMessage("You feel charged with a magical power.")
	mob.playAnimation(Animation.create(811))
	mob.playGraphics(Graphic.create(301))
	mob.getCombatState().setCharged(100 * 7)		



		
