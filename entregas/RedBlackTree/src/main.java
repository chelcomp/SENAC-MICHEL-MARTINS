import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import configuration.Config;
import configuration.KeyPair;
import configuration.KeyPairList;
import configuration.ReadFile;

import tree.Node;
import tree.Tree;

public class main {

	private static Tree<String, String> _tree;
	private final static String DELIMITER = "=";
	private final static String INTIAL_FILE = "C:\\X.TXT";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		_tree = new Tree<String, String>();
		lerArquivo(INTIAL_FILE);
		System.out.println("- Arquivo inicial carregado: " + INTIAL_FILE);
		// Escreve saida bonitinha no output
		// System.out.println(saida);

		leTeclado();
	}

	private static void salvarArquivo(String arquivo)
	{
		// Escreve saida bonitinha em disco
		String saida = _tree.toString();
		File f = new File(arquivo);
		FileWriter fw;
		try {
			fw = new FileWriter(f);

			fw.write(saida);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void lerArquivo(String arquivo)
	{
		// Le arquivo com dados
		ReadFile<String, String> rf = new ReadFile<String, String>(arquivo, DELIMITER);
		KeyPairList<String, String> ret = rf.getAll();

		// Cria árvore
		for (KeyPair<String, String> keyPair : ret.getList()) {
			String key = keyPair.getKey();
			String value = keyPair.getValue();
			Node<String, String> nodo = new Node<String, String>(key, value);
			_tree.add(nodo);
		}

	}

	private static void leTeclado()
	{
		// List<String> ret = new ArrayList<String>();
		Scanner sc = new Scanner(System.in);
		sc.reset();
		String line = "";

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
				case adicionar: {
					line = sc.next();
					KeyPair<String, String> kp = new Config<String, String>().getKeyPairFromString(line, DELIMITER);

					Node<String, String> node = new Node<String, String>(kp.getKey(), kp.getValue());

					_tree.add(node);
					System.out.println("- chave adicionada: " + node.toString());
					break;
				}

				case consultar: {
					line = sc.next();
					Node<String, String> node = _tree.getNode(line);

					System.out.println("- " + node.toString());
					break;
				}
				case ler: {
					line = sc.next();
					lerArquivo(line);

					System.out.println("- Arquivo carregado: " + line);
					break;
				}
				case salvar: {
					line = sc.next();
					salvarArquivo(line);

					System.out.println("- Arquivo salvo: " + line);
					break;
				}
				case imprimir: {
					System.out.println(_tree.toString());
					break;
				}
				case remover: {
					line = sc.next();
					boolean r = _tree.remove(line);

					if (r)
						System.out.println("- chave removida");
					else
						System.out.println("- chave não localizada para remoção");
					break;
				}
				default:
					System.out.println("Comandos disponíveis:\r\n adicionar\r\n consultar\r\n remover\r\n sair\r\n imprimir\r\n salvar\r\nler");
					break;

				}
			} catch (Exception ex)
			{
				System.out.println("ERRO. Comando inválido. Digite ajuda");
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
