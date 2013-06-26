package br.com.senacrs.alp.aulas;

import java.io.Reader;
import java.io.Writer;
import java.util.Date;

public interface ResponderRequisicaoGet {
		
	public static final String NOVA_LINHA = "\r\n";
	public static final Date DATE = new Date();
	public static final String SERVER = "MeuServidorJava";

	void responderRequisicao(Reader requisicao, Writer saida);
}
