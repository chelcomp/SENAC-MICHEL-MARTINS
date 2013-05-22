package br.com.senacrs.alp.aulas;

public class Factory {
	
	private static final Factory instancia = new Factory();
	
	private Factory() {
	}
	
	public ListaConteudoDiretorio criar() {
	
		ListaConteudoDiretorio resultado = new zListaConteudoDiretorio();
		
		//implementar
		
		return resultado;
	}
	
	public static Factory getInstancia() {
		return instancia;
	}
}
