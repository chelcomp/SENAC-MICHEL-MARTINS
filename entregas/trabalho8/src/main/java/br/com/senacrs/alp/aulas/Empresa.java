package br.com.senacrs.alp.aulas;

import java.util.List;

public interface Empresa {
	
	String getNome();
	
	List<Departamento> listaDepartamentoOrdemCrescentePorQtdFuncionarios();
	
	void adicionarDepartamento(Departamento departamento);
	
	int quantidadeFuncionarios();
}
