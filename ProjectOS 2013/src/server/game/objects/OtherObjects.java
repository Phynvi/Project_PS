package server.game.objects;

import server.Server;
import server.game.content.clipping.clip.region.ObjectDef;
import server.game.players.Client;
import server.task.Task;

public class OtherObjects {
	

	public static void useUp(final Client c, final int objectId) {
        c.startAnimation(828);
        Server.getTaskScheduler().schedule(new Task(1) {
            @Override
            protected void execute() {
            if (c.disconnected) {
                stop();
                return;
            }
            c.teleportToX = c.absX;
            c.teleportToY = c.absY - 6400;
            c.getPA().removeAllWindows();
            final String ObjectName = ObjectDef.getObjectDef(objectId).name;
            c.sendMessage("You climb up the " + ObjectName + ".");
            stop();
            }
        });
	}
	
	public static void useDown(final Client c, final int objectId) {
        c.startAnimation(827);
        Server.getTaskScheduler().schedule(new Task(1) {
            @Override
            protected void execute() {
            if (c.disconnected) {
                stop();
                return;
            }
            c.teleportToX = c.absX;
            c.teleportToY = c.absY + 6400;
            c.getPA().removeAllWindows();
            final String ObjectName = ObjectDef.getObjectDef(objectId).name;
            c.sendMessage("You climb down the " + ObjectName + ".");
            stop();
            }
        });
	}
}