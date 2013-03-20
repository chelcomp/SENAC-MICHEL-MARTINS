package br.com.senacrs.alp.aulas;

public class ListaClass<T extends Object> implements Lista<T> {

	private NodeLinkedList<T> x = new NodeLinkedList<T>();

	@Override
	public void adicionarFinal(T valor) {
		adicionarPosicao(obterTamanho(), valor);
	}

	@Override
	public void adicionarInicio(T valor) {
		adicionarPosicao(0, valor);
	}

	@Override
	public void adicionarPosicao(int posicao, T valor) {
		x.add(posicao, valor);
	}

	@Override
	public T obterPrimeiro() {
		return obterPosicao(0);
	}

	@Override
	public T obterUltimo() {
		int i = obterTamanho() - 1;		
		return obterPosicao(i);
	}

	@Override
	public T obterPosicao(int posicao) {		
		return x.get(posicao);
	}

	@Override
	public int obterTamanho() {
		return x.size();
	}

	@Override
	public T removerPosicao(int posicao) {
		return x.remove(posicao);
	}

	@Override
	public void esvaziar() {
		x.clear();
	}

}
