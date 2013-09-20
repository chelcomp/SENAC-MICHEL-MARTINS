import java.io.PrintStream;

public class PNMFile {

	private final String FILE_HEADER = "%s\n%d %d\n";
	private final String ESPACO = " ";
	private int largura;
	private int altura;
	private Cor[][] imagem;

	/**
	 * @param args
	 */
	PNMFile(int largura, int altura) {

		this.largura = largura;
		this.altura = altura;

		imagem = new Cor[largura][altura];
	}

	public void setPixel(int x, int y, Cor cor)
	{
		Coordenada xy = new Coordenada(x, y);
		setPixel(xy, cor);
	}

	public void setPixel(Coordenada coodenada, Cor cor)
	{
		int x = coodenada.getX();
		int y = coodenada.getY();

		validaCoordenadas(x, y);

		imagem[x][y] = cor;
	}

	public void circulo(Coordenada coordenada, int r, Cor cor)
	{
		int x = coordenada.getX();
		int y = coordenada.getY();
		int x1, y1;

		for (int i = 0; i < 360; i++) {

			
			double d = Math.PI * i / 180;
			x1 = (int) Math.round(r * Math.cos(d) + x);
			y1 = (int) Math.round(r * Math.sin(d) + y);

			if ( (x > 0 && x < largura)
			   && (y > 0 && y < altura))			
			setPixel(x1, y1, cor);
		}
	}

	public void retangulo(Coordenada coordenadaSuperior, Coordenada coordenadaInferior, Cor cor)
	{
		Coordenada xy1 = coordenadaSuperior;
		Coordenada xy2 = new Coordenada(coordenadaSuperior.getX(), coordenadaInferior.getY());
		Coordenada xy3 = coordenadaInferior;
		Coordenada xy4 = new Coordenada(coordenadaInferior.getX(), coordenadaSuperior.getY());

		linha(xy1, xy2, cor);
		linha(xy2, xy3, cor);
		linha(xy4, xy3, cor);
		linha(xy1, xy4, cor);
	}

	public void triangulo(Coordenada coordenada1, Coordenada coordenada2, Coordenada coordenada3, Cor cor)
	{
		validaTriangulo(coordenada1, coordenada2, coordenada3);

		linha(coordenada1, coordenada2, cor);
		linha(coordenada1, coordenada3, cor);
		linha(coordenada2, coordenada3, cor);

	}

	private void validaTriangulo(Coordenada coordenada1, Coordenada coordenada2, Coordenada coordenada3) {
		boolean heTriangulo = !(coordenada1.equals(coordenada2) || coordenada2.equals(coordenada3) || coordenada3.equals(coordenada1));

		if (!heTriangulo)
			throw new IllegalArgumentException("Não é um triangulo válido.");
	}

	public void linha(Coordenada coordenada1, Coordenada coordenada2, Cor cor)
	{
		int x1 = coordenada1.getX();
		int y1 = coordenada1.getY();

		int x2 = coordenada2.getX();
		int y2 = coordenada2.getY();

		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);

		int sx = 0;
		int sy = 0;

		if (x1 > x2)
			sx = -1;
		else
			sx = 1;

		if (y1 > y2)
			sy = -1;
		else
			sy = 1;

		int err = dx - dy;

		while (true)
		{
			setPixel(x1, y1, cor);

			if (x1 == x2 && y1 == y2)
				break;

			int e2 = 2 * err;
			if (e2 > -dy)
			{
				err -= dy;
				x1 += sx;
			}

			if (x1 == x2 && y1 == y2)
			{
				setPixel(x1, y1, cor);
				break;
			}

			if (e2 < dx)
			{
				err += dx;
				y1 += sy;
			}

		}

	}

	private void validaCoordenadas(int x, int y) {

		if (x < 0 || x >= largura)
			throw new IllegalArgumentException("Valor de x fora de limites: " + x);

		if (y < 0 || y >= altura)
			throw new IllegalArgumentException("Valor de y fora de limites: " + y);
	}

	public void printImage(PrintStream out, PNMFileType pNMFileType)
	{
		String header = String.format(FILE_HEADER, pNMFileType.getValue(), largura, altura);
		if(pNMFileType != PNMFileType.PORTABLE_BITMAP)
			header += "255\n";
		
		out.print(header);

		for (int x = 0; x < largura; x++) {
			for (int y = 0; y < altura; y++) {
				Cor pixel = getPixel(x, y);
				
				if(pixel == null)
					pixel = new Cor(255,255,255);

				String p;

				switch (pNMFileType) {
				case PORTABLE_BITMAP:
					p = pixel.GetBitmap().toString();
					break;

				case PORTABLE_GRAYMAP:
					p = pixel.getGraymap().toString();
					break;

				default:
					p = pixel.getPixMap();
					break;
				}

				out.print(p);
				out.print(ESPACO);
			}
			out.print("\n");
		}

	}

	private Cor getPixel(int x, int y) {
		validaCoordenadas(x, y);

		Cor pixel = imagem[x][y];
		
		
		
		return pixel;
	}

}
