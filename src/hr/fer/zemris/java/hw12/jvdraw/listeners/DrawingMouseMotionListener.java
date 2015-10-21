package hr.fer.zemris.java.hw12.jvdraw.listeners;

import hr.fer.zemris.java.hw12.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Mouse motion listener that draws requested object.
 * 
 * @author Juraj
 *
 */

public class DrawingMouseMotionListener implements MouseMotionListener {

	/**
	 * Object that is being drawn
	 */
	private GeometricalObject object;
	/**
	 * Drawing canvas that is being painted as the mouse moves
	 */
	private JDrawingCanvas listener;

	/**
	 * Constructs mouse motion listener form given object and drawing canvas
	 * 
	 * @param object
	 *            given object that is being drawn
	 * @param listener
	 *            given drawing canvas
	 */

	public DrawingMouseMotionListener(GeometricalObject object,
			JDrawingCanvas listener) {

		this.object = object;
		this.listener = listener;
	}

	/**
	 * Object getter.
	 * 
	 * @return object
	 */

	public GeometricalObject getObject() {
		return object;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	/**
	 * Paints canvas as mouse moves.
	 */
	@Override
	public void mouseMoved(MouseEvent arg0) {

		Point p = arg0.getPoint();
		object.update(p);
		listener.repaint();
	}
}
