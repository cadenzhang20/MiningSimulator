/*
 * Game.java
 * by Evan, Caleb, and Caden
 * 
 * file started: Monday, January 6, 2025
 * file finished: Monday, January 20, 2025
 * 
 * this is the main file in the entire game
/*/

import hsa2.GraphicsConsole;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static packages.GameVariables.*;

public class Game {
  public static void main(String[] args) {
    new Game();
  }

  // graphics console
  static final int winW = 768, winH = 576;
  GraphicsConsole gc = new GraphicsConsole(winW, winH, "Mining Simulator");

  // new fonts
  Font currenciesFont = new Font("Arial", Font.BOLD, 14);
  Font errorFont = new Font("Helvetica", Font.PLAIN, 12);
  // images hashmap
  public static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();

  // player variables
  Player player = new Player(0, 0, 2);
	PlayerAnimation playerAnimation = new PlayerAnimation();
	boolean keyClicked;

  // mouse position and keyboard down
	boolean[] keysDown = new boolean[256];

  // ticks
  int tick = 0;
	int lastMove = 0;
	int lastAutomine = 0;

  // game tiles
  String[][] field;
	Animation[][] animations;

	// window display variables
  double transparency = 255.0f;
  int transparencyForCurrency = 0;

  Game() {
    setup();
		gc.setVisible(false);
		new Title();
		gc.setVisible(true);
		gc.sleep(50);

    // game loop
    while (true) {
      controls(); 
      autominer();
      graphics();
			tick++;
      gc.sleep(10);
		}
	}

