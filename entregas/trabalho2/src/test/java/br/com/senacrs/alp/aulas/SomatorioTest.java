package br.com.senacrs.alp.aulas;

import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.senacrs.alp.aulas.Somatorio;


public class SomatorioTest {
	
	private Somatorio obj = null;

	@Before
	public void setUp() throws Exception {

		obj = Somatorio.getInstancia();
	}

	@After
	public void tearDown() throws Exception {

		obj = null;
	}

	@Test
	public void testSomaResultadoArgumentoNulo() {

		double[] valores = null;		
		double esperado = 0.0;
		double resultado = 0.0;
		int comparacao = 0;
		String msg = null;

		//preparacao do ambiente
		esperado = Double.NaN;
		resultado = this.obj.somaTotal(valores);
		msg = String.format("Quando o argumento for nulo deve retornar %f, valor retornado %f", esperado, resultado);
		//teste
		comparacao = Double.compare(resultado, esperado);
		Assert.assertEquals(msg, comparacao, 0);
		
	}

	@Test
	public void testSomaResultadoArgumentoVazio() {

		double[] valores = null;		
		double esperado = 0.0;
		double resultado = 0.0;
		int comparacao = 0;
		String msg = null;
		
		//preparacao do ambiente
		valores = new double[0];
		resultado = this.obj.somaTotal(valores);
		msg = String.format("Quando o argumento for vazio deve retornar %f, valor retornando %f", esperado, resultado);
		//teste
		comparacao = Double.compare(resultado, esperado);
		Assert.assertEquals(msg, comparacao, 0);
		
	}

	@Test
	public void testSomaResultadoArgumentoArrayUnitario() {

		double[] valores = null;		
		double esperado = 0.0;
		double resultado = 0.0;
		int comparacao = 0;
		String msg = null;
		
		//preparacao do ambiente
		valores = new double[1];
		valores[0] = Math.random();
		esperado = valores[0];
		resultado = this.obj.somaTotal(valores);
		msg = String.format("Esperado valor %f , mas retornado valor %f", esperado, resultado);
		//teste
		comparacao = Double.compare(resultado, esperado);
		Assert.assertEquals(msg, comparacao, 0);		
	}

	@Test
	public void testSomaResultadoArgumentoArray() {

		double[] valores = null;		
		double esperado = 0.0;
		double resultado = 0.0;
		int comparacao = 0;
		String msg = null;
		
		//preparacao do ambiente
		valores = new double[10];
		for (int i = 0; i < valores.length; i++) {
			double val = Math.random();
			valores[i] = val;
			esperado += val;
		}
		resultado = this.obj.somaTotal(valores);
		msg = String.format("Esperado valor %f , mas retornado valor %f, array completo %s", esperado, resultado, Arrays.toString(valores));
		//teste
		comparacao = Double.compare(resultado, esperado);
		Assert.assertEquals(msg, comparacao, 0);
		
	}
}
