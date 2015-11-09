public class DoublyLinkedList<T> extends LinkedList<T>{

	/**
	 * Constructor
	 */
	public DoublyLinkedList(){
		super();
	}

	/**
	 * Insert the node at the head of the list.
	 **/
	@Override
	public void insertFirstNode( LinkedListNode<T> node ) {

		// node must be an instance of DoublyLinkedListNode
		if(node instanceof DoublyLinkedListNode<?>){

			// if list is empty, this node is also tail
			if(this.isEmpty()) tail = (DoublyLinkedListNode<T>) node;

			// make sure that the previous pointer is null
			((DoublyLinkedListNode<T>)node).setPrevious( null );

			// update the old head's previous pointer, if list is not empty
			if(!this.isEmpty()) ((DoublyLinkedListNode<T>)head).setPrevious((DoublyLinkedListNode<T>)node);

			// call superclass' implementation to update head and next pointers
			super.insertFirstNode(node);
		}
		else{
			// print an error message
			System.err.println( "Parameter must be an instance of DoublyLinkedListNode" );
		}
	}

	/**
	 * Insert a new node with data after currentNode
	 **/
	@Override
	public void insertAfter( LinkedListNode<T> currentNode, T data ){
		// currentNode must be an instance of DoublyLinkedListNode
		if(currentNode instanceof DoublyLinkedListNode<?>){

			// call superclass' implementation to update head and next pointers
			super.insertAfter( currentNode, data );

			try{
				// update new node (which is now currentNode's next) 's previous pointer
				DoublyLinkedListNode<T> newNode = (DoublyLinkedListNode<T>)currentNode.getNext();
				newNode.setPrevious((DoublyLinkedListNode<T>)currentNode);

				// update the node after the new node's previous pointer
				DoublyLinkedListNode<T> nextNode = (DoublyLinkedListNode<T>)newNode.getNext();
				if(nextNode!=null) nextNode.setPrevious(newNode);
			}catch(NullPointerException ex){};
		}
		else{
			// print an error message
			System.err.println( "Parameter must be an instance of DoublyLinkedListNode");
		}
	}

	/**
	 * Insert a new node with data at the tail of the list.
	 **/
	@Override
	public void insertLast( T data ){
		// if list is empty, insert data as first
		if(this.isEmpty()) {
			insertFirst(data);
		}
		else{ 
			// insert a node containing data after tail
			insertAfter(tail,data);
			// update tail 
			//((DoublyLinkedListNode<T>)tail.getNext()).setPrevious((DoublyLinkedListNode<T>)tail);
			tail = tail.getNext();

		}
	}

	/**
	 * Remove head node
	 **/
	@Override
	public void deleteFirst(){
		// if list is not empty
		if(!this.isEmpty()){
			// call superclass' implementation to update head
			super.deleteFirst();
			// update previous pointers
			if(head!=null) {
				((DoublyLinkedListNode<T>)head).setPrevious(null);

				if(head.getNext() != null) ((DoublyLinkedListNode<T>)head.getNext()).setPrevious((DoublyLinkedListNode<T>)head);
			}
		}
	}

	/**
	 * Remove tail node
	 */
	@Override
	public void deleteLast(){
		if(!this.isEmpty()){
			if( head.getNext() == null ) deleteFirst();
			// set tail's previous node's next pointer to null
			else {
				// assign tail's previous node as new tail
				tail = ((DoublyLinkedListNode<T>)tail).getPrevious();
				// update tail's next pointer
				tail.setNext(null);
			}
		}
	}

	/**
	 * Remove node following currentNode
	 * If no node exists (i.e., currentNode is the tail), do nothing
	 **/
	@Override
	public void deleteNext( LinkedListNode<T> currentNode ){
		// call superclass' implementation to update next pointer of current node
		super.deleteNext(currentNode);

		if( currentNode instanceof DoublyLinkedListNode<?> ){
			if(currentNode.getNext() != null){
				// update the node after currentNode's previous pointer
				((DoublyLinkedListNode<T>)currentNode.getNext()).setPrevious((DoublyLinkedListNode<T>)currentNode);
			}
		}
		else{
			// print an error message
			System.err.println( "Parameter must be an instance of DoublyLinkedListNode" );
		}
	}

	/**
	 * create a node containing the data
	 * @param data
	 * @return a doubly linked list node that contains the data 
	 */
	@Override
	public LinkedListNode<T> createNode(T data){
		return new DoublyLinkedListNode<T>(data);
	}
}
