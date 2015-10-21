package hr.fer.zemris.java.hw12.jvdraw.listmodels;

import hr.fer.zemris.java.hw12.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw12.jvdraw.JVDraw;
import hr.fer.zemris.java.hw12.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * List model for drawn objects. It is also drawing model listener.
 * 
 * @author Juraj
 *
 */

public class DrawingObjectListModel extends
		AbstractListModel<GeometricalObject> implements DrawingModelListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Drawing model
	 */
	private DrawingModel drawingModel;
	/**
	 * Frame in which list model is stored
	 */
	private JVDraw frame;

	/**
	 * Constructs list model from given frame and drawing model. Also adds
	 * itself to drawing model listeners.
	 * 
	 * @param frame
	 *            given frame
	 * @param drawingModel
	 *            given drawing model
	 */

	public DrawingObjectListModel(JVDraw frame, DrawingModel drawingModel) {

		this.drawingModel = drawingModel;
		this.frame = frame;
		drawingModel.addDrawingModelListener(this);
	}

	@Override
	public GeometricalObject getElementAt(int arg0) {

		return drawingModel.getObject(arg0);
	}

	@Override
	public int getSize() {

		return drawingModel.getSize();
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {

		frame.setCanvasEdited(true);
		ListDataListener[] listeners = this.getListDataListeners();

		for (ListDataListener listener : listeners) {

			listener.intervalAdded(new ListDataEvent(this,
					ListDataEvent.INTERVAL_ADDED, index0, index1));
		}
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {

		frame.setCanvasEdited(true);
		ListDataListener[] listeners = this.getListDataListeners();

		for (ListDataListener listener : listeners) {

			listener.intervalRemoved(new ListDataEvent(this,
					ListDataEvent.INTERVAL_REMOVED, index0, index1));
		}
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {

		frame.setCanvasEdited(true);
		ListDataListener[] listeners = this.getListDataListeners();

		for (ListDataListener listener : listeners) {

			listener.contentsChanged(new ListDataEvent(this,
					ListDataEvent.CONTENTS_CHANGED, index0, index1));
		}
	}

}
