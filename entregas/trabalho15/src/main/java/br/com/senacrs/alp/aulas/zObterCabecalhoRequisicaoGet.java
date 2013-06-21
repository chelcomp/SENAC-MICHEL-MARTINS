package br.com.senacrs.alp.aulas;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class zObterCabecalhoRequisicaoGet implements
		ObterCabecalhoRequisicaoGet {

	ArquivoConfiguracao config;

	private static String PWD = System.getProperty("user.dir");
	private static final String ESPACO_BRANCO = " ";
	private static final char BARRA_REMOTA = '/';
	private static final char BARRA_LOCAL = File.separatorChar;
	private static final String HTTP_200 = "HTTP/1.0 200 OK";
	private static final String HTTP_400 = "HTTP/1.0 404 NotFound";

	zObterCabecalhoRequisicaoGet(ArquivoConfiguracao config) {
		this.config = config;
	}

	@Override
	public String[] obterCabecalhoResposta(Reader requisicao) {

		String readerString = null;
		try {
			readerString = getReaderString(requisicao);
		} catch (IllegalAccessError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String relativePath = getRelativePath(readerString);
		File absoluteFile = getAbsoluteFile(relativePath);

		boolean fileExists = isFileExists(absoluteFile);

		String httpResult = HTTP_200;
		if (!fileExists) {
			httpResult = HTTP_400;
			absoluteFile = get404FileError();
		}

		long FileSize = absoluteFile.length();

		String[] retornoHeader = getRetornoHeader(httpResult, DATE, SERVER,
				FileSize);

		return retornoHeader;
	}

	private File get404FileError() {

		String erroDir = config.getErrorDir();

		File retorno = new File(erroDir + "\\error_404.html");

		return retorno;
	}

	private String[] getRetornoHeader(String httpResult, java.util.Date date,
			String serverName, long contentLength) {

		String gmtDate = getDateGMT(date);

		String[] retorno = new String[] { httpResult + NOVA_LINHA,
				"Date: " + gmtDate + NOVA_LINHA,
				"Server: " + serverName + NOVA_LINHA,
				"Content-Length: " + contentLength + NOVA_LINHA,
				"Content-Type: text/html; charset=utf-8" + NOVA_LINHA,
				"Connection: close" + NOVA_LINHA };

		return retorno;

	}

	private boolean isFileExists(File absoluteFile) {

		return absoluteFile.exists();

	}

	private String getRelativePath(String requisicao) {

		String[] linhas = requisicao.split(NOVA_LINHA);
		String[] linha1 = linhas[0].split(ESPACO_BRANCO);
		String path = linha1[1];

		return path;
	}

	private File getAbsoluteFile(String relativePath) {
		// Entrada = "/algo", "/", "afdga", "/pasta/asdfa.htm"

		String root_dir = config.getRootDir();
		String absolutePath = PWD + BARRA_LOCAL + root_dir + BARRA_LOCAL
				+ relativePath;
		String pathComBarrasAjustadas = absolutePath.replace(BARRA_REMOTA,
				BARRA_LOCAL);

		File fileLocal = new File(pathComBarrasAjustadas);

		if (fileLocal.isDirectory()) {
			pathComBarrasAjustadas += BARRA_LOCAL + "index.html";
			fileLocal = new File(pathComBarrasAjustadas);
		}

		return fileLocal;
	}

	private String getDateGMT(java.util.Date data) {

		DateFormat formatador = null;

		formatador = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z",
				Locale.getDefault());
		formatador.setTimeZone(TimeZone.getTimeZone("GMT"));
		String retorno = formatador.format(data);
		return retorno;
	}

	private String getReaderString(Reader requisicao)
			throws IllegalAccessError, IOException {
		StringBuilder requisicaoString = new StringBuilder();

		requisicao.reset();

		int charInt = -1;
		while ((charInt = requisicao.read()) >= 0) {
			requisicaoString.append((char) charInt);
		}

		String retorno = requisicaoString.toString();
		return retorno;
	}

}
