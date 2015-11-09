import org.junit.Test;

public class LinkedListTester {

	public void printIntegerList(LinkedList<Integer> list){
		System.out.println(list.toString());
	}
	
	@Test
	public void testStrList() {
		LinkedList<Integer> list = new LinkedList<Integer>();
		
		// test if all methods can handle null pointer exception
		list.deleteFirst();
		list.deleteLast();
		list.deleteNext(list.getFirstNode());
		list.getFirst();
		list.getFirstNode();
		list.getLast();
		list.getLastNode();
		
		// test insert methods
		list.insertFirst(new Integer(3));
		printIntegerList(list);
		
		list.insertFirstNode(new DoublyLinkedListNode<Integer>(new Integer(1)));
		printIntegerList(list);
		
		list.insertAfter(list.head, new Integer(2));
		printIntegerList(list);
		
		list.insertLast(new Integer(4));
		printIntegerList(list);
		
		// test getter methods
		System.out.println(list.getFirst());
		System.out.println(list.getFirstNode().toString());
		System.out.println(list.getLast());
		System.out.println(list.getLastNode().toString());
		
		// test delete methods
		list.deleteFirst();
		printIntegerList(list);
		
		list.deleteLast();
		printIntegerList(list);
		
		list.deleteNext(list.getFirstNode()); // delete next not work with tail/before tail
		printIntegerList(list);
		
		// test size
		System.out.println(list.size());
	}

}
