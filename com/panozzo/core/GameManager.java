package com.panozzo.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.panozzo.core.screens.Screenable;
import com.panozzo.core.screens.SplashScreen;
import com.panozzo.core.screens.TicTacToeScreen;
import com.panozzo.graphics.RenderableRoutine;
import com.panozzo.graphics.RenderableSurface;

/**
 * Keeps track of the game state, the current map, players, and actions
 * Will delegate computations to threads
 */
public class GameManager {
	private List<GameManagerListener> listeners;
	private List<RenderableRoutine> renderables;
	private HashMap<String, RenderableRoutine> renderableDictionary;
	private HashMap<String, GameManagerListener> managerListenerDictionary;
	
	private Screenable currentScreening;
	/**
	 * Represents the game workflow state
	 */
	private GameState workflowState;
	
	private final RenderableSurface renderSpace;
	
	public GameManager(final RenderableSurface renderSpace)
	{
		listeners = new LinkedList<GameManagerListener>();
		renderables = new LinkedList<RenderableRoutine>();
		renderableDictionary = new HashMap<String, RenderableRoutine>();
		managerListenerDictionary = new HashMap<String, GameManagerListener>();
		this.renderSpace = renderSpace;
		this.workflowState = GameState.STARTUP;
	}
	
	public void addListener(GameManagerListener listener)
	{
		this.listeners.add(listener);
	}
	
	public void removeListener(GameManagerListener listener)
	{
		this.listeners.remove(listener);
	}
	
	public void removeAllListeners()
	{
		this.listeners.clear();
	}
	
	public void addRenderable(RenderableRoutine renderable)
	{
		this.renderables.add(renderable);
	}
	
	public void removeRenderable(RenderableRoutine renderable)
	{
		this.renderables.remove(renderable);
	}
	
	public void removeAllRenderables()
	{
		this.renderables.clear();
	}
	
	/**
	 * Indicates the start of the game workflow. Push out a state change
	 * to all listeners.
	 */
	public void start()
	{
		// Trigger the splash screen
		// TODO: Fix why its not loading
		showGameBoard();
		return;
		
		
//		workflowState = GameState.STARTUP;
//		SplashScreen splash = new SplashScreen();
//		renderableDictionary.put("splash", splash);
//		managerListenerDictionary.put("splash", splash);
//		renderables.add(splash);
//		listeners.add(splash);
//		currentScreening = splash;
	}
	
	public void cleanupSplashScreen() {
		RenderableRoutine splash = renderableDictionary.get("splash");
		renderables.remove(splash);
		GameManagerListener mListen = managerListenerDictionary.get("splash");
		listeners.remove(mListen);
	}
	
	public void showMainMenu() {
		
	}
	
	public void showGameBoard() {
		workflowState = GameState.GAMEPLAY;
		TicTacToeScreen ticTacToe = new TicTacToeScreen();
		renderableDictionary.put("tictac", ticTacToe);
		managerListenerDictionary.put("tictac", ticTacToe);
		renderables.add(ticTacToe);
		listeners.add(ticTacToe);
		currentScreening = ticTacToe;
	}
	
	public void tick()
	{
		// Check to see if we can progress to the next screen
		switch (workflowState) {
		case STARTUP:
			if (currentScreening.checkIfDone()) {
				cleanupSplashScreen();
				showGameBoard();
			}
			
			break;
		default:
			break;
		}
		
		for (var listener : listeners)
		{
			listener.gameTick();
		}
	}
	
	public void render()
	{
		// Request graphics resources
		Graphics2D g2d = renderSpace.requestGraphics();
		Dimension size = renderSpace.getSize();
		g2d.setBackground(Color.BLACK);
		g2d.clearRect(0, 0, size.width, size.height);
		for (var renderable : renderables)
		{
			renderable.render(g2d, size);
		}
		
		// Render on the surface after all images drawn
		renderSpace.render();
	}
}
