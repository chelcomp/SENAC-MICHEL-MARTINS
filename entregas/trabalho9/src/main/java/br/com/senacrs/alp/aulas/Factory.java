package br.com.senacrs.alp.aulas;

public class Factory {
	
	private static final Factory instancia = new Factory();
	
	private Factory() {
	}
	
	public EmissorMensagens criarEmissor(String arquivoEntrada) {
	
		EmissorMensagens resultado = new zEmissorMensagens(arquivoEntrada);
		
		//implementar
		
		return resultado;
	}
	
	public LeitorArquivos criarLeitor() {
		
		LeitorArquivos resultado = new zLeitorArquivos();
		
		//implementar
		
		return resultado;
	}
	
	
	public InterpretadorStrings criarInterpretador() {
		
		InterpretadorStrings resultado = new zInterpretadorStrings();
		
		//implementar
		
		return resultado;
	}
	
	
	public static Factory getInstancia() {
		return instancia;
	}
}
