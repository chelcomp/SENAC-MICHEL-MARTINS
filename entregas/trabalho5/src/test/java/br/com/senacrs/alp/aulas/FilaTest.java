package br.com.senacrs.alp.aulas;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FilaTest {

	private static final int TAMANHO_MAXIMO_100 = 100;
	private static final int TAMANHO_MINIMO_2 = 2;
	private FilaFactory factory = FilaFactory.getInstancia();
	private Fila<Integer> obj = null;
	private static Random rand = new Random(System.currentTimeMillis());

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() throws Exception {

		String msg = null;

		obj = factory.criarFila();
		msg = String
				.format("%s não está criando o objeto necessário, está retonornando null",
						FilaFactory.class.getSimpleName());
		Assert.assertNotNull(msg, obj);
	}

	@After
	public void tearDown() throws Exception {

		obj = null;
	}

	@Test
	public void testInsercaoNull() {

		popularFila();
		exception.expect(IllegalArgumentException.class);
		obj.insercao(null);
	}

	private int popularFila() {
		
		int qtd = 0;
		Integer valor = null;
		
		qtd = getRandomTamanho();
		for (int i = 0; i < qtd; i++) {
			valor = Integer.valueOf(rand.nextInt());
			obj.insercao(valor);
		}
		
		return qtd;
	}

	private int getRandomTamanho() {
	
		int resultado = 0;
	
		resultado = rand.nextInt(TAMANHO_MAXIMO_100
				- TAMANHO_MINIMO_2)
				+ TAMANHO_MINIMO_2;
	
		return resultado;
	}

	@Test
	public void testTamanhoFilaVazia() {

		int obtido = 0;
		String tmpl = null;
		String msg = null;

		tmpl = "Nenhum elemento inserido, mas tamanho retorna %d. Fila %s";
		obtido = obj.tamanho();
		msg = String.format(tmpl, obtido, String.valueOf(obj));
		Assert.assertEquals(msg, 0, obtido);		
	}

	@Test
	public void testVaziaFilaVazia() {

		boolean obtido = false;
		String tmpl = null;
		String msg = null;

		tmpl = "Nenhum elemento inserido, mas teste se vazia retorna %b. Fila %s";
		obtido = obj.vazia();
		msg = String.format(tmpl, obtido, String.valueOf(obj));
		Assert.assertEquals(msg, true, obtido);		
	}

	@Test
	public void testVazia() {

		boolean obtido = false;
		String tmpl = null;
		String msg = null;

		tmpl = "Elemento inserido, mas teste se vazia retorna %b. Fila %s";
		obj.insercao(Integer.valueOf(rand.nextInt()));
		obtido = obj.vazia();
		msg = String.format(tmpl, obtido, String.valueOf(obj));
		Assert.assertEquals(msg, false, obtido);		
	}

	@Test
	public void testTamanho() {

		int esperado = 0;
		int obtido = 0;
		String tmpl = null;
		String msg = null;

		tmpl = "Inseridos %d elementos, mas tamanho retorna %d. Fila %s";
		esperado = popularFila();
		obtido = obj.tamanho();
		msg = String.format(tmpl, esperado, obtido, String.valueOf(obj));
		Assert.assertEquals(msg, esperado, obtido);		
	}

	@Test
	public void testRemoverFilaVazia() {
	
		exception.expect(IllegalArgumentException.class);
		obj.remocao();
	}

	@Test
	public void testInsercaoRemocaoObter() {

		int qtd = 0;
		Integer valor = null;
		Integer obtido = null;
		String tmpl = null;
		String msg = null;
		List<Integer> lista = null;

		tmpl = "Proximo elemento esperado %s, mas obtido %s. Fila %s";
		qtd = getRandomTamanho();
		qtd = 3;
		lista = new LinkedList<Integer>();
		for (int i = 0; i < qtd; i++) {
			valor = Integer.valueOf(rand.nextInt());
			obj.insercao(valor);
			lista.add(valor);
		}
		for (int i = 0; i < qtd; i++) {
			valor = lista.get(i);
			obtido = obj.remocao();
			msg = String.format(tmpl, String.valueOf(valor),
					String.valueOf(obtido), String.valueOf(obj));
			Assert.assertEquals(msg, valor, obtido);			
		}
	}

	@Test
	public void testEsvaziar() {

		int tamanho = 0;
		boolean vazia = false;
		String tmpl = null;
		String msg = null;

		tmpl = "Quantidade de elementos apos esvaziar %d, mas esperado 0. Teste de fila vazia %b. Fila %s";
		popularFila();
		obj.esvaziar();
		vazia = obj.vazia();
		tamanho = obj.tamanho();
		msg = String.format(tmpl, tamanho, vazia, String.valueOf(obj));
		Assert.assertEquals(msg, 0, tamanho);
		Assert.assertEquals(msg, true, vazia);
	}

}
