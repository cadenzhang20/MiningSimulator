/*
 * Title.java
 * by Evan, Caleb, and Caden
 * 
 * file started: Sunday, January 19, 2025
 * file finished: Monday, January 20, 2025
 * 
 * this file defines the title screen of the game
/*/

import hsa2.GraphicsConsole;

public class Title {
	static GraphicsConsole gcIntro;

	/**
	 * what happens upon creating the title screen
	 */
	Title() {
		gcIntro = new GraphicsConsole(Game.winW, Game.winH, "Intro");
		gcIntro.setLocationRelativeTo(null);
		// gcIntro.setAntiAlias(true);

		gcIntro.drawImage(Game.images.get("intro-screen"), 0, 0);

		// Listen for key press to start the game
		while (true) {
			if (gcIntro.getKeyCode() != 0) break;
			gcIntro.sleep(10);
		}
		gcIntro.close();
	}
}