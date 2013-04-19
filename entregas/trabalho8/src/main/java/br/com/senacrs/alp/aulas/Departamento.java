package br.com.senacrs.alp.aulas;

import java.util.List;

public interface Departamento {

	Empresa getEmpresa();
	
	String getNome();
	
	List<Funcionario> listarFuncionariosPorOrdemAlfabetica();
	
	List<Funcionario> listarFuncionariosPorDecrescenteSalario();
	
	int quantidadeFuncionarios();
	
	void adicionarFuncionario(Funcionario funcionario);
}
