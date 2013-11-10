package configuration;

public class KeyPair<K, V> {
	private K key;
	private V value;
	
	KeyPair()
	{}
	
	KeyPair(K key, V value)
	{
		setKey(key);
		setValue(value);
	}
	
	public K getKey() {
		return key;
	}
	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}
	
}
