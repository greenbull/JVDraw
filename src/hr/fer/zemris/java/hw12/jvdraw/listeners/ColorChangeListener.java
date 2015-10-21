package hr.fer.zemris.java.hw12.jvdraw.listeners;

import hr.fer.zemris.java.hw12.jvdraw.IColorProvider;

import java.awt.Color;

/**
 * Color change listener.
 * 
 * @author Juraj
 *
 */

public interface ColorChangeListener {

	/**
	 * Called if new color is selected.
	 * 
	 * @param source
	 *            source of color changing
	 * @param oldColor
	 *            color before changing
	 * @param newColor
	 *            new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor,
			Color newColor);
}