package org.rs2server.rs2.model;

public enum CatSize {

	/*
	 * Data for the enum.
	 */
	SMALL_CAT(0), MEDIUM_CAT(30), LARGE_CAT(65), OVERWEIGHT_CAT(100);

	private int foodGiven;

	/**
	 * Constructs a new cat size.
	 * 
	 * @param foodGiven
	 *            The food needed to be given to the cat to achieve this size.
	 */
	CatSize(int foodGiven) {
		this.foodGiven = foodGiven;
	}

	public int getFoodGiven() {
		return foodGiven;
	}
}
