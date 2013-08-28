public class Cliente {
	private Tempo momentoEntradaAgencia;
	private Tempo momentoInicioAtendimento;
	private Tempo momentoFimAtendimento;
	private int tempoMinutosPrevistoAtendimento;

	Cliente(Tempo momentoEntradaAgencia, int tempoMinutosPrevistoAtendimento)
	{
		this.setMomentoEntradaAgencia(momentoEntradaAgencia);
		this.setTempoMinutosPrevistoAtendimento(tempoMinutosPrevistoAtendimento);
	}

	public Tempo getMomentoEntradaAgencia() {
		return momentoEntradaAgencia;
	}

	public void setMomentoEntradaAgencia(Tempo momentoEntradaAgencia) {
		this.momentoEntradaAgencia = momentoEntradaAgencia;
	}

	public Tempo getMomentoInicioAtendimento() {
		return momentoInicioAtendimento;
	}

	public void setMomentoInicioAtendimento(Tempo momentoInicioAtendimento) {
		this.momentoInicioAtendimento = momentoInicioAtendimento;
	}

	public Tempo getMomentoFimAtendimento() {
		return momentoFimAtendimento;
	}

	public void setMomentoFimAtendimento(Tempo momentoFimAtendimento) {
		this.momentoFimAtendimento = momentoFimAtendimento;
	}

	public int getTempoMinutosPrevistoAtendimento() {
		return tempoMinutosPrevistoAtendimento;
	}

	public void setTempoMinutosPrevistoAtendimento(int tempoMinutosPrevistoAtendimento) {
		this.tempoMinutosPrevistoAtendimento = tempoMinutosPrevistoAtendimento;
	}

}
