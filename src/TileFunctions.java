/*
 * TileFunctions.java
 * by Evan, Caleb, and Caden
 * 
 * file started: Tuesday, January 14, 2025
 * file finished: Monday, January 20, 2025
 * 
 * this is the section of the code that contains the functions that different tiles can use
/*/

import static packages.GameVariables.*;

public class TileFunctions {
  static Game game;
	static void setGame(Game g) {game = g;}
    
	/**
	 * triggers a given 
	 * @param key		the tile function trigger
	 */
	static void function(String key) {
		//Variables for costs
		int buyPrice;
		int buyPrice2;

		// what each block does upon interaction
		switch (key) {
			case "stone":
				// add stone
				if (stones < 5 + invUpgradeCount * 2) stones += stoneIncr;
				if (stones > 5 + invUpgradeCount * 2) stones = 5 + invUpgradeCount * 2;
				
				// add gems
				int gemChance = (int) (Math.random() * 100);
				if (gemChance <= gemUpgradeCount + 1){
					gems++;
				}
				break;
			case "sell-block":
				coins += stones * (sellUpgradeCount + 1);
				stones = 0;
				break;
			case "upgrade-sell-value":
				buyPrice = 20 * sellUpgradeCount + 20;
				sellUpgradeCost = buyPrice;
				if (coins >= buyPrice) {
					coins -= buyPrice;
					sellUpgradeCount++;
				}
				else {
					game.transparency = 255.0;
					errormsg = true;
				}
				break;
			case "upgrade-backpack":
				buyPrice = 20 * invUpgradeCount + 20;
				invUpgradeCost = buyPrice;
				if (coins >= buyPrice) {
					coins -= buyPrice;
					invUpgradeCount++;
				}
				else {
					game.transparency = 255.0;
					errormsg = true;
				}
				break;
			case "upgrade-gem-chance":
				buyPrice = 20 * gemUpgradeCount + 20;
				gemUpgradeCost = buyPrice;
				if (coins >= buyPrice) {
					coins -= buyPrice;
					gemUpgradeCount++;
				}
				else {
					game.transparency = 255.0;              
					errormsg = true;
				}
				break; 
			case "autominer":
				buyPrice = autominer * 200 + 200;
				autominerCost = buyPrice;
					if (coins >= buyPrice){
					coins -= buyPrice;
					autominer++;
				}
				else {
					game.transparency = 255.0;              
					errormsg = true;
				}
				break;
			case "rebirth":
				buyPrice = 1000 * powerIntOf(rebirth, 3) + 1000;
				buyPrice2 = 75 * powerIntOf(rebirth, 3) + 75;
				rebirthcostCoin = buyPrice;
				rebirthcostGem = buyPrice2;

				if (coins >= buyPrice && gems >= buyPrice2){
					coins = 0;
					gems = 0;
					stones = 0;
					sellUpgradeCount = 0;
					invUpgradeCount = 0;
					spdUpgradeCount = 0;
					gemUpgradeCount = 0;

					sellUpgradeCost = 20;
					invUpgradeCost = 20;
					spdUpgradeCost = 20;
					gemUpgradeCost = 20;

					autominer = 0;
					autominerCost = 200;
					autosell = false;
					game.addAutosell();
					stoneIncr *= 2;
					rebirth++;
				}
				else {
					game.transparency = 255.0;
					errormsg = true;
				}
				break;
			case "autosell":
				if (gems >= 100){
					gems -= 100;
					autosell = true;
					game.removeAutosell();
				} else {
					game.transparency = 255.0;
					errormsg = true;
				}
				break;
			case "minigame":
				buyPrice = 100;
				minigameCost = buyPrice;
				if (coins >= buyPrice){
					coins -= buyPrice;
					new Dodge(game);
				} else {
					game.transparency = 255.0;
					errormsg = true;
				}
				break;
			default:
				break;
		}
  }

	/**
	 * a helper method that returns a rounded integer of a power
	 * used to make code more readable
	 * @param b		the base
	 * @param e		the exponent
	 * @return	int of the rounded result
	 */
	private static int powerIntOf(double b, double e) {
		return (int) Math.round(Math.pow(b, e));
	}
}