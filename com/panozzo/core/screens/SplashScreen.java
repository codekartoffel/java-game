package com.panozzo.core.screens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import com.panozzo.core.GameManagerListener;
import com.panozzo.core.GameState;
import com.panozzo.graphics.RenderableRoutine;

public class SplashScreen implements GameManagerListener, RenderableRoutine {
	
	private final int SCREEN_ERROR_NONE = 0x0;
	private final int SCREEN_ERROR_IO = 0x1;
	
	private Timer animationTimer;
	/**
	 * Animation stage flags.
	 * 0: White screen before
	 * 1: Fade in logo
	 * 2: keep logo up
	 * 3: keep logo up
	 * 4: Fade out logo, go to black
	 */
	private int animationStage = 0;
	
	// Ideally, animation will run at 60 fps, so 1 unit is1/60th of a second.
	private double animationTime = 0;
	private ClassLoader classLoader;
	
	private int errorState = SCREEN_ERROR_NONE;
	private BufferedImage logo;
	public SplashScreen()
	{
		try {
			classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("/res/logo.png");
			logo = ImageIO.read(input);
		} catch (IOException exc) {
			errorState = SCREEN_ERROR_IO;
			return;
		}
		
		animationTimer = new Timer();
		// Schedule a timer
		animationTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (shouldAnimate())
				{
					nextAnimationStage();
					return;
				}
				
			}
		}, 0, 3000);
	}
	
	private synchronized void nextAnimationStage(){
		animationStage++;
	}
	
	private synchronized boolean shouldAnimate()
	{
		return animationStage < 3;
	}
	
	private synchronized int getAnimationStage()
	{
		return animationStage;
	}

	@Override
	public void render(Graphics2D g2d) {
		if (errorState != SCREEN_ERROR_NONE) {
			return;
		}
		
		// TODO Auto-generated method stub
		g2d.setColor(Color.WHITE);
		g2d.clearRect(0, 0, 100, 100);
		
		g2d.drawImage(logo,null,0,0);
	}

	@Override
	public void gameQuit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameTick() {
		// TODO Auto-generated method stub
		animationTime++;
	}

	@Override
	public void gameStateChange(GameState current, GameState previous) {
		// TODO Auto-generated method stub
		
	}
}
