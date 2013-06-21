package br.com.senacrs.alp.aulas;

public class Factory {
	
	private static final Factory instancia = new Factory();
	
	private Factory() {
	}
	
	public ObterRequisicaoGet criarValidacao(ArquivoConfiguracao config) {
	
		ObterRequisicaoGet resultado = new zObterRequisicaoGet(config);
		
		//implementar
		
		return resultado;
	}
	
	public static Factory getInstancia() {
		return instancia;
	}
}
