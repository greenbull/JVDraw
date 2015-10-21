package hr.fer.zemris.java.hw12.jvdraw;

import hr.fer.zemris.java.hw12.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of drawing model. Contains objects and listeners. After any
 * change in objects all listeners are notified.
 * 
 * @author Juraj
 *
 */

public class DrawingModelImpl implements DrawingModel {

	/**
	 * Drawing model listeners
	 */
	private List<DrawingModelListener> listeners;
	/**
	 * List of all objects stored in drawing model
	 */
	private List<GeometricalObject> geometricalObjects;

	/**
	 * Constructor. Initiates listeners and geometricalObjects lists.
	 */

	public DrawingModelImpl() {
		listeners = new ArrayList<DrawingModelListener>();
		geometricalObjects = new ArrayList<GeometricalObject>();
	}

	@Override
	public int getSize() {

		return geometricalObjects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {

		if (index < 0 || index >= getSize()) {
			return null;
		}

		return geometricalObjects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {

		geometricalObjects.add(object);

		for (DrawingModelListener listener : listeners) {
			listener.objectsAdded(this, geometricalObjects.size() - 1,
					geometricalObjects.size() - 1);
		}
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {

		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {

		listeners.remove(l);
	}

	@Override
	public void objectChanged(GeometricalObject object) {

		int index = geometricalObjects.indexOf(object);

		for (DrawingModelListener listener : listeners) {
			listener.objectsChanged(this, index, index);
		}
	}

	@Override
	public void remove(GeometricalObject object) {

		int index = geometricalObjects.indexOf(object);
		geometricalObjects.remove(object);

		for (DrawingModelListener listener : listeners) {
			listener.objectsRemoved(this, index, index);
		}
	}
}
