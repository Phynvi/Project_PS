package org.rs2server.rs2.model;

/**
 * Contains client-side settings.
 * 
 * @author Graham Edgecombe
 * 
 */
public class Settings {

	/**
	 * Withdraw as notes flag.
	 */
	private boolean withdrawAsNotes = false;

	/**
	 * Swapping flag.
	 */
	private boolean swapping = true;

	/**
	 * The player's brightness setting.
	 */
	private int brightnessSetting = 2;

	/**
	 * The two mouse buttons flag.
	 */
	private boolean twoMouseButtons = true;

	/**
	 * The chat effects flag.
	 */
	private boolean chatEffects = true;

	/**
	 * The split private chat flag.
	 */
	private boolean splitPrivateChat = false;

	/**
	 * The accept aid flag.
	 */
	private boolean acceptAid = false;

	/**
	 * The auto retaliate flag.
	 */
	private boolean autoRetaliate = true;

	/**
	 * @return the chatEffects
	 */
	public boolean chatEffects() {
		return chatEffects;
	}

	/**
	 * @return the brightnessSetting
	 */
	public int getBrightnessSetting() {
		return brightnessSetting;
	}

	/**
	 * @return the acceptAid
	 */
	public boolean isAcceptingAid() {
		return acceptAid;
	}

	/**
	 * @return the autoRetaliate
	 */
	public boolean isAutoRetaliating() {
		return autoRetaliate;
	}

	/**
	 * Checks if the player is swapping.
	 * 
	 * @return The swapping flag.
	 */
	public boolean isSwapping() {
		return swapping;
	}

	/**
	 * Checks if the player is withdrawing as notes.
	 * 
	 * @return The withdrawing as notes flag.
	 */
	public boolean isWithdrawingAsNotes() {
		return withdrawAsNotes;
	}

	/**
	 * @param acceptAid
	 *            the acceptAid to set
	 */
	public void setAcceptAid(boolean acceptAid) {
		this.acceptAid = acceptAid;
	}

	/**
	 * @param autoRetaliate
	 *            the autoRetaliate to set
	 */
	public void setAutoRetaliate(boolean autoRetaliate) {
		this.autoRetaliate = autoRetaliate;
	}

	/**
	 * @param brightnessSetting
	 *            the brightnessSetting to set
	 */
	public void setBrightnessSetting(int brightnessSetting) {
		this.brightnessSetting = brightnessSetting;
	}

	/**
	 * @param chatEffects
	 *            the chatEffects to set
	 */
	public void setChatEffects(boolean chatEffects) {
		this.chatEffects = chatEffects;
	}

	/**
	 * @param splitPrivateChat
	 *            the splitPrivateChat to set
	 */
	public void setSplitPrivateChat(boolean splitPrivateChat) {
		this.splitPrivateChat = splitPrivateChat;
	}

	/**
	 * Sets the swapping flag.
	 * 
	 * @param swapping
	 *            The swapping flag.
	 */
	public void setSwapping(boolean swapping) {
		this.swapping = swapping;
	}

	/**
	 * @param twoMouseButtons
	 *            the twoMouseButtons to set
	 */
	public void setTwoMouseButtons(boolean twoMouseButtons) {
		this.twoMouseButtons = twoMouseButtons;
	}

	/**
	 * Sets the withdraw as notes flag.
	 * 
	 * @param withdrawAsNotes
	 *            The flag.
	 */
	public void setWithdrawAsNotes(boolean withdrawAsNotes) {
		this.withdrawAsNotes = withdrawAsNotes;
	}

	/**
	 * @return the splitPrivateChat
	 */
	public boolean splitPrivateChat() {
		return splitPrivateChat;
	}

	/**
	 * @return the twoMouseButtons
	 */
	public boolean twoMouseButtons() {
		return twoMouseButtons;
	}

}
