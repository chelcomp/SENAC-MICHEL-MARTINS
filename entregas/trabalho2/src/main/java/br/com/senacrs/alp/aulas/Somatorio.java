package br.com.senacrs.alp.aulas;

public class Somatorio {

	private static Somatorio instancia = new Somatorio();

	private Somatorio() {

		super();
	}

	public double somaTotal(double[] valores) {

		double resultado = 0.0;

		// Implementar
		if (valores == null)
			resultado = Double.NaN;
		else
			for (double d : valores) {
				resultado += d;
			}

		return resultado;
	}

	public static Somatorio getInstancia() {

		return Somatorio.instancia;
	}
}
