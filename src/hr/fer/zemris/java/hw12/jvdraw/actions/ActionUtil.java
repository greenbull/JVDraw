package hr.fer.zemris.java.hw12.jvdraw.actions;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw12.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw12.jvdraw.JVDraw;
import hr.fer.zemris.java.hw12.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw12.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw12.jvdraw.objects.Line;

/**
 * Utility class. Contains public static methods that are used by several
 * classes.
 *
 * @author Juraj
 *
 */

public class ActionUtil {

	/**
	 * Disposes given frame. If drawing canvas is unsaved asks user if he/she
	 * wants to save it before exiting.
	 * 
	 * @param frame
	 *            given frame
	 * @param drawingModel
	 *            given drawing model
	 */
	public static void exit(JVDraw frame, DrawingModel drawingModel) {

		if (frame.getCanvasEdited()) {

			int result = JOptionPane.showConfirmDialog(frame,
					"Do you want to save current image ?", "Save",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (result == JOptionPane.YES_OPTION) {
				save(frame, drawingModel);
			}
		}

		int result = JOptionPane.showConfirmDialog(frame,
				"Do you want to exit ?", "Exit", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			frame.dispose();
		}
	}

	/**
	 * If file already exists on disc saves current modifications, else calls
	 * <code>saveAs</code> method.
	 * 
	 * @param frame
	 *            given frame in which file is opened
	 * @param drawingModel
	 *            given drawing model
	 */
	public static void save(JVDraw frame, DrawingModel drawingModel) {

		String title = frame.getTitle();
		Path file = new File(title).toPath();

		if (Files.exists(file)) {
			if (!frame.getCanvasEdited()) {
				return;
			}
			String text = generateFile(drawingModel);
			try {
				Files.write(file, text.getBytes(StandardCharsets.UTF_8));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(
						frame,
						"Error while writing file (" + file + "):"
								+ e.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
			}
			frame.setCanvasEdited(false);
		} else {
			saveAs(frame, drawingModel);
		}
	}

	/**
	 * Asks user how he/she wants to save its file, if user chooses the one that
	 * already exists than asks permission to overwrite, else creates new file
	 * by name user has entered. User can only save file with .jvd extension, if
	 * any other extension is written than error message will be displayed and
	 * file won't be saved.
	 * 
	 * @param frame
	 *            given frame in which file is opened
	 * @param drawingModel
	 *            given drawing model
	 */

	public static void saveAs(JVDraw frame, DrawingModel drawingModel) {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save as");

		if (fileChooser.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
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
		if (Files.exists(file)) {
			int result = JOptionPane.showConfirmDialog(frame, "Chosen file ("
					+ file + ") already exists. Do you want to overwrite it?",
					"Warning", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (result != JOptionPane.YES_OPTION) {
				return;
			}
		}

		String text = generateFile(drawingModel);
		try {
			Files.write(file, text.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "Error while writing file ("
					+ file + "):" + e.getMessage(), "Error!",
					JOptionPane.ERROR_MESSAGE);
		}

		frame.setCanvasEdited(false);
		frame.setTitle(file.toString());
	}

	/**
	 * Generates text representation for given drawing model. Format: 
	 * LINE x0 y0 x1 y1 red green blue 
	 * CIRCLE centerx centery radius red green blue 
	 * FCIRCLE centerx centery radius red green blue red green blue
	 * 
	 * @param drawingModel
	 *            given drawing model
	 * @return text representation for given drawing model
	 */

	private static String generateFile(DrawingModel drawingModel) {

		StringBuilder sb = new StringBuilder();
		String ls = System.lineSeparator();

		for (int i = 0; i < drawingModel.getSize(); i++) {
			GeometricalObject object = drawingModel.getObject(i);
			String s = null;
			if (object instanceof Line) {
				s = processLine((Line) object);
			} else if (object instanceof Circle) {
				s = processCircle((Circle) object);
			} else if (object instanceof FilledCircle) {
				s = processFilledCircle((FilledCircle) object);
			}
			if (i < drawingModel.getSize() - 1) {
				sb.append(s).append(ls);
			} else {
				sb.append(s);
			}
		}

		return sb.toString();
	}

	/**
	 * Generates text representation for given line.
	 * 
	 * @param line
	 *            given line
	 * @return text representation for given line
	 */

	public static String processLine(Line line) {

		StringBuilder sb = new StringBuilder();
		sb.append("LINE ");
		sb.append(line.getX1() + " ");
		sb.append(line.getY1() + " ");
		sb.append(line.getX2() + " ");
		sb.append(line.getY2() + " ");
		sb.append(line.getColor().getRed() + " ");
		sb.append(line.getColor().getGreen() + " ");
		sb.append(line.getColor().getBlue());

		return sb.toString();
	}

	/**
	 * Generates text representation for given circle.
	 * 
	 * @param circle
	 *            given circle
	 * @return text representation for given circle
	 */

	public static String processCircle(Circle circle) {

		StringBuilder sb = new StringBuilder();
		sb.append("CIRCLE ");
		sb.append(circle.getCenterX() + " ");
		sb.append(circle.getCenterY() + " ");
		sb.append(circle.getRadius() + " ");
		sb.append(circle.getForegroundColor().getRed() + " ");
		sb.append(circle.getForegroundColor().getGreen() + " ");
		sb.append(circle.getForegroundColor().getBlue());

		return sb.toString();
	}

	/**
	 * Generates text representation for given filled circle.
	 * 
	 * @param fcircle
	 *            given filled circle
	 * @return text representation for given filled circle
	 */

	public static String processFilledCircle(FilledCircle fcircle) {

		StringBuilder sb = new StringBuilder();
		sb.append("FCIRCLE ");
		sb.append(fcircle.getCenterX() + " ");
		sb.append(fcircle.getCenterY() + " ");
		sb.append(fcircle.getRadius() + " ");
		sb.append(fcircle.getForegroundColor().getRed() + " ");
		sb.append(fcircle.getForegroundColor().getGreen() + " ");
		sb.append(fcircle.getForegroundColor().getBlue() + " ");
		sb.append(fcircle.getBackgroundColor().getRed() + " ");
		sb.append(fcircle.getBackgroundColor().getGreen() + " ");
		sb.append(fcircle.getBackgroundColor().getBlue());

		return sb.toString();
	}

	/**
	 * Gets extension from given file.
	 * 
	 * @param file
	 *            given file
	 * @return extension from given file
	 */

	public static String getFileExtension(Path file) {

		String path = file.toString();
		String[] split = path.split("\\.");

		if (split.length != 2) {
			return "";
		} else {
			return split[1];
		}
	}
}
