package br.com.senacrs.alp.aulas;

import java.io.File;


public interface ListaConteudoDiretorio {
	
	public static final char PERMISSAO_LEITURA = 'r';
	public static final char PERMISSAO_ESCRITA = 'w';
	public static final char PERMISSAO_EXECUCAO = 'x';
	public static final char IDENTIFICA_DIRETORIO = 'd';
	public static final char SEPARADOR = ' ';
	public static final char NADA_CONSTA = '-';

	String[] listarConteudo(File diretorio);
}
