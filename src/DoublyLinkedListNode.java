public class DoublyLinkedListNode<T> extends LinkedListNode<T> {

	/*** 	INSTANCE FIELDS		***/
	private LinkedListNode<T> prev;
	
	public DoublyLinkedListNode(T data) {
		super(data);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Get (pointer to) prev node.
	 * @return prev node
	 **/
	public LinkedListNode<T> getPrevious(){
		return prev;
	}
	 
	/**
	 * Set the prev pointer to passed node.
	 * @param node to be the prev node
	 **/
	public void setPrevious( DoublyLinkedListNode<T> node ){
		this.prev = node;
	}

}
