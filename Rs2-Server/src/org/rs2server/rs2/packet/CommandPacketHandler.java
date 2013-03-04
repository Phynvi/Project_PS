package org.rs2server.rs2.packet;

import org.rs2server.rs2.ScriptManager;
import org.rs2server.rs2.model.Player;
import org.rs2server.rs2.model.Player.Rights;
import org.rs2server.rs2.net.Packet;

/**
 * Handles player commands (the ::words).
 * 
 * @author Graham Edgecombe
 * @author Josh Mai
 * 
 */
public class CommandPacketHandler implements PacketHandler {

	@Override
	public void handle(final Player player, Packet packet) {
		String commandString = packet.getRS2String();
		commandString = commandString.replaceAll(":", "");
		String[] args = commandString.split(" ");
		String command = args[0].toLowerCase();
		String scriptName = "command_".concat(command);
		try {
			if (command.equals("xreload")) {
				ScriptManager.getScriptManager().loadScripts();
			} else {
				if (!ScriptManager.getScriptManager().call(scriptName, player,
						args)) {
					if (player.getRights().toInteger() >= Rights.MODERATOR
							.toInteger()) {
						if (command.equals("modtest")) {
							player.getActionSender().sendMessage(
									"You are a Moderator/Administrator!");
						} else {
							scriptName = "mod_".concat(scriptName);
							if (!ScriptManager.getScriptManager().call(
									scriptName, player, args)) {
								if (player.getRights().equals(
										Rights.ADMINISTRATOR)) {
									if (command.startsWith("shutdown")) {
										System.exit(1);
										
									} else {
										scriptName = "admin_".concat("command_"
												.concat(command));
										if (!ScriptManager.getScriptManager()
												.call(scriptName, player, args)) {
											player.getActionSender()
													.sendMessage(
															"Oops! You have attempted to execute a non-existant command : "
																	+ command);
										}

									}
								} else {
									player.getActionSender().sendMessage(
											"Oops! You have attempted to execute a non-existant command : "
													+ command);
								}

							}

						}
					} else {
						player.getActionSender().sendMessage(
								"Oops! You have attempted to execute a non-existant command : "
										+ command);
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			player.getActionSender().sendMessage(
					"Error while processing command.");
		}
	}
}
