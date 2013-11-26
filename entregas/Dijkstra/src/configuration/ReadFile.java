package configuration;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import Grafos.Aresta;
import Grafos.Grafo;
import Grafos.Vertice;

public class ReadFile {

	private final static String COMENT = "#";
	private final static String EMPTY = "";

	String file;
	private String delimiter;

	public ReadFile(String pathFileName, String delimiter)
	{
		this.file = pathFileName;
		this.delimiter = delimiter;
	}

	public Grafo getAll()
	{
		FileReader fr;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Não foi possível abrir arquivo.");

		}

		

		BufferedReader br = new BufferedReader(fr);
		Grafo grafo = new Grafo();
		HashMap<Integer, Vertice> verticeMap = new HashMap<Integer, Vertice>();
		List<Aresta> arestaList = new LinkedList<Aresta>();
		Vertice vertice1;
		Vertice vertice2;

		try {

			while (br.ready())
			{
				String line = br.readLine();

				String[] values = line.split(delimiter);

				// ignora linhas de comentario
				if (values.length < 3
						|| values[0].startsWith(COMENT)
						|| values[0].trim() == EMPTY)
					return null;

				int a1 = Integer.valueOf(values[0]);
				int a2 = Integer.valueOf(values[1]);
				int p = Integer.valueOf(values[2]);

				if (verticeMap.containsKey(a1)) {
					vertice1 = verticeMap.get(a1);
				} else {
					vertice1 = new Vertice();
					vertice1.setDescricao(String.format("%s", a1));
					verticeMap.put(a1, vertice1);
					grafo.adicionarVertice(vertice1);
				}

				if (verticeMap.containsKey(a2)) {
					vertice2 = verticeMap.get(a2);
				} else {
					vertice2 = new Vertice();
					vertice2.setDescricao(String.format("%s", a2));
					verticeMap.put(a2, vertice2);
					grafo.adicionarVertice(vertice2);
				}

				Aresta aresta = new Aresta(vertice1, vertice2);
				aresta.setPeso(p);

				arestaList.add(aresta);
			}
			
			
			return grafo;

		} catch (IOException e) {
			throw new IllegalArgumentException("Formato de arquivo invalido");
		} finally
		{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
