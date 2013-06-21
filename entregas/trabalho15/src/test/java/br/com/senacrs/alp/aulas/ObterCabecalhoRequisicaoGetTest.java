package br.com.senacrs.alp.aulas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ObterCabecalhoRequisicaoGetTest {

	private static final String HTTP_1_0_200_OK = "HTTP/1.0 200 OK";
	private static final String HTTP_1_0_404_NOT_FOUND = "HTTP/1.0 404 NotFound";
	private static final String PWD = System.getProperty("user.dir");
	private static final String ERROR_404_HTML = "error_404.html";
	private static final String INDEX_HTML = "index.html";
	private static final String TEMP_DIR = "tmp";
	private static final int QTD_MIN_CHARS_1024 = 1024;
	private static final int PORT_54321 = 54321;
	private static final String DIR_SEP = "/";
	private static final int TAM_NOME_DIR_RAND_10 = 10;
	private static final String PREFIXO_ROOT_DIR_HTML = "html";
	private static final String PREFIXO_ERROR_DIR_ERROR = "error";
	private static final Factory factory = Factory.getInstancia();
	private static final Random rand = new Random(System.currentTimeMillis());

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

	@Rule
	public ExpectedException exception = ExpectedException.none();
	private ArquivoConfiguracao config = null;
	private ObterCabecalhoRequisicaoGet obj = null;
	private File error404 = null;
	private File index = null;

	@Before
	public void setUp() throws Exception {
		
		config = criarArquivoConfiguracao();
		obj = factory.criarValidacao(config);
		Assert.assertNotNull(obj);
	}

	private ArquivoConfiguracao criarArquivoConfiguracao() throws IOException {
		
		MeuArquivoConfiguracao resultado = null;
		File rootDir = null;
		File errorDir = null;
		String rootDirStr = null;
		String errorDirStr = null;
		
		resultado = new MeuArquivoConfiguracao();
		rootDir = criarDirTemporario(PREFIXO_ROOT_DIR_HTML);
		errorDir = criarDirTemporario(PREFIXO_ERROR_DIR_ERROR);
		error404 = criarArquivo(errorDir, ERROR_404_HTML);
		index = criarArquivo(rootDir, INDEX_HTML);
		rootDirStr = obterConfigDir(rootDir);
		errorDirStr = obterConfigDir(errorDir);		
		resultado.rootDir = rootDirStr;
		resultado.errorDir = errorDirStr;
		resultado.port = PORT_54321;
		
		return resultado;
	}

	private File criarDirTemporario(String prefixoDir) {
		
		File resultado = null;
		String dirPai = null;
		String dir = null;
		String path = null;
		
		dirPai = PWD + File.separator + TEMP_DIR + File.separator;
		dir = obterNomeAleatorio(prefixoDir);
		path = dirPai + dir;
		resultado = new File(path);
		Assert.assertTrue("Tentando criar diretorio aleatorio: " + path, 
				resultado.mkdir());
		
		return resultado;
	}

	private String obterNomeAleatorio(String prefixoDir) {
		
		String resultado = null;
		
		resultado = prefixoDir + RandomStringUtils.randomAlphabetic(TAM_NOME_DIR_RAND_10);

		return resultado;
	}

	private String obterConfigDir(File dir) {
		
		String resultado = null;
		String completo = null;
		String relativo = null;
		
		completo = dir.getAbsolutePath();
		relativo = completo.replace(PWD, "");
		resultado = relativo.replace(File.separator, DIR_SEP);
		if (resultado.charAt(0) == DIR_SEP.charAt(0)) {
			resultado = "." + resultado;			
		} else {
			resultado = "./" + resultado;
		}

		return resultado;
	}

	private File criarArquivo(File dirPai, String nomeArquivo) throws IOException {
		
		File resultado = null;
		
		if (!dirPai.exists()) {
			Assert.assertTrue(dirPai.mkdirs());			
		}
		Assert.assertTrue(dirPai.isDirectory());
		resultado = new File(dirPai, nomeArquivo);
		gerarConteudoArquivo(resultado);

		return resultado;
	}

	private void gerarConteudoArquivo(File file) throws IOException {
		
		FileWriter writer = null;
		BufferedWriter out = null;
		String conteudo = null;		

		writer = new FileWriter(file);
		out = new BufferedWriter(writer);
		conteudo = RandomStringUtils.random(QTD_MIN_CHARS_1024 + rand.nextInt(QTD_MIN_CHARS_1024));
		out.append(conteudo);
		out.close();
	}

	@After
	public void tearDown() throws Exception {

		obj = null;
		config = null;
		error404 = null;
	}
	
	@Test
	public void testRequisicaoInexistenteErro404() throws Exception {
		
		Reader requisicao = null;
		String[] resultado = null;
		String[] esperado = null;
		String path = null;
		
		path = obterNomeAleatorio(DIR_SEP);
		requisicao = obterRequisicao(path);
		resultado = obj.obterCabecalhoResposta(requisicao);
		esperado = obterHeader(HTTP_1_0_404_NOT_FOUND, error404);
		Assert.assertNotNull(resultado);
		Assert.assertArrayEquals(esperado, resultado);
	}

	private Reader obterRequisicao(String path) {

		StringReader resultado = null;
		StringBuilder builder = null;
		String sequencia = null;

		builder = new StringBuilder();
		builder.append("GET " + path + " http/1.1");
		builder.append(ObterCabecalhoRequisicaoGet.NOVA_LINHA);
		builder.append("Host: www.google.com");
		builder.append(ObterCabecalhoRequisicaoGet.NOVA_LINHA);
		sequencia = builder.toString();
		resultado = new StringReader(sequencia);

		return resultado;
	}

	private String[] obterHeader(String resposta, File file) {
		
		String[] resultado = null;
		
		resultado = new String[6];
		resultado[0] = resposta + ObterCabecalhoRequisicaoGet.NOVA_LINHA;
		resultado[1] = "Date: " + obterDataFormatada() + ObterCabecalhoRequisicaoGet.NOVA_LINHA;
		resultado[2] = "Server: " + ObterCabecalhoRequisicaoGet.SERVER + ObterCabecalhoRequisicaoGet.NOVA_LINHA;
		resultado[3] = "Content-Length: " + file.length() + ObterCabecalhoRequisicaoGet.NOVA_LINHA;
		resultado[4] = "Content-Type: text/html; charset=utf-8" + ObterCabecalhoRequisicaoGet.NOVA_LINHA;
		resultado[5] = "Connection: close" + ObterCabecalhoRequisicaoGet.NOVA_LINHA;

		return resultado;
	}

	private String obterDataFormatada() {
		
		String resultado = null;
		DateFormat formatador = null;
		
		formatador = new SimpleDateFormat(
	            "EEE, dd MMM yyyy HH:mm:ss z",
	            Locale.getDefault());
	    formatador.setTimeZone(TimeZone.getTimeZone("GMT"));
	    resultado = formatador.format(ObterCabecalhoRequisicaoGet.DATE);

		return resultado;
	}
	
	@Test
	public void testRequisicaoBarra() throws Exception {
		
		Reader requisicao = null;
		String[] resultado = null;
		String[] esperado = null;
		String path = null;
		
		path = DIR_SEP;
		requisicao = obterRequisicao(path);
		resultado = obj.obterCabecalhoResposta(requisicao);
		esperado = obterHeader(HTTP_1_0_200_OK, index);
		Assert.assertArrayEquals(esperado, resultado);
		Assert.assertNotNull(resultado);
	}
	
	@Test
	public void testRequisicaoAlgo() throws Exception {
		
		Reader requisicao = null;
		String[] resultado = null;
		String[] esperado = null;
		String path = null;
		File algo = null;
		String dir = null;
		String arq = null;
		File dirPai = null;
		
		dir = obterNomeAleatorio("dir");
		arq = obterNomeAleatorio("algo") + ".html";
		dirPai = new File(index.getParent(), dir);
		Assert.assertTrue(dirPai.mkdirs());
		algo = criarArquivo(dirPai, arq);
		path = DIR_SEP + dir + DIR_SEP + arq;
		requisicao = obterRequisicao(path);
		resultado = obj.obterCabecalhoResposta(requisicao);
		esperado = obterHeader(HTTP_1_0_200_OK, algo);
		Assert.assertArrayEquals(esperado, resultado);
		Assert.assertNotNull(resultado);
	}
}
