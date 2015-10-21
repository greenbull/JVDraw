package hr.fer.zemris.java.hw12.jvdraw;

import hr.fer.zemris.java.hw12.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;

/**
 * Drawing model.
 * 
 * @author Juraj
 *
 */

public interface DrawingModel {

	/**
	 * Gets number of object in drawing model.
	 * 
	 * @return size of drawing model
	 */
	public int getSize();

	/**
	 * Gets object by given index.
	 * 
	 * @param index
	 *            given index
	 * @return object stored by given index
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds new object in drawing model.
	 * 
	 * @param object
	 *            object to add in drawing model
	 */
	public void add(GeometricalObject object);

	/**
	 * Adds drawing model listener.
	 * 
	 * @param l
	 *            drawing model listener
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes drawing model listener.
	 * 
	 * @param l
	 *            drawing model listener
	 */
	public void removeDrawingModelListener(DrawingModelListener l);

	/**
	 * Notifies drawing model that given object is changed.
	 * 
	 * @param object
	 *            object that is changed
	 */
	public void objectChanged(GeometricalObject object);

	/**
	 * Removes given object.
	 * 
	 * @param object
	 *            given object
	 */
	public void remove(GeometricalObject object);
}
