package hr.fer.zemris.java.hw12.jvdraw.actions;

import hr.fer.zemris.java.hw12.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw12.jvdraw.JVDraw;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

/**
 * Class representing exit action.
 * 
 * @author Juraj
 *
 */

public class ExitAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	/**
	 * Frame which is being exited
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
	public ExitAction(JVDraw frame, DrawingModel drawingModel) {
		this.frame = frame;
		this.drawingModel = drawingModel;

		putValue(Action.NAME, "Exit");
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt F4"));
		putValue(Action.SHORT_DESCRIPTION, "Exit JVDraw");
	}

	/**
	 * Exits given frame. Details of exiting process are explained in static
	 * <code>exit</code> method located in package
	 * hr.fer.zemris.java.hw12.jvdraw.actions in class <code>ActionUtil</code>.
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

		ActionUtil.exit(frame, drawingModel);
	}
}
