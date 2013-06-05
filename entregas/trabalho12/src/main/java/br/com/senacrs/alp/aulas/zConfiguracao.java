package br.com.senacrs.alp.aulas;

import java.io.File;

public class zConfiguracao implements Configuracao {

	ArquivoConfiguracao a = null;

	zConfiguracao(ArquivoConfiguracao config) {
		
		if(config == null)
			throw new IllegalArgumentException(
					"O nome do arquivo não pode ser nulo.");
		a = config;
	}

	@Override
	public ArquivoConfiguracao getArquivoConfiguracao() {
		// TODO Auto-generated method stub
		return a;
	}

	@Override
	public boolean valido() {

		return validaChaves();
	}

	private boolean validaChaves() {
		/*
		 * if (!dicionario.containsKey(CHAVE1)) return false;
		 * 
		 * if (!dicionario.containsKey(CHAVE2)) return false;
		 * 
		 * if (!dicionario.containsKey(CHAVE3)) return false;
		 */
		String erroDir = a.getErrorDir();
		File f = new File(erroDir);
		if (!f.exists() || !f.isDirectory())
			return false;
		else {
			f = new File(erroDir + "\\error_404.html");
			if (!f.exists())
				return false;

		}

		// valida root
		String root = a.getRootDir();
		if (!root.startsWith("./"))
			return false;
		else {
			f = new File(root);
			if (!f.exists() || !f.isDirectory())
				return false;

		}

		// valida portas

		int p = a.getPort();
		if (p < 1024 || p > 65535)
			return false;

		return true;

	}

}
