package hr.fer.zemris.java.hw12.jvdraw.listeners;

import hr.fer.zemris.java.hw12.jvdraw.DrawingModel;

/**
 * Drawing model listener.
 * 
 * @author Juraj
 *
 */

public interface DrawingModelListener {

	/**
	 * Called if new objects are added in drawing model.
	 * 
	 * @param source
	 *            drawing model where new objects are added
	 * @param index0
	 *            start index of new added objects
	 * @param index1
	 *            end index of new added objects
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Called if objects are removed from drawing model.
	 * 
	 * @param source
	 *            drawing model where objects are removed
	 * @param index0
	 *            start index of removed objects
	 * @param index1
	 *            end index of removed objects
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Called if objects are changed in drawing model.
	 * 
	 * @param source
	 *            drawing model where objects are changed
	 * @param index0
	 *            start index of changed objects
	 * @param index1
	 *            end index of changed objects
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
