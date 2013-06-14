package br.com.senacrs.alp.aulas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class zValidarRequisicaoGet implements ValidarRequisicaoGet {

	@Override
	public boolean ehRequisicaoGetValida(Reader requisicao) {
		if (requisicao == null)
			throw new IllegalArgumentException();

		
		String requisicaoString = readerToString(requisicao);

		return ehRequisicaoValida(requisicaoString);
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
