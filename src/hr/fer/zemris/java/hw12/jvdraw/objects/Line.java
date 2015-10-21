package hr.fer.zemris.java.hw12.jvdraw.objects;

import hr.fer.zemris.java.hw12.jvdraw.actions.ActionUtil;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Class representing line.
 * 
 * @author Juraj
 *
 */

public class Line implements GeometricalObject {

	/**
	 * X coordinate of line's start point
	 */
	private int x1;
	/**
	 * Y coordinate of line's start point
	 */
	private int y1;
	/**
	 * X coordinate of line's end point
	 */
	private int x2;
	/**
	 * Y coordinate of line's end point
	 */
	private int y2;
	/**
	 * Foreground color
	 */
	private Color myForeground;
	/**
	 * Background color
	 */
	private Color myBackground;

	/**
	 * Constructs line from given parameters.
	 * 
	 * @param x1
	 *            X coordinate of line's start point
	 * @param y1
	 *            Y coordinate of line's start point
	 * @param x2
	 *            X coordinate of line's end point
	 * @param y2
	 *            Y coordinate of line's end point
	 */
	public Line(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	@Override
	public void update(Point p) {

		this.x2 = p.x;
		this.y2 = p.y;
	}

	/**
	 * Getter for X coordinate of line's start point
	 * 
	 * @return X coordinate of line's start point
	 */
	public int getX1() {
		return x1;
	}

	/**
	 * Getter for Y coordinate of line's start point
	 * 
	 * @return Y coordinate of line's start point
	 */
	public int getY1() {
		return y1;
	}

	/**
	 * Getter for X coordinate of line's end point
	 * 
	 * @return X coordinate of line's end point
	 */
	public int getX2() {
		return x2;
	}

	/**
	 * Getter for Y coordinate of line's end point
	 * 
	 * @return Y coordinate of line's end point
	 */
	public int getY2() {
		return y2;
	}

	@Override
	public Color getForegroundColor() {
		return myForeground;
	}

	@Override
	public Color getBackgroundColor() {
		return myBackground;
	}

	@Override
	public void paintMe(Graphics2D g2d, Color foreground, Color background) {

		if (myForeground == null) {
			myForeground = foreground;
		}

		g2d.setColor(myForeground);
		g2d.drawLine(x1, y1, x2, y2);
	}

	@Override
	public String toString() {

		return ActionUtil.processLine(this);
	}

	/**
	 * Sets line's start point.
	 * 
	 * @param p
	 *            start point
	 */
	public void setStart(Point p) {

		x1 = p.x;
		y1 = p.y;
	}

	/**
	 * Sets line's end point.
	 * 
	 * @param p
	 *            end point
	 */
	public void setEnd(Point p) {

		x2 = p.x;
		y2 = p.y;
	}

	/**
	 * Sets foreground color to given color
	 * 
	 * @param c
	 *            given color
	 */
	public void setForegroundColor(Color c) {

		myForeground = c;
	}

	/**
	 * Sets background color to given color
	 * 
	 * @param c
	 *            given color
	 */
	public void setBackgroundColor(Color c) {

		myBackground = c;
	}

	/**
	 * Getter for line's color
	 * 
	 * @return line's color
	 */
	public Color getColor() {
		return myForeground;
	}

	@Override
	public GeometricalObject makeCopy() {

		Line newLine = new Line(x1, y1, x2, y2);
		newLine.setBackgroundColor(myBackground);
		newLine.setForegroundColor(myForeground);
		return newLine;
	}
}
