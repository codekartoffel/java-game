package com.panozzo.core;

public enum GameState {
	/**
	 * The game is in its loading screen phase
	 */
	STARTUP,
	/**
	 * The game is in its main menu after the loading screen.
	 */
	MAIN_MENU,
	/**
	 * The game is in the settings screen
	 */
	SETTINGS,
	/**
	 * The game is in the level select screen
	 */
	LEVEL_SELECT,
	/**
	 * The game is in the main gameplay section.
	 */
	GAMEPLAY,
	/**
	 * The game is in the multiplayer lobby / selection screen.
	 */
	MULTIPLAYER_SELECT
}
