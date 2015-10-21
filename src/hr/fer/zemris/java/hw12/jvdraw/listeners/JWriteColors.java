package hr.fer.zemris.java.hw12.jvdraw.listeners;

import hr.fer.zemris.java.hw12.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw12.jvdraw.JColorArea;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * Class representing label in which foreground and background color's rgb
 * components are displayed.
 * 
 * @author Juraj
 *
 */

public class JWriteColors extends JLabel implements ColorChangeListener {

	private static final long serialVersionUID = 1L;
	/**
	 * Foreground color
	 */
	private Color foregroundColor;
	/**
	 * Background color
	 */
	private Color backgroundColor;

	/**
	 * Constructs label from given foreground and background colors.
	 * 
	 * @param foreground
	 *            given foreground color
	 * @param background
	 *            given background color
	 */
	public JWriteColors(JColorArea foreground, JColorArea background) {
		foregroundColor = foreground.getCurrentColor();
		backgroundColor = background.getCurrentColor();

		foreground.addColorChangeListener(this);
		background.addColorChangeListener(this);

		generateText();
	}

	/**
	 * Generates label's text based on foreground and background colors.
	 */

	private void generateText() {

		StringBuilder sb = new StringBuilder();
		sb.append("Foreground color: (");
		sb.append(foregroundColor.getRed()).append(", ");
		sb.append(foregroundColor.getGreen()).append(", ");
		sb.append(foregroundColor.getBlue()).append("), ");
		sb.append("background color : (");
		sb.append(backgroundColor.getRed()).append(", ");
		sb.append(backgroundColor.getGreen()).append(", ");
		sb.append(backgroundColor.getBlue()).append(").");

		setText(sb.toString());
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor,
			Color newColor) {

		if (source.isBackground()) {
			backgroundColor = newColor;
		} else {
			foregroundColor = newColor;
		}
		generateText();
	}
}
