package br.com.senacrs.alp.aulas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComparatorNumericoTest {

	private static final int MAXIMO_100 = 100;
	private static final int MINIMO_2 = 2;
	private ComparatorFactory factory = ComparatorFactory.getInstancia();
	private Comparator<Integer> obj = null; 
	private static Random rand = new Random(System.currentTimeMillis());

	@Before
	public void setUp() throws Exception {

		String msg = null;

		obj = factory.criarComparatorParesAntesDeImparesCrescente();
		msg = String
				.format("%s não está criando o objeto necessário, está retonornando null",
						ComparatorFactory.class.getSimpleName());
		Assert.assertNotNull(msg, obj);
	}

	@After
	public void tearDown() throws Exception {
		
		obj = null;
	}

	@Test(timeout = 500)
	public void testParesAntesImpares() {
		
		int par = 0;
		int impar = 0;
		int random = 0;
		int tam = 0;
		int ndx = 0;
		int obtido = 0;
		int esperado = 0;
		List<Integer> lista = null;
		String tmpl = null;
		String msg = null;
		
		random = getRandom();
		if (random % 2 == 0) {
			par = random;
			impar = random - 1;
		} else {
			impar = random;
			par = random + 1;
		}
		tam = getRandom();
		ndx = rand.nextInt(tam - 1) + 1;
		lista = new ArrayList<Integer>();
		for (int i = 0; i < ndx; i++) {
			lista.add(Integer.valueOf(impar));
		}
		for (int i = ndx; i < tam; i++) {
			lista.add(Integer.valueOf(par));
		}
		Collections.sort(lista, obj);
		tmpl = "Esperado na posição %d valor %d, mas obtido %d. Lista %s";
		esperado = par;
		for (int i = 0; i < tam - ndx; i++) {
			obtido = lista.get(i).intValue();
			msg = String.format(tmpl, i, esperado, obtido, lista.toString());			
			Assert.assertEquals(msg, esperado, obtido);
		}
		esperado = impar;
		for (int i = tam - ndx; i < tam; i++) {
			obtido = lista.get(i).intValue();
			msg = String.format(tmpl, i, esperado, obtido, lista.toString());			
			Assert.assertEquals(msg, esperado, obtido);
		}
	}

	private int getRandom() {
	
		int resultado = 0;
	
		resultado = rand.nextInt(MAXIMO_100
				- MINIMO_2)
				+ MINIMO_2;
	
		return resultado;
	}

	@Test(timeout = 500)
	public void testOrdemCrescente() {
		
		int tam = 0;
		int obtido = 0;
		int anterior = 0;
		boolean par = false;
		List<Integer> lista = null;
		String tmpl = null;
		String msg = null;
		
		tam = getRandom();
		lista = new ArrayList<Integer>();
		for (int i = 0; i < tam; i++) {
			lista.add(Integer.valueOf(getRandom()));
		}
		Collections.sort(lista, obj);
		tmpl = "Esperado na posição %d valor maior que o anterior %d, mas obtido %d. Lista %s";
		anterior = -1;
		par = true;
		for (int i = 0; i < tam; i++) {			
			obtido = lista.get(i).intValue();
			if (obtido % 2 == 0) {
				msg = "Houve uma troca inesperada de par para ímpares, verifique o teste anterior está OK" + lista.toString();
				Assert.assertTrue(msg, par);
			}
			if (obtido % 2 != 0) {
				anterior = -1;
				par = false;
			}
			msg = String.format(tmpl, i, anterior, obtido, lista.toString());			
			Assert.assertTrue(msg, obtido >= anterior);
			anterior = obtido;
		}
	}
}
