import java.util.List;
import java.util.Scanner;

import configuration.ReadFile;
import Grafos.Dijkstra;
import Grafos.Grafo;
import Grafos.Vertice;

public class main {

	private static Grafo grafo;
	private final static String DELIMITER = " ";
	private final static String INTIAL_FILE = "C:\\GRAFO.TXT";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		lerArquivo(INTIAL_FILE);
		System.out.println("- Arquivo inicial carregado: " + INTIAL_FILE);
		leTeclado();
	}

	
	private static void lerArquivo(String arquivo)
	{
		// Le arquivo com dados
		ReadFile rf = new ReadFile(arquivo, DELIMITER);
		grafo = rf.getAll();
	}

	private static void leTeclado()
	{
		Integer a1;
		Integer a2;

		Scanner sc = new Scanner(System.in);
		sc.reset();
		try {
			System.out.print("Digite a Origem e o Destino: ");

			a1 = sc.nextInt();
			a2 = sc.nextInt();

		} finally {
			sc.close();
		}

		// Cata os vertices no grafo
		Vertice v1 = grafo.findVertice(a1.toString());
		Vertice v2 = grafo.findVertice(a2.toString());
		String msgErro = "Vertice não localizado: %s";

		// Valida vertice não localizado
		if (v1 == null)
			System.out.println(String.format(msgErro, a1));
		if (v2 == null)
			System.out.println(String.format(msgErro, a2));
		if (v1 == null || v2 == null)
			return;

		// Calcula a menor distancia
		Dijkstra dijkstra = new Dijkstra();
		List<Vertice> verticeList = dijkstra.encontrarMenorCaminhoDijkstra(grafo, v1, v2);

		StringBuilder caminho = new StringBuilder();
		int custo = 0;
		for (Vertice vertice : verticeList) {
			caminho.append(" > ");
			caminho.append(vertice.getDescricao());
			custo = vertice.getDistancia();
		}

		String out = "\r\nOrigem: %d" +
				"\r\nDestino: %d" +
				"\r\nMenor Caminho: %s" +
				"\r\nMenor Custo: %d";

		out = String.format(out, a1, a2, caminho.toString(), custo);
		System.out.println(out);
		
	}
}
