package configuration;

public class Config<K,V> {
	
	private final static String COMENT = "#";
	private final static String EMPTY = "";

	public KeyPair<K, V> getKeyPairFromString(String line, String delimiter) {

		KeyPair<K, V> kp = null;

		String[] values = line.split(delimiter, 2);

		// ignora linhas de comentario
		if (values.length < 2
				|| values[0].startsWith(COMENT)
				|| values[0].trim() == EMPTY
				|| values[1].trim() == EMPTY)
			return null;

		String[] valueClean = values[1].split(delimiter);

		if (values.length < 1
				|| values[1].trim() == EMPTY)
			return null;

		@SuppressWarnings("unchecked")
		K key = (K) values[0];
		@SuppressWarnings("unchecked")
		V value = (V) valueClean[0];

		kp = new KeyPair<K, V>(key, value);

		return kp;
	}
}
