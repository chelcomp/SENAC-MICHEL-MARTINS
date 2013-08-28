import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;

public class Estatisticas {

	private static Dictionary<Byte, Byte> probabilidadeEntradaClienteHora;
	private static final byte HORA_ABERTURA_AGENCIA = (byte) 10;
	private static final byte HORA_FECHAMENTO_AGENCIA = (byte) 15;
	private static final byte CHANCE_CLIENTE_FILA_CAIXA = (byte) 30;
	private static final byte CHANCE_CLIENTE_PREFERENCIAL = (byte) 20;
	private static final byte TEMPO_MINIMO_ATENDIMENTO_CAIXA = (byte) 1;
	private static final byte TEMPO_MAXIMO_ATENDIMENTO_CAIXA = (byte) 12;
	private static final byte QUANTIDADE_CAIXAS = (byte) 5;
	private static final byte QUANTIDADE_CAIXAS_PREFERENCIAIS = (byte) 1;
	private static final byte QUANTIDADE_GERENTES = (byte) 3;
	private static final byte QUANTIDADE_GERENTES_PREFERENCIAIS = (byte) 1;

	private static ArrayList<Cliente> filaClienteCaixa;
	private static ArrayList<Cliente> filaClientePreferencialCaixa;
	private static ArrayList<Cliente> filaClienteGerente;
	private static ArrayList<Cliente> filaClientePreferencialGerente;

	private static Cliente[] caixas;
	private static Cliente[] gerentes;

	private static int quantidadeAtendimentosCaixas;
	private static int quantidadeAtendimentosGerentes;

	private static int somaTempoAtendimentosCaixas;
	private static int somaTempoAtendimentosGerentes;

	private static int somaTempoClientesAgencia;
	private static int maiorTempoPermanenciaClienteAgencia;

	private static int maiorTamanhoFila;

	public static void main(String[] args) {
		// Prepara variáveis de classe
		filaClienteCaixa = new ArrayList<Cliente>();
		filaClientePreferencialCaixa = new ArrayList<Cliente>();
		filaClienteGerente = new ArrayList<Cliente>();
		filaClientePreferencialGerente = new ArrayList<Cliente>();

		caixas = new Cliente[QUANTIDADE_CAIXAS
				+ QUANTIDADE_CAIXAS_PREFERENCIAIS];

		gerentes = new Cliente[QUANTIDADE_GERENTES
				+ QUANTIDADE_GERENTES_PREFERENCIAIS];

		probabilidadeEntradaClienteHora = new Hashtable<Byte, Byte>();
		preparaProbabilidadeEntradaClienteHora();

		simulaAgencia();

		System.out.println(String.format("Média de tempo de atendimento nos caixas: %d", somaTempoAtendimentosCaixas / quantidadeAtendimentosCaixas));
		System.out.println(String.format("Média de tempo de atendimento nos gerentes: %d", somaTempoAtendimentosGerentes / quantidadeAtendimentosGerentes));
		System.out.println(String.format("Tempo médio dos clientes na agência: %d", somaTempoClientesAgencia / quantidadeAtendimentosCaixas));
		System.out.println(String.format("Tempo máximo que um cliente permaneceu na agência: %d", maiorTempoPermanenciaClienteAgencia));
		System.out.println(String.format("Tamanho máximo das filas da agência: %d", maiorTamanhoFila));

	}

	private static void simulaAgencia() {

		System.out.println(String.format("Hora atendimento Agencia: %d até %d", HORA_ABERTURA_AGENCIA, HORA_FECHAMENTO_AGENCIA));
		byte horas = HORA_ABERTURA_AGENCIA;
		byte minutos = 0;

		while (horas <= HORA_FECHAMENTO_AGENCIA
				|| getQuantidadeClientesDentroAgencia() > 0) {

			Tempo momentoAtual = new Tempo(horas, minutos);
			// Só permite entrada de clientes no horário de atendimento
			if (horas <= HORA_FECHAMENTO_AGENCIA) {
				simulaEntradaDeClientesNaAgencia(momentoAtual);
			}

			simulaAtendimentoFilas(momentoAtual);

			// Atualiza tempo
			minutos++;
			if (minutos > 60) {
				minutos = 0;
				horas++;
			}

		}

		System.out.println(String.format("Hora Fechamento Real da Agencia: %d:%d", horas, minutos));
	}

