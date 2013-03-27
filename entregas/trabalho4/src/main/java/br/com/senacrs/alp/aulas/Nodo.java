package br.com.senacrs.alp.aulas;

public class Nodo<T> {
	private T value;
	private Nodo<T> subNodo;

	Nodo(T value) {
		setValue(value);
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public Nodo<T> getSubNodo() {
		return subNodo;
	}

	public void setSubNodo(Nodo<T> subNodo) {
		this.subNodo = subNodo;
	}

}
