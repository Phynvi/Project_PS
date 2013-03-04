package org.rs2server.rs2.model.content;

import org.rs2server.rs2.action.impl.ItemInteractAction;
import org.rs2server.rs2.model.Item;
import org.rs2server.rs2.model.Mob;

public class SpinningPlate {

	public static SpinningPlate instance = null;

	/**
	 * An instance of the teleport action
	 * 
	 * @param mob
	 * @return the instance.
	 */
	public static SpinningPlate getSpinningPlate() {
		if (instance == null) {
			instance = new SpinningPlate();
		}
		return instance;
	}

	protected final int spinningAnimation = 1902;
	protected final int droppingAnimation = 1904;

	public int getDroppingAnimation() {
		return droppingAnimation;
	}

	public int getSpinningAnimation() {
		return spinningAnimation;
	}

	public void init(Mob mob, Item item) {
		ItemInteractAction spinningPlateAction = new ItemInteractAction(mob,
				item);
		spinningPlateAction.execute();

	}

}
