package hr.fer.zemris.java.hw12.jvdraw.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Interface representing geometrical object.
 * 
 * @author Juraj
 *
 */

public interface GeometricalObject {

	/**
	 * Draws object using given graphics, foreground and background color.
	 * 
	 * @param g2d
	 *            given graphics
	 * @param foreground
	 *            given foreground color
	 * @param background
	 *            given background color
	 */
	public void paintMe(Graphics2D g2d, Color foreground, Color background);

	/**
	 * Updates second point of object that is being drawn.
	 * 
	 * @param p
	 *            second point of object that is being drawn
	 */
	public void update(Point p);

	/**
	 * Foreground color getter.
	 * 
	 * @return foreground color
	 */
	public Color getForegroundColor();

	/**
	 * Background color getter.
	 * 
	 * @return background color
	 */
	public Color getBackgroundColor();

	/**
	 * Makes copy of this object.
	 * 
	 * @return copy of this object
	 */
	public GeometricalObject makeCopy();
}
