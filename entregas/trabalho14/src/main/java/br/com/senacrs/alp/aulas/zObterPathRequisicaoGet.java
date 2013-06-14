package br.com.senacrs.alp.aulas;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

public class zObterPathRequisicaoGet implements ObterPathRequisicaoGet {

	@Override
	public String caminhoAbsoluto(Reader requisicao) {
		if (requisicao == null )
			throw new IllegalArgumentException();
		
		String requisicaoString = readerToString(requisicao);
		
		if(!ehRequisicaoValida(requisicaoString))
			//throw new IllegalArgumentException();
			return null;
			
		String caminhoAcesso = getCaminhoAcesso(requisicaoString);
		
		
		
		String caminhoAbsoluto = getCaminhoAbsoluto(caminhoAcesso);

		return caminhoAbsoluto;
	}
	
	
	private boolean ehRequisicaoValida(String requisicaoString) {
		if(!requisicaoString.endsWith("\r\n"))
			return false;
		
		// Valida reader
		String[] linhas = requisicaoString.split("\r\n");
		if (linhas.length < 2)
			return false;

		String[] linha1 = linhas[0].split(" ");
		String[] linha2 = linhas[1].split(" ");

		if (linha1.length < 3 || linha2.length < 2)
			return false;

		if (linha1[0].equals("GET") && linha1[1].startsWith("/")
				&& linha1[2].startsWith("http/1.1")
				&& linha2[0].startsWith("Host:")
				&& !linha2[1].startsWith("\r\n")
				&& linha2[1].length() > 0)

			return true;

		return false;
	}
	
	
	private String getCaminhoAbsoluto(String caminhoAcesso) {
		if(caminhoAcesso.equals("/"))
			caminhoAcesso = "";
		else
			if (	caminhoAcesso.startsWith("/"))
			caminhoAcesso = caminhoAcesso.substring(1, caminhoAcesso.length() );
		
		
		
		File f = new File(caminhoAcesso);
		
		
		String caminhoAbsoluto = f.getAbsolutePath();
		if(caminhoAcesso.equals(""))
			caminhoAbsoluto += File.separator;
		
		return caminhoAbsoluto;
		
	}

	private String getCaminhoAcesso(String requisicaoString) {
		
		String[] linhas = requisicaoString.split("\r\n");
		
		String[] linha1 = linhas[0].split(" ");
	

		return linha1[1];

	}

	private String readerToString(Reader requisicao)			throws IllegalAccessError {
		String requisicaoString = "";

		try {
			requisicao.reset();

		} catch (IOException e) {
			throw new IllegalAccessError();
		}

		// BufferedReader br = new BufferedReader(requisicao);

		try {
			int l = -1;
			// while (br.ready() && (l = br.readLine())!= null) {
			do {
				l = requisicao.read();
				if(l > 0)
					requisicaoString += (char)l;
			} while (requisicao.ready() && l > -1);
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
		return requisicaoString;
	}

}
