package br.com.senacrs.alp.aulas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DepartamentoTest {

	private static Factory factory = Factory.getInstancia();
	private static Random rand = new Random(System.currentTimeMillis());
	private Empresa empresa = null;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() throws Exception {

		String nome = null;
		
		nome = EmpresaTest.getRandomString(10);
		empresa = factory.criarEmpresa(nome);
		Assert.assertNotNull(empresa);
	}

	@After
	public void tearDown() throws Exception {
		
		empresa = null;
	}

	@Test
	public void testCriarEmpresaNull() {
		
		String nome = null;
		
		nome = EmpresaTest.getRandomString(10);
		exception.expect(IllegalArgumentException.class);
		factory.criarDepartamento(null, nome);
	}

	@Test
	public void testCriarNomeNull() {
		
		exception.expect(IllegalArgumentException.class);
		factory.criarDepartamento(empresa, null);
	}

	@Test
	public void testGetEmpresa() {
		
		Departamento obj = null;
		Empresa obtido = null;
		String nome = null;
		
		nome = EmpresaTest.getRandomString(10);				
		obj = factory.criarDepartamento(empresa, nome);
		obtido = obj.getEmpresa();
		Assert.assertEquals(empresa, obtido);
	}

	@Test
	public void testGetNome() {
		
		Departamento obj = null;
		String obtido = null;
		String nome = null;
		
		nome = EmpresaTest.getRandomString(10);				
		obj = factory.criarDepartamento(empresa, nome);
		obtido = obj.getNome();
		Assert.assertEquals(nome, obtido);
	}

	@Test
	public void testListarFuncionariosPorOrdemAlfabetica() {
		
		Departamento obj = null;
		String nome = null;
		Funcionario func = null;
		List<Funcionario> esperado = null;
		List<Funcionario> obtido = null;
		
		nome = EmpresaTest.getRandomString(10);				
		obj = factory.criarDepartamento(empresa, nome);
		esperado = new ArrayList<Funcionario>();
		for (char c = 'Z'; c >= 'A'; c--) {
			func = criarFuncionario(obj, String.valueOf(c));
			obj.adicionarFuncionario(func);		
			esperado.add(0, func);
		}
		obtido = obj.listarFuncionariosPorOrdemAlfabetica();
		Assert.assertEquals(esperado, obtido);
	}

	private Funcionario criarFuncionario(Departamento obj, String nome) {
		
		Funcionario resultado = null;
		double salario = 0.0;
		
		salario = 1.0 + rand.nextDouble();
		resultado = factory.criarFuncionario(obj, nome, salario);				

		return resultado;
	}

	@Test
	public void testListarFuncionariosPorDecrescenteSalario() {
		
		Departamento obj = null;
		String nome = null;
		Funcionario func = null;
		List<Funcionario> esperado = null;
		List<Funcionario> obtido = null;
		
		nome = EmpresaTest.getRandomString(10);				
		obj = factory.criarDepartamento(empresa, nome);
		esperado = new ArrayList<Funcionario>();
		for (int i = 20; i > 10; i--) {
			func = criarFuncionario(obj, Double.valueOf(i));
			obj.adicionarFuncionario(func);		
			esperado.add(func);
		}
		obtido = obj.listarFuncionariosPorDecrescenteSalario();
		Assert.assertEquals(esperado, obtido);
	}

	private Funcionario criarFuncionario(Departamento obj, double salario) {
		
		Funcionario resultado = null;
		String nome = null;
		
		nome = EmpresaTest.getRandomString(10);
		resultado = factory.criarFuncionario(obj, nome, salario);				

		return resultado;
	}

	@Test
	public void testQuantidadeFuncionarios() {
		
		Departamento obj = null;
		String nome = null;
		Funcionario func = null;
		List<Funcionario> lista = null;
		int esperado = 0;
		
		nome = EmpresaTest.getRandomString(10);				
		obj = factory.criarDepartamento(empresa, nome);
		esperado = rand.nextInt(10) + 2;
		for (int i = 0; i < esperado; i++) {
			func = criarFuncionario(obj);
			obj.adicionarFuncionario(func);			
		}
		lista = obj.listarFuncionariosPorOrdemAlfabetica();
		Assert.assertEquals(esperado, lista.size());		
		lista = obj.listarFuncionariosPorDecrescenteSalario();
		Assert.assertEquals(esperado, lista.size());		
	}

	private Funcionario criarFuncionario(Departamento obj) {
		
		Funcionario resultado = null;
		String nome = null;
		
		nome = EmpresaTest.getRandomString(10);
		resultado = criarFuncionario(obj, nome);				

		return resultado;
	}

	@Test
	public void testAdicionarFuncionarioNull() {
		
		Departamento obj = null;
		String nome = null;
		
		nome = EmpresaTest.getRandomString(10);				
		obj = factory.criarDepartamento(empresa, nome);
		exception.expect(IllegalArgumentException.class);
		obj.adicionarFuncionario(null);
	}

	@Test
	public void testAdicionarFuncionario() {
		
		Departamento obj = null;
		String nome = null;
		Funcionario func = null;
		List<Funcionario> lista = null;
		
		nome = EmpresaTest.getRandomString(10);				
		obj = factory.criarDepartamento(empresa, nome);
		func = criarFuncionario(obj);
		obj.adicionarFuncionario(func);
		lista = obj.listarFuncionariosPorOrdemAlfabetica();
		Assert.assertTrue(lista.contains(func));		
	}

}
