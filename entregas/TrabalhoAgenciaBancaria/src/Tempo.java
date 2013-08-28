public class Tempo {
	private byte hora;
	private byte minuto;

	Tempo()
	{
		hora = 0;
		minuto = 0;
	}

	Tempo(byte hora, byte minuto)
	{
		this.hora = hora;
		this.minuto = minuto;
	}

	public byte getHora() {
		return hora;
	}

	public void setHora(byte hora) {
		this.hora = hora;
	}

	public byte getMinuto() {
		return minuto;
	}

	public void setMinuto(byte minuto) {
		this.minuto = minuto;
	}

	public int getTotalMinutos()
	{
		int totalMinutos = this.hora * 60 + this.minuto;
		return totalMinutos;

	}

}
