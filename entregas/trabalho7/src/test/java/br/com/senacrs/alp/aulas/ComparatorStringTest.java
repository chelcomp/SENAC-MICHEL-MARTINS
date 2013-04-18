package br.com.senacrs.alp.aulas;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComparatorStringTest {

	private ComparatorFactory factory = ComparatorFactory.getInstancia();
	private Comparator<String> obj = null; 

	@Before
	public void setUp() throws Exception {

		String msg = null;

		obj = factory.criarComparatorOrdemAlfabeticaReversa();
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
	public void testOrdemAlfabeticaReversa() {
		
		String[] entrada = null;
		String[] esperado = null;
		int tam = 0;
		int ndx = 0;
		String tmpl = null;
		String msg = null;
			
		tam = 'Z' - 'A' + 1;
		entrada = new String[tam];
		esperado = new String[tam];
		ndx = 0;
		for (char i = 'A'; i <= 'Z'; i++) {
			entrada[ndx++] = String.valueOf(i);
		}
		ndx = 0;
		for (char i = 'Z'; i >= 'A'; i--) {
			esperado[ndx++] = String.valueOf(i);
		}
		Arrays.sort(entrada, obj);
		tmpl = "Esperado array %s, mas obtido %s";
		msg = String.format(tmpl, Arrays.toString(esperado), Arrays.toString(entrada));
		Assert.assertArrayEquals(msg, esperado, entrada);
	}
}
