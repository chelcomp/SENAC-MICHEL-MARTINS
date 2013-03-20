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

	public Node<T> getSubNodo() {
		return subNodo;
	}

	public void setSubNodo(Node<T> subNodo) {
		this.subNodo = subNodo;
	}

}
