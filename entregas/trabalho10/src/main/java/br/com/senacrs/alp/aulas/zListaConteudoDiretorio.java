package br.com.senacrs.alp.aulas;

import java.io.File;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class zListaConteudoDiretorio implements ListaConteudoDiretorio{

	@Override
	public String[] listarConteudo(File diretorio) {
		
		if(diretorio == null ||  !diretorio.isDirectory() || !diretorio.canRead())
			throw new IllegalArgumentException();
		
		String[] directoryList = diretorio.list();
		
		
		List<String> diretoriosList = new LinkedList<String>();
		List<String> arquivosList = new LinkedList<String>();
		
		for (int i = 0; i < directoryList.length; i++) {
			File f = new File(diretorio, directoryList[i]);
			StringBuilder desc = new StringBuilder();
			
			if(f.isDirectory())
			{
				desc.append(ListaConteudoDiretorio.IDENTIFICA_DIRETORIO);
			}
			else //if(f.isFile())
			{
				desc.append(ListaConteudoDiretorio.NADA_CONSTA);
			}
			
			
			desc.append(ListaConteudoDiretorio.SEPARADOR);
			
			desc.append(f.canRead()? ListaConteudoDiretorio.PERMISSAO_LEITURA:ListaConteudoDiretorio.NADA_CONSTA);
			desc.append(f.canWrite()? ListaConteudoDiretorio.PERMISSAO_ESCRITA:ListaConteudoDiretorio.NADA_CONSTA);
			desc.append(f.canExecute()? ListaConteudoDiretorio.PERMISSAO_EXECUCAO:ListaConteudoDiretorio.NADA_CONSTA);
			
			desc.append(ListaConteudoDiretorio.SEPARADOR);
			
			desc.append(f.length());
			
			desc.append(ListaConteudoDiretorio.SEPARADOR);
			
			desc.append(f.getName());
			
			
			if(f.isDirectory())
			{
				diretoriosList.add(desc.toString());
			}
			else //if(f.isFile())
			{
				arquivosList.add(desc.toString());
			}			
		}
		
		Collections.sort(diretoriosList);
		Collections.sort(arquivosList);
		
		
		List<String> tudinho = new LinkedList<String>();
		tudinho.addAll(diretoriosList);
		tudinho.addAll(arquivosList);
		
		
		
		String[] listaCompletaOrdenada = tudinho.toArray(new String[tudinho.size()]);
		
		
		return listaCompletaOrdenada;
	}

}
