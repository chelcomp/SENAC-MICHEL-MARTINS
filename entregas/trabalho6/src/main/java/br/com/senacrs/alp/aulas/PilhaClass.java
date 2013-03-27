package br.com.senacrs.alp.aulas;

public class PilhaClass<T extends Object> implements Pilha<T> {

	Nodo<T> nodoInicial = null;
	Nodo<T> nodoFinal = null;
	int size;

	@Override
	public void insercao(T valor) {
		if (valor == null)
			throw new IllegalArgumentException(
					"Não é permitido a inserção de valor NULL na Pilha");

		Nodo<T> newNodo = new Nodo<T>(valor);
		
		if (nodoFinal == null) {
			nodoFinal = newNodo;
		} else {
			newNodo.setSubNodo(nodoInicial);			
		}
		nodoInicial = newNodo;
		size++;
	}

	@Override
	public T obter() {
		if (nodoInicial == null)
			throw new IllegalArgumentException("A Fila esta vazia");

		return nodoInicial.getValue();
	}

	@Override
	public T remocao() {
		T r = obter();
		nodoInicial = nodoInicial.getSubNodo();
		size--;
		return r;
	}

	@Override
	public boolean vazia() {
		return nodoInicial == null;
	}

	@Override
	public int tamanho() {
		return size;
	}

	@Override
	public void esvaziar() {
		nodoInicial = null;
		nodoFinal = null;
		size = 0;
	}

}
