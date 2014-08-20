package maykish.colin.OrbitalSim.Utils;

import java.util.LinkedList;
import java.util.List;

public class ColinsQueue<T> {

	private final int maxSize;

	private List<T> list;

	public ColinsQueue(int maxSize) {
		this.maxSize = maxSize;
		list = new LinkedList<T>();
	}

	public void add(T element) {
		list.add(element);
		if (list.size() > maxSize) {
			list.remove(0);
		}
	}

	public List<T> getList() {
		return list;
	}

}
