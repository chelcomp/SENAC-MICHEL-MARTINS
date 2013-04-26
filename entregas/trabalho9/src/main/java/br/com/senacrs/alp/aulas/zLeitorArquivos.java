package br.com.senacrs.alp.aulas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils.Null;

public class zLeitorArquivos implements LeitorArquivos {

	@Override
	public String[] lerArquivo(String arquivo) {

		List<String> linhas = getBoddyFile(arquivo);
		return linhas.toArray(new String[linhas.size()]);

	}

	private List<String> getBoddyFile(String arquivo) {
		File file = null;
		BufferedReader bufferedReader = null;
		Reader fileReader = null;

		file = getFile(arquivo);

		try {
			fileReader = getFileReader(file);
			bufferedReader = new BufferedReader(fileReader);

			return getLinesFile(bufferedReader);

		} finally {
			closeFileLinks(bufferedReader, fileReader);
		}
	}

	private void closeFileLinks( BufferedReader bufferedReader,
			Reader fileReader) {
		try {
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			throw new IllegalStateException("Arquivo não pode ser lido", e);
		}
	}

	private List<String> getLinesFile(BufferedReader bufferedReader) {
		List<String> lsitaLinhas = new ArrayList<String>();
		String linha;

		try {

			while ((linha = bufferedReader.readLine()) != null) {
				lsitaLinhas.add(linha);
			}

			return lsitaLinhas;

		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Arquivo não pode ser localizado", e);
		}
	}

	private Reader getFileReader(File file) {
		Reader fileReader = null;

		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(
					"Arquivo não pode ser localizado", e);
		}
		return fileReader;
	}

	private File getFile(String arquivo) {
		File file = new File(arquivo);

		if (!file.exists())
			throw new IllegalArgumentException(
					"Arquivo não pode ser localizado " + arquivo);

		if (!file.isFile())
			throw new IllegalArgumentException(
					"Endereço fornecido não representa um arquivo " + arquivo);

		if (!file.canRead())
			throw new IllegalArgumentException("Arquivo não pode ser lido");

		return file;
	}

	@Override
	public String[] lerArquivoComSubstituicao(String arquivo, String busca,	String substituicao) {

		List<String> linhasList = getBoddyFile(arquivo);
		List<String> linhasRsponse = new ArrayList<String>();

		for (int i = 0; i < linhasList.size(); i++) {
			String a = linhasList.get(i);
			String b = a.replace(busca, substituicao);
			linhasRsponse.add(b);
		}

		return linhasRsponse.toArray(new String[linhasList.size()]);
	}

}
