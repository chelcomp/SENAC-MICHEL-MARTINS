package tree;
public class Node<K extends Comparable<K>, V> {
	private boolean vermelho;

	private Node<K, V> parent;
	private Node<K, V> left;
	private Node<K, V> right;
	private V value;
	private K chave;

	// private N Int;

	public Node(K key, V value)
	{
		setKey(key);
		setValue(value);
	}

	public int size()
	{
		int n = 1;
		if (getLeft() != null)
			n += getLeft().size();

		if (getRight() != null)
			n += getRight().size();

		return n;
	}

	public int altura()
	{
		int l = 0;
		int r = 0;

		if (getLeft() != null)
			l = getLeft().altura();

		if (getRight() != null)
			r += getRight().altura();

		if (l > r)
			return l + 1;
		else
			return r + 1;

	}

	public Node<K, V> getRight() {
		return right;
	}

	public void setRight(Node<K, V> direita) {
		right = direita;
	}

	public Node<K, V> getLeft() {
		return left;
	}

	public void setLeft(Node<K, V> esquerda) {
		left = esquerda;
	}

	public boolean isRed() {
		return vermelho;
	}

	public boolean isBlack() {
		return !vermelho;
	}

	public void setRed(boolean ehVermelho) {
		this.vermelho = ehVermelho;
	}

	public void setRead() {
		this.vermelho = true;
	}

	public void setBlack() {
		this.vermelho = false;
	}

	public Node<K, V> getGrandparent()
	{
		if (parent == null)
			return null;
		else
			return parent.getParent();
	}

	public Node<K, V> getUncle()
	{
		Node<K, V> grandparent = getGrandparent();
		if (grandparent == null) {
			return null;
		}
		else if (parent == grandparent.getLeft()) {
			return grandparent.getRight();
		}
		else {
			return grandparent.getLeft();
		}
	}

	public Node<K, V> getSibling()
	{
		if (parent == null)
		{
			return null;
		}
		if (this == parent.getLeft())
		{
			return parent.getRight();
		}
		else
		{
			return parent.getLeft();
		}

	}

	public Node<K, V> AddFilho(Node<K, V> nodoFilho)
	{
		nodoFilho.setRead();

		if (this.getKey().compareTo(nodoFilho.getKey()) > 0)
		{
			if (left == null)
			{
				nodoFilho.setParent(this);
				left = nodoFilho;
			}
			else
			{
				return left.AddFilho(nodoFilho);
			}
		}
		else if (this.getKey().compareTo(nodoFilho.getKey()) < 0)
		{
			if (right == null)
			{
				nodoFilho.setParent(this);
				right = nodoFilho;
			}
			else
			{
				return right.AddFilho(nodoFilho);
			}
		}

		return nodoFilho;

	}

	public Node<K, V> getParent() {
		return parent;
	}

	public void setParent(Node<K, V> pai) {
		parent = pai;
	}

	public K getKey() {
		return chave;
	}

	public void setKey(K chave) {
		this.chave = chave;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	

	public String toString() {
		return String.format("{%s, %s}", getKey().toString(),getValue().toString());
		
	}

	
	
	public StringBuilder getTreeString() {

		StringBuilder sb = new StringBuilder();

		StringBuilder chave = new StringBuilder();
		if (getParent() != null)
			if (getParent().getLeft() == this)
				chave.append("___ L");
			else
				chave.append("___ R");

		if (isRed()) {
			chave.append("R");
		}
		else {
			chave.append("B");
		}

		chave.append(toString());

		sb.append(chave);
		sb.append("\r\n");
		StringBuilder l = new StringBuilder();
		StringBuilder r = new StringBuilder();

		if (getLeft() != null)
		{
			l = getLeft().getTreeString();
		}

		if (getRight() != null)
		{
			r = getRight().getTreeString();
		}

		String spc = " ";

		if (getParent() != null && (l.length() > 0 || r.length() > 0))
		{
			int i = 5;;

			for (int j = 0; j < i; j++) {
				spc += " ";
			}
		}

		if (l.length() > 0)
		{
			String[] linhasLeft = l.toString().split("\r\n");
			boolean primeiraLinha = true;
			for (String item : linhasLeft) {
				sb.append(spc);
				if (r.length() > 0 || primeiraLinha) {
					sb.append("|");
					primeiraLinha = false;
				}
				else {
					sb.append(" ");
				}
				sb.append(item);
				sb.append("\r\n");
			}
		}

		if (r.length() > 0) {

			String[] linhaRight = r.toString().split("\r\n");
			boolean primeiraLinha = true;
			for (String item : linhaRight) {
				sb.append(spc);
				if (primeiraLinha) {
					sb.append("|");
					primeiraLinha = false;
				}
				else
					sb.append(" ");
				sb.append(item);
				sb.append("\r\n");
			}

		}

		return sb;

	}
	
}
