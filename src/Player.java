/*
 * Player.java
 * by Evan, Caleb, and Caden
 * 
 * file started: Friday, January 10, 2025
 * file finished: Monday, January 20, 2025
 * 
 * this contains all the data of the player
/*/

public class Player {
	static String[] spriteDirection = new String[] {"player-left", "player-right", "player-up", "player-down"};
	int direction; // 0, 1, 2, 3 for left, right, up, down
	int x, y;
	/**
	 * the Player constructor
	 * @param gc	graphics console to draw on
	 * @param x		x pos of player
	 * @param y		y pos of player
	 */
	public Player(int x, int y, int direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	private static int[][] directionArray = {
		{0, -1},
		{0, 1},
		{-1, 0},
		{1, 0}
	};

	/**
	 * moves the player when bound in a grid
	 * @param player		the player to move
	 * @param grid			the grid the player is bound on
	 * @return					the new valid position
	 */
	public int[] nextTile(String[][] grid) {
		int[] increament = directionArray[direction];
		int[] goalTile = new int[] {y + increament[0], x + increament[1]};
		
		if (goalTile[0] < 0) goalTile[0] = 0;
		if (goalTile[1] < 0) goalTile[1] = 0;
		if (goalTile[0] > grid.length - 1) goalTile[0] = grid.length - 1;
		if (goalTile[1] > grid[0].length - 1) goalTile[1] = grid[0].length - 1;

		return goalTile;
	}
}
