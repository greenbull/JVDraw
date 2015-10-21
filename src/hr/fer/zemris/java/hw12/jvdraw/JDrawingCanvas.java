package hr.fer.zemris.java.hw12.jvdraw;

import hr.fer.zemris.java.hw12.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw12.jvdraw.listeners.DrawingMouseMotionListener;
import hr.fer.zemris.java.hw12.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw12.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw12.jvdraw.objects.Line;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JComponent;

/**
 * Drawing canvas. This component is intended for drawing objects.
 * 
 * @author Juraj
 *
 */

public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	private static final long serialVersionUID = 1L;
	/**
	 * Drawing model that is backing this drawing canvas
	 */
	private DrawingModel drawingModel;
	/**
	 * Foreground color
	 */
	private IColorProvider foreground;
	/**
	 * Background color
	 */
	private IColorProvider background;
	/**
	 * Mouse motion listener that draws object as the mouse moves
	 */
	private DrawingMouseMotionListener draw;
	/**
	 * Object that is being drawn but not yet finished
	 */
	private GeometricalObject objectInCreation;

	/**
	 * Constructs drawing canvas from given drawing mode, foreground color,
	 * background color and button group. Also adds mouse listener to this
	 * component.
	 * 
	 * @param drawingModel
	 *            given drawing model
	 * @param foreground
	 *            given foreground color
	 * @param background
	 *            given background color
	 * @param buttonGroup
	 *            given button group
	 */

	public JDrawingCanvas(DrawingModel drawingModel, IColorProvider foreground,
			IColorProvider background, ButtonGroup buttonGroup) {
		this.drawingModel = drawingModel;
		this.foreground = foreground;
		this.background = background;

		drawingModel.addDrawingModelListener(this);

		addMouseListener(new MouseAdapter() {

			/**
			 * If it is the first click than starts to draw requested object as
			 * the mouse moves, if it is the secon click than deregisters mouse
			 * motion listener and adds newly drawn object to drawing model.
			 */
			@Override
			public void mousePressed(MouseEvent e) {

				Object source = e.getSource();

				if (source instanceof JDrawingCanvas) {

					if (draw != null) {
						removeMouseMotionListener(draw);
						draw = null;
						drawingModel.add(objectInCreation);
						objectInCreation = null;
						return;
					}

					Point p = e.getPoint();
					ButtonModel button = buttonGroup.getSelection();
					if (button == null) {
						return;
					}
					String object = button.getActionCommand();

					if (object.equals("Line")) {

						objectInCreation = new Line(p.x, p.y, p.x, p.y);
						draw = new DrawingMouseMotionListener(objectInCreation,
								JDrawingCanvas.this);
						addMouseMotionListener(draw);
					} else if (object.equals("Circle")) {

						objectInCreation = new Circle(p.x, p.y, 0);
						draw = new DrawingMouseMotionListener(objectInCreation,
								JDrawingCanvas.this);
						addMouseMotionListener(draw);
					} else if (object.equals("FilledCircle")) {

						objectInCreation = new FilledCircle(p.x, p.y, 0);
						draw = new DrawingMouseMotionListener(objectInCreation,
								JDrawingCanvas.this);
						addMouseMotionListener(draw);
					}
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		for (int i = 0; i < drawingModel.getSize(); i++) {

			if (drawingModel.getObject(i) != null) {
				drawingModel.getObject(i).paintMe(g2d,
						foreground.getCurrentColor(),
						background.getCurrentColor());
			}
		}
		if (objectInCreation != null) {
			objectInCreation.paintMe(g2d, foreground.getCurrentColor(),
					background.getCurrentColor());
		}
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

}
