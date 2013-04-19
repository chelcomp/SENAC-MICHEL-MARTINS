package br.com.senacrs.alp.aulas;

public interface Funcionario {
	
	Empresa getEmpresa();

	Departamento getDepartamento();
	
	String getNome();
	
	double getSalario();
}
