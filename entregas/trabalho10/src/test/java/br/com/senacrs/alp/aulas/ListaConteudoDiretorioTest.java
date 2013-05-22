package br.com.senacrs.alp.aulas;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ListaConteudoDiretorioTest {
	
	private final static String DIRETORIO_ENTRADA = System
			.getProperty("user.dir")
			+ File.separatorChar
			+ "diretorio"
			+ File.separatorChar;
	private final static String PREFIXO_ARQ = "arq";
	private final static String PREFIXO_DIR = "dir";
	private static final Random rand = new Random(System.currentTimeMillis());
	private static final Factory factory = Factory.getInstancia();
	private static ListaConteudoDiretorio obj = null;
	private static File diretorio = null;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		
		diretorio = criarDiretorio();
		obj = factory.criar();
		Assert.assertNotNull(obj);
	}

	private File criarDiretorio() {

		File resultado = null;

		resultado = new File(DIRETORIO_ENTRADA);
		Assert.assertTrue(resultado.mkdirs());
		
		return resultado;
	}

	@After
	public void tearDown() throws Exception {
		
		removeAll(diretorio);
		obj = null;
		diretorio = null;
	}

	private void removeAll(File dir) {

		File[] filhos = null;

		filhos = dir.listFiles();
		if (filhos != null) {
			for (File f : filhos) {
				removeAll(f);
			}
		}
		dir.delete();
	}

	@Test	
	public void testListarConteudoNull() {
		
		exception.expect(IllegalArgumentException.class);
		obj.listarConteudo(null);
	}

	@Test	
	public void testListarConteudoInexistente() throws IOException {
		
		File inexistente = null;
		String nome = null;
		
		exception.expect(IllegalArgumentException.class);
		nome = criarStringAleatoria();
		inexistente = new File(diretorio, nome); 
		obj.listarConteudo(inexistente);
	}

	private String criarStringAleatoria() {

		String resultado = null;

		resultado = RandomStringUtils.randomAlphabetic(tamanhoAleatorio());

		return resultado;
	}

	private int tamanhoAleatorio() {

		return rand.nextInt(10) + 5;
	}

	@Test	
	public void testListarConteudoArquivo() throws IOException {
		
		File arquivo = null;
		
		exception.expect(IllegalArgumentException.class);
		arquivo = criarArquivo();
		obj.listarConteudo(arquivo);
	}

	private File criarArquivo() throws IOException {
	
		File resultado = null;
		String nome = null;
	
		nome = criarStringAleatoria();
		resultado = criarArquivo(nome);
		
		return resultado;
	}

	private File criarArquivo(String nome) throws IOException {
		
		File resultado = null;
		resultado = new File(diretorio, nome);
		Assert.assertTrue(resultado.createNewFile());
		
		return resultado;
	}

	@Test	
	public void testListarConteudoDiretorioSemPermissao() throws IOException {
		
		File dir = null;
		
		exception.expect(IllegalArgumentException.class);
		dir = criarSubDiretorio();
		if (!dir.setReadable(false)) {
			throw new IllegalArgumentException();
		}
		obj.listarConteudo(dir);
	}

	private File criarSubDiretorio() throws IOException {
	
		File resultado = null;
		String nome = null;
	
		nome = criarStringAleatoria();
		resultado = criarSubDiretorio(nome);
		
		return resultado;
	}

	private File criarSubDiretorio(String nome) {
		File resultado = null;
		
		resultado = new File(diretorio, nome);
		Assert.assertTrue(resultado.mkdirs());
		
		return resultado;
	}

	@Test	
	public void testListarConteudoVazio() throws IOException {
		
		String[] resultado = null;
		
		resultado = obj.listarConteudo(diretorio);
		Assert.assertNotNull(resultado);
		Assert.assertEquals(0, resultado.length);
	}

	@Test	
	public void testListarConteudoApenasUmArquivo() throws IOException {
		
		String[] resultado = null;
		String esperado = null;
		File arq = null;
		boolean canSetExecutable = false;
		
		arq = criarArquivo();
		Assert.assertTrue(arq.setReadable(true));
		Assert.assertTrue(arq.setWritable(false));
		canSetExecutable = arq.setExecutable(false);
		esperado = "" + ListaConteudoDiretorio.NADA_CONSTA + ListaConteudoDiretorio.SEPARADOR
				+ ListaConteudoDiretorio.PERMISSAO_LEITURA 
				+ ListaConteudoDiretorio.NADA_CONSTA
				+ (canSetExecutable ? ListaConteudoDiretorio.NADA_CONSTA : ListaConteudoDiretorio.PERMISSAO_EXECUCAO) + ListaConteudoDiretorio.SEPARADOR
				+ arq.length() + ListaConteudoDiretorio.SEPARADOR
				+ arq.getName();
		resultado = obj.listarConteudo(diretorio);
		Assert.assertEquals(1, resultado.length);
		Assert.assertEquals(esperado, resultado[0]);
	}

	@Test	
	public void testListarConteudoApenasUmDiretorio() throws IOException {
		
		String[] resultado = null;
		String esperado = null;
		File arq = null;
		boolean canSetExecutable = false;
		
		arq = criarSubDiretorio();
		canSetExecutable = arq.setExecutable(false);
		esperado = "" + ListaConteudoDiretorio.IDENTIFICA_DIRETORIO + ListaConteudoDiretorio.SEPARADOR
				+ ListaConteudoDiretorio.PERMISSAO_LEITURA
				+ ListaConteudoDiretorio.PERMISSAO_ESCRITA
				+ (canSetExecutable ? ListaConteudoDiretorio.NADA_CONSTA : ListaConteudoDiretorio.PERMISSAO_EXECUCAO) + ListaConteudoDiretorio.SEPARADOR
				+ arq.length() + ListaConteudoDiretorio.SEPARADOR
				+ arq.getName();
		resultado = obj.listarConteudo(diretorio);
		Assert.assertEquals(1, resultado.length);
		Assert.assertEquals(esperado, resultado[0]);
	}

	@Test	
	public void testListarConteudo() throws IOException {
		
		String[] resultado = null;
		String[] esperado = null;
		
		esperado = criarVariosDiretoriosArquivos();
		resultado = obj.listarConteudo(diretorio);
		Assert.assertArrayEquals(esperado, resultado);
	}

	private String[] criarVariosDiretoriosArquivos() throws IOException {
		
		String[] resultado;
		int qtd = 0;
		File file = null;

		qtd = tamanhoAleatorio();
		resultado = new String[2 * qtd];
		for (int i = qtd; i < 2 * qtd; i++) {
			file = criarArquivo(PREFIXO_ARQ + (char) ('A' + i));
			resultado[i] = "" + ListaConteudoDiretorio.NADA_CONSTA + ListaConteudoDiretorio.SEPARADOR
					+ ListaConteudoDiretorio.PERMISSAO_LEITURA
					+ ListaConteudoDiretorio.PERMISSAO_ESCRITA
					+ (file.canExecute() ? ListaConteudoDiretorio.PERMISSAO_EXECUCAO : ListaConteudoDiretorio.NADA_CONSTA) + ListaConteudoDiretorio.SEPARADOR
					+ String.valueOf(file.length()) + ListaConteudoDiretorio.SEPARADOR
					+ file.getName();
		}
		for (int i = 0; i < qtd; i++) {
			file = criarSubDiretorio(PREFIXO_DIR + (char) ('A' + i));
			resultado[i] = "" + ListaConteudoDiretorio.IDENTIFICA_DIRETORIO + ListaConteudoDiretorio.SEPARADOR
					+ ListaConteudoDiretorio.PERMISSAO_LEITURA
					+ ListaConteudoDiretorio.PERMISSAO_ESCRITA
					+ (file.canExecute() ? ListaConteudoDiretorio.PERMISSAO_EXECUCAO : ListaConteudoDiretorio.NADA_CONSTA) + ListaConteudoDiretorio.SEPARADOR
					+ String.valueOf(file.length()) + ListaConteudoDiretorio.SEPARADOR
					+ file.getName();
		}
		
		return resultado;
	}
}
