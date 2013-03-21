package br.com.senacrs.alp.aulas;

public class Node<T> {
	private T value;
	private Node<T> subNodo;

	Node(T value) {
		setValue(value);
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public Node<T> getSubNode() {
		return subNodo;
	}

	public void setSubNode(Node<T> subNodo) {
		this.subNodo = subNodo;
	}

}
