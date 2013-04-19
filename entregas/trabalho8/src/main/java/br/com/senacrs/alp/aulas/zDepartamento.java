package br.com.senacrs.alp.aulas;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;



public class zDepartamento implements Departamento {

	private List<Funcionario> listaFuncionarios;
	private String nome;
	private Empresa empresa;
	
	public zDepartamento(Empresa empresa, String nome)
	{
		if(empresa == null || nome == null)
			throw new IllegalArgumentException();
		
		this.empresa = empresa;
		this.nome = nome;		
		
		listaFuncionarios = new LinkedList<Funcionario>();
	}
	
	@Override
	public Empresa getEmpresa() {
		// TODO Auto-generated method stub
		return empresa;
	}

	@Override
	public String getNome() {
		// TODO Auto-generated method stub
		return nome;
	}

	@Override
	public List<Funcionario> listarFuncionariosPorOrdemAlfabetica() {
		zFuncionario.SortPorOrdemAlfabetica o = new zFuncionario.SortPorOrdemAlfabetica();
		Collections.sort(listaFuncionarios,o);
		return listaFuncionarios;
	}

	@Override
	public List<Funcionario> listarFuncionariosPorDecrescenteSalario() {
		zFuncionario.SortPorDecrescenteSalario o = new zFuncionario.SortPorDecrescenteSalario();
		Collections.sort(listaFuncionarios,o);
		return listaFuncionarios;
	}

	@Override
	public int quantidadeFuncionarios() {
		// TODO Auto-generated method stub
		return listaFuncionarios.size();
	}

	@Override
	public void adicionarFuncionario(Funcionario funcionario) {
		if(funcionario == null)
			throw new IllegalArgumentException();
		listaFuncionarios.add(funcionario);
	}
	
	public static class SortOrdemCrescentePorQtdFuncionarios implements Comparator<Departamento>
	{
		@Override
		public int compare(Departamento arg0, Departamento arg1) {
			
			return arg1.quantidadeFuncionarios() - arg0.quantidadeFuncionarios();
		}		
	}

	
}
