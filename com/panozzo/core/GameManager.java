package com.panozzo.core;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import com.panozzo.graphics.RenderableRoutine;
import com.panozzo.graphics.RenderableSurface;

/**
 * Keeps track of the game state, the current map, players, and actions
 * Will delegate computations to threads
 */
public class GameManager {
	private List<GameManagerListener> listeners;
	private List<RenderableRoutine> renderables;
	
	private final RenderableSurface renderSpace;
	
	public GameManager(final RenderableSurface renderSpace)
	{
		listeners = new LinkedList<GameManagerListener>();
		renderables = new LinkedList<RenderableRoutine>();
		this.renderSpace = renderSpace;
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
	
	public void tick()
	{
		
	}
	
	public void render()
	{
		// Request graphics resources
		Graphics2D g2d = renderSpace.requestGraphics();
		for (var renderable : renderables)
		{
			renderable.render(g2d);
		}
	}
}
