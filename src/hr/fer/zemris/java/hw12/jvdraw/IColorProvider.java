package hr.fer.zemris.java.hw12.jvdraw;

import java.awt.Color;

/**
 * Color provider.
 * 
 * @author Juraj
 *
 */

public interface IColorProvider {

	/**
	 * Gets current color in color provider.
	 * 
	 * @return current color
	 */
	public Color getCurrentColor();

	/**
	 * Checks if current color is background.
	 * 
	 * @return True if current color is background color, false if current color
	 *         is foreground color
	 */
	public boolean isBackground();
}