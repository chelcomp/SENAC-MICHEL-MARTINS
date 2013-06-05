package br.com.senacrs.alp.aulas;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

public class ConfiguracaoTest {
	
	private static class MeuArquivoConfiguracao implements ArquivoConfiguracao {
		
		public String rootDir = null;
		public String errorDir = null;
		public int port = -1;

		@Override
		public String getRootDir() {

			return this.rootDir;
		}

		@Override
		public String getErrorDir() {

			return this.errorDir;
		}

		@Override
		public int getPort() {

			return this.port;
		}
		
		@Override
		public String toString() {

			return "r:[" + getRootDir() + "] e:[" + getErrorDir() + "] p:[" + port + "]";
		}
		
		@Override
		public boolean equals(Object obj) {
			
			boolean resultado = false;
			ArquivoConfiguracao casted = null;
			
			resultado = obj instanceof ArquivoConfiguracao;
			if (resultado) {
				casted = (ArquivoConfiguracao) obj;
				resultado = casted.getRootDir().equals(rootDir)
						&& casted.getErrorDir().equals(errorDir)
						&& (casted.getPort() == port);
			}

			return resultado;
		}
	}

	private final static String ROOT_DIR = "./html";
	private final static String ROOT_DIR_NOK = "./html_inexistente";
	private final static String ERROR_DIR = "./html/error/";
	private final static String ERROR_DIR_NOK = "./html/error_empty/";
	private final static int PORT = 54321;
	private static final Factory factory = Factory.getInstancia();

	@Test
	public void testConfiguracaoNull() {

		try {
			criarConfiguracao(null);
			fail("Deveria ter abortado");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(true);
		}
	}

	private Configuracao criarConfiguracao(ArquivoConfiguracao config) {

		return factory.criarConfiguracao(config);
	}

	@Test
	public void testArquivoConfiguracao() throws Exception {
		
		Configuracao obj = null;
		MeuArquivoConfiguracao config = null;
		
		config = criarArquivoConfiguracao();
		obj = criarConfiguracao(config);
		Assert.assertNotNull(obj);
		Assert.assertNotNull(obj.getArquivoConfiguracao());
		Assert.assertTrue(config.toString(), config.equals(obj.getArquivoConfiguracao()));
	}

	@Test
	public void testValido() throws Exception {
		
		Configuracao obj = null;
		MeuArquivoConfiguracao config = null;
		
		config = criarArquivoConfiguracao();
		obj = criarConfiguracao(config);
		Assert.assertNotNull(obj);
		Assert.assertTrue(config.toString(), obj.valido());
	}

	private MeuArquivoConfiguracao criarArquivoConfiguracao() {
		
		MeuArquivoConfiguracao resultado = null;
		
		resultado = new MeuArquivoConfiguracao();
		resultado.rootDir = ROOT_DIR;
		resultado.errorDir = ERROR_DIR;
		resultado.port = PORT;
	
		return resultado;
	}

	@Test
	public void testRootDirInexistente() throws Exception {
		
		Configuracao obj = null;
		MeuArquivoConfiguracao config = null;
		
		config = criarArquivoConfiguracao();
		config.rootDir = ROOT_DIR_NOK; 
		obj = criarConfiguracao(config);
		Assert.assertNotNull(obj);
		Assert.assertFalse(config.toString(), obj.valido());
	}

	@Test
	public void testErrorDirInexistente() throws Exception {
		
		Configuracao obj = null;
		MeuArquivoConfiguracao config = null;
		
		config = criarArquivoConfiguracao();
		config.errorDir = ERROR_DIR_NOK; 
		obj = criarConfiguracao(config);
		Assert.assertNotNull(obj);
		Assert.assertFalse(config.toString(), obj.valido());
	}

	@Test
	public void testPortNegativo() throws Exception {
		
		Configuracao obj = null;
		MeuArquivoConfiguracao config = null;
		
		config = criarArquivoConfiguracao();
		config.port = -1; 
		obj = criarConfiguracao(config);
		Assert.assertNotNull(obj);
		Assert.assertFalse(config.toString(), obj.valido());
	}

	@Test
	public void testPortAbaixo() throws Exception {
		
		Configuracao obj = null;
		MeuArquivoConfiguracao config = null;
		
		config = criarArquivoConfiguracao();
		config.port = 1023; 
		obj = criarConfiguracao(config);
		Assert.assertNotNull(obj);
		Assert.assertFalse(config.toString(), obj.valido());
	}

	@Test
	public void testPortAcima() throws Exception {
		
		Configuracao obj = null;
		MeuArquivoConfiguracao config = null;
		
		config = criarArquivoConfiguracao();
		config.port = 65536; 
		obj = criarConfiguracao(config);
		Assert.assertNotNull(obj);
		Assert.assertFalse(obj.valido());
	}
}
