package hr.fer.zemris.java.hw12.jvdraw.actions;

import hr.fer.zemris.java.hw12.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw12.jvdraw.JVDraw;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

/**
 * Class representing save action.
 * 
 * @author Juraj
 *
 */

public class SaveAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	/**
	 * Frame which content is being saved
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
	public SaveAction(JVDraw frame, DrawingModel drawingModel) {
		this.frame = frame;
		this.drawingModel = drawingModel;

		putValue(Action.NAME, "Save");
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		putValue(Action.SHORT_DESCRIPTION, "Saves opened file");
	}

	/**
	 * Saves opened image. Details of saving process are explained in static
	 * <code>save</code> method located in package
	 * hr.fer.zemris.java.hw12.jvdraw.actions in class <code>ActionUtil</code>.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		ActionUtil.save(frame, drawingModel);
	}
}