	private static void simulaAtendimentoFilas(Tempo momentoAtual) {

		verificaDisponibilidadeDosCaixas(momentoAtual);
		verificaDisponibilidadeDosGerentes(momentoAtual);

	}

	private static void verificaDisponibilidadeDosCaixas(Tempo momentoAtual) {
		// Percorre os caixas
		for (int c = 0; c < caixas.length; c++) {
			Cliente clienteAtendimento = caixas[c];

			boolean atendimentoPreferencial = (c < QUANTIDADE_CAIXAS_PREFERENCIAIS);
			boolean pegaProxCliente = false;

			// Se o gerente estiver sem nenhum cliente deve pegar o primeiro
			if (clienteAtendimento == null) {
				pegaProxCliente = true;
			} else {
				int tempoEmAtendimento = momentoAtual.getTotalMinutos() - clienteAtendimento.getMomentoInicioAtendimento().getTotalMinutos();

				pegaProxCliente = (tempoEmAtendimento >= clienteAtendimento.getTempoMinutosPrevistoAtendimento());
			}

			if (pegaProxCliente) {
				if (atendimentoPreferencial) {
					clienteAtendimento = getProxClienteFila(filaClientePreferencialCaixa);
				} else {
					clienteAtendimento = getProxClienteFila(filaClienteCaixa);
				}

				if (clienteAtendimento != null) {
					clienteAtendimento.setMomentoInicioAtendimento(momentoAtual);
					quantidadeAtendimentosCaixas++;
					somaTempoAtendimentosCaixas += clienteAtendimento.getTempoMinutosPrevistoAtendimento();

					int tempoPermanenciaAgencia = momentoAtual.getTotalMinutos() - clienteAtendimento.getMomentoEntradaAgencia().getTotalMinutos();
					somaTempoClientesAgencia += tempoPermanenciaAgencia;

					if (tempoPermanenciaAgencia > maiorTempoPermanenciaClienteAgencia)
						maiorTempoPermanenciaClienteAgencia = tempoPermanenciaAgencia;
				}

				caixas[c] = clienteAtendimento;

			}

		}

	}

	private static void verificaDisponibilidadeDosGerentes(Tempo momentoAtual) {
		// Percorre os gerentes
		for (int c = 0; c < gerentes.length; c++) {
			Cliente clienteAtendimento = gerentes[c];

			boolean atendimentoPreferencial = (c < QUANTIDADE_GERENTES_PREFERENCIAIS);
			boolean pegaProxCliente = false;

			// Se o gerente estiver sem nenhum cliente deve pegar o primeiro
			if (clienteAtendimento == null) {
				pegaProxCliente = true;
			} else {
				int tempoEmAtendimento = momentoAtual.getTotalMinutos() - clienteAtendimento.getMomentoInicioAtendimento().getTotalMinutos();

				pegaProxCliente = (tempoEmAtendimento >= clienteAtendimento.getTempoMinutosPrevistoAtendimento());
			}

			if (pegaProxCliente) {

				// Gera novo tempo para atendimento deste cliente e envia para
				// os caixas
				if (clienteAtendimento != null) {
					byte tempoMinutosPrevistoAtendimento = geraTempoAtendimento();
					clienteAtendimento.setTempoMinutosPrevistoAtendimento(tempoMinutosPrevistoAtendimento);
				}

				if (atendimentoPreferencial) {
					// coloca o cliente na fila Preferencial dos caixas
					if (clienteAtendimento != null) {
						filaClientePreferencialCaixa.add(clienteAtendimento);
					}

					clienteAtendimento = getProxClienteFila(filaClientePreferencialGerente);

				} else {
					// coloca o cliente na fila dos caixas
					if (clienteAtendimento != null) {
						filaClienteCaixa.add(clienteAtendimento);
					}

					clienteAtendimento = getProxClienteFila(filaClienteGerente);

					if (clienteAtendimento != null)
					{
						clienteAtendimento.setMomentoInicioAtendimento(momentoAtual);
						quantidadeAtendimentosGerentes++;
						somaTempoAtendimentosGerentes += clienteAtendimento.getTempoMinutosPrevistoAtendimento();
					}

					gerentes[c] = clienteAtendimento;

				}

			}
		}
	}

	private static Cliente getProxClienteFila(ArrayList<Cliente> fila) {

		Cliente cliente = null;
		int tamanhoFila = fila.size();
		if (tamanhoFila > 0) {
			if (tamanhoFila > maiorTamanhoFila)
				maiorTamanhoFila = tamanhoFila;

			cliente = fila.get(0);
			fila.remove(0);
		}
		return cliente;
	}

