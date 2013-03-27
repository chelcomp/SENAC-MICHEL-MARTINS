package br.com.senacrs.alp.aulas;

public class PilhaFactory {
	
	private static final PilhaFactory instancia = new PilhaFactory();
	
	private PilhaFactory() {
	}
	
	public <T> Pilha<T> criarPilha() {
		
		Pilha<T> resultado = new PilhaClass<T>();
		
		//implementar
		
		return resultado;
	}
	
	public static PilhaFactory getInstancia() {
		return instancia;
	}
}
