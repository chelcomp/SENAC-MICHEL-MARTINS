package br.com.senacrs.alp.aulas;

import java.io.Reader;
import java.util.Date;

public interface ObterCabecalhoRequisicaoGet {
	
	
	
	public static final String NOVA_LINHA = "\r\n";
	public static final Date DATE = new Date();
	public static final String SERVER = "MeuServidorJava";
	
	String[] obterCabecalhoResposta(Reader requisicao);
}
