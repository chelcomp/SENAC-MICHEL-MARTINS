package br.com.senacrs.alp.aulas;

public interface InterpretadorStrings {
	
	enum TIPO_STRING {
		COMENTARIO,
		MAL_FORMADA,
		BEM_FORMADA,
		;
	}
	
	public static final String PREFIXO_COMENTARIO = "#";
	public static final String SEPARADOR = "=";
	
	TIPO_STRING lerString(String str);
}
