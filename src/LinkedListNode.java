/**
 * 
 * @author vu24n
 *
 * @param <T>
 */
public class LinkedListNode<T> {
	/*** 	INSTANCE FIELDS		***/
	protected T data;
	protected LinkedListNode<T> next;
	
	
	/***	INSTANCE METHODS	***/
	/**
	 * Constructor
	 */
	public LinkedListNode(T data){
		
		setData(data);
	}
	
	/**
	 * Get the data stored at this node.
	 * @return data
	 */
	public T getData(){
		return data;
	}
	 
	/**
	 * Set the data stored at this node.
	 * @param data 
	 **/
	public void setData( T data ){
		this.data = data;
	}
	 
	/**
	 * Get (pointer to) next node.
	 * @return next node
	 **/
	public LinkedListNode<T> getNext(){
		return next;
	}
	 
	/**
	 * Set the next pointer to passed node.
	 * @param node to be the next node
	 **/
	public void setNext( LinkedListNode<T> node ){
			this.next = node;
	}
	
	/**
	 * Check if the node is a tail
	 * @return true if next points to null
	 */
	public boolean isTail(){
		if(this.next == null) return true;
		else return false;
	}
	 
	/**
	 * @return a String representation of this node.
	 **/
	public String toString(){
		return data.toString();
	}
}
