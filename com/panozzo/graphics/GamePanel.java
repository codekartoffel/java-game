package com.panozzo.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * A special JPanel capable of rendering graphics in a buffered way
 */
public class GamePanel extends JPanel implements RenderableSurface{
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * An image used to store graphics we intend to draw in the future.
	 */
	private BufferedImage backBuffer;
	
	/**
	 * Graphics object for drawing
	 */
	private Graphics2D g2d;
	
	public GamePanel(int width, int height)
	{
		this.setBounds(0, 0, width, height);
		init();
	}
	
	private void init()
	{
		backBuffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		g2d = backBuffer.createGraphics();
	}
	
	public Graphics2D requestGraphics()
	{
		return this.g2d;
	}
	
	/**
	 * Draw game graphics on the image
	 */
	public void render()
	{
		// Do not do any additional actions
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D screen = (Graphics2D) g;
		screen.drawImage(backBuffer, 0, 0, null);
	}
}