  /**
	 * setup of game
	 */
	void setup() {
		// welcome in console
		System.out.println("Hi, welcome to Mining Simulator");
		System.out.println("Debug:");
		System.out.println("7 - get 1000 coins");
		System.out.println("8 - get 1000 gems");
		System.out.println("9 - get 1000 stones");
		System.out.println("Have fun!");

		// setup graphics console
		gc.setAntiAlias(true);
		gc.setLocationRelativeTo(null);
		gc.setBackgroundColor(new Color(200, 200, 200, 255));
		gc.clear();

		// setup tile functions
		TileFunctions.setGame(this);

		// load images
		String fileName;
		for (File file : new File("src/images").listFiles()) {
			fileName = file.getName();
			int dotLocation = fileName.lastIndexOf('.');
			if (dotLocation != -1) images.put(fileName.substring(0, dotLocation), loadImage(file));
		}

		// setup game tile field
		field = new String[5][8]; // empty tiles
		animations = new Animation[5][8];
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[0].length; j++) {
				animations[i][j] = new Animation();
			}
		}

		// tiles with something on them
		field[0][3] = "upgrade-sell-value";
		field[0][4] = "upgrade-backpack";
    field[0][5] = "upgrade-gem-chance";
    field[0][6] = "sell-block";
    field[2][1] = "stone";
    field[4][3] = "autominer";
    field[4][4] = "autosell";
    field[4][5] = "rebirth";
    field[4][6] = "minigame";
	}
 

	/**
	 * method to handle the not enough coins display
	 */
	void notEnoughCoinsOrGems() {
    if (errormsg == true) {
      
    // Only reduce transparency if it's greater than 0
      if (transparency > 0) {
        transparency -= 1.0f; 
        gc.setColor(new Color(200, 200, 200, (int) transparency));
        gc.fillRect(550, 25, 175, 50); // Fill the rectangle

        
        gc.setColor(new Color(0, 0, 0, (int) transparency));
        gc.drawRect(550, 25, 175, 50); // Draw the outline of the rectangle

        // Draw the text on top of the rectangle with fading transparency
        gc.setFont(new Font("Arial", Font.PLAIN, 20));
        gc.setColor(new Color(255, 0, 0, (int) transparency)); 
        gc.drawString("Not enough coins", 555, 50); // Adjust text position if necessary
        gc.drawString("or gems", 555, 70); // Adjust text position if necessary

        }
      
      // Ensure transparency doesn't go below 0
      if (transparency < 0) {
				transparency = 0;
				errormsg = false;
			}
		}
	}
  
  /*
   * ************************************************************
   * MAIN GAME LOOP METHODS
   * /* ************************************************************
   */
  /**
   * the controls of the game
   */
  void controls() {
    // move player (wasd)
    if (isKeyUpAndDown(65)) { // left
			player.direction = 0;
			keyClicked = true;
		}
      
    if (isKeyUpAndDown(68)) { // right
			player.direction = 1;
			keyClicked = true;
		}
		
    if (isKeyUpAndDown(87)) { // up
			player.direction = 2;
			keyClicked = true;
		}
      
    if (isKeyUpAndDown(83)) { // down
			player.direction = 3;
			keyClicked = true;
		}

		if (keyClicked && (tick - lastMove > 7)) {
			playerMove();
			lastMove = tick;
		}
		keyClicked = false;

		// other controls
    if (isKeyUpAndDown(71)) { // toggle currency view
      currencyView = !currencyView;
    }

    if (gc.isKeyDown(90)){ // autosell
      if (autosell == true){
        autosell();
      }
    }

		if (isKeyUpAndDown(27)) { // display intructions
			gc.setVisible(false);
			new Title();
			gc.setVisible(true);
			gc.sleep(50);
		}

		// debug
		if (isKeyUpAndDown(55)) {
			coins += 1000;
		}
		if (isKeyUpAndDown(56)) {
			gems += 1000;
		}
		if (isKeyUpAndDown(57)) {
			stones += 1000;
		}
  }

	/**
	 * helper method for what the player does on move
	 */
	private void playerMove() {
		int[] nextPos = player.nextTile(field);
		String nextTile = field[nextPos[0]][nextPos[1]];

		if (nextTile != null) {
			animations[nextPos[0]][nextPos[1]].setAnimation(1, player.direction);
			TileFunctions.function(nextTile);
			playerAnimation.setAnimation(2, player.direction);
		} else {
			if (!(nextPos[0] == player.y && nextPos[1] == player.x)) playerAnimation.setAnimation(1, player.direction);
			player.y = nextPos[0];
			player.x = nextPos[1];
		}
	}

	/**
	 * checks if key has been released before press
	 * @param key		the key to check
	 * @return			if yes, true, else false
	 */
	private boolean isKeyUpAndDown(int key) {
		if (gc.isKeyDown(key)) {
			if (!keysDown[key]) {
				keysDown[key] = true;
				return true;
			}
			keysDown[key] = true;
		} else {
			keysDown[key] = false;
		}
		return false;
	}

  /**
   * autominer calculations
   */
  void autominer() {
		if ((tick - lastAutomine >= 100) && (autominer > 0)) {
			lastAutomine = tick; 
			if (stones < 5 + invUpgradeCount * 2) stones += autominer; 
			if (stones > 5 + invUpgradeCount * 2) stones = 5 + invUpgradeCount * 2 * (rebirth + 1);
		}
	}

	/**
	 * methods for autosell power
	 */
	void autosell() {
		coins += stones * (sellUpgradeCount + 1);
		stones = 0;
	}

	/**
	 * removes the autosell block after purchase
	 */
	public void removeAutosell() {
		field[4][4] = null;
	}

	/**
	 * adds the autosell block after rebirth
	 */
	public void addAutosell() {
		field[4][4] = "autosell";
	}

  /**
   * drawing graphics of the game
   */
  void graphics() {
    synchronized (gc) {
      gc.clear();

			drawBackground();
			
			// draws each tile
			for (int y = 0; y < field.length; y++) {
        for (int x = 0; x < field[0].length; x++) {
					animations[y][x].nextFrame();
					int yPos = 96 * (y + 1) + animations[y][x].getYOffset();
					int xPos = 96 * x + animations[y][x].getXOffset();
					
					if (field[y][x] == null || images.containsKey(field[y][x])) {
						gc.drawImage(images.get(field[y][x]), xPos, yPos, 96, 96);
					} else {
						gc.drawImage(images.get("untextured"), xPos, yPos, 96, 96);
					}
				}
			}

			// displays the currencies
			if (currencyView) {
				transparencyForCurrency = 0;
        gc.setColor(new Color(150, 150, 255));
				gc.fillRect(96, 96, 0, 85);
				
				Font currenciesFont = new Font("Montserrat", Font.BOLD, 12);
				gc.setFont(currenciesFont);
				gc.setColor(Color.BLACK);

				String coinsString = Integer.toString(coins);

				String gemsString = Integer.toString(gems);
				String stonesString = Integer.toString(stones);
        
				String[] currencyStrings = {
						"G to hide",
						"coins:    " + coinsString,
						"gems:     " + gemsString,
						"stones:   " + stonesString + " / " + (5 + (invUpgradeCount * 2))
				};
				for (int i = 0; i < currencyStrings.length; i++) {
					gc.drawString(currencyStrings[i], 32, 25 + (i * 18));
				}
			} else {
        gc.setColor(new Color(200,200,200, transparencyForCurrency));
        gc.fillRect(0, 0, 125, 96); 
			}

			// displays 'esc' prompt
			gc.setFont(currenciesFont);
			gc.drawString("\'esc\' to view intructions", 300, 32);
      			
      // draw player
			playerAnimation.nextFrame();
			gc.drawImage(images.get(Player.spriteDirection[player.direction]), player.x * 96 + playerAnimation.getXOffset(), (player.y + 1) * 96 + playerAnimation.getYOffset(), 96, 96);
    
      // display costs
      gc.setColor(Color.BLACK);
      gc.setFont(currenciesFont);
      gc.drawString("Coins: " + sellUpgradeCost, 96 * 3, 96 * 2);
      gc.drawString("Coins: " + invUpgradeCost, 96 * 4, 96 * 2);
      gc.drawString("Coins: " + gemUpgradeCost, 96 * 5, 96 * 2);
      gc.drawString("Coins: " + autominerCost, 96 * 3, 480);
      gc.drawString("Coins: " + rebirthcostCoin, 96 * 5, 480);
      gc.drawString("Gems: " + rebirthcostGem, 96 * 5, 500);
      gc.drawString("Coins: " + minigameCost, 96 * 6, 480);
      if (!autosell) gc.drawString("Gems: 100", 96 * 4, 480);

			// display not enough coins
      notEnoughCoinsOrGems();
		}
	}

	/**
	 * method to draw background
	 */
	private void drawBackground() {
		// draws menu background
		gc.drawImage(images.get("left-menu"), 0, 0, 96, 96);
		for (int i = 1; i < field[0].length - 1; i++) {
			gc.drawImage(images.get("middle-menu"), 96 * i, 0, 96, 96);
		}
		gc.drawImage(images.get("right-menu"), 672, 0, 96, 96);

		// draws the background of the game
		for (int y = 0; y < field.length; y++) {
			for (int x = 0; x < field[0].length; x++) {
				gc.drawImage(images.get("tile-grass"), 96 * x, 96 * (y + 1), 96, 96);
			}
		}
	}
  
  /*
   * ************************************************************
   * OTHER METHODS
   * /* ************************************************************
   */
  /**
   * loads an image
   * @param file	the file to load
   * @return	the buffered image of the file
   */
  private static BufferedImage loadImage(File file) {
    BufferedImage img = null;
    try {
      img = ImageIO.read(file);
    } catch (IOException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "An image (" + file.getName() + ") failed to load", "ERROR",
					JOptionPane.ERROR_MESSAGE);
    }
    return img;
  }
}