package br.com.senacrs.alp.aulas;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.senacrs.alp.aulas.InterpretadorStrings.TIPO_STRING;

public class InterpretadorStringsTest {

	private static final Factory factory = Factory.getInstancia();
	private InterpretadorStrings obj = null;
	private final String[] COMENTARIOS = new String[] {
			InterpretadorStrings.PREFIXO_COMENTARIO,
			InterpretadorStrings.PREFIXO_COMENTARIO + " ",
			" " + InterpretadorStrings.PREFIXO_COMENTARIO,
			"\t" + InterpretadorStrings.PREFIXO_COMENTARIO,
	};
	private final String[] BEM_FORMADA = new String[] {
			"A" + InterpretadorStrings.SEPARADOR + "B",
			" A" + InterpretadorStrings.SEPARADOR + "B",
			" A" + InterpretadorStrings.SEPARADOR + "B ",
			"A " + InterpretadorStrings.SEPARADOR + " B",
			"\tA\t" + InterpretadorStrings.SEPARADOR + "\tB\t",
			"A\t" + InterpretadorStrings.SEPARADOR + "\tB",
			"\tA" + InterpretadorStrings.SEPARADOR + "B",
	};
	private final String[] MAL_FORMADA = new String[] {
			null,
			"",
			InterpretadorStrings.SEPARADOR,
			"A" + InterpretadorStrings.SEPARADOR,
			InterpretadorStrings.SEPARADOR + "B",
			" " + InterpretadorStrings.SEPARADOR + "B",
			"A" + InterpretadorStrings.SEPARADOR + " ",
			"A" + InterpretadorStrings.SEPARADOR + "\t",
	};

	@Before
	public void setUp() throws Exception {

		obj = factory.criarInterpretador();
		Assert.assertNotNull(obj);
	}

	@After
	public void tearDown() throws Exception {

		obj = null;
	}

	@Test
	public void testLerStringComentario() {

		TIPO_STRING resultado = null;
		String msg = null;
		String tmpl = null;
		
		tmpl = "String a ser aceita: [%s]";
		for (String c : COMENTARIOS) {
			resultado = obj.lerString(c);
			msg = String.format(tmpl, c);
			Assert.assertEquals(msg, TIPO_STRING.COMENTARIO, resultado);			
		}
	}

	@Test
	public void testLerStringBemFormada() {

		TIPO_STRING resultado = null;
		String msg = null;
		String tmpl = null;
		
		tmpl = "String a ser aceita: [%s]";
		for (String c : BEM_FORMADA) {
			resultado = obj.lerString(c);
			msg = String.format(tmpl, c);
			Assert.assertEquals(msg, TIPO_STRING.BEM_FORMADA, resultado);			
		}
	}
	
	@Test
	public void testLerStringMalFormada() {

		TIPO_STRING resultado = null;
		String msg = null;
		String tmpl = null;
		
		tmpl = "String a ser rejeitada: [%s]";
		for (String c : MAL_FORMADA) {
			resultado = obj.lerString(c);
			msg = String.format(tmpl, c);
			Assert.assertEquals(msg, TIPO_STRING.MAL_FORMADA, resultado);			
		}
	}
}
