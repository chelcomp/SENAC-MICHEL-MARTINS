package br.com.senacrs.alp.aulas;

public  class NodeLinkedList<T> {
	private Node<T> startNode = null;
	private int size = 0;

	T get(int index) {
		Node<T> n = getNode(index);
		
		if(n == null)
			throw new IllegalArgumentException();
		
		return n.getValue();
	}

	private Node<T> getNode(int index) {
		if (index < 0 || index > size - 1) 
			return null;

		Node<T> n = startNode;

		for (int i = 0; i < index; i++) {
			n = n.getSubNode();
		}

		return n;

	}

	void add(int index, T value) {

		if (index < 0 || index > size || value == null)
			throw new IllegalArgumentException();

		Node<T> newNodo = new Node<T>(value);
		
		
		if (index > 0) {
			Node<T> nodoAnterior = getNode(index - 1);
			newNodo.setSubNode(nodoAnterior.getSubNode());
			nodoAnterior.setSubNode(newNodo);
		} else {
			newNodo.setSubNode(startNode);
			startNode = newNodo;
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

		Node<T> nodoRemover = getNode(index);
		if (nodoRemover == null)
			throw new IllegalArgumentException();

		if (index == 0)
			startNode = nodoRemover.getSubNode();
		else 
		{
			Node<T> nodoAnterior = getNode(index - 1);
			nodoAnterior.setSubNode(nodoRemover.getSubNode());			
		}
		
		size--;
		return nodoRemover.getValue();

	}

	void clear() {
		startNode = null;
		size = 0;

	}
}
