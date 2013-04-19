package br.com.senacrs.alp.aulas;

 import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EmpresaTest {
	
	private static Factory factory = Factory.getInstancia();
	private static Random rand = new Random(System.currentTimeMillis());

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testCriarNomeNull() {
		
		exception.expect(IllegalArgumentException.class);
		factory.criarEmpresa(null);
	}

	@Test
	public void testCriar() {
		
		String nome = null;
		Empresa obj = null;
		
		nome = getRandomString(10);
		obj = factory.criarEmpresa(nome);
		Assert.assertNotNull(obj);
	}

	protected static String getRandomString(int tam) {
		
		StringBuilder resultado = null;
		char randomChar = '\0';

		resultado = new StringBuilder();
		for (int i = 0; i < tam; i++) {
			randomChar = randomChar();
			resultado.append(randomChar);
		}

		return resultado.toString();
	}

	private static char randomChar() {
		
		char resultado = '\0';
		
		resultado = (char) (' ' + rand.nextInt('~' - ' ' + 1));

		return resultado;
	}

	@Test
	public void testGetNome() {
		
		String esperado = null;
		String obtido = null;
		Empresa obj = null;
		
		esperado = getRandomString(10);
		obj = factory.criarEmpresa(esperado);
		obtido = obj.getNome();
		Assert.assertEquals(esperado, obtido);
	}

	@Test
	public void testListaDepartamentoOrdemCrescentePorQtdFuncionarios() {
		
		Empresa obj = null;
		List<Departamento> obtido = null;
		List<Departamento> esperado = null;
		Departamento dep = null;		
		
		obj = factory.criarEmpresa(getRandomString(10));
		esperado = new ArrayList<Departamento>();
		for (int i = 20; i > 10; i--) {
			dep = criarDepartamento(obj, i);
			obj.adicionarDepartamento(dep);
			esperado.add(dep);
		}
		obtido = obj.listaDepartamentoOrdemCrescentePorQtdFuncionarios();
		Assert.assertEquals(esperado, obtido);
	}

	private Departamento criarDepartamento(Empresa obj, int qtdFunc) {
		
		Departamento resultado = null;

		resultado = criarDepartamento(obj);
		popularDepartamento(resultado, qtdFunc);

		return resultado;
	}

	private Departamento criarDepartamento(Empresa obj) {
		
		Departamento resultado = null;
		String nome = null;

		nome = getRandomString(10);
		resultado = factory.criarDepartamento(obj, nome);

		return resultado;
	}

	private void popularDepartamento(Departamento dep, int qtdFunc) {
		
		Funcionario func = null;
		
		for (int i = 0; i < qtdFunc; i++) {
			func = criarFuncionario(dep);
			dep.adicionarFuncionario(func);
		}		
	}

	private static Funcionario criarFuncionario(Departamento dep) {
		
		Funcionario resultado = null;
		String nome = null;
		double salario = 0.0;
		
		nome = getRandomString(10);
		salario = rand.nextDouble();
		resultado = factory.criarFuncionario(dep, nome, salario);

		return resultado;
	}

	@Test
	public void testAdicionarDepartamentoNull() {

		Empresa obj = null;

		obj = factory.criarEmpresa(getRandomString(10));
		exception.expect(IllegalArgumentException.class);
		obj.adicionarDepartamento(null);
	}

	@Test
	public void testAdicionarDepartamento() {

		Empresa obj = null;
		List<Departamento> deps = null;
		Departamento dep = null;		

		obj = factory.criarEmpresa(getRandomString(10));
		dep = criarDepartamento(obj);
		obj.adicionarDepartamento(dep);
		deps = obj.listaDepartamentoOrdemCrescentePorQtdFuncionarios();
		Assert.assertTrue(deps.contains(dep));
	}

	@Test
	public void testQuantidadeFuncionarios() {
		
		Empresa obj = null;
		int obtido = 0;
		int esperado = 0;
		Departamento dep = null;		
		int qtd = 0;

		obj = factory.criarEmpresa(getRandomString(10));
		for (int i = 0; i < 10; i++) {
			qtd = rand.nextInt(20);
			dep = criarDepartamento(obj, qtd);
			obj.adicionarDepartamento(dep);
			esperado += qtd;
		}
		obtido = obj.quantidadeFuncionarios();
		Assert.assertEquals(esperado, obtido);
	}

}
