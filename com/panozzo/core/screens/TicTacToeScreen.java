package com.panozzo.core.screens;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

import com.panozzo.core.GameManagerListener;
import com.panozzo.core.GameState;
import com.panozzo.graphics.RenderableRoutine;

public class TicTacToeScreen implements GameManagerListener, RenderableRoutine, Screenable {

	/**
	 * Animation Stages
	 * 1: Black Screen, Draw in Vertical line 1
	 * 2: Draw in vertical line 2
	 * 3: Draw in horizontal line 1
	 * 4: Draw in horizontal line 2:
	 * 
	 */
	private int animationStage = 0;
	private int previousAnimationStage = 0;
	private Timer animationTimer;
	private float animationTime = 0;
	private int animationFrameMilli = 2000;
	private ClassLoader classLoader;
	private int errorState = SCREEN_ERROR_NONE;
	
	
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
	
	public TicTacToeScreen() {
		animationTimer = new Timer();
		// Schedule a timer
		animationTimer.scheduleAtFixedRate(updateAnimationStage, 0, animationFrameMilli);
	}
	
	private synchronized void nextAnimationStage() {
		animationStage++;
	}

	private synchronized boolean shouldAnimate() {
		return animationStage < 5;
	}

	private synchronized int getAnimationStage() {
		return animationStage;
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
	public boolean checkIfDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render(Graphics2D g2d, Dimension size) {
		
		g2d.setBackground(Color.BLACK);
		g2d.clearRect(0, 0, size.width, size.height);
		
		int limitingLength = Math.min(size.width, size.height); // Max length that a line should be, so that v and h are same length
		
		resetAnimationStageTiming();
		animationTime++;
		
		int cellSize = limitingLength / 3;		
		int centerX = size.width / 2;
		int centerY = size.height / 2;
		g2d.setColor(Color.green);
		//g2d.fillArc(centerX - 5, centerY - 5, 10, 10, 0, 360);
		g2d.setColor(Color.white);
		
		// UL
		int x1 = centerX - cellSize;
		int y1 = centerY - cellSize; 
		// UR
		int x2 = centerX + cellSize;
		int y2 = centerY - cellSize;
		// LL
		int x3 = centerX - cellSize;
		int y3 = centerY + cellSize;
		//LR
		int x4 = centerX + cellSize;
		int y4 = centerY + cellSize;
		
		// Draw the bounding box of the tic-tac-toe board
//		g2d.setColor(Color.white);
//		g2d.setStroke(new BasicStroke(3));
//		g2d.drawLine(x1, y1, x3, y3);
//		g2d.drawLine(x2, y2, x4, y4);
//		g2d.drawLine(x1, y1, x2, y2);
//		g2d.drawLine(x3, y3, x4, y4);
		
		g2d.setStroke(new BasicStroke(4));
		g2d.setColor(Color.white);
		if (animationStage == 0) {
			
			
			return;
		}
		
		float progress = Math.min((animationTime / 60.0f) / (animationFrameMilli / 1000), 1.0f);
		if (animationStage == 1) {
			// Old lines
			
			float yDiff = (float) (y3 - y1);
			int yProcess = (int) (progress * yDiff);
			g2d.drawLine(centerX - cellSize / 3, y1, centerX - cellSize / 3, y1 + yProcess);
			return;
		}
		
		g2d.drawLine(centerX - cellSize / 3, y1, centerX - cellSize / 3, y3);
		if (animationStage == 2) {
			float yDiff = (float) (y3 - y1);
			int yProcess = (int) (progress * yDiff);
			g2d.drawLine(centerX + cellSize / 3, y1, centerX + cellSize / 3, y1 + yProcess);
			return;
		}
		g2d.drawLine(centerX + cellSize / 3, y1, centerX + cellSize / 3, y3);
		
		if (animationStage == 3) {
			float xDiff = (float) (x2 - x1);
			int xProcess = (int) (Math.min(progress * 2,1.0f) * xDiff);
			g2d.drawLine(x1, centerY - cellSize / 3, x1 + xProcess, centerY - cellSize / 3);
			return;
		}
		
		g2d.drawLine(x1, centerY - cellSize / 3, x2, centerY - cellSize / 3);
		
		if (animationStage == 4) {
			float xDiff = (float) (x2 - x1);
			int xProcess = (int) (Math.min(progress * 2,1.0f) * xDiff);
			g2d.drawLine(x1, centerY + cellSize / 3, x1 + xProcess, centerY + cellSize / 3);
			return;
		}
		
		g2d.drawLine(x1, centerY + cellSize / 3, x2, centerY + cellSize / 3);
	}

	@Override
	public void gameQuit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameStateChange(GameState current, GameState previous) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameClick(int x, int y) {
		// TODO Auto-generated method stub
		System.out.println(String.format("Click: %d %d", x, y));
	}

}
