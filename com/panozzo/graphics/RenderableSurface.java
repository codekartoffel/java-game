package com.panozzo.graphics;

import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * Represents something that undergoes rendering
 */
public interface RenderableSurface {
	public void init();
	public Graphics2D requestGraphics();
	public Dimension getSize();
	public void render();
}
