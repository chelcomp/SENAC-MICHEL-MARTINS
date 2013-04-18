package br.com.senacrs.alp.aulas;

import java.util.Comparator;

public class CompararInteiro implements Comparator<Integer> {

	@Override
	public int compare(Integer arg0, Integer arg1) {

		if ((arg0 % 2 == 0 && arg1 % 2 == 0)
				|| (arg0 % 2 != 0 && arg1 % 2 != 0)) {
			return arg0 - arg1;

		} else if (arg0 % 2 == 0 && arg1 % 2 != 0) {
			return -1;
		} else {
			return 1;
		}

	}
}
