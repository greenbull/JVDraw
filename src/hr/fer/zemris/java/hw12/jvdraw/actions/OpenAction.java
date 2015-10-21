package hr.fer.zemris.java.hw12.jvdraw.actions;

import hr.fer.zemris.java.hw12.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw12.jvdraw.JVDraw;
import hr.fer.zemris.java.hw12.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw12.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw12.jvdraw.objects.Line;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * Class representing open action.
 * 
 * @author Juraj
 *
 */

public class OpenAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	/**
	 * Frame in which new file is being opened.
	 */
	private JVDraw frame;
	/**
	 * Given drawing model.
	 */
	private DrawingModel drawingModel;

	/**
	 * Constructs and initiates action from given frame and drawing model.
	 * 
	 * @param frame
	 *            given frame
	 * @param drawingModel
	 *            given drawing model
	 */
	public OpenAction(JVDraw frame, DrawingModel drawingModel) {
		this.frame = frame;
		this.drawingModel = drawingModel;

		putValue(Action.NAME, "Open...");
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		putValue(Action.SHORT_DESCRIPTION, "Opens new file with extension .jvd");
	}

	/**
	 * Displayes chosen file in drawing canvas. Only files with extension .jvd
	 * can be displayed. If file's content is invalid appropriate message is
	 * shown to user.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Open file");

		if (fileChooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		Path file = fileChooser.getSelectedFile().toPath();
		String extension = getFileExtension(file);
		if (!extension.equals("jvd")) {
			JOptionPane.showMessageDialog(frame,
					"Chosen file must have .jvd extension", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		byte[] fileBytes;
		try {
			fileBytes = Files.readAllBytes(file);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "Cannot read file", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		List<GeometricalObject> objects = readGeomObjects(fileBytes);
		if (objects == null) {
			return;
		}

		List<GeometricalObject> dmObjects = new ArrayList<GeometricalObject>();

		for (int i = 0; i < drawingModel.getSize(); i++) {
			dmObjects.add(drawingModel.getObject(i));
		}

		for (GeometricalObject obj : dmObjects) {
			drawingModel.remove(obj);
		}

		for (GeometricalObject object : objects) {
			drawingModel.add(object);
		}

		frame.setTitle(file.toString());
		frame.setCanvasEdited(false);
	}

	/**
	 * Reads objects from given file bytes.
	 * 
	 * @param fileBytes
	 *            given file bytes
	 * @return list of read objects
	 */
	private List<GeometricalObject> readGeomObjects(byte[] fileBytes) {

		String str = new String(fileBytes, StandardCharsets.UTF_8);
		String ls = System.lineSeparator();
		String[] lines = str.split(ls);

		List<GeometricalObject> objects = new ArrayList<GeometricalObject>();

		for (String line : lines) {
			String[] params = line.split(" ");
			GeometricalObject object = null;
			if (params[0].toLowerCase().equals("line")) {
				object = processLine(params);
			} else if (params[0].toLowerCase().equals("circle")) {
				object = processCircle(params);
			} else if (params[0].toLowerCase().equals("fcircle")) {
				object = processFCircle(params);
			} else {
				JOptionPane
						.showMessageDialog(
								frame,
								"Invalid file content, requested file won't be pictured",
								"Error", JOptionPane.ERROR_MESSAGE);
			}
			if (object == null) {
				JOptionPane
						.showMessageDialog(
								frame,
								"Invalid file content, requested file won't be pictured",
								"Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}
			objects.add(object);
		}

		return objects;
	}

	/**
	 * Constructs line from given line parameters.
	 * 
	 * @param params
	 *            given line paramters
	 * @return constructed line
	 */

	private GeometricalObject processLine(String[] params) {

		if (params.length != 8) {
			return null;
		}

		int x1 = 0, y1 = 0, x2 = 0, y2 = 0, r = 0, g = 0, b = 0;
		try {
			x1 = Integer.parseInt(params[1]);
			y1 = Integer.parseInt(params[2]);
			x2 = Integer.parseInt(params[3]);
			y2 = Integer.parseInt(params[4]);
			r = Integer.parseInt(params[5]);
			g = Integer.parseInt(params[6]);
			b = Integer.parseInt(params[7]);
		} catch (Exception e) {
			return null;
		}

		Line line = new Line(x1, y1, x2, y2);
		try {
			Color c = new Color(r, g, b);
			line.setForegroundColor(c);
		} catch (Exception e) {
			return null;
		}

		return line;
	}

	/**
	 * Constructs circle from given circle parameters.
	 * 
	 * @param params
	 *            given circle paramters
	 * @return constructed circle
	 */

	private GeometricalObject processCircle(String[] params) {

		if (params.length != 7) {
			return null;
		}

		int centerX = 0, centerY = 0, radius = 0, r = 0, g = 0, b = 0;
		try {
			centerX = Integer.parseInt(params[1]);
			centerY = Integer.parseInt(params[2]);
			radius = Integer.parseInt(params[3]);
			r = Integer.parseInt(params[4]);
			g = Integer.parseInt(params[5]);
			b = Integer.parseInt(params[6]);
		} catch (Exception e) {
			return null;
		}

		Circle circle = new Circle(centerX, centerY, radius);
		try {
			Color c = new Color(r, g, b);
			circle.setForegroundColor(c);
		} catch (Exception e) {
			return null;
		}

		return circle;
	}

	/**
	 * Constructs filled circle from given fcircle parameters.
	 * 
	 * @param params
	 *            given fcircle paramters
	 * @return constructed filled circle
	 */

	private GeometricalObject processFCircle(String[] params) {

		if (params.length != 10) {
			return null;
		}

		int centerX = 0, centerY = 0, radius = 0, r1 = 0, g1 = 0, b1 = 0, r2 = 0, g2 = 0, b2 = 0;
		try {
			centerX = Integer.parseInt(params[1]);
			centerY = Integer.parseInt(params[2]);
			radius = Integer.parseInt(params[3]);
			r1 = Integer.parseInt(params[4]);
			g1 = Integer.parseInt(params[5]);
			b1 = Integer.parseInt(params[6]);
			r2 = Integer.parseInt(params[7]);
			g2 = Integer.parseInt(params[8]);
			b2 = Integer.parseInt(params[9]);
		} catch (Exception e) {
			return null;
		}

		FilledCircle fcircle = new FilledCircle(centerX, centerY, radius);
		try {
			Color fc = new Color(r1, g1, b1);
			Color bc = new Color(r2, g2, b2);
			fcircle.setForegroundColor(fc);
			fcircle.setBackgroundColor(bc);
		} catch (Exception e) {
			return null;
		}

		return fcircle;
	}

	/**
	 * Gets given file's extension.
	 * 
	 * @param file
	 *            given file
	 * @return file's extension
	 */

	private String getFileExtension(Path file) {

		String path = file.toString();
		String[] split = path.split("\\.");

		if (split.length != 2) {
			return "";
		} else {
			return split[1];
		}
	}
}
