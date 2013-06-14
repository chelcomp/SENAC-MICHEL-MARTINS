package br.com.senacrs.alp.aulas;

public class Factory {
	
	private static final Factory instancia = new Factory();
	
	private Factory() {
	}
	
	public ValidarRequisicaoGet criarValidacao() {
	
		ValidarRequisicaoGet resultado = new zValidarRequisicaoGet();
		
		//implementar
		
		return resultado;
	}
	
	public static Factory getInstancia() {
		return instancia;
	}
}
