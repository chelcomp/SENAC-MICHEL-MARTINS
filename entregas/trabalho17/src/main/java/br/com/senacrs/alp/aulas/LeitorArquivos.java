package br.com.senacrs.alp.aulas;

public interface LeitorArquivos {			
	
	String[] lerArquivo(String arquivo);
	
	String[] lerArquivoComSubstituicao(String arquivo, String busca, String substituicao);
}
