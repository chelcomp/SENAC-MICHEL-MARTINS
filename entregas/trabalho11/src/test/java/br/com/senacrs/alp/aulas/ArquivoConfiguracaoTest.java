package br.com.senacrs.alp.aulas;

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class ArquivoConfiguracaoTest {

	private final static String DIRETORIO_ENTRADA = System
			.getProperty("user.dir")
			+ File.separatorChar
			+ "config"
			+ File.separatorChar;
	private final static String NOME_ARQUIVO_OK = "config_ok.txt";
	private final static String[] NOME_ARQUIVO_NOK = new String[] {
			"config_nok_incompleto.txt", 
			"config_nok_root.txt", 
			"config_nok_port.txt",
			"config_nok_error.txt", 
	};
	private final static String ROOT_DIR = "./html";
	private final static String ERROR_DIR = "./html/error/";
	private final static int PORT = 54321;
	private static final Factory factory = Factory.getInstancia();

	@Test
	public void testArquivoConfiguracaoNull() {

		try {
			criarArquivoConfiguracao(null);
			fail("Deveria ter abortado");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(true);
		}
	}

	private ArquivoConfiguracao criarArquivoConfiguracao(String arquivo) {

		return factory.criarArquivoConfiguracao(arquivo);
	}

	@Test
	public void testArquivoConfiguracaoDiretorio() {

		try {
			criarArquivoConfiguracao(DIRETORIO_ENTRADA);
			fail("Deveria ter abortado");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testArquivoConfiguracaoInexistente() {

		try {
			criarArquivoConfiguracao(nomeCompleto(NOME_ARQUIVO_OK)
					+ ".nao_existe");
			fail("Deveria ter abortado");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(true);
		}
	}

	private String nomeCompleto(String nomeArq) {

		return DIRETORIO_ENTRADA + nomeArq;
	}

	@Test
	public void testArquivoConfiguracaoFormatoIncorreto() {

		for (String arq : NOME_ARQUIVO_NOK) {
			try {
				criarArquivoConfiguracao(nomeCompleto(arq));
				fail("Deveria ter abortado: " + arq);
			} catch (IllegalArgumentException e) {
				Assert.assertTrue(true);
			}
		}
	}

	@Test
	public void testArquivoConfiguracaoFormatoCorreto() {

		ArquivoConfiguracao obj = null;
		
		obj = criarArquivoConfiguracao(nomeCompleto(NOME_ARQUIVO_OK));
		Assert.assertNotNull(obj);
		Assert.assertEquals(ROOT_DIR, obj.getRootDir());
		Assert.assertEquals(ERROR_DIR, obj.getErrorDir());
		Assert.assertEquals(PORT, obj.getPort());
	}
}
