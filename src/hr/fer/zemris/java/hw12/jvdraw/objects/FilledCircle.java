package hr.fer.zemris.java.hw12.jvdraw.objects;

import hr.fer.zemris.java.hw12.jvdraw.actions.ActionUtil;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Class representing filled circle.
 * @author Juraj
 *
 */

public class FilledCircle implements GeometricalObject {

	/**
	 * X coordinate of circle's center
	 */
	private int centerX;
	/**
	 * Y coordinate of circle's center
	 */
	private int centerY;
	/**
	 * Circle's radius
	 */
	private int radius;
	/**
	 * Foreground color
	 */
	private Color myForeground = null;
	/**
	 * Background color
	 */
	private Color myBackground = null;

	/**
	 * Constructs filled circle.
	 * 
	 * @param centerX
	 *            X coordinate of circle's center
	 * @param centerY
	 *            Y coordinate of circle's center
	 * @param radius
	 *            circle's radius
	 */
	public FilledCircle(int centerX, int centerY, int radius) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
	}

	@Override
	public void paintMe(Graphics2D g2d, Color foreground, Color background) {

		if (myForeground == null) {
			myForeground = foreground;
		}
		if (myBackground == null) {
			myBackground = background;
		}

		g2d.setColor(myForeground);
		g2d.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
		g2d.setColor(myBackground);
		g2d.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
	}

	@Override
	public void update(Point p) {

		radius = (int) Math.sqrt((p.x - centerX) * (p.x - centerX)
				+ (p.y - centerY) * (p.y - centerY));
	}

	@Override
	public String toString() {

		return ActionUtil.processFilledCircle(this);
	}

	/**
	 * Sets center point on given point.
	 * 
	 * @param p
	 *            given point
	 */
	public void setCenter(Point p) {
		
		centerX = p.x;
		centerY = p.y;
	}
	
	/**
	 * Sets radius on given radius.
	 * 
	 * @param r
	 *            given radius
	 */
	public void setRadius(int r) {
		
		radius = r;
	}
	
	/**
	 * Sets foreground color on given color.
	 * 
	 * @param c
	 *            given color
	 */
	public void setForegroundColor(Color c) {
		
		myForeground = c;
	}
	
	/**
	 * Sets background color on given color.
	 * 
	 * @param c
	 *            given color
	 */
	public void setBackgroundColor(Color c) {
		
		myBackground = c;
	}
	
	@Override
	public Color getForegroundColor() {
		
		return myForeground;
	}
	
	@Override
	public Color getBackgroundColor() {
		
		return myBackground;
	}

	/**
	 * Getter for X coordinate of circle's center.
	 * 
	 * @return X coordinate of circle's center
	 */
	public int getCenterX() {
		return centerX;
	}

	/**
	 * Getter for Y coordinate of circle's center
	 * 
	 * @return Y coordinate of circle's center
	 */
	public int getCenterY() {
		return centerY;
	}

	/**
	 * Getter for circle's radius
	 * 
	 * @return circle's radius
	 */
	public int getRadius() {
		return radius;
	}

	@Override
	public GeometricalObject makeCopy() {
		
		FilledCircle newCirle = new FilledCircle(centerX, centerY, radius);
		newCirle.setBackgroundColor(myBackground);
		newCirle.setForegroundColor(myForeground);
		return newCirle;
	}
}
