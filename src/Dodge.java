/*
 * Dodge.java
 * by Evan, Caleb, and Caden
 * 
 * file started: Saturday, January 18, 2025
 * file finished: Monday, January 20, 2025
 * 
 * this is the code for the minigame in the game
/*/

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import static packages.GameVariables.*;

import hsa2.GraphicsConsole;

public class Dodge{
	static Game game;

  // Graphics Console and Variables
  GraphicsConsole gc2 = new GraphicsConsole(768, 576, "Dodge");

  int cartX = 100;
  int cartY;

  int score = 0;

  Boolean isRock;
  Boolean isRunning = true;

  // Arraylist, rectangle, and random object
  ArrayList<FallingObject> fallingObjects = new ArrayList<>();
  Rectangle r1;
  Random random = new Random();

  // images
  BufferedImage cart = Game.images.get("Minecart");
  BufferedImage gem = Game.images.get("gem");
  BufferedImage rock = Game.images.get("rock");

  Dodge(Game g){
		game = g;
    setup();

    //main game loop
    while (isRunning) {
      drawGraphics();
      moveCart();
      fallingObjects();
      moveFallingObjects();
      checkCollision();
      gc2.sleep(10);
    }
		gc2.close();
  }

  /*
   * setups the graphics console
   */
  void setup(){
    gc2.setAntiAlias(true);
	  gc2.setResizable(false);
	  gc2.setLocationRelativeTo(null);
    cartY = 500; 
	}

  /*
   * controls to move cart
   */
  void moveCart() {
    if (gc2.isKeyDown(65)) {
      if (cartX > 0) cartX -= 5;

    }
    if (gc2.isKeyDown(68)) {
      if (cartX < 704) cartX += 5;

    }
  }

  /*
   * draws the cart and objects
   */
  void drawGraphics(){
		synchronized (gc2) {
			gc2.clear();
			// gc2.drawImage(cart, cartX, 500);
			gc2.drawImage(cart, cartX, 500, 64, 64);

			for (FallingObject obj : fallingObjects) {
				if (obj.isRock) {
					gc2.drawImage(rock, obj.x, obj.y, 50, 50);
				} else {
					gc2.drawImage(gem, obj.x, obj.y, 50, 50);
				}
			}
		}

  }

  /*
   * stores the falling objects in an array
   */
  void fallingObjects(){
    if (random.nextInt(16) == 0){
      int x = random.nextInt(gc2.getWidth() - 30);
      boolean isRock = random.nextInt(100) < 80;
      fallingObjects.add(new FallingObject(x, 0, isRock));
    }
  }

  /*
   * moves all the falling objects
   */
  void moveFallingObjects(){
    ArrayList<FallingObject> toRemove = new ArrayList<>();
    for (FallingObject obj: fallingObjects){
      obj.y += random.nextInt(12) + 2;

      if (obj.y > gc2.getHeight()) {
        toRemove.add(obj);
      }
    }
    
    fallingObjects.removeAll(toRemove);
  }

  /*
   * checks if cart collides with object
   */
  void checkCollision(){
  ArrayList<FallingObject> toRemove = new ArrayList<>();

    for (FallingObject obj : fallingObjects) {
      if (obj.y + 64 >= cartY && obj.x + 64 >= cartX && obj.x <= cartX + 64) {
        if (obj.isRock) {
          gems += score;
          isRunning = false;
				} else {
					score += 1;
				}
				toRemove.add(obj);
			}
		}

      fallingObjects.removeAll(toRemove);
    }

  /*
   * class for falling object
   */
	class FallingObject {
		int x, y, speed;
		boolean isRock;

		public FallingObject(int x, int y, boolean isRock) {
			this.x = x;
			this.y = y;
			this.speed = random.nextInt(3) + 3;
			this.isRock = isRock;
		}
	}
}
