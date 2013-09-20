public class Cor {
	private int r;
	private int g;
	private int b;

	public Integer GetBitmap()
	{
		Cor c = new Cor(0, 0, 0);

		if (this.equals(c))
			return 0;
		else
			return 1;
	}

	public Integer getGraymap()
	{
		Double calculo = 0.2126 * r + 0.7152 * g + 0.0722 * b;
		int gray = calculo.intValue();
		return gray;
	}
	
	public String getPixMap()
	{
		String c = String.format("%3d %3d %3d", r, g, b);
		return c;
		
	}

	Cor(int r, int g, int b)
	{
		this.r = clip(r);
		this.g = clip(g);
		this.b = clip(b);
	}
	
	

	private int clip(int valor)
	{
		int v = valor;
		if (valor < 0)
			v = 0;
		else if (valor > 255)
			v = (int)255;

		return  (int)v;
	}

	public int getR() {
		return r;
	}

	public int getG() {
		return g;
	}

	public int getB() {
		return b;
	}

	

	public Boolean equals(Cor cor)
	{
		Boolean ret = cor.getR() == this.r &&
				cor.getG() == this.g &&
				cor.getB() == this.b;
		return ret;

	}
}
