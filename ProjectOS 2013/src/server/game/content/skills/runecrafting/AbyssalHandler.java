package server.game.content.skills.runecrafting;

import server.game.players.Client;

public class AbyssalHandler {
    
    public static void handleAbyssalTeleport(Client c , int objectId) {
        switch (objectId) {
            case 7147://added
                c.getPA().movePlayer(3030, 4842, 0);
                break;
            case 7133://added nature
                c.getPA().startTeleport(2395, 4841, 0, "modern");
                break;
            case 7132: //cosmic
                c.getPA().startTeleport(2144, 4831, 0, "modern");
                break;
            case 7129: //fire
                c.getPA().startTeleport(2585, 4836, 0, "modern");
                break;
            case 7130: //earth
                c.getPA().startTeleport(2658, 4839, 0, "modern");
                break;
            case 7131: //body
                c.getPA().startTeleport(2525, 4830, 0, "modern");
                break;
            case 7140: //mind
                c.getPA().startTeleport(2786, 4834, 0, "modern");
                break;
            case 7139: //air
                c.getPA().startTeleport(2844, 4837, 0, "modern");
                break;
            case 7138: //soul
                c.sendMessage("This altar is disabled atm.");
                break;
            case 7141: //blood
                c.sendMessage("This altar is disabled atm.");
                break;
            case 7137: //water
                c.getPA().startTeleport(2722, 4833, 0, "modern");
                break;
            case 7136: //death
                c.getPA().startTeleport(2205, 4834, 0, "modern");
                break;
            case 7135:
                c.getPA().startTeleport(2464, 4830, 0, "modern");
                break;
			case 7134: // chaos
				 c.getPA().startTeleport(2274, 4842, 0, "modern");
                break;
			default:
                c.sendMessage("If you see this message, please PM an Administrator.");
            break;
        }
        c.sendMessage("As you click the object, you teleport to a mystical place...");
    }

}
