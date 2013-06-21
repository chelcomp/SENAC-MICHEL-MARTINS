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

public class zLeitorArquivos implements LeitorArquivos {

	@Override
	public String[] lerArquivo(String arquivo) {
		// TODO Auto-generated method stub
		File f = new File(arquivo);

		try {
			Reader fr = new FileReader(f);
			BufferedReader frb = new BufferedReader(fr);

			List<String> linhas = new ArrayList<String>();

			String linha;
			while ((linha = frb.readLine()) != null) {
				linhas.add(linha);
			}

			return linhas.toArray(new String[linhas.size()]);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public String[] lerArquivoComSubstituicao(String arquivo, String busca,String substituicao) {
		// TODO Auto-generated method stub
		String[] linhas = lerArquivo(arquivo);
		List<String> linhasList = new ArrayList<String>();
		
		for (int i = 0; i < linhas.length; i++) {
			linhasList.add(linhas[i].replace(busca,substituicao));
		}
		
		return linhasList.toArray(new String[linhasList.size()]);
	}

}
