package br.com.senacrs.alp.aulas;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import java.util.TimeZone;

public class zObterRequisicaoGet implements ObterRequisicaoGet {

	ArquivoConfiguracao config;

	private static String PWD = System.getProperty("user.dir");
	private static final String ESPACO_BRANCO = " ";
	private static final char BARRA_REMOTA = '/';
	private static final char BARRA_LOCAL = File.separatorChar;
	private static final String HTTP_200 = "HTTP/1.0 200 OK";
	private static final String HTTP_400 = "HTTP/1.0 404 NotFound";

	zObterRequisicaoGet(ArquivoConfiguracao config) {
		this.config = config;
	}

	@Override
	public String[] obterResposta(Reader requisicao) {

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

		

		String[] retorno = getRetorno(httpResult, DATE, SERVER, absoluteFile);

		return retorno;
	}

	private File get404FileError() {

		String erroDir = config.getErrorDir();

		File retorno = new File(erroDir + "\\error_404.html");

		return retorno;
	}

	private String[] getRetorno(String httpResult, java.util.Date date,	String serverName, File file) {
		
		java.util.List<String> retorno = new LinkedList<String>();
		long contentLength = file.length();
		
		java.util.List<String> header = getRetornoHeader(httpResult, date, serverName, contentLength);
		retorno.addAll(header);
		
		retorno.add(NOVA_LINHA);
		
		java.util.List<String> boddy = getRetornoBoddy(httpResult, date, httpResult, file);
		retorno.addAll(boddy);
		
		return retorno.toArray(new String[retorno.size()]);

	}

	private java.util.List<String> getRetornoBoddy(String httpResult,
			java.util.Date date, String httpResult2, File file) {

		java.util.List<String> boddy = new LinkedList<String>();
		
		try {
			Reader fr = new FileReader(file);
			BufferedReader frb = new BufferedReader(fr);

			String linha;
			while ((linha = frb.readLine()) != null) {
				boddy.add(linha);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return boddy;
	}

	private java.util.List<String> getRetornoHeader(String httpResult,java.util.Date date, String serverName, long contentLength) {

		String gmtDate = getDateGMT(date);

		java.util.List<String> retorno = new LinkedList<String>();
		
		retorno.add(httpResult + NOVA_LINHA);
		retorno.add("Date: " + gmtDate + NOVA_LINHA);
		retorno.add("Server: " + serverName + NOVA_LINHA);
		retorno.add("Content-Length: " + contentLength + NOVA_LINHA);
		retorno.add("Content-Type: text/html; charset=utf-8" + NOVA_LINHA);
		retorno.add("Connection: close" + NOVA_LINHA);

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