	private static int getQuantidadeClientesDentroAgencia() {
		int qtd = filaClienteCaixa.size();
		qtd += filaClienteGerente.size();
		qtd += filaClientePreferencialCaixa.size();
		qtd += filaClientePreferencialGerente.size();
		qtd += contaClienteEmAtendimento(caixas);
		qtd += contaClienteEmAtendimento(gerentes);

		return qtd;
	}

	private static int contaClienteEmAtendimento(Cliente[] atendente)
	{
		int c = 0;
		for (Cliente cliente : atendente) {
			if (cliente != null)
				c++;
		}
		return c;

	}

	/*
	 * Simula a entrada de clientes na agencia considerando as probabilidade de
	 * entrada a cada minuto. Separa os clientes em filas
	 */
	private static void simulaEntradaDeClientesNaAgencia(Tempo momentoAtual) {
		// Entrada de clientes na agencia
		// Percorre as horas
		byte probabilidadeDaHora = getProbabilidadeDaHora(momentoAtual.getHora());

		// Percorre os minutos
		// for (int minutos = 0; minutos < 60; minutos++) {
		byte probabilidadeSimulada = geraProbabilidadeSimulada();

		// simula entrada de um cliente neste minuto
		if (probabilidadeDaHora >= probabilidadeSimulada) {
			byte tempoMinutosPrevistoAtendimento = geraTempoAtendimento();
			Cliente cliente = new Cliente(momentoAtual, tempoMinutosPrevistoAtendimento);

			// Distribui em uma fila de acordo com as probabilidades
			byte probabilidadeClientePreferencial = geraProbabilidadeSimulada();
			byte probabilidadeClienteFilaCaixa = geraProbabilidadeSimulada();

			boolean clienteFilaCaixa = probabilidadeClienteFilaCaixa <= CHANCE_CLIENTE_FILA_CAIXA;
			boolean clientePreferencial = probabilidadeClientePreferencial <= CHANCE_CLIENTE_PREFERENCIAL;

			if (clienteFilaCaixa) {
				if (clientePreferencial) {
					filaClientePreferencialCaixa.add(cliente);
				} else {
					filaClienteCaixa.add(cliente);
				}
			} else {
				if (clientePreferencial) {
					filaClientePreferencialGerente.add(cliente);

				} else {
					filaClienteGerente.add(cliente);
				}
			}
		}
		// }

	}

	/**
	 * Carrega as statisticas com as probabilidade de entrada de clientes no
	 * dictionary para posterior consulta
	 */
	private static void preparaProbabilidadeEntradaClienteHora() {
		// setProbabilidadeEntradaClienteHora(9, 50);

		setProbabilidadeEntradaClienteHora(10, 50);
		setProbabilidadeEntradaClienteHora(11, 70);
		setProbabilidadeEntradaClienteHora(12, 60);
		setProbabilidadeEntradaClienteHora(13, 70);
		setProbabilidadeEntradaClienteHora(14, 40);
		setProbabilidadeEntradaClienteHora(15, 80);
		// setProbabilidadeEntradaClienteHora(16, 5);

	}

	private static void setProbabilidadeEntradaClienteHora(int minutos,
			int percentual) {
		byte m = (byte) minutos;
		byte p = (byte) percentual;
		probabilidadeEntradaClienteHora.put(m, p);
	}

	/*
	 * Sorteia um número e se este for inferior a probabilidae retorna
	 * VERDADEIRO
	 */
	private static byte geraProbabilidadeSimulada() {
		Random r = new Random();
		int Sorteio = r.nextInt(101);
		byte retSorteio = (byte) Sorteio;

		return retSorteio;
	}

	private static byte geraTempoAtendimento() {
		Random r = new Random();
		int Sorteio = r.nextInt(TEMPO_MAXIMO_ATENDIMENTO_CAIXA - TEMPO_MINIMO_ATENDIMENTO_CAIXA + 1);
		byte retSorteio = (byte) (Sorteio + TEMPO_MINIMO_ATENDIMENTO_CAIXA);

		return retSorteio;
	}

	private static byte getProbabilidadeDaHora(byte h) {
		byte retProbabilidadeDaHora = 0;

		retProbabilidadeDaHora = probabilidadeEntradaClienteHora.get(h);

		return retProbabilidadeDaHora;
	}
}
