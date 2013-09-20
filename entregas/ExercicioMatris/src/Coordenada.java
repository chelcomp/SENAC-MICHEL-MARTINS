
public class Coordenada {
	private int x;
	private int y;

	Coordenada(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean equals(Coordenada coordenada)
	{
		boolean ret = coordenada.getX() == this.x && coordenada.getY() == this.y;
		return ret;

	}

}
