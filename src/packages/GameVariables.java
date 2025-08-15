package packages;
/*
 * GameVariables.java
 * by Evan, Caleb, and Caden
 * 
 * file started: Tuesday, January 14, 2025
 * file finished: Monday, January 20, 2025
 * 
 * this is a package imported into staticly imported into relevent classes
 * this is the source of all major game variables
/*/

public class GameVariables {
	// Currency variables
	static public int gems = 0, coins = 0, stones = 0;

  // Shop upgrade count
  static public int sellUpgradeCount = 0, invUpgradeCount = 0, spdUpgradeCount = 0, gemUpgradeCount = 0;
	static public int stoneIncr = 1;

  // cost of upgrade variables
  static public int sellUpgradeCost = 20, invUpgradeCost = 20, spdUpgradeCost = 20, gemUpgradeCost = 20, autominerCost = 200;
  static public Boolean currencyView = true, errormsg = false;
  // autominers
  static public int autominer = 0;
  //
  static public boolean autosell = false;
  // rebirth
  static public int rebirth = 0, rebirthcostCoin = 1000, rebirthcostGem = 200;
  // 
  static public int minigameCost = 100;
}
