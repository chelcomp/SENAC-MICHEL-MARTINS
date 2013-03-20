package br.com.senacrs.alp.aulas;

public class Node <T>{
  private T value;
  private Node subNodo;
public T getValue() {
	return value;
}
public void setValue(T value) {
	this.value = value;
}
public Node<T> getSubNodo() {
	return subNodo;
}
public void setSubNodo(Node subNodo) {
	this.subNodo = subNodo;
}
	
}
