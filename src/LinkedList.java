/**
 * 
 * @author vu24n
 *
 * @param <T>
 */
public class LinkedList<T> {
	/***			INSTANCE FIELDS 		***/
	protected LinkedListNode<T> head;
	protected LinkedListNode<T> tail;


	/*** 			INSTANCE METHODS		***/
	/**
	 * Constructor
	 */
	public LinkedList(){

	}

	/**
	 * Get data stored in head node of list.
	 **/
	public T getFirst(){
		if(!this.isEmpty()) return head.getData();
		else return null;
	}

	/**
	 * Get the head node of the list.
	 **/
	public LinkedListNode<T> getFirstNode(){
		return head;
	}

	/**
	 * Get data stored in tail node of list.
	 **/
	public T getLast(){
		if(!this.isEmpty()) return tail.getData();
		else return null;
	}

	/**
	 * Get the tail node of the list.
	 **/
	public LinkedListNode<T> getLastNode(){
		return tail;
	}

	/**
	 * Insert a new node with data at the head of the list.
	 **/
	public void insertFirst( T data ){
		// Invoke insertFirstNode to link up the new node
		insertFirstNode(createNode(data));
	}

	/**
	 * Insert the node at the head of the list.
	 **/
	public void insertFirstNode( LinkedListNode<T> node ) {
		// Have the new node point to the old head
		node.setNext(head);

		// if list is empty, set new node as tail
		if(this.isEmpty()){
			tail = node;
		}

		// Set the new node as the head.
		head=node;
	}

	/**
	 * Insert a new node with data after currentNode
	 **/
	public void insertAfter( LinkedListNode<T> currentNode, T data ){
		// if list is empty, insert data as first
		if(this.isEmpty()) {
			insertFirst(data);
		}
		else{
			// make a new node to insert the data
			LinkedListNode<T> newNode = createNode(data);

			// point newNode to the node after current node
			try{
				newNode.setNext( currentNode.getNext() );
			}catch(NullPointerException ex){};

			// point currentNode to the newly created node
			currentNode.setNext( newNode );
		}
	}

	/**
	 * Insert a new node with data at the tail of the list.
	 **/
	public void insertLast( T data ){
		// if list is empty, insert data as first
		if(this.isEmpty()) {
			insertFirst(data);
		}
		else{ 
			// insert a node containing data after tail
			insertAfter(tail,data);
			// update tail 
			tail = tail.getNext();
		}
	}

	/**
	 * Remove head node
	 **/
	public void deleteFirst(){
		if(!this.isEmpty()){
			// make the node next to head the new head
			head = head.getNext();
		}
		
	}

	/**
	 * Remove tail node
	 **/
	public void deleteLast(){
		if(this.isEmpty()) return;
		// declare newLastNode, initially set to head
		LinkedListNode<T> newLastNode = head;

		// while the newLastNode hasn't pointed to the tail node yet
		while(!newLastNode.getNext().isTail()){
			// assign lastNode to the next node in the list
			newLastNode = newLastNode.getNext();
		}

		// point this new last node to null
		newLastNode.setNext( null );

		// update tail property
		tail = newLastNode;
	}

	/**
	 * Remove node following currentNode
	 * If no node exists (i.e., currentNode is the tail), do nothing
	 **/
	public void deleteNext( LinkedListNode<T> currentNode ){
		try{
			// if next node is not null
			if(currentNode.getNext()!=null){
				// if the next next node is null
				if(currentNode.getNext().getNext()==null){
					// set currentNode's next pointer to null
					currentNode.setNext(null);
					// assign tail to currentNode
					tail = currentNode;
				}
				// else, set currentNode's next pointer to the next next node
				else currentNode.setNext(currentNode.getNext().getNext());	
			}
		}catch(NullPointerException e){
			System.err.println("NullPointerException. Must pass a LinkedListNode");
		}
	}

	/**
	 * Return the number of nodes in this list.
	 **/
	public int size(){
		// declare lastNode, initially set to head
		LinkedListNode<T> lastNode = head;

		int size = 0;

		// while lastNode is not yet the tail node
		while(lastNode!=null){
			// assign lastNode to the next node in the list
			lastNode = lastNode.getNext();
			// increment the size by 1
			size++;
		}
		return size;
	}

	/**
	 * Check if list is empty.
	 * @return true if list contains no items.
	 **/
	public boolean isEmpty(){
		if( head==null ) return true;
		else return false;
	}

	/**
	 * Return a String representation of the list.
	 **/
	public String toString(){
		String str ="";

		// declare lastNode, initally set to head
		LinkedListNode<T> lastNode = head;

		if( lastNode != null ){
			// while lastNode is not yet the tail node
			while(lastNode.getNext()!=null){

				str += lastNode.toString() + " -> ";
				// assign lastNode to the next node in the list
				lastNode = lastNode.getNext();
			}

			// add last node
			str += lastNode.toString();
		}
		return str;
	}

	/**
	 * 
	 * @param data
	 * @return a linked list node that contains the data 
	 */
	public LinkedListNode<T> createNode(T data){
		return new LinkedListNode<T>(data);
	}
}
