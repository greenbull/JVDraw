package hr.fer.zemris.java.hw12.jvdraw;

import hr.fer.zemris.java.hw12.jvdraw.listeners.ColorChangeListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * Class representing color area where background or foreground color can be
 * choosed.
 * 
 * @author Juraj
 *
 */

public class JColorArea extends JComponent implements IColorProvider {

	private static final long serialVersionUID = 1L;
	/**
	 * Currently selected color
	 */
	private Color selectedColor;
	/**
	 * True if color is background, false if color is foreground
	 */
	private boolean background;
	/**
	 * List of color change listeners
	 */
	private List<ColorChangeListener> listeners;

	/**
	 * Constructs JColorArea from given color. Also adds mouse listener to it so
	 * new color can be chosen if this component is pressed.
	 * 
	 * @param selectedColor
	 *            selected color
	 * @param background
	 *            true if color is background, false if color is foreground
	 */

	public JColorArea(Color selectedColor, boolean background) {
		this.selectedColor = selectedColor;
		listeners = new ArrayList<ColorChangeListener>();
		this.background = background;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				Object source = e.getSource();

				if (source instanceof JColorArea) {

					Color newColor = JColorChooser.showDialog(JColorArea.this,
							"Choose color", selectedColor);
					if (newColor == null) {
						return;
					}
					if (!newColor.equals(selectedColor)) {
						changeColor(newColor);
					}
				}
			}
		});
	}

	@Override
	public Dimension getPreferredSize() {

		return new Dimension(15, 15);
	}

	@Override
	public Color getCurrentColor() {

		return selectedColor;
	}

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g.create();

		Rectangle rect = new Rectangle(this.getPreferredSize());
		g2d.setColor(selectedColor);
		g2d.fillRoundRect(0, 0, rect.width, rect.height, 2, 2);
	}

	@Override
	public boolean isBackground() {

		return background;
	}

	/**
	 * Adds given color change listener
	 * 
	 * @param l
	 *            given color change listener
	 */

	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l);
	}

	/**
	 * Removes given color change listener
	 * 
	 * @param l
	 *            given color change listener
	 */

	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}

	/**
	 * Changes selected color and notifies all listeners about the change.
	 * 
	 * @param newColor
	 *            new color
	 */

	public void changeColor(Color newColor) {

		for (ColorChangeListener listener : listeners) {
			listener.newColorSelected(this, selectedColor, newColor);
		}
		selectedColor = newColor;
		repaint();
	}
}
