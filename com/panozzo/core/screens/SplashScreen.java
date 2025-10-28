package com.panozzo.core.screens;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import com.panozzo.core.GameManagerListener;
import com.panozzo.core.GameState;
import com.panozzo.graphics.RenderableRoutine;

public class SplashScreen implements GameManagerListener, RenderableRoutine, Screenable {

	private Timer animationTimer;
	/**
	 * Animation stage flags.
	 * 0: White screen before 
	 * 1: Fade in logo 
	 * 2: keep logo up
	 * 3: fade logo out
	 * 4: go to black 
	 * 5: disclaimer 
	 * 6: disclaimer
	 * 7: done
	 */
	private int animationStage = 0;
	private int previousAnimationStage = 0;
	// Ideally, animation will run at 60 fps, so 1 unit is1/60th of a second.
	private float animationTime = 0;

	private ClassLoader classLoader;

	private int errorState = SCREEN_ERROR_NONE;
	private BufferedImage logo;

	private TimerTask updateAnimationStage = new TimerTask() {
		@Override
		public void run() {
			if (shouldAnimate()) {
				nextAnimationStage();
				return;
			}
			
			animationTimer.cancel();

		}
	};
	
	public SplashScreen() {
		try {
			classLoader = Thread.currentThread().getContextClassLoader();
			// Apparently we do not need to prefix with /res
			logo = ImageIO.read(classLoader.getResource("tictac.png"));
			if (logo == null) {
				System.out.println("Error: Couldnt ");
				throw new IOException();
			}
		} catch (IOException exc) {
			errorState = SCREEN_ERROR_IO;
			return;
		}

		animationTimer = new Timer();
		// Schedule a timer
		animationTimer.scheduleAtFixedRate(updateAnimationStage, 0, 2000);
	}

	private synchronized void nextAnimationStage() {
		animationStage++;
	}

	private synchronized boolean shouldAnimate() {
		return animationStage < 7;
	}

	private synchronized int getAnimationStage() {
		return animationStage;
	}

	@Override
	public void render(Graphics2D g2d, Dimension size) {
		if (errorState != SCREEN_ERROR_NONE) {
			return;
		}
		if (animationStage == 0) {
			
			return;
		}
		
		g2d.setBackground(Color.WHITE);
		g2d.clearRect(0, 0, size.width, size.height);
		
		// Compute where center of screen is (for the logo)
		int logoX = (size.width / 2) - (logo.getWidth() / 2);
		int logoY = (size.height / 2) - (logo.getHeight() / 2);
		
		resetAnimationStageTiming();

		// For all future frames, we will animate, so we need to track # of ticks
		// elapsed
		animationTime++;
		float fadeInSeconds = 3.0f;

		// Fade in logo
		if (animationStage == 1) {
			float opacity = (float) Math.min((animationTime / 60.0f) / fadeInSeconds, 1.0f);
			
			AlphaComposite composite = (AlphaComposite) g2d.getComposite();
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
			g2d.setComposite(ac);
			g2d.drawImage(logo, null, logoX, logoY);
			g2d.setComposite(composite);
			return;
		}
		
		// Preserve logo
		if (animationStage == 2) {
			g2d.drawImage(logo, null, logoX, logoY);
			return;
		}
		
		// Fade out logo
		if (animationStage == 3) {
			float opacity = (float) Math.max(1.0f - ((animationTime / 60.0f) / fadeInSeconds), 0.0f);
			
			AlphaComposite composite = (AlphaComposite) g2d.getComposite();
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
			g2d.setComposite(ac);
			g2d.drawImage(logo, null, logoX, logoY);
			AlphaComposite ac2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f - opacity);
			g2d.setComposite(ac2);
			g2d.setColor(Color.black);
			g2d.fillRect(0, 0, size.width, size.height);
			return;
		}
		
		// Display text disclaimer
		if (animationStage == 4 || animationStage == 5 || animationStage == 6 ) {
			FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
			int x = 0 + (size.width - metrics.stringWidth("Made with Java")) / 2;
			
			g2d.setBackground(Color.BLACK);
			g2d.clearRect(0, 0, size.width, size.height);
			g2d.setColor(Color.white);
			g2d.drawString("Made with Java", x, size.height / 2);
			return;
		}

		
		
	}
	
	private void resetAnimationStageTiming()
	{
		// Reset animation timing variables
		if (animationStage != previousAnimationStage) {
			animationTime = 0;
			previousAnimationStage = animationStage;
			return;
		}
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

	@Override
	public boolean checkIfDone() {
		// TODO Auto-generated method stub
		return !shouldAnimate();
	}

	@Override
	public void terminate() {
		animationTimer.cancel();
		errorState = SCREEN_TERMINATED;
	}

	@Override
	public void gameClick(int x, int y) {
		// TODO Auto-generated method stub
		
	}
}
