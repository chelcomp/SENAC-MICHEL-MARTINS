package br.com.senacrs.alp.aulas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class zResponderRequisicaoGet implements ResponderRequisicaoGet {

	ArquivoConfiguracao config;

	private static String PWD = System.getProperty("user.dir");
	private static final String ESPACO_BRANCO = " ";
	private static final char BARRA_REMOTA = '/';
	private static final char BARRA_LOCAL = File.separatorChar;
	private static final String HTTP_200 = "HTTP/1.0 200 OK";
	private static final String HTTP_400 = "HTTP/1.0 404 NotFound";
	private static final String HTML_ERRO_400 = "\\error_404.html";
	private static final String HTTP_HEADER = "%s" + NOVA_LINHA + "Date: %s"
			+ NOVA_LINHA + "Server: %s" + NOVA_LINHA + "Content-Length: %d"
			+ NOVA_LINHA + "Content-Type: text/html; charset=utf-8"
			+ NOVA_LINHA + "Connection: close" + NOVA_LINHA;

	zResponderRequisicaoGet(ArquivoConfiguracao config) {
		this.config = config;
	}

	@Override
	public void responderRequisicao(Reader requisicao, Writer saida) {
		String readerString = null;
		try {
			readerString = getReaderString(requisicao);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		String relativePath = getRelativePath(readerString);
		File absoluteFile = getAbsoluteFile(relativePath);

		boolean fileExists = absoluteFile.exists();

		String httpResult = HTTP_200;
		if (!fileExists) {
			httpResult = HTTP_400;
			absoluteFile = get404FileError();
		}

		StringBuilder retornoArray = getRetorno(httpResult, DATE, SERVER,
				absoluteFile);

		String retornoString = retornoArray.toString();

		try {

			saida.write(retornoString);

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

	}

	private File get404FileError() {

		String erroDir = config.getErrorDir();

		File retorno = new File(erroDir + HTML_ERRO_400);

		return retorno;
	}

	private StringBuilder getRetorno(String httpResult, java.util.Date date,
			String serverName, File file) {

		StringBuilder retorno = new StringBuilder();
		long contentLength = file.length();

		StringBuilder header = getRetornoHeader(httpResult, date, serverName,
				contentLength);
		retorno.append(header);

		retorno.append(NOVA_LINHA);

		StringBuilder boddy = getRetornoBoddy(httpResult, date, httpResult,
				file);
		retorno.append(boddy);

		return retorno;

	}

	private StringBuilder getRetornoBoddy(String httpResult,
			java.util.Date date, String httpResult2, File file) {

		StringBuilder boddy = new StringBuilder();

		try {
			Reader fr = new FileReader(file);
			BufferedReader frb = new BufferedReader(fr);

			String linha;
			while ((linha = frb.readLine()) != null) {
				boddy.append(linha);
			}

			frb.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return boddy;
	}

	private StringBuilder getRetornoHeader(String httpResult,
			java.util.Date date, String serverName, long contentLength) {

		String gmtDate = getDateGMT(date);

		StringBuilder retorno = new StringBuilder();
		retorno.append(String.format(HTTP_HEADER, httpResult, gmtDate,
				serverName, contentLength));

		return retorno;

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

	private String getReaderString(Reader requisicao) throws IOException {
		BufferedReader bf = new BufferedReader(requisicao);
		String retorno = bf.readLine();
		return retorno;
	}

}
