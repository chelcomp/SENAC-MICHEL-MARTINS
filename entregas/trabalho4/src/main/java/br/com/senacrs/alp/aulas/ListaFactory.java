package br.com.senacrs.alp.aulas;

public class ListaFactory {
	
	private static final ListaFactory instancia = new ListaFactory();
	
	private ListaFactory() {
	}
	
	public <T> Lista<T> criarLista() {
		
		Lista<T> resultado =   new ListaClass<T>();
		
		return resultado;
	}
	
	public static ListaFactory getInstancia() {
		return instancia;
	}
}
