import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		leTeclado();

		PNMFile f = new PNMFile(400, 400);

		/*
		 * for (int x = 0; x < 16; x++) { for (int y = 0; y < 16; y++) {
		 * f.setPixel(x, y, x + y * 16);
		 * 
		 * } }
		 */

		Coordenada xy = new Coordenada(100, 100);
		Cor cor = new Cor(100, 200, 100);
		f.circulo(xy, 50, cor);

		xy = new Coordenada(110, 100);
		cor = new Cor(100, 10, 100);
		f.circulo(xy, 30, cor);

		xy = new Coordenada(10, 10);
		Coordenada xy2 = new Coordenada(10, 80);
		cor = new Cor(150, 100, 100);
		f.linha(xy, xy2, cor);

		xy = new Coordenada(1, 20);
		xy2 = new Coordenada(20, 80);
		cor = new Cor(150, 100, 150);
		f.linha(xy, xy2, cor);

		File file = new File("c:\\teste.pnm");
		try {
			// FileWriter fw = new FileWriter(file);
			PrintStream ps = new PrintStream(file);
			f.printImage(ps, PNMFileType.PORTABLE_GRAYMAP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// cf.printImage(System.out);

	}

	private static void leTeclado()
	{
		PNMFile pNMFile = new PNMFile(0, 0);
		Cor cor = new Cor(0, 0, 0);
		char cores = '\0';

		// List<String> ret = new ArrayList<String>();
		Scanner sc = new Scanner(System.in);
		sc.reset();

		System.out.print(">");

		while (sc.hasNext()) {

			comandosType comando;
			try {
				comando = comandosType.valueOf(sc.next());

				switch (comando) {
				case sair: {
					System.out.println("Fim do programa");
					return;
				}
				case circulo: {
					int x = sc.nextInt();
					int y = sc.nextInt();
					int r = sc.nextInt();
					Coordenada coordenada = new Coordenada(x, y);
					pNMFile.circulo(coordenada, r, cor);
					System.out.println("-Circulo Criado");
					break;
				}
				case cor: {
					int r = 0;
					int b = 0;
					int g = sc.nextInt();
					if (sc.hasNext()) {
						r = g;
						g = sc.nextInt();
						b = sc.nextInt();
					}
					cor = new Cor(r, g, b);
					System.out.println("-Cor Configurada");
					break;
				}
				case image: {
					int l = sc.nextInt();
					int h = sc.nextInt();
					cores = sc.next().charAt(0);
					pNMFile = new PNMFile(l, h);
					System.out.println("-Imagem Setada");
					break;
				}
				case reta: {
					int x0 = sc.nextInt();
					int y0 = sc.nextInt();
					int x1 = sc.nextInt();
					int y1 = sc.nextInt();
					Coordenada coordenada1 = new Coordenada(x0, y0);
					Coordenada coordenada2 = new Coordenada(x1, y1);
					pNMFile.linha(coordenada1, coordenada2, cor);
					System.out.println("-Reta Criada");
					break;
				}
				case retangulo: {
					int x0 = sc.nextInt();
					int y0 = sc.nextInt();
					int x1 = sc.nextInt();
					int y1 = sc.nextInt();
					Coordenada coordenada1 = new Coordenada(x0, y0);
					Coordenada coordenada2 = new Coordenada(x1, y1);
					pNMFile.retangulo(coordenada1, coordenada2, cor);
					System.out.println("-Retangulo Criado");
					break;
				}
				case salvar: {
					String fileName = sc.next();

					File file = new File(fileName);

					try {
						PrintStream ps = new PrintStream(file);

						try {
							if (cores == 'C')
								pNMFile.printImage(ps, PNMFileType.PORTABLE_BITMAP);
							else
								pNMFile.printImage(ps, PNMFileType.PORTABLE_GRAYMAP);
						} finally
						{
							ps.flush();
							ps.close();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println("-Imagem Salva");
					break;
				}
				case triangulo: {
					int x0 = sc.nextInt();
					int y0 = sc.nextInt();
					int x1 = sc.nextInt();
					int y1 = sc.nextInt();
					int x2 = sc.nextInt();
					int y2 = sc.nextInt();

					Coordenada coordenada1 = new Coordenada(x0, y0);
					Coordenada coordenada2 = new Coordenada(x1, y1);
					Coordenada coordenada3 = new Coordenada(x2, y2);
					pNMFile.triangulo(coordenada1, coordenada2, coordenada3, cor);
					System.out.println("-Triangulo Criado");
					break;
				}
				}
			} catch (Exception ex)
			{
				System.out.println("ERRO. Comando inv�lido verifique help");
				sc.nextLine();
				sc.reset();
			}

			System.out.println();
			System.out.print(">");
		}

		sc.close();

		// return ret.toArray(new String[ret.size()]);

	}

}
