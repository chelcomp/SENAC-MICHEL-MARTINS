package br.com.senacrs.alp.aulas;

import java.io.Reader;

public interface ValidarRequisicaoGet {
	
	public static final String NOVA_LINHA = "\r\n";
	
	boolean ehRequisicaoGetValida(Reader requisicao);
}
