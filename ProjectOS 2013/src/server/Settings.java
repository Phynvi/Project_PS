package server;

import server.game.players.Client;

public class Settings {

	private boolean running = true;
	private boolean autoRetaliate = true;
	private byte brightness = 1;
	private byte mouseButtons = 2;
	private boolean chatEffects = true;
	private boolean splitPrivateChat = true; //dat changes upon click....-.
	private boolean acceptAid = false; 
	private byte musicVolume = 5;
	private byte effectVolume = 5;

	public boolean running() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean autoRetaliate() {
		return autoRetaliate;
	}

	public void setAutoRetaliate(boolean autoRetaliate) {
		this.autoRetaliate = autoRetaliate;
	}

	public byte getBrightness() {
		return brightness;
	}

	public void setBrightness(byte brightness) {
		this.brightness = brightness;
	}

	public byte getMouseButtons() {
		return mouseButtons;
	}

	public void setMouseButtons(byte mouseButtons) {
		this.mouseButtons = mouseButtons;
	}

	public boolean chatEffects() {
		return chatEffects;
	}

	public void setChatEffects(boolean chatEffects) {
		this.chatEffects = chatEffects;
	}

	public boolean splitPrivateChat() {
		return splitPrivateChat;
	}

	public void setSplitPrivateChat(boolean splitPrivateChat) {
		this.splitPrivateChat = splitPrivateChat;
	}

	public boolean acceptAid() {
		return acceptAid;
	}

	public void setAcceptAid(boolean acceptAid) {
		this.acceptAid = acceptAid;
	}

	public byte getMusicVolume() {
		return musicVolume;
	}

	public void setMusicVolume(byte musicVolume) {
		this.musicVolume = musicVolume;
	}

	public byte getEffectVolume() {
		return effectVolume;
	}

	public void setEffectVolume(byte effectVolume) {
		this.effectVolume = effectVolume;
	}

	public void sendSettings(Client client) {
		client.getPA().sendConfig(173, running ? 1 : 0);
		client.getPA().sendConfig(172, autoRetaliate ? 0 : 1);
		client.getPA().sendConfig(166, brightness);
		client.getPA().sendConfig(170, mouseButtons == 2 ? 0 : 1);
		client.getPA().sendConfig(171, chatEffects ? 0 : 1);
		client.getPA().sendConfig(287, splitPrivateChat ? 1 : 0);
		client.getPA().sendConfig(427, acceptAid ? 1 : 0);
		switch (musicVolume) {
		case 0:
			client.getPA().sendConfig(168, 4);
			break;
		case 1:
			client.getPA().sendConfig(168, 3);
			break;
		case 2:
			client.getPA().sendConfig(168, 2);
			break;
		case 3:
			client.getPA().sendConfig(168, 1);
			break;
		case 4:
			client.getPA().sendConfig(168, 0);
			break;
		}
		switch (effectVolume) {
		case 0:
			client.getPA().sendConfig(169, 4);
			break;
		case 1:
			client.getPA().sendConfig(169, 3);
			break;
		case 2:
			client.getPA().sendConfig(169, 2);
			break;
		case 3:
			client.getPA().sendConfig(169, 1);
			break;
		case 4:
			client.getPA().sendConfig(169, 0);
			break;
		}
	}

}