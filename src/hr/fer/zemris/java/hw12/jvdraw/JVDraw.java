package hr.fer.zemris.java.hw12.jvdraw;

import hr.fer.zemris.java.hw12.jvdraw.actions.ActionUtil;
import hr.fer.zemris.java.hw12.jvdraw.actions.ExitAction;
import hr.fer.zemris.java.hw12.jvdraw.actions.ExportAction;
import hr.fer.zemris.java.hw12.jvdraw.actions.OpenAction;
import hr.fer.zemris.java.hw12.jvdraw.actions.SaveAction;
import hr.fer.zemris.java.hw12.jvdraw.actions.SaveAsAction;
import hr.fer.zemris.java.hw12.jvdraw.listeners.JWriteColors;
import hr.fer.zemris.java.hw12.jvdraw.listeners.ListClickedListener;
import hr.fer.zemris.java.hw12.jvdraw.listmodels.DrawingObjectListModel;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Class representing simple tool for drawing lines, circles and filled circles
 * in different colors.
 * 
 * @author Juraj
 *
 */

public class JVDraw extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * True if drawing canvas is edited without saving, false if there wasn't
	 * any changes after last saving
	 */
	private boolean canvasEdited = false;

	/**
	 * Constructor. Sets size, location, layout and title of JVDraw. Calls
	 * method for initiating GUI.
	 */

	public JVDraw() {

		setSize(1000, 600);
		setLocation(100, 100);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
		setLayout(new BorderLayout());
		setTitle("JVDraw");

		initGUI();
	}

	/**
	 * Initiates GUI. Creates toolbar with foreground and background color to
	 * choose. Creates button group from which user can choose object for
	 * drawing. Creates label in which foreground and background colors are
	 * written as rgb values. Creates list that contains all objects currently
	 * drawn on canvas.
	 */

	private void initGUI() {

		JToolBar toolbar = new JToolBar("Toolbar");

		JPanel panel = new JPanel(new FlowLayout());
		JColorArea foreground = new JColorArea(Color.RED, false);
		JColorArea background = new JColorArea(Color.BLUE, true);
		panel.add(foreground);
		panel.add(background);

		ButtonGroup buttonGroup = new ButtonGroup();
		JToggleButton lineButton = new JToggleButton("Line");
		lineButton.setActionCommand("Line");
		JToggleButton circleButton = new JToggleButton("Circle");
		circleButton.setActionCommand("Circle");
		JToggleButton filledCircleButton = new JToggleButton("FilledCircle");
		filledCircleButton.setActionCommand("FilledCircle");
		buttonGroup.add(lineButton);
		buttonGroup.add(circleButton);
		buttonGroup.add(filledCircleButton);

		JPanel shapePanel = new JPanel(new GridLayout(1, 0));
		shapePanel.add(lineButton);
		shapePanel.add(circleButton);
		shapePanel.add(filledCircleButton);

		toolbar.add(panel);
		toolbar.add(shapePanel);
		getContentPane().add(toolbar, BorderLayout.PAGE_START);

		JWriteColors writeColors = new JWriteColors(foreground, background);

		getContentPane().add(writeColors, BorderLayout.PAGE_END);

		DrawingModel drawingModel = new DrawingModelImpl();
		JDrawingCanvas canvas = new JDrawingCanvas(drawingModel, foreground,
				background, buttonGroup);

		getContentPane().add(canvas, BorderLayout.CENTER);

		JList<GeometricalObject> list = new JList<GeometricalObject>(
				new DrawingObjectListModel(this, drawingModel));
		JScrollPane listScroll = new JScrollPane(list);
		Dimension d = list.getPreferredSize();
		d.width = 200;
		listScroll.setPreferredSize(d);
		list.addMouseListener(new ListClickedListener(drawingModel, list));

		getContentPane().add(listScroll, BorderLayout.EAST);

		createMenus(drawingModel);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ActionUtil.exit(JVDraw.this, drawingModel);
			}
		});
	}

	/**
	 * Create menu with items for opening, saving, exporting and closing drawn
	 * image. Also initiates all necessary actions.
	 * 
	 * @param drawingModel
	 *            drawing model
	 */

	private void createMenus(DrawingModel drawingModel) {

		OpenAction openAction = new OpenAction(this, drawingModel);
		SaveAction saveAction = new SaveAction(this, drawingModel);
		SaveAsAction saveAsAction = new SaveAsAction(this, drawingModel);
		ExportAction exportAction = new ExportAction(this, drawingModel);
		ExitAction exitAction = new ExitAction(this, drawingModel);

		JMenuBar menu = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menu.add(fileMenu);
		JMenuItem openMenu = new JMenuItem(openAction);
		fileMenu.add(openMenu);
		JMenuItem saveMenu = new JMenuItem(saveAction);
		fileMenu.add(saveMenu);
		JMenuItem saveAsMenu = new JMenuItem(saveAsAction);
		fileMenu.add(saveAsMenu);
		JMenuItem exportMenu = new JMenuItem(exportAction);
		fileMenu.add(exportMenu);
		JMenuItem exitMenu = new JMenuItem(exitAction);
		fileMenu.add(exitMenu);

		this.setJMenuBar(menu);
	}

	/**
	 * CanvasEdited getter.
	 * 
	 * @return true if canvas is edited, false otherwise
	 */

	public boolean getCanvasEdited() {
		return canvasEdited;
	}

	/**
	 * CanvasEdited setter.
	 * 
	 * @param e
	 *            to set canvasEdited
	 */

	public void setCanvasEdited(boolean e) {
		canvasEdited = e;
	}

	/**
	 * Main method. Sets system default look and feel, initiates JVDraw.
	 * 
	 * @param args
	 *            Doesn't use command line arguments.
	 */

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		SwingUtilities.invokeLater(() -> {
			new JVDraw();
		});
	}
}
