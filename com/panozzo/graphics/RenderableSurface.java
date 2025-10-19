package com.panozzo.graphics;

import java.awt.Graphics2D;

/**
 * Represents something that undergoes rendering
 */
public interface RenderableSurface {
	public Graphics2D requestGraphics();
	public void render();
}
