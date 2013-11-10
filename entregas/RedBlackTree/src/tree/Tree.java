package tree;

public class Tree<K extends Comparable<K>, V> {
	private Node<K, V> raiz;

	public void add(Node<K, V> nodoFilho)
	{

		if (raiz == null)
		{
			raiz = nodoFilho;
			raiz.setParent(null);
		}
		else
		{
			nodoFilho = raiz.AddFilho(nodoFilho);
		}

		addCaso1(nodoFilho);

	}

	public Boolean remove(K chave)
	{
		Node<K, V> n = getNode(chave);
		if (n == null)
			return true; // Key not found, do nothing
		if (n.getLeft() != null && n.getRight() != null) {
			Node<K, V> pred = maximumNode(n.getRight());
			n.setKey(pred.getKey());
			n.setValue(pred.getValue());
			n = pred;
		}

		Node<K, V> child = (n.getRight() == null) ? n.getLeft() : n.getRight();
		if (isBlack(n)) {
			n.setRed(!isBlack( child ));
			removeCaso1(n);
		}
		replaceNode(n, child);

		return true;
	}

	private Node<K, V> maximumNode(Node<K, V> n) {

		while (n.getLeft() != null) {
			n = n.getLeft();
		}
		return n;
	}

	public Node<K, V> getNode(K chave) {
		Node<K, V> node = raiz;

		do {
			if (node.getKey().equals(chave))
				return node;

			if (node.getKey().compareTo(chave) > 0)
			{
				node = node.getLeft();
			}
			else if (node.getKey().compareTo(chave) < 0)
			{
				node = node.getRight();
			}
		} while (node != null);

		return null;
	}

	private void addCaso1(Node<K, V> node)
	{
		// Verificação de bloqueio
		if (node == null)
			return;

		if (node.getParent() == null)
			node.setBlack();
		else
			addCaso2(node);

	}

	private void addCaso2(Node<K, V> node)
	{
		// Verificação de bloqueio
		if (node == null || node.getParent() == null)
			return;

		if (node.getParent().isBlack())
		{
			return;
		}
		else
		{
			addCaso3(node);
		}
	}

	private void addCaso3(Node<K, V> node)
	{
		// Verificação de bloqueio
		if (node == null)
			return;

		Node<K, V> uncle = node.getUncle();
		if (uncle != null && uncle.isRed())
		{
			node.getParent().setBlack();
			uncle.setBlack();
			Node<K, V> grandparent = node.getGrandparent();
			grandparent.setRead();
			addCaso1(grandparent);
		}
		else
		{
			addCaso4(node);
		}
	}

	private void addCaso4(Node<K, V> node)
	{
		// Verificação de bloqueio
		if (node == null)
			return;

		Node<K, V> grandparent = node.getGrandparent();
		Node<K, V> parent = node.getParent();

		if (grandparent != null)
		{
			if (node == parent.getRight() && parent == grandparent.getLeft())
			{
				rotateLeft(parent);
				node = node.getLeft();
			}
			else if (node == parent.getLeft() && parent == grandparent.getRight())
			{
				rotateRight(parent);
				node = node.getRight();
			}
		}
		addCaso5(node);

	}

	private void addCaso5(Node<K, V> node)
	{
		// Verificação de bloqueio
		if (node == null)
			return;

		Node<K, V> grandparent = node.getGrandparent();
		Node<K, V> parent = node.getParent();

		parent.setBlack();

		if (grandparent != null)
		{
			grandparent.setRead();

			if (node == parent.getLeft())
			{
				rotateRight(grandparent);
			}
			else
			{
				rotateLeft(grandparent);
			}
		}
	}

	private void rotateLeft(Node<K, V> n)
	{
		Node<K, V> r = n.getRight();
		replaceNode(n, r);
		n.setRight(r.getLeft());
		if (r.getLeft() != null) {
			r.getLeft().setParent(n);
		}
		r.setLeft(n);
		n.setParent(r);

	}

	private void rotateRight(Node<K, V> n)
	{
		Node<K, V> l = n.getLeft();
		replaceNode(n, l);
		n.setLeft(l.getRight());
		if (l.getRight() != null) {
			l.getRight().setParent(n);
		}
		l.setRight(n);
		n.setParent(l);
	}

	private void replaceNode(Node<K, V> oldn, Node<K, V> newn) {

		if (oldn.getParent() == null) {
			raiz = newn;
		} else {
			if (oldn == oldn.getParent().getLeft())
				oldn.getParent().setLeft(newn);
			else
				oldn.getParent().setRight(newn);
		}
		if (newn != null) {
			newn.setParent(oldn.getParent());
		}
	}

	private void removeCaso1(Node<K, V> node)
	{
		if (node.getParent() != null)
		{
			removeCaso2(node);
		}
	}

	private void removeCaso2(Node<K, V> node)
	{

		Node<K, V> sibling = node.getSibling();

		if (sibling != null && sibling.isRed()) {
			node.getParent().setRead();
			sibling.setBlack();
			if (node == node.getParent().getLeft())
				rotateLeft(node.getParent());
			else
				rotateRight(node.getParent());
		}
		removeCaso3(node);
	}

	private boolean isBlack(Node<K, V> node)
	{
		return node == null ? true : node.isBlack();
		
	}
	
	private void removeCaso3(Node<K, V> node)
	{
		Node<K, V> sibling = node.getSibling();
		if (isBlack(sibling )  &&
				isBlack(node.getParent()) &&
				isBlack(sibling.getLeft()) &&
				isBlack(sibling.getRight())) {

			sibling.setRead();
			removeCaso1(node.getParent());

		} else {
			removeCaso4(node);
		}
	}

	private void removeCaso4(Node<K, V> node)
	{
		Node<K, V> sibling = node.getSibling();

		if (isBlack(sibling) &&
				!isBlack(node.getParent()) &&
				isBlack(sibling.getLeft()) &&
				isBlack(sibling.getRight())) {

			sibling.setRead();
			node.getParent().setBlack();
		} else {
			removeCaso5(node);
		}

	}

	private void removeCaso5(Node<K, V> node)
	{
		Node<K, V> sibling = node.getSibling();

		if (sibling.isBlack()) {
			if (node == node.getParent().getLeft() &&
					isBlack(sibling.getRight()) &&
					isBlack(sibling.getLeft())) {

				sibling.setRead();
				sibling.getLeft().setBlack();
				rotateRight(sibling);

			} else if (node == node.getParent().getRight() &&
					isBlack(sibling.getLeft()) &&
					isBlack(sibling.getRight() )) {

				sibling.setRead();
				sibling.getRight().setBlack();
				rotateLeft(sibling);
			}
		}
		removeCaso6(node);
	}

	private void removeCaso6(Node<K, V> node) {
		if (node == null)
			return;

		Node<K, V> sibling = node.getSibling();

		if (isBlack(node.getParent()))
			sibling.setBlack();
		else
			sibling.setRead();

		node.getParent().setBlack();

		if (isBlack(node.getParent())) {
			sibling.getRight().setBlack();
			rotateLeft(node.getParent());
		} else {
			sibling.getLeft().setBlack();
			rotateLeft(node.getParent());
		}

	}

	public String toString()
	{
		if (raiz == null)
			return "<Vazia>";

		return raiz.getTreeString().toString();

	}

}
