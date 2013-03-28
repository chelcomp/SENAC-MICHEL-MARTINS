package br.com.senacrs.alp.aulas;

public class PilhaClass<T extends Object> implements Pilha<T> {

	Lista<T> lista = ListaFactory.getInstancia().criarLista();

	@Override
	public void insercao(T valor) {
		lista.adicionarInicio(valor);
	}

	@Override
	public T obter() {
		return lista.obterPrimeiro();
	}

	@Override
	public T remocao() {
		T r = obter();
		lista.removerPosicao(0);
		return r;
	}

	@Override
	public boolean vazia() {
		return tamanho() == 0;
	}

	@Override
	public int tamanho() {
		return lista.obterTamanho();
	}

	@Override
	public void esvaziar() {
		lista.esvaziar();
	}

}
