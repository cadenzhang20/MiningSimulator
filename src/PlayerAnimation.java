/*
 * PlayerAnimation.java
 * by Evan, Caleb, and Caden
 * 
 * file started: Saturday, January 18, 2025
 * file finished: Monday, January 20, 2025
 * 
 * this is the object that describes the player animation
/*/

public class PlayerAnimation {
	private int type; // 0: null   1: movement   2: interaction
	private double offset;
	private int frame;
	private final int totalFrames = 8;
	private int direction;

	/**
	 * makes the player begin animating
	 */
	public void setAnimation(int type, int direction) {
		this.type = type;
		this.direction = direction;
		type = 1;
		frame = 0;
	}

	/**
	 * finds the next frame of a player character
	 * @param p
	 */
	public void nextFrame() {
		if (type == 0) return;	// escape if not animating
		if (type == 1) {
			offset = 96 * (1 - (double) frame / totalFrames);
		} else if (type == 2) {
			double half = (double) totalFrames / 2;
			offset = - 64 * ((half - Math.abs(frame - half)) / totalFrames);
		}

		// increament frame
		frame++;
		if (frame == totalFrames) {
			type = 0;
			offset = 0;
		}
	}

	/**
	 * gives the x offset
	 * @return	int x offset
	 */
	public int getXOffset() {
		if (direction == 0) return (int) offset;
		if (direction == 1) return - (int) offset;
		return 0;
	}

	/**
	 * gives the y offset
	 * @return	int y offset
	 */
	public int getYOffset() {
		if (direction == 2) return (int) offset;
		if (direction == 3) return - (int) offset;
		return 0;
	}
}
