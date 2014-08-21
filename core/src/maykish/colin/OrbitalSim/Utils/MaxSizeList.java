package maykish.colin.OrbitalSim.Utils;

import java.util.LinkedList;

/**
 * Enforces a maximum size on a LinkedList. Once the maximum size has been met,
 * the oldest elements will be removed as new ones are added.
 * 
 * @author Colin Maykish
 */
public class MaxSizeList<E> extends LinkedList<E> {
	private int maxSize;

	public MaxSizeList(int maxSize) {
		this.maxSize = maxSize;
	}

	@Override
	public boolean add(E e) {
		super.add(e);
		if (size() > maxSize) {
			super.remove(0);
		}

		return true;
	}

}
