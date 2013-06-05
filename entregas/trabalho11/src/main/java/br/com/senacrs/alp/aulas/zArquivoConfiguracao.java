package br.com.senacrs.alp.aulas;

import java.io.File;
import java.io.FileWriter;
import java.util.Hashtable;
import java.util.Map;

import br.com.senacrs.alp.aulas.InterpretadorStrings.TIPO_STRING;

public class zArquivoConfiguracao implements ArquivoConfiguracao {

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

		validaChaves();

	}

	String CHAVE1 = "root_dir";
	String CHAVE2 = "port";
	String CHAVE3 = "error_dir";

	private void validaChaves() {

		if (!dicionario.containsKey(CHAVE1))
			throw new IllegalArgumentException("Faltando chave " + CHAVE1);

		if (!dicionario.containsKey(CHAVE2))
			throw new IllegalArgumentException("Faltando chave " + CHAVE2);

		if (!dicionario.containsKey(CHAVE3))
			throw new IllegalArgumentException("Faltando chave " + CHAVE3);

		String root = dicionario.get(CHAVE1);
		if (!root.startsWith("./"))
			throw new IllegalArgumentException(
					"O arquivo não contem um caminho válido para a chave "
							+ CHAVE1);

		String port = dicionario.get(CHAVE2);
		if (!port.matches("[0-9]*"))
			throw new IllegalArgumentException(
					"O arquivo não contem um valor válido para a chave "
							+ CHAVE2);

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
		return dicionario.get(CHAVE1);
	}

	@Override
	public String getErrorDir() {
		// TODO Auto-generated method stub
		return dicionario.get(CHAVE3);
	}

	@Override
	public int getPort() {
		// TODO Auto-generated method stub
		return Integer.parseInt(dicionario.get(CHAVE2));
	}

}
