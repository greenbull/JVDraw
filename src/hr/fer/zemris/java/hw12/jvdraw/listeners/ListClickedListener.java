package hr.fer.zemris.java.hw12.jvdraw.listeners;

import hr.fer.zemris.java.hw12.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw12.jvdraw.JColorArea;
import hr.fer.zemris.java.hw12.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw12.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw12.jvdraw.objects.Line;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Mouse adapter that detects click on JList.
 * 
 * @author Juraj
 *
 */

public class ListClickedListener extends MouseAdapter {

	/**
	 * JList in which elements clicks are detected
	 */
	private JList<GeometricalObject> list;
	/**
	 * Drawing model
	 */
	private DrawingModel drawingModel;

	/**
	 * Constructs this class from given drawing model and jlist.
	 * 
	 * @param drawingModel
	 *            given drawing model
	 * @param list
	 *            given jlist
	 */

	public ListClickedListener(DrawingModel drawingModel,
			JList<GeometricalObject> list) {

		this.list = list;
		this.drawingModel = drawingModel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		Object object = e.getSource();

		if (!(object instanceof JList<?>)) {
			return;
		}
		if (e.getClickCount() != 2) {
			return;
		}

		int index = list.locationToIndex(e.getPoint());

		serveUser(index);
	}

	/**
	 * Checks if object is line, circle or filled circle and call theirs update
	 * methods.
	 * 
	 * @param index
	 *            index of object in list that is double-clicked
	 */

	private void serveUser(int index) {

		GeometricalObject object = list.getModel().getElementAt(index);

		if (object instanceof Line) {
			updateLine((Line) object);
		} else if (object instanceof Circle) {
			updateCircle((Circle) object);
		} else if (object instanceof FilledCircle) {
			updateFilledCircle((FilledCircle) object);
		} else {
			return;
		}
	}

	/**
	 * Shows dialog for updating line parameters. If user enters invalid
	 * parameters error dialog is shown and object is not changed, otherwise
	 * object is drawn again with new parameters.
	 * 
	 * @param object
	 *            line being updated
	 */

	private void updateLine(Line object) {

		JPanel panel = new JPanel(new GridLayout(0, 2));

		panel.add(new JLabel("Start X: "));
		JTextField startX = new JTextField();
		startX.setText(Integer.toString(object.getX1()));
		panel.add(startX);
		panel.add(new JLabel("Start Y: "));
		JTextField startY = new JTextField();
		startY.setText(Integer.toString(object.getY1()));
		panel.add(startY);

		panel.add(new JLabel("End X: "));
		JTextField endX = new JTextField();
		endX.setText(Integer.toString(object.getX2()));
		panel.add(endX);
		panel.add(new JLabel("End Y: "));
		JTextField endY = new JTextField();
		endY.setText(Integer.toString(object.getY2()));
		panel.add(endY);

		panel.add(new JLabel("Choose line color: "));
		JColorArea lineColor = new JColorArea(object.getColor(), false);
		panel.add(lineColor);

		int result = JOptionPane.showConfirmDialog(list, panel, "Edit line",
				JOptionPane.OK_CANCEL_OPTION);
		if (result != JOptionPane.OK_OPTION) {
			return;
		}

		int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
		try {
			x1 = Integer.parseInt(startX.getText());
			y1 = Integer.parseInt(startY.getText());
			x2 = Integer.parseInt(endX.getText());
			y2 = Integer.parseInt(endY.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(list, "Invalid parameters", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		Color color = lineColor.getCurrentColor();

		object.setForegroundColor(color);
		object.setStart(new Point(x1, y1));
		object.setEnd(new Point(x2, y2));

		drawingModel.objectChanged(object);
	}

	/**
	 * Shows dialog for updating circle parameters. If user enters invalid
	 * parameters error dialog is shown and object is not changed, otherwise
	 * object is drawn again with new parameters.
	 * 
	 * @param object
	 *            circle being updated
	 */

	private void updateCircle(Circle object) {

		JPanel panel = new JPanel(new GridLayout(0, 2));

		panel.add(new JLabel("Center X: "));
		JTextField centerX = new JTextField();
		centerX.setText(Integer.toString(object.getCenterX()));
		panel.add(centerX);
		panel.add(new JLabel("Center Y: "));
		JTextField centerY = new JTextField();
		centerY.setText(Integer.toString(object.getCenterY()));
		panel.add(centerY);
		panel.add(new JLabel("Radius: "));
		JTextField radius = new JTextField();
		radius.setText(Integer.toString(object.getRadius()));
		panel.add(radius);

		panel.add(new JLabel("Choose circle color: "));
		JColorArea circleColor = new JColorArea(object.getForegroundColor(),
				false);
		panel.add(circleColor);

		int result = JOptionPane.showConfirmDialog(list, panel, "Edit circle",
				JOptionPane.OK_CANCEL_OPTION);
		if (result != JOptionPane.OK_OPTION) {
			return;
		}

		int x = 0, y = 0, r = 0;
		try {
			x = Integer.parseInt(centerX.getText());
			y = Integer.parseInt(centerY.getText());
			r = Integer.parseInt(radius.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(list, "Invalid parameters", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		Color color = circleColor.getCurrentColor();

		object.setForegroundColor(color);
		object.setCenter(new Point(x, y));
		object.setRadius(r);

		drawingModel.objectChanged(object);
	}

	/**
	 * Shows dialog for updating filled circle parameters. If user enters
	 * invalid parameters error dialog is shown and object is not changed,
	 * otherwise object is drawn again with new parameters.
	 * 
	 * @param object
	 *            filled circle being updated
	 */

	private void updateFilledCircle(FilledCircle object) {

		JPanel panel = new JPanel(new GridLayout(0, 2));

		panel.add(new JLabel("Center X: "));
		JTextField centerX = new JTextField();
		centerX.setText(Integer.toString(object.getCenterX()));
		panel.add(centerX);
		panel.add(new JLabel("Center Y: "));
		JTextField centerY = new JTextField();
		centerY.setText(Integer.toString(object.getCenterY()));
		panel.add(centerY);
		panel.add(new JLabel("Radius: "));
		JTextField radius = new JTextField();
		radius.setText(Integer.toString(object.getRadius()));
		panel.add(radius);

		panel.add(new JLabel("Choose foreground color: "));
		JColorArea foregroundColor = new JColorArea(
				object.getForegroundColor(), false);
		panel.add(foregroundColor);
		panel.add(new JLabel("Choose background color: "));
		JColorArea backgroundColor = new JColorArea(
				object.getBackgroundColor(), false);
		panel.add(backgroundColor);

		int result = JOptionPane.showConfirmDialog(list, panel,
				"Edit filled circle", JOptionPane.OK_CANCEL_OPTION);
		if (result != JOptionPane.OK_OPTION) {
			return;
		}

		int x = 0, y = 0, r = 0;
		try {
			x = Integer.parseInt(centerX.getText());
			y = Integer.parseInt(centerY.getText());
			r = Integer.parseInt(radius.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(list, "Invalid parameters", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		Color bColor = backgroundColor.getCurrentColor();
		Color fColor = foregroundColor.getCurrentColor();

		object.setBackgroundColor(bColor);
		object.setForegroundColor(fColor);
		object.setCenter(new Point(x, y));
		object.setRadius(r);

		drawingModel.objectChanged(object);
	}
}
