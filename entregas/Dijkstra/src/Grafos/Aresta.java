package Grafos;

/* Código de autoria e extraido do site 
 * https://code.google.com/p/projeto-algoritmos-em-grafos-java/source/browse/branches/TrabalhoCarlosInterfaceGrafica/src/Aresta.java?r=24
 * Foram realizados ajustes e alterações no código original
 * */

public class Aresta {

	private int peso;
	private Vertice origem;
	private Vertice destino;

	public Aresta(Vertice v1, Vertice v2) {

		this.peso = 1;
		this.origem = v1;
		this.destino = v2;
		// MPM - Adicionado
		v1.getArestas().add(this);

	}

	public void setPeso(int novoPeso) {

		this.peso = novoPeso;
	}

	public int getPeso() {

		return peso;
	}

	public void setDestino(Vertice destino) {
		this.destino = destino;
	}

	public Vertice getDestino() {
		return destino;
	}

	public void setOrigem(Vertice origem) {
		this.origem = origem;
	}

	public Vertice getOrigem() {
		return origem;
	}

}
