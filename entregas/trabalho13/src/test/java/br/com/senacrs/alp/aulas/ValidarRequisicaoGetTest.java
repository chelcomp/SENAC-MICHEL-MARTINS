package br.com.senacrs.alp.aulas;

import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ValidarRequisicaoGetTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private static final Factory factory = Factory.getInstancia();
	private ValidarRequisicaoGet obj = null;
	private static String[] REQUISICAO = new String[] {
			"GET / http/1.1" + ValidarRequisicaoGet.NOVA_LINHA,
			"Host: www.google.com" + ValidarRequisicaoGet.NOVA_LINHA, };

	@Before
	public void setUp() throws Exception {

		obj = factory.criarValidacao();
		Assert.assertNotNull(obj);
	}

	@After
	public void tearDown() throws Exception {

		obj = null;
	}

	@Test
	public void testEhRequisicaoGetValidaNullRequisicao() {

		exception.expect(IllegalArgumentException.class);
		obj.ehRequisicaoGetValida(null);
		
	}

	@Test
	public void testEhRequisicaoGetValidaVazia() {

		boolean resultado = false;
		Reader requisicao = null;
		String req = null;

		req = "";
		requisicao = getStringInputStream(req);
		resultado = obj.ehRequisicaoGetValida(requisicao);
		Assert.assertFalse(req, resultado);
	}

	private Reader getStringInputStream(String... entrada) {

		StringReader resultado = null;
		StringBuilder builder = null;
		String sequencia = null;

		builder = new StringBuilder();
		for (String s : entrada) {
			builder.append(s);
		}
		sequencia = builder.toString();
		resultado = new StringReader(sequencia);

		return resultado;
	}

	@Test
	public void testEhRequisicaoGetValidaSemHost() {

		boolean resultado = false;
		Reader requisicao = null;
		String req = null;

		req = REQUISICAO[0];
		requisicao = getStringInputStream(req);
		resultado = obj.ehRequisicaoGetValida(requisicao);
		Assert.assertFalse(req, resultado);
	}

	@Test
	public void testEhRequisicaoGetValidaSemGet() {

		boolean resultado = false;
		Reader requisicao = null;
		String req = null;

		req = REQUISICAO[1];
		requisicao = getStringInputStream(req);
		resultado = obj.ehRequisicaoGetValida(requisicao);
		Assert.assertFalse(req, resultado);
	}

	@Test
	public void testEhRequisicaoGetValidaSemNovaLinhaGet() {

		boolean resultado = false;
		Reader requisicao = null;
		String[] req = null;

		req = new String[] { "GET / http/1.1", REQUISICAO[1], };
		requisicao = getStringInputStream(req);
		resultado = obj.ehRequisicaoGetValida(requisicao);
		Assert.assertFalse(Arrays.toString(req), resultado);
	}

	@Test
	public void testEhRequisicaoGetValidaSemNovaLinhaHost() {

		boolean resultado = false;
		Reader requisicao = null;
		String[] req = null;

		req = new String[] { REQUISICAO[0], "Host: www.google.com", };
		requisicao = getStringInputStream(req);
		resultado = obj.ehRequisicaoGetValida(requisicao);
		Assert.assertFalse(Arrays.toString(req), resultado);
	}

	@Test
	public void testEhRequisicaoGetValidaSemHostCompleto() {

		boolean resultado = false;
		Reader requisicao = null;
		String[] req = null;

		req = new String[] { REQUISICAO[0],
				"Host: " + ValidarRequisicaoGet.NOVA_LINHA, };
		requisicao = getStringInputStream(req);
		resultado = obj.ehRequisicaoGetValida(requisicao);
		Assert.assertFalse(Arrays.toString(req), resultado);
	}

	@Test
	public void testEhRequisicaoGetValidaGetSemGet() {

		boolean resultado = false;
		Reader requisicao = null;
		String[] req = null;

		req = new String[] {
				"/ http/1.1" + ValidarRequisicaoGet.NOVA_LINHA,
				REQUISICAO[1], };
		requisicao = getStringInputStream(req);
		resultado = obj.ehRequisicaoGetValida(requisicao);
		Assert.assertFalse(Arrays.toString(req), resultado);
	}

	@Test
	public void testEhRequisicaoGetValidaGetSemPath() {

		boolean resultado = false;
		Reader requisicao = null;
		String[] req = null;

		req = new String[] {
				"GET http/1.1" + ValidarRequisicaoGet.NOVA_LINHA,
				REQUISICAO[1], };
		requisicao = getStringInputStream(req);
		resultado = obj.ehRequisicaoGetValida(requisicao);
		Assert.assertFalse(Arrays.toString(req), resultado);
	}

	@Test
	public void testEhRequisicaoGetValidaPathSemBarra() {

		boolean resultado = false;
		Reader requisicao = null;
		String[] req = null;

		req = new String[] {
				"GET algo http/1.1" + ValidarRequisicaoGet.NOVA_LINHA,
				REQUISICAO[1], };
		requisicao = getStringInputStream(req);
		resultado = obj.ehRequisicaoGetValida(requisicao);
		Assert.assertFalse(Arrays.toString(req), resultado);
	}

	@Test
	public void testEhRequisicaoGetValidaGetSemHttp() {

		boolean resultado = false;
		Reader requisicao = null;
		String[] req = null;

		req = new String[] {
				"GET /" + ValidarRequisicaoGet.NOVA_LINHA,
				REQUISICAO[1], };
		requisicao = getStringInputStream(req);
		resultado = obj.ehRequisicaoGetValida(requisicao);
		Assert.assertFalse(Arrays.toString(req), resultado);
	}

	@Test
	public void testEhRequisicaoGetValida() {

		boolean resultado = false;
		Reader requisicao = null;

		requisicao = getStringInputStream(REQUISICAO);
		resultado = obj.ehRequisicaoGetValida(requisicao);
		Assert.assertTrue(Arrays.toString(REQUISICAO), resultado);
	}
}
