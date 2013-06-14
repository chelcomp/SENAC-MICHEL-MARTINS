package br.com.senacrs.alp.aulas;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ObterPathRequisicaoGetTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private static final Factory factory = Factory.getInstancia();
	private ObterPathRequisicaoGet obj = null;
	private static String PWD = System.getProperty("user.dir");

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
	public void testCaminhoAbsolutoNull() {
		exception.expect(IllegalArgumentException.class);
		obj.caminhoAbsoluto(null);
		
	}

	@Test
	public void testCaminhoAbsolutoBarra() {

		String resultado = null;
		Reader requisicao = null;
		String path = null;
		String abs = null;

		path = "/";
		abs = PWD + path.replace("/", File.separator);
		requisicao = getRequisicao(path);
		resultado = obj.caminhoAbsoluto(requisicao);
		Assert.assertEquals(path, abs, resultado);
	}

	private Reader getRequisicao(String path) {

		StringReader resultado = null;
		StringBuilder builder = null;
		String sequencia = null;

		builder = new StringBuilder();
		builder.append("GET " + path + " http/1.1");
		builder.append(ObterPathRequisicaoGet.NOVA_LINHA);
		builder.append("Host: www.google.com");
		builder.append(ObterPathRequisicaoGet.NOVA_LINHA);
		sequencia = builder.toString();
		resultado = new StringReader(sequencia);

		return resultado;
	}

	@Test
	public void testEhRequisicaoGetValidaVazia() {

		String resultado = null;
		Reader requisicao = null;
		String path = null;

		path = "";
		requisicao = getRequisicao(path);
		resultado = obj.caminhoAbsoluto(requisicao);
		Assert.assertNull(path, resultado);
	}

	@Test
	public void testCaminhoAbsolutoSemBarra() {

		String resultado = null;
		Reader requisicao = null;
		String path = null;

		path = "algo";
		requisicao = getRequisicao(path);
		resultado = obj.caminhoAbsoluto(requisicao);
		Assert.assertNull(path, resultado);
	}

	@Test
	public void testCaminhoAbsolutoAlgo() {

		String resultado = null;
		Reader requisicao = null;
		String path = null;
		String abs = null;

		path = "/algo";
		abs = PWD + path.replace("/", File.separator);
		requisicao = getRequisicao(path);
		resultado = obj.caminhoAbsoluto(requisicao);
		Assert.assertEquals(path, abs, resultado);
	}
}
