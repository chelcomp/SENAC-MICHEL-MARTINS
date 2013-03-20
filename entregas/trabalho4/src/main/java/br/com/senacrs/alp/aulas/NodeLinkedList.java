package br.com.senacrs.alp.aulas;

public  class NodeLinkedList<T> {
	private Node<T> nodoInicial = null;
	private int size = 0;

	T get(int index) {
		Node<T> n = getNodo(index);
		
		if(n == null)
			throw new IllegalArgumentException();
		
		return n.getValue();
	}

	private Node<T> getNodo(int index) {
		if (index < 0 || index > size - 1) 
			return null;

		Node<T> n = nodoInicial;

		for (int i = 0; i < index; i++) {
			n = n.getSubNodo();
		}

		return n;

	}

	void add(int index, T value) {

		if (index < 0 || index > size || value == null)
			throw new IllegalArgumentException();

		Node<T> newNodo = new Node<T>();
		newNodo.setValue(value);
		
		if (index > 0) {
			Node<T> nodoAnterior = getNodo(index - 1);
			newNodo.setSubNodo(nodoAnterior.getSubNodo());
			nodoAnterior.setSubNodo(newNodo);
		} else {
			newNodo.setSubNodo(nodoInicial);
			nodoInicial = newNodo;
		}
		size++;
	}

	int size() {
		/*
		 * int i = 0; Nodo<T> n = nodoInicial; if (n != null) { i++; while
		 * (n.getSubNodo() != null) { i++; n = n.getSubNodo(); } }
		 */
		return size;

	}

	T remove(int index) {

		Node<T> nodoRemover = getNodo(index);
		if (nodoRemover == null)
			throw new IllegalArgumentException();

		if (index == 0)
			nodoInicial = nodoRemover.getSubNodo();
		else 
		{
			Node<T> nodoAnterior = getNodo(index - 1);
			nodoAnterior.setSubNodo(nodoRemover.getSubNodo());			
		}
		
		size--;
		return nodoRemover.getValue();

	}

	void clear() {
		nodoInicial = null;
		size = 0;

	}
}
