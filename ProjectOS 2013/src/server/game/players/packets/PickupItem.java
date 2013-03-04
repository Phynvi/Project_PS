package server.game.players.packets;

import server.Server;
import server.game.players.Client;
import server.game.players.PacketType;
import server.task.Task;
import server.world.GlobalDropsHandler;

/**
 * Pickup Item
 **/
public class PickupItem implements PacketType {

	@Override
	public void processPacket(final Client c, int packetType, int packetSize) {
		c.pItemY = c.getInStream().readSignedWordBigEndian();
		c.pItemId = c.getInStream().readUnsignedWord();
		c.pItemX = c.getInStream().readSignedWordBigEndian();
		GlobalDropsHandler.pickup(c, c.pItemId, c.pItemX, c.pItemY);
		if (Math.abs(c.getX() - c.pItemX) > 25
				|| Math.abs(c.getY() - c.pItemY) > 25) {
			c.resetWalkingQueue();
			return;
		}
		c.getCombat().resetPlayerAttack();
		if (c.getX() == c.pItemX && c.getY() == c.pItemY) {
			Server.itemHandler.removeGroundItem(c, c.pItemId, c.pItemX,
					c.pItemY, true);
		} else {
			c.walkingToItem = true;
			Server.getTaskScheduler().schedule(new Task(1) {
                @Override
                protected void execute() {
			        if(!c.walkingToItem)
			            stop();
			        if(c.getX() == c.pItemX && c.getY() == c.pItemY) {
			            Server.itemHandler.removeGroundItem(c, c.pItemId, c.pItemX, c.pItemY, true);
			            stop();
			        }
			    }
			    @Override
			    public void stop() {
			        c.walkingToItem = false;
			    }
			});
		}
	}
}
