package Grafos;


/* Código de autoria e extraido do site 
 * https://code.google.com/p/projeto-algoritmos-em-grafos-java/source/browse/branches/TrabalhoCarlosInterfaceGrafica/src/Grafo.java?r=24
 * Foram realizados ajustes e alterações no código original
 * */
import java.util.ArrayList;
import java.util.List;

public class Grafo {

        private List<Vertice> grafoList = new ArrayList<Vertice>();

        /* MPM - Comentado
        public void setVertices(List<Vertice> vertices) {

                this.grafo.addAll(vertices);
        }
*/
        public void adicionarVertice(Vertice novoVertice) {

                this.grafoList.add(novoVertice);
        }

        public List<Vertice> getVertices() {

                return this.grafoList;
        }

        // Método que retorna o vértice cuja descrição é igual à procurada.
        // MPM - Ajustado Nomenclatura
        public Vertice findVertice(String nome) {

                for (int i = 0; i < this.getVertices().size(); i++) {

                        if (nome.equalsIgnoreCase(this.getVertices().get(i).getDescricao())) {

                                return this.getVertices().get(i);
                        }
                }

                return null;

        }

}
