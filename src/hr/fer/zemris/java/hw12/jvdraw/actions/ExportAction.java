package hr.fer.zemris.java.hw12.jvdraw.actions;

import hr.fer.zemris.java.hw12.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw12.jvdraw.JVDraw;
import hr.fer.zemris.java.hw12.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw12.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw12.jvdraw.objects.Line;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

/**
 * Class representing export action.
 * 
 * @author Juraj
 *
 */

public class ExportAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	/**
	 * JVDraw frame which content is being exported
	 */
	private JVDraw frame;
	/**
	 * Drawing model
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
	public ExportAction(JVDraw frame, DrawingModel drawingModel) {
		this.frame = frame;
		this.drawingModel = drawingModel;

		putValue(Action.NAME, "Export");
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		putValue(Action.SHORT_DESCRIPTION, "Exports as picture");
	}

	/**
	 * Exports drawing canvas as jpg, png or gif picture. User is expected to
	 * choose one from extension list in file chooser and isn't required to
	 * explicitly write that extension in file's name. Exported image is reduced
	 * to appropriate size so that there is no blank space on the edges.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Export");

		FileFilter jpgFilter = new FileTypeFilter(".jpg", "JPG picture");
		FileFilter pngFilter = new FileTypeFilter(".png", "PNG picture");
		FileFilter gifFilter = new FileTypeFilter(".gif", "GIF picture");

		fileChooser.addChoosableFileFilter(jpgFilter);
		fileChooser.addChoosableFileFilter(pngFilter);
		fileChooser.addChoosableFileFilter(gifFilter);

		if (fileChooser.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		Path file = fileChooser.getSelectedFile().toPath();
		FileFilter extensionFilter = fileChooser.getFileFilter();
		if (!extensionFilter.accept(file.toFile())) {
			String temp = file.toString() + extensionFilter.toString();
			file = new File(temp).toPath();
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

		List<GeometricalObject> geomObjects = new ArrayList<GeometricalObject>();
		// make copies of objects in drawing model
		for (int i = 0; i < drawingModel.getSize(); i++) {
			GeometricalObject obj = drawingModel.getObject(i);
			geomObjects.add(obj.makeCopy());
		}

		Dimension d = generateDimensionAndObjects(geomObjects);

		BufferedImage image = new BufferedImage(d.width, d.height,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, d.width, d.height);
		for (GeometricalObject obj : geomObjects) {
			obj.paintMe(g, obj.getForegroundColor(), obj.getBackgroundColor());
		}
		g.dispose();
		String ext = ActionUtil.getFileExtension(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, ext, bos);
			FileOutputStream fos = new FileOutputStream(file.toFile());
			fos.write(bos.toByteArray());
			fos.close();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(frame, "Cannot export image",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(frame, "Image successfuly exported",
				"Success", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Finds minimum and maximum x and y value of all objects in drawing model.
	 * When found translates all given objects accordingly. Calculates dimension
	 * of exported image.
	 * 
	 * @param geomObjects
	 *            given objects that are translated
	 * @return dimension of exported image
	 */

	private Dimension generateDimensionAndObjects(
			List<GeometricalObject> geomObjects) {

		int minX = frame.getWidth();
		int minY = frame.getHeight();
		int maxX = 0;
		int maxY = 0;

		for (int i = 0; i < drawingModel.getSize(); i++) {

			Point min = new Point();
			Point max = new Point();
			if (drawingModel.getObject(i) instanceof Line) {
				Line object = (Line) drawingModel.getObject(i);
				lineFind(object, min, max);

			} else if (drawingModel.getObject(i) instanceof Circle) {
				Circle object = (Circle) drawingModel.getObject(i);
				circleFind(object, min, max);
			} else if (drawingModel.getObject(i) instanceof FilledCircle) {
				FilledCircle object = (FilledCircle) drawingModel.getObject(i);
				filledCircleFind(object, min, max);
			}

			minX = Math.min(minX, min.x);
			minY = Math.min(minY, min.y);
			maxX = Math.max(maxX, max.x);
			maxY = Math.max(maxY, max.y);
		}

		generateNewObjects(geomObjects, minX, minY);

		return new Dimension(maxX - minX, maxY - minY);
	}

	/**
	 * Translates given objects by minY pixels up and minX pixels left.
	 * 
	 * @param geomObjects
	 *            given objects that are translated
	 * @param minX
	 *            x axis translation
	 * @param minY
	 *            y axis translation
	 */
	private void generateNewObjects(List<GeometricalObject> geomObjects,
			int minX, int minY) {

		for (GeometricalObject obj : geomObjects) {

			if (obj instanceof Line) {
				int x1 = ((Line) obj).getX1() - minX;
				int x2 = ((Line) obj).getX2() - minX;
				int y1 = ((Line) obj).getY1() - minY;
				int y2 = ((Line) obj).getY2() - minY;
				((Line) obj).setStart(new Point(x1, y1));
				((Line) obj).setEnd(new Point(x2, y2));
			} else if (obj instanceof Circle) {
				int centerX = ((Circle) obj).getCenterX() - minX;
				int centerY = ((Circle) obj).getCenterY() - minY;
				((Circle) obj).setCenter(new Point(centerX, centerY));
			} else if (obj instanceof FilledCircle) {
				int centerX = ((FilledCircle) obj).getCenterX() - minX;
				int centerY = ((FilledCircle) obj).getCenterY() - minY;
				((FilledCircle) obj).setCenter(new Point(centerX, centerY));
			}
		}
	}

	/**
	 * Finds minimal and maximal x and y value covered by given object.
	 * 
	 * @param object
	 *            given object
	 * @param min
	 *            minimal point
	 * @param max
	 *            maximal point
	 */
	private void filledCircleFind(FilledCircle object, Point min, Point max) {

		int minX = object.getCenterX() - object.getRadius();
		int minY = object.getCenterY() - object.getRadius();

		int maxX = object.getCenterX() + object.getRadius();
		int maxY = object.getCenterY() + object.getRadius();

		min.setLocation(minX, minY);
		max.setLocation(maxX, maxY);
	}

	/**
	 * Finds minimal and maximal x and y value covered by given object.
	 * 
	 * @param object
	 *            given object
	 * @param min
	 *            minimal point
	 * @param max
	 *            maximal point
	 */
	private void circleFind(Circle object, Point min, Point max) {

		int minX = object.getCenterX() - object.getRadius();
		int minY = object.getCenterY() - object.getRadius();

		int maxX = object.getCenterX() + object.getRadius();
		int maxY = object.getCenterY() + object.getRadius();

		min.setLocation(minX, minY);
		max.setLocation(maxX, maxY);
	}

	/**
	 * Finds minimal and maximal x and y value covered by given object.
	 * 
	 * @param object
	 *            given object
	 * @param min
	 *            minimal point
	 * @param max
	 *            maximal point
	 */
	private void lineFind(Line object, Point min, Point max) {

		int minX = Math.min(object.getX1(), object.getX2());
		int minY = Math.min(object.getY1(), object.getY2());

		int maxX = Math.max(object.getX1(), object.getX2());
		int maxY = Math.max(object.getY1(), object.getY2());

		min.setLocation(minX, minY);
		max.setLocation(maxX, maxY);
	}
}
