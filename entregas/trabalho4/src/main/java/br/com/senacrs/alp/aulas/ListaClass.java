package br.com.senacrs.alp.aulas;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class ListaClass<T extends Object> implements Lista<T> {

	private List<T> x = new LinkedList<T>();

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
		if (posicao > obterTamanho() || posicao < 0 || valor == null)
			throw new IllegalArgumentException();

		x.add(posicao, valor);
	}

	@Override
	public T obterPrimeiro() {
		return obterPosicao(0);
	}

	@Override
	public T obterUltimo() {
		int i = obterTamanho();
		if (i > 0)
			i = i - 1;

		return obterPosicao(i);
	}

	@Override
	public T obterPosicao(int posicao) {
		if (posicao > obterTamanho() - 1 || posicao < 0)
			throw new IllegalArgumentException();

		return x.get(posicao);
	}

	@Override
	public int obterTamanho() {
		return x.size();
	}

	@Override
	public T removerPosicao(int posicao) {
		if (posicao > obterTamanho() - 1 || posicao < 0)
			throw new IllegalArgumentException();

		return x.remove(posicao);
	}

	@Override
	public void esvaziar() {
		x.clear();
	}

}
