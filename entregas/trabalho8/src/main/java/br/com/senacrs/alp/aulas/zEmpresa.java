package br.com.senacrs.alp.aulas;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class zEmpresa implements Empresa {

	private String nome;
	private List<Departamento> listaDepartamentos;

	public  zEmpresa(String nome) {
		if(nome == null)
			throw new IllegalArgumentException();
		
		this.nome = nome;
		listaDepartamentos = new LinkedList<Departamento>();
	}

	@Override
	public String getNome() {
		// TODO Auto-generated method stub
		return nome;
	}

	@Override
	public List<Departamento> listaDepartamentoOrdemCrescentePorQtdFuncionarios() {
		// TODO Auto-generated method stub
		zDepartamento.SortOrdemCrescentePorQtdFuncionarios ordem = new zDepartamento.SortOrdemCrescentePorQtdFuncionarios();

		Collections.sort(listaDepartamentos, ordem);
		return listaDepartamentos;
	}

	@Override
	public void adicionarDepartamento(Departamento departamento) {
		if(departamento == null)
			throw new IllegalArgumentException();
		
		
		listaDepartamentos.add(departamento);
	}

	@Override
	public int quantidadeFuncionarios() {
		// TODO Auto-generated method stub
		int count = 0;
		for (Departamento dep : listaDepartamentos) {
			count += dep.quantidadeFuncionarios();
		}
		return count;
	}

}
