package br.com.senacrs.alp.aulas;

import java.util.Random;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FuncionarioTest {
	
	private static Factory factory = Factory.getInstancia();
	private static Random rand = new Random(System.currentTimeMillis());
	
	private Departamento departamento = null;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() throws Exception {

		String nome = null;
		Empresa empresa = null;
		
		nome = EmpresaTest.getRandomString(10);
		empresa = criaEmpresa();
		departamento = factory.criarDepartamento(empresa, nome);
		Assert.assertNotNull(departamento);
	}

	private Empresa criaEmpresa() {
		
		Empresa resultado = null;
		String nome = null;

		nome = EmpresaTest.getRandomString(10);
		resultado = factory.criarEmpresa(nome);

		return resultado;
	}


	@After
	public void tearDown() throws Exception {
		
		departamento = null;
	}

	@Test
	public void testCriaDepartamentoNull() {
		
		String nome = null;
		double salario = 0.0;
		
		nome = EmpresaTest.getRandomString(10);
		salario = 1.0 + rand.nextDouble();
		exception.expect(IllegalArgumentException.class);
		factory.criarFuncionario(null, nome, salario);
	}

	@Test
	public void testCriaNomeNull() {
		
		double salario = 0.0;
		
		salario = 1.0 + rand.nextDouble();
		exception.expect(IllegalArgumentException.class);
		factory.criarFuncionario(departamento, null, salario);
	}

	@Test
	public void testCriaSalarionMenorQueZero() {
		
		String nome = null;
		
		nome = EmpresaTest.getRandomString(10);
		exception.expect(IllegalArgumentException.class);
		factory.criarFuncionario(departamento, nome, -1.0);
	}

	@Test
	public void testGetEmpresa() {
		
		Empresa esperado = null;
		Empresa obtido = null;
		Funcionario obj = null;
		
		esperado = departamento.getEmpresa();
		obj = criarFuncionario();
		obtido = obj.getEmpresa();
		Assert.assertEquals(esperado, obtido);
	}

	private Funcionario criarFuncionario() {
		
		Funcionario resultado = null;
		String nome = null;
		double salario = 0.0;
		
		nome = EmpresaTest.getRandomString(10);
		salario = 1.0 + rand.nextDouble();		
		resultado = factory.criarFuncionario(departamento, nome, salario);
		
		return resultado;
	}

	@Test
	public void testGetDepartamento() {
		
		Departamento esperado = null;
		Departamento obtido = null;
		Funcionario obj = null;
		
		esperado = departamento;
		obj = criarFuncionario();
		obtido = obj.getDepartamento();
		Assert.assertEquals(esperado, obtido);
	}

	@Test
	public void testGetNome() {
		
		String esperado = null;
		String obtido = null;
		Funcionario obj = null;
		
		esperado = EmpresaTest.getRandomString(10);
		obj = criarFuncionario(esperado);
		obtido = obj.getNome();
		Assert.assertEquals(esperado, obtido);
	}

	private Funcionario criarFuncionario(String nome) {
		
		Funcionario resultado = null;
		double salario = 0.0;
		
		salario = 1.0 + rand.nextDouble();		
		resultado = factory.criarFuncionario(departamento, nome, salario);
		
		return resultado;
	}

	@Test
	public void testGetSalario() {
		
		double esperado = 0.0;
		double obtido = 0.0;
		Funcionario obj = null;
		
		esperado = 1.0 + rand.nextDouble();
		obj = criarFuncionario(esperado);
		obtido = obj.getSalario();
		Assert.assertEquals(esperado, obtido, 0.1);
	}

	private Funcionario criarFuncionario(double salario) {
		
		Funcionario resultado = null;
		String nome = null;
		
		nome = EmpresaTest.getRandomString(10);
		resultado = factory.criarFuncionario(departamento, nome, salario);
		
		return resultado;
	}

}
