package configuration;
import java.util.LinkedList;
import java.util.List;


public class KeyPairList<K,V> {
	List<KeyPair<K,V>> _keyPairList;
	
	KeyPairList()
	{
		_keyPairList = new LinkedList<KeyPair<K,V>>();
	}
	
	public void put(K key, V value)
	{
		KeyPair<K, V> kpl = new KeyPair<K,V>(key, value);
		_keyPairList.add(kpl);		
	}	
	
	public void put(KeyPair<K, V> keyPair)
	{
		
		_keyPairList.add(keyPair);		
	}
	
	public List<KeyPair<K,V>> getList() {
		return _keyPairList;
	}

	

}
