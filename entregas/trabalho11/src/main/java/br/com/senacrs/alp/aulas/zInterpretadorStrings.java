package br.com.senacrs.alp.aulas;

public class zInterpretadorStrings implements InterpretadorStrings {

	@Override
	public TIPO_STRING lerString(String str) {
		if (str != null) {

			if (isComment(str)){
				return TIPO_STRING.COMENTARIO;
			}
			
			if(isFormatOk(str))
			{
				return TIPO_STRING.BEM_FORMADA;
			}
		}
		return TIPO_STRING.MAL_FORMADA;

	}

	private Boolean isFormatOk(String str) {
		int i = str.indexOf('=');
		if (i > -1) {
			String a = str.substring(0, i);
			String b = str.substring(i + 1, str.length());
			return ( i > -1 && !a.trim().isEmpty() && !b.trim().isEmpty());
		}
		return false;
	}

	private boolean isComment(String str) {
		return str.trim().startsWith("#");
	}
	
	

}
