package br.com.senacrs.alp.aulas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class LeitorArquivosTest {

	private final static String DIRETORIO_ENTRADA = System
			.getProperty("user.dir")
			+ File.separatorChar
			+ "diretorio"
			+ File.separatorChar;
	private final static String NOME_ARQUIVO_ENTRADA = "arquivo.txt";
	private final static String ARQUIVO_ENTRADA = DIRETORIO_ENTRADA
			+ NOME_ARQUIVO_ENTRADA;
	private static final String NL = System.getProperty("line.separator");
	private static final Random rand = new Random(System.currentTimeMillis());
	private static final Factory factory = Factory.getInstancia();
	private LeitorArquivos obj = null;

	@Before
	public void setUp() throws Exception {

		criarDiretorio();
		obj = factory.criarLeitor();
		Assert.assertNotNull(obj);
	}

	private void criarDiretorio() {

		File dir = null;

		dir = new File(DIRETORIO_ENTRADA);
		dir.mkdirs();
	}

	@After
	public void tearDown() throws Exception {

		limparDiretorio();
		obj = null;
	}

	private void limparDiretorio() {
		File dir = null;

		dir = new File(DIRETORIO_ENTRADA);
		//removeAll(dir);
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
	public void testLerArquivo() {

		String[] conteudo = null;
		String[] resultado = null;

		conteudo = criarConteudo();
		escreverConteudo(ARQUIVO_ENTRADA, conteudo);
		resultado = obj.lerArquivo(ARQUIVO_ENTRADA);
		Assert.assertArrayEquals(conteudo, resultado);
	}

	private String[] criarConteudo() {

		String[] resultado = null;
		int total = 0;

		total = tamanhoAleatorio();
		resultado = new String[total];
		for (int i = 0; i < total; i++) {
			resultado[i] = criarStringAleatoria();
		}

		return resultado;
	}

	private int tamanhoAleatorio() {

		return rand.nextInt(100) + 10;
	}

	private String criarStringAleatoria() {

		String resultado = null;

		resultado = RandomStringUtils.randomAlphabetic(tamanhoAleatorio());

		return resultado;
	}

	private void escreverConteudo(String arquivoEntrada, String[] conteudo) {

		Writer saida = null;
		Writer arqWriter = null;
		File arq = null;

		try {
			arq = new File(arquivoEntrada);
			arqWriter = new FileWriter(arq);
			saida = new BufferedWriter(arqWriter);
			for (String s : conteudo) {
				saida.write(s);
				saida.write(NL);
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} finally {
			try {
				saida.close();
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	
	@Test
	public void testLerArquivoComSubstituicao() {

		String[] conteudo = null;
		String[] esperado = null;
		String[] resultado = null;
		String busca = null;
		String substituicao = null;
		String prefixo = null;
		String sufixo = null;

		busca = "Alguma";
		substituicao = "Coisa";
		prefixo = criarStringAleatoria();
		sufixo = criarStringAleatoria();
		conteudo = new String[] {
				 prefixo + busca + sufixo				
		};
		esperado = new String[] {
				 prefixo + substituicao + sufixo				
		};
		escreverConteudo(ARQUIVO_ENTRADA, conteudo);
		resultado = obj.lerArquivoComSubstituicao(ARQUIVO_ENTRADA, busca, substituicao);
		Assert.assertArrayEquals(esperado, resultado);
	}
}
