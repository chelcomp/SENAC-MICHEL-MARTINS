package br.com.senacrs.alp.aulas;

import java.util.Random;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ListaTest {

	private static final int TAMANHO_MAXIMO_DA_LISTA_100 = 100;
	private static final int TAMANHO_MINIMO_DA_LISTA_2 = 2;
	private ListaFactory factory = ListaFactory.getInstancia();
	private Lista<Integer> obj = null;
	private static Random rand = new Random(System.currentTimeMillis());

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() throws Exception {

		String msg = null;

		obj = factory.criarLista();
		msg = String
				.format("%s não está criando o objeto necessário, está retonornando null",
						ListaFactory.class.getSimpleName());
		Assert.assertNotNull(msg, obj);
	}

	@After
	public void tearDown() throws Exception {

		obj = null;
	}

	@Test
	public void testAdicionarFinalNull() {

		popularLista();
		exception.expect(IllegalArgumentException.class);
		obj.adicionarFinal(null);
	}

	private int popularLista() {
		
		int qtd = 0;
		Integer valor = null;
		
		qtd = getRandomTamanhoLista();
		for (int i = 0; i < qtd; i++) {
			valor = Integer.valueOf(rand.nextInt());
			obj.adicionarFinal(valor);
		}
		
		return qtd;
	}

	private int getRandomTamanhoLista() {
	
		int resultado = 0;
	
		resultado = rand.nextInt(TAMANHO_MAXIMO_DA_LISTA_100
				- TAMANHO_MINIMO_DA_LISTA_2)
				+ TAMANHO_MINIMO_DA_LISTA_2;
	
		return resultado;
	}

	@Test
	public void testAdicionarFinal() {

		int qtd = 0;
		Integer ultimo = null;
		Integer obtido = null;
		String tmpl = null;
		String msg = null;

		tmpl = "Ultimo elemento inserido ao final %s, mas obtido %s. Lista %s";
		qtd = getRandomTamanhoLista();
		for (int i = 0; i < qtd; i++) {
			ultimo = Integer.valueOf(rand.nextInt());
			obj.adicionarFinal(ultimo);
			obtido = obj.obterUltimo();
			msg = String.format(tmpl, String.valueOf(ultimo),
					String.valueOf(obtido), String.valueOf(obj));
			Assert.assertEquals(msg, ultimo, obtido);
		}
	}

	@Test
	public void testAdicionarInicioNull() {

		popularLista();
		exception.expect(IllegalArgumentException.class);
		obj.adicionarInicio(null);
	}

	@Test
	public void testAdicionarInicio() {

		int qtd = 0;
		Integer primeiro = null;
		Integer obtido = null;
		String tmpl = null;
		String msg = null;

		tmpl = "Ultimo elemento inserido no inicio %s, mas obtido %s. Lista %s";
		qtd = getRandomTamanhoLista();
		for (int i = 0; i < qtd; i++) {
			primeiro = Integer.valueOf(rand.nextInt());
			obj.adicionarInicio(primeiro);
			obtido = obj.obterPrimeiro();
			msg = String.format(tmpl, String.valueOf(primeiro),
					String.valueOf(obtido), String.valueOf(obj));
			Assert.assertEquals(msg, primeiro, obtido);
		}
	}

	@Test
	public void testAdicionarPosicaoNull() {

		int qtd = 0;
		int posicao = 0;

		qtd = popularLista();
		posicao = rand.nextInt(qtd);
		exception.expect(IllegalArgumentException.class);
		obj.adicionarPosicao(posicao, null);
	}

	@Test
	public void testAdicionarPosicao() {

		int qtd = 0;
		int posicao = 0;
		Integer valor = null;
		Integer obtido = null;
		String tmpl = null;
		String msg = null;

		tmpl = "Elemento %s inserido na posicao %d, mas elemento obtido para esta posicao eh %s. Lista %s";
		qtd = getRandomTamanhoLista();
		for (int i = 0; i < qtd; i++) {
			valor = Integer.valueOf(rand.nextInt());
			posicao = rand.nextInt(i + 1);
			obj.adicionarPosicao(posicao, valor);
			obtido = obj.obterPosicao(posicao);
			msg = String.format(tmpl, String.valueOf(valor), posicao,
					String.valueOf(obtido), String.valueOf(obj));
			Assert.assertEquals(msg, valor, obtido);
		}
	}

	@Test
	public void testAdicionarPosicaoZero() {

		int posicao = 0;
		Integer esperado = null;
		Integer obtido = null;
		String tmpl = null;
		String msg = null;

		tmpl = "Elemento adicionado na posicao 0 %s nao eh o elemento na primeira posicao, %s. Lista %s";
		popularLista();
		esperado = Integer.valueOf(rand.nextInt());
		posicao = 0;
		obj.adicionarPosicao(posicao, esperado);
		obtido = obj.obterPrimeiro();
		msg = String.format(tmpl, esperado, obtido, String.valueOf(obj));
		Assert.assertEquals(msg, esperado, obtido);
	}

	@Test
	public void testAdicionarPosicaoNegativa() {

		int qtd = 0;
		int posicao = 0;
		int tamanho = 0;
		Integer valor = null;
		String tmpl = null;
		String msg = null;

		tmpl = "Tamanho original %d e apos tentativa de adicao %d, mas deveria ser o mesmo. Lista %s";
		qtd = popularLista();
		valor = Integer.valueOf(rand.nextInt());
		posicao = -1 - rand.nextInt(qtd);
		exception.expect(IllegalArgumentException.class);
		obj.adicionarPosicao(posicao, valor);
		tamanho = obj.obterTamanho();
		msg = String.format(tmpl, qtd, tamanho, qtd, String.valueOf(obj));
		Assert.assertEquals(msg, qtd, tamanho);
	}

	@Test
	public void testAdicionarPosicaoEmTamanho() {

		int qtd = 0;
		int posicao = 0;
		Integer esperado = null;
		Integer obtido = null;
		String tmpl = null;
		String msg = null;

		tmpl = "Elemento adicionado na posicao igual a tamanho, %s, nao eh o elemento na ultima posicao, %s. Lista %s";
		qtd = popularLista();
		posicao = qtd;
		esperado = Integer.valueOf(rand.nextInt());
		try {
			obj.adicionarPosicao(posicao, esperado);
		} catch (IllegalArgumentException e) {
			Assert.fail("Adicionar informando o tamanho como argumento eh valido, equivale a adicionarFinal");
		}
		obtido = obj.obterUltimo();
		msg = String.format(tmpl, esperado, obtido, String.valueOf(obj));
		Assert.assertEquals(msg, esperado, obtido);
	}

	@Test
	public void testAdicionarPosicaoAcimaDeTamanho() {

		int qtd = 0;
		int posicao = 0;
		int tamanho = 0;
		Integer valor = null;
		String tmpl = null;
		String msg = null;

		tmpl = "Tamanho original %d e apos tentativa de adicao %d, mas deveria ser o mesmo. Lista %s";
		qtd = popularLista();
		valor = Integer.valueOf(rand.nextInt());
		posicao = qtd + rand.nextInt(TAMANHO_MAXIMO_DA_LISTA_100);
		exception.expect(IllegalArgumentException.class);
		obj.adicionarPosicao(posicao, valor);
		tamanho = obj.obterTamanho();
		msg = String.format(tmpl, qtd, tamanho, String.valueOf(obj));
		Assert.assertEquals(msg, qtd, tamanho);
	}

	@Test
	public void testObterPrimeiroListaVazia() {

		exception.expect(IllegalArgumentException.class);
		obj.obterPrimeiro();
	}

	@Test
	public void testObterPrimeiro() {

		int qtd = 0;
		Integer primeiro = null;
		Integer valor = null;
		Integer obtido = null;
		String tmpl = null;
		String msg = null;

		tmpl = "Elemento esperado para primeira posicao %s, mas obtido %s. Lista %s";
		qtd = getRandomTamanhoLista();
		primeiro = Integer.valueOf(rand.nextInt());
		obj.adicionarFinal(primeiro);
		for (int i = 1; i < qtd; i++) {
			valor = Integer.valueOf(rand.nextInt());
			obj.adicionarFinal(valor);
		}
		obtido = obj.obterPrimeiro();
		msg = String.format(tmpl, String.valueOf(primeiro),
				String.valueOf(obtido), String.valueOf(obj));
		Assert.assertEquals(msg, primeiro, obtido);
	}

	@Test
	public void testObterUltimoListaVazia() {

		exception.expect(IllegalArgumentException.class);
		obj.obterUltimo();
	}

	@Test
	public void testObterUltimo() {

		int qtd = 0;
		Integer ultimo = null;
		Integer valor = null;
		Integer obtido = null;
		String tmpl = null;
		String msg = null;

		tmpl = "Elemento esperado para ultima posicao %s, mas obtido %s. Lista %s";
		qtd = getRandomTamanhoLista();
		ultimo = Integer.valueOf(rand.nextInt());
		obj.adicionarInicio(ultimo);
		for (int i = 1; i < qtd; i++) {
			valor = Integer.valueOf(rand.nextInt());
			obj.adicionarInicio(valor);
		}
		obtido = obj.obterUltimo();
		msg = String.format(tmpl, String.valueOf(ultimo),
				String.valueOf(obtido), String.valueOf(obj));
		Assert.assertEquals(msg, ultimo, obtido);
	}	
	
	@Test
	public void testObterPosicaoZeroListaVazia() {

		exception.expect(IllegalArgumentException.class);
		obj.obterPosicao(0);
	}

	@Test
	public void testObterPosicao() {

		int qtd = 0;
		int posicao = 0;
		Integer valor = null;
		Integer obtido = null;
		Integer esperado = null;
		String tmpl = null;
		String msg = null;

		tmpl = "Elemento %s na posicao %d, mas elemento esperado nesta posicao eh %s. Lista %s";
		qtd = getRandomTamanhoLista();
		posicao = rand.nextInt(qtd);
		for (int i = 0; i < qtd; i++) {
			valor = Integer.valueOf(rand.nextInt());
			if (i == posicao) {
				esperado = valor;
			}
			obj.adicionarFinal(valor);
		}
		obtido = obj.obterPosicao(posicao);
		msg = String.format(tmpl, String.valueOf(esperado), posicao,
				String.valueOf(obtido), String.valueOf(obj));
		Assert.assertEquals(msg, esperado, obtido);
	}


	@Test
	public void testObterTamanhoListaVazia() {

		int tamanaho = 0;
		String tmpl = null;
		String msg = null;

		tmpl = "Tamanho esperado eh 0, mas obtido %d. Lista %s";
		tamanaho = obj.obterTamanho();
		msg = String.format(tmpl, tamanaho, String.valueOf(obj));
		Assert.assertEquals(msg, 0, tamanaho);
	}

	@Test
	public void testObterTamanho() {

		int qtd = 0;
		int tamanaho = 0;
		String tmpl = null;
		String msg = null;

		tmpl = "Tamanho esperado %d, mas obtido %d. Lista %s";
		qtd = popularLista();
		tamanaho = obj.obterTamanho();
		msg = String.format(tmpl, qtd, tamanaho, String.valueOf(obj));
		Assert.assertEquals(msg, qtd, tamanaho);
	}

	@Test
	public void testRemoverPosicao() {

		int qtd = 0;
		int posicao = 0;
		int tamanho = 0;
		Integer valor = null;
		Integer obtido = null;
		Integer esperado = null;
		String tmpl1 = null;
		String tmpl2 = null;
		String msg1 = null;
		String msg2 = null;

		tmpl1 = "Elemento %s removido da posicao %d, mas elemento esperado eh %s. Lista %s";
		tmpl2 = "Tamanho original %d e apos a remocao %d, mas esperado eh %d. Lista %s";
		qtd = getRandomTamanhoLista();
		posicao = rand.nextInt(qtd);
		for (int i = 0; i < qtd; i++) {
			valor = Integer.valueOf(rand.nextInt());
			if (i == posicao) {
				esperado = valor;
			}
			obj.adicionarFinal(valor);
		}
		obtido = obj.removerPosicao(posicao);
		msg1 = String.format(tmpl1, String.valueOf(esperado), posicao,
				String.valueOf(obtido), String.valueOf(obj));
		Assert.assertEquals(msg1, esperado, obtido);
		tamanho = obj.obterTamanho();
		msg2 = String.format(tmpl2, qtd, tamanho, qtd - 1, String.valueOf(obj));
		Assert.assertEquals(msg2, qtd - 1, tamanho);
	}

	@Test
	public void testRemoverPosicaoZero() {

		int qtd = 0;
		int posicao = 0;
		int tamanho = 0;
		Integer valor = null;
		Integer obtido = null;
		Integer esperado = null;
		String tmpl1 = null;
		String tmpl2 = null;
		String msg1 = null;
		String msg2 = null;

		tmpl1 = "Elemento %s removido da posicao %d, mas elemento esperado eh %s. Lista %s";
		tmpl2 = "Tamanho original %d e apos a remocao %d, mas esperado eh %d. Lista %s";
		qtd = getRandomTamanhoLista();
		valor = Integer.valueOf(rand.nextInt());
		obj.adicionarFinal(valor);
		esperado = valor;
		for (int i = 1; i < qtd; i++) {
			valor = Integer.valueOf(rand.nextInt());
			obj.adicionarFinal(valor);
		}
		obtido = obj.removerPosicao(posicao);
		msg1 = String.format(tmpl1, String.valueOf(esperado), posicao,
				String.valueOf(obtido), String.valueOf(obj));
		Assert.assertEquals(msg1, esperado, obtido);
		tamanho = obj.obterTamanho();
		msg2 = String.format(tmpl2, qtd, tamanho, qtd - 1, String.valueOf(obj));
		Assert.assertEquals(msg2, qtd - 1, tamanho);
	}

	@Test
	public void testRemoverPosicaoNegativa() {

		int qtd = 0;
		int posicao = 0;
		int tamanho = 0;
		String tmpl = null;
		String msg = null;

		tmpl = "Tamanho original %d e apos tentativa de remocao %d, mas deveria ser o mesmo. Lista %s";
		qtd = popularLista();
		posicao = -1 - rand.nextInt(qtd);
		exception.expect(IllegalArgumentException.class);
		obj.removerPosicao(posicao);
		tamanho = obj.obterTamanho();
		msg = String.format(tmpl, qtd, tamanho, qtd, String.valueOf(obj));
		Assert.assertEquals(msg, qtd, tamanho);
	}

	@Test
	public void testRemoverPosicaoEmTamanho() {

		int qtd = 0;
		int posicao = 0;
		int tamanho = 0;
		String tmpl = null;
		String msg = null;

		tmpl = "Tamanho original %d e apos tentativa de remocao %d, mas deveria ser o mesmo. Lista %s";
		qtd = popularLista();
		posicao = qtd;
		exception.expect(IllegalArgumentException.class);
		obj.removerPosicao(posicao);
		tamanho = obj.obterTamanho();
		msg = String.format(tmpl, qtd, tamanho, String.valueOf(obj));
		Assert.assertEquals(msg, qtd, tamanho);
	}

	@Test
	public void testRemoverPosicaoAcimaDeTamanho() {

		int qtd = 0;
		int posicao = 0;
		int tamanho = 0;
		String tmpl = null;
		String msg = null;

		tmpl = "Tamanho original %d e apos tentativa de remocao %d, mas deveria ser o mesmo. Lista %s";
		qtd = popularLista();
		posicao = qtd + rand.nextInt(TAMANHO_MAXIMO_DA_LISTA_100);
		exception.expect(IllegalArgumentException.class);
		obj.removerPosicao(posicao);
		tamanho = obj.obterTamanho();
		msg = String.format(tmpl, qtd, tamanho, qtd, String.valueOf(obj));
		Assert.assertEquals(msg, qtd, tamanho);
	}

	@Test
	public void testEsvaziar() {

		int tamanho = 0;
		String tmpl = null;
		String msg = null;

		tmpl = "Quantidade de elementos apos esvaziar %d, mas esperado 0. Lista %s";
		popularLista();
		obj.esvaziar();
		tamanho = obj.obterTamanho();
		msg = String.format(tmpl, tamanho, String.valueOf(obj));
		Assert.assertEquals(msg, 0, tamanho);
	}

}
