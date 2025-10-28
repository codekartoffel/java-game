package com.panozzo.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * A special JPanel capable of rendering graphics in a buffered way
 */
public class GamePanel extends Canvas implements RenderableSurface{
	
	
	private static final long serialVersionUID = 1L;
	private BufferStrategy strategy;
	
	/**
	 * Graphics object for drawing
	 */
	private Graphics2D g2d;
	
	public GamePanel(int width, int height)
	{
		this.setBounds(0, 0, width, height);
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				reinit();
			}
		});
	}
	
	public void init()
	{
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		g2d = (Graphics2D) strategy.getDrawGraphics();
	}
	
	public Graphics2D requestGraphics()
	{
		return this.g2d;
	}
	
	private void reinit() {
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		g2d = (Graphics2D) strategy.getDrawGraphics();
		
	}
	
	/**
	 * Draw game graphics on the image
	 */
	public void render()
	{
		Graphics g = strategy.getDrawGraphics();
		strategy.show();
	}
}
