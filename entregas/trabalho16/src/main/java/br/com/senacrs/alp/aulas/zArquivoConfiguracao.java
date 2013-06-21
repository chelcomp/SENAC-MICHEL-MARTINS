package br.com.senacrs.alp.aulas;

import java.io.File;
import java.util.Hashtable;

import br.com.senacrs.alp.aulas.InterpretadorStrings.TIPO_STRING;

public class zArquivoConfiguracao implements ArquivoConfiguracao {

	private static final String ROT_DIR = "root_dir";
	private static final String PORT = "port";
	private static final String ERROR_DIR = "error_dir";
	
	private Hashtable<String, String> dicionario;

	zArquivoConfiguracao(String arquivoEntrada) {
		if (arquivoEntrada == null) {
			throw new IllegalArgumentException(
					"O nome do arquivo não pode ser nulo.");
		}

		File f = new File(arquivoEntrada);

		if (!f.exists()) {
			throw new IllegalArgumentException(
					"Arquivo inexistente ou não localizado.");
		}

		dicionario = new Hashtable<String, String>();

		LeitorArquivos leitorArqivo = new zLeitorArquivos();
		String[] chaves = null;

		try {
			chaves = leitorArqivo.lerArquivo(arquivoEntrada);
		} catch (Exception ex) {
			throw new IllegalArgumentException(
					"Erro ao tenar ler o arquivo do disco", ex);

		}

		if (chaves == null || chaves.length <= 0)
			throw new IllegalArgumentException("Arquivo vazio ou inexistente");

		InterpretadorStrings validador = new zInterpretadorStrings();

		for (int i = 0; i < chaves.length; i++) {
			if (!chaves[i].trim().equals("")) {
				TIPO_STRING ts = validador.lerString(chaves[i]);

				if (ts == TIPO_STRING.BEM_FORMADA) {
					String[] valores = chaves[i].split("=");
					
					dicionario.put(valores[0].trim(),
							LimpaComentarios(valores[1].trim()));
				} else if (ts == TIPO_STRING.MAL_FORMADA) {
					throw new IllegalArgumentException(
							"Existe uma chave invÃ¡lida no arquivo");
				}
			}

		}

		//validaChaves();

	}

	


	private String LimpaComentarios(String valor) {
		int i = valor.indexOf("#");

		if(i == 0){
			throw new IllegalArgumentException("Valor da chave não pode ser vazio");
		}
	
		if (i > -1) {
			String ret = valor.substring(0, i - 1);
			return ret;
		}
		return valor;

	}

	@Override
	public String getRootDir() {
		// TODO Auto-generated method stub
		return dicionario.get(ROT_DIR);
	}

	@Override
	public String getErrorDir() {
		// TODO Auto-generated method stub
		return dicionario.get(ERROR_DIR);
	}

	@Override
	public int getPort() {
		// TODO Auto-generated method stub
		return Integer.parseInt(dicionario.get(PORT));
	}


}
