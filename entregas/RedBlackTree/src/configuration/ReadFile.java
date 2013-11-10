package configuration;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ReadFile<K,V> {

	
	String file;
	String delimiter;
	
	public ReadFile(String pathFileName, String delimiter)
	{
		this.file = pathFileName;
		this.delimiter = delimiter;
	}
	
	public static <OUT> OUT Convert(Object o, Class<OUT> clazz) {
	    try {
	        return clazz.cast(o);
	    } catch(ClassCastException e) {
	        return null;
	    }
	}
	
	

	public KeyPairList<K,V> getAll() 
	{
		FileReader fr;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Não foi possível abrir arquivo.");
			
		}
		
		KeyPairList<K, V> kpl = new KeyPairList<K, V>();
		
		BufferedReader br = new BufferedReader( fr );
		
		try {
			while (br.ready())
			{
				String line = br.readLine();
				KeyPair<K, V> kp =  new Config<K,V>().getKeyPairFromString(line, delimiter);
				
				if(kp == null)
					continue;
				
				kpl.put(kp);
				
			}
			
			return kpl;
			
		} catch (IOException e) {
			throw new IllegalArgumentException("Formato de arquivo invalido");
		}
		finally
		{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	
	
}
