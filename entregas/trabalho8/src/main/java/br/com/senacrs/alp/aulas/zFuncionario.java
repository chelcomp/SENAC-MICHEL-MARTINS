package br.com.senacrs.alp.aulas;

import java.util.Comparator;

public class zFuncionario implements Funcionario {

	private Departamento departamento;
	private String nome;
	private double salario;
	
    zFuncionario(Departamento departamento, String nome, double salario)
	{
    	if(departamento == null || salario < 0 || nome == null)
    		throw new IllegalArgumentException();
    	
    	
		this.departamento = departamento;
		this.nome = nome;
		this.salario = salario;		
	}
	
	@Override
	public Empresa getEmpresa() {
		// TODO Auto-generated method stub
		return departamento.getEmpresa();
	}

	@Override
	public Departamento getDepartamento() {
		// TODO Auto-generated method stub
		return departamento;
	}

	@Override
	public String getNome() {
		// TODO Auto-generated method stub
		return nome;
	}

	@Override
	public double getSalario() {
		// TODO Auto-generated method stub
		return salario;
	}

	public static class SortPorDecrescenteSalario implements Comparator<Funcionario>
	{

		@Override
		public int compare(Funcionario arg0, Funcionario arg1) {
			// TODO Auto-generated method stub
			return Double.compare( arg1.getSalario() , arg0.getSalario());
		}
			
	}
	
	public static class SortPorOrdemAlfabetica implements Comparator<Funcionario>
	{

		@Override
		public int compare(Funcionario arg0, Funcionario arg1) {
			// TODO Auto-generated method stub
			return  arg0.getNome().compareTo( arg1.getNome());
		}
			
	}

	
}
