/*
 * Animation.java
 * by Evan, Caleb, and Caden
 * 
 * file started: Friday, January 17, 2025
 * file finished: Monday, January 20, 2025
 * 
 * this is the object for animations
 * contains particular ways a tile should be animated
/*/

public class Animation {
	private int type; // 0: nothing    1: animating
	private int frame; 
	private double offset;
	private int direction;
	private final double magnitude = 32;
	private final int totalFrames = 96;

	/**
	 * animation constructor
	 */
	public Animation() {
		type = 0;
		frame = 0;
		offset = 0;
	}

	/**
	 * starts the animation of a tile
	 * @param type				the type of animation
	 * @param direction 	the direction of the player
	 */
	public void setAnimation(int type, int direction) {
		this.type = type;
		this.direction = direction;
		frame = 0;
	}

	/**
	 * increaments the frame by one
	 */
	public void nextFrame() {
		if (type == 0) return;	// escape if not animating
		offset = precompAnimation[frame] * magnitude;

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
		if (direction == 0) return - (int) offset;
		if (direction == 1) return (int) offset;
		return 0;
	}

	/**
	 * gives the y offset
	 * @return	int y offset
	 */
	public int getYOffset() {
		if (direction == 2) return - (int) offset;
		if (direction == 3) return (int) offset;
		return 0;
	}

	/* ************************************************************
	 * STATIC PRECOMPUTATION
	/* ************************************************************ */
	private static int precompSize = 100;
	private static double precompTime = 0.25;
	private static double[] precompAnimation = new double[precompSize];

	static {
		// precomputation of a timely formula
		double x;
		for (int i = 0; i < precompSize; i++) {
			x = i / (precompSize * precompTime) ;
			precompAnimation[i] = 1.62361383968 * Math.sin(20 * Math.pow(x, 1.5)) / Math.pow(x + 1, 3);
		}
	}
}
