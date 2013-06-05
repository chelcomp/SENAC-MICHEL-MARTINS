package br.com.senacrs.alp.aulas;

import java.io.FileNotFoundException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

import br.com.senacrs.alp.aulas.InterpretadorStrings.TIPO_STRING;

public class zEmissorMensagens implements EmissorMensagens {
	private Hashtable<String, String> dicionario;

	public zEmissorMensagens(String arquivoEntrada) {
		dicionario = new Hashtable<String, String>();

		LeitorArquivos leitorArqivo = Factory.getInstancia().criarLeitor();
		String[] chaves = null;

		try {
			chaves = leitorArqivo.lerArquivo(arquivoEntrada);
		} catch (Exception ex) {
			throw new IllegalArgumentException(
					"Erro ao tenar ler o arquivo do disco", ex);

		}

		if (chaves == null || chaves.length <= 0)
			throw new IllegalArgumentException("Arquivo vazio ou inexistente");

		InterpretadorStrings validador = Factory.getInstancia()
				.criarInterpretador();

		for (int i = 0; i < chaves.length; i++) {
			TIPO_STRING ts = validador.lerString(chaves[i]);

			if (ts == TIPO_STRING.BEM_FORMADA) {
				String[] valores = chaves[i].split("=");
				dicionario.put(valores[0].trim(), valores[1].trim());
			} else if (ts == TIPO_STRING.MAL_FORMADA) {
				throw new IllegalArgumentException(
						"Existe uma chave inválida no arquivo");
			}

		}

	}

	@Override
	public String formatarMensagem(String chave, Object... argumentos) {

		if (!dicionario.containsKey(chave))
			throw new IllegalArgumentException(String.format(
					"Chave inexistente [%s]", chave));

		String formatada = String.format(dicionario.get(chave), argumentos);

		return formatada;
	}

}
