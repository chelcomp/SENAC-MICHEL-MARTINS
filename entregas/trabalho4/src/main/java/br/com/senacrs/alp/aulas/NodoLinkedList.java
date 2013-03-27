package br.com.senacrs.alp.aulas;

public class NodoLinkedList<T> {
	private Nodo<T> startNodo = null;
	private Nodo<T> endNodo = null;
	private int size = 0;

	T get(int index) {
		Nodo<T> n = getNodo(index);

		if (n == null)
			throw new IllegalArgumentException();

		return n.getValue();
	}

	private Nodo<T> getNodo(int index) {
		if (index < 0 || index > size - 1)
			return null;

		Nodo<T> n = null;

		if (size > 1 && index == size - 1)
			n = endNodo;
		else {
			n = startNodo;

			for (int i = 0; i < index; i++) {
				n = n.getSubNodo();
			}
		}
		return n;
	}

	
	void add(T value) {
		endNodo.setSubNodo( new Nodo<T>(value));
		endNodo = endNodo.getSubNodo();
		size++;
	}

	void add(int index, T value) {

		if (index < 0 || index > size || value == null)
			throw new IllegalArgumentException();

		Nodo<T> newNodo = new Nodo<T>(value);
		if (index == 0) {
			newNodo.setSubNodo(startNodo);
			startNodo = newNodo;
			if (endNodo == null)
				endNodo = startNodo;
			size++;
		} else if (index < size) {
			Nodo<T> nodoAnterior = getNodo(index - 1);
			newNodo.setSubNodo(nodoAnterior.getSubNodo());
			nodoAnterior.setSubNodo(newNodo);
			if (nodoAnterior == endNodo)
				endNodo = newNodo;
			size++;
		} else if (index == size)
			add(value);


	}

	int size() {
		/*
		 * int i = 0; Nodo<T> n = nodoInicial; if (n != null) { i++; while
		 * (n.getSubNodo() != null) { i++; n = n.getSubNodo(); } }
		 */
		return size;

	}

	T remove(int index) {

		Nodo<T> nodoRemover = getNodo(index);
		if (nodoRemover == null)
			throw new IllegalArgumentException();

		if (index == 0)
			startNodo = nodoRemover.getSubNodo();
		else {
			Nodo<T> nodoAnterior = getNodo(index - 1);
			nodoAnterior.setSubNodo(nodoRemover.getSubNodo());

			if (nodoRemover.getSubNodo() == endNodo)
				endNodo = nodoAnterior.getSubNodo();
		}

		size--;
		return nodoRemover.getValue();

	}

	void clear() {
		startNodo = null;
		endNodo = null;
		size = 0;

	}
}
