package com.panozzo.core;

/**
 * Indicates an object that watches the GameManager
 */
public interface GameManagerListener {
	/**
	 * The game manager wishes to quit the game
	 */
	public void gameQuit();
	
	public void gameTick();
	
	public void gameStateChange(GameState current, GameState previous);
}
