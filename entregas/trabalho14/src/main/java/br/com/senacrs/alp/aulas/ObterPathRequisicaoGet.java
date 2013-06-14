package br.com.senacrs.alp.aulas;

import java.io.Reader;

public interface ObterPathRequisicaoGet {
	
	public static final String NOVA_LINHA = "\r\n";
	
	String caminhoAbsoluto(Reader requisicao);
}
