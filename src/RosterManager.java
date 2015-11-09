public class RosterManager {

	/*** 	INSTANCE FIELDS		***/
	private DoublyLinkedList<Student> rosterList; // list of students who were added for the course
	private DoublyLinkedList<Student> waitlist; // list of students on waitlist
	private int rosterListCapacity = 15; // maximum number of students on roster list, default 25

	/**
	 * Constructor
	 */
	public RosterManager(){
		// initiate rosterList and waitlist
		this.rosterList = new DoublyLinkedList<Student>();
		this.waitlist = new DoublyLinkedList<Student>();
	}

	/**
	 * Add student to the roster list, if roster list reaches maximum capacity
	 * Add to waitlist
	 * @param student
	 * @return true if adding was successful
	 */
	public void addStudent(Student s){
		// compare this student's name with names in the list
		// if roster list is empty
		if(rosterList.isEmpty()){
			// insert this student as first
			rosterList.insertFirst(s);
		}
		// if roster list reaches its capacity
		else if(rosterList.size() >= rosterListCapacity){
			// insert student to the end of the waitlist
			waitlist.insertLast(s);
		}
		// if roster list is not empty but still have space for more students
		else{
			// if this student's name should be before list's first student, insert first
			if(s.compareStudentName(rosterList.getFirst()) <= 0){
				rosterList.insertFirst(s);
			}
			// if this student's name should be after list's last student, insert last
			else if(s.compareStudentName(rosterList.getLast()) >= 0){
				rosterList.insertLast(s);
			}
			else{
				DoublyLinkedListNode<Student> tmp = (DoublyLinkedListNode<Student>) rosterList.getFirstNode();
				// compare student's name to each existing name on the list 
				while(s.compareStudentName(tmp.getData()) > 0){
					tmp = (DoublyLinkedListNode<Student>)tmp.getNext();
				}
				// when tmp has the student's name that should be after this student's name
				// insert this student after tmp's previous node
				rosterList.insertAfter(tmp.getPrevious(), s);
			}
		}	
	}

	/**
	 * Remove a specific student (by name) from the list passed in
	 * @param student and the list to perform removing action on
	 */
	public void removeStudent(Student s, DoublyLinkedList<Student> l){
		// if roster size < capacity after removing
		DoublyLinkedListNode<Student> tmp = (DoublyLinkedListNode<Student>) l.getFirstNode();

		// traverse l
		for(int i = 0; i < l.size(); i++){
			// if tmp contains the same student name
			if(s.compareStudentName(tmp.getData()) == 0){
				// delete tmp from l
				// if tmp is head, then delete first 
				if(tmp.getPrevious()==null) l.deleteFirst();
				// else, use deleteNext method to delete tmp
				else l.deleteNext((LinkedListNode<Student>)(tmp.getPrevious()));
				return;
			}
			// else check next node
			else tmp = (DoublyLinkedListNode<Student>)tmp.getNext();
		}
	}

	/**
	 * Set the roster list capacity to the passed int
	 * @param size
	 */
	public void setRosterListCapacity(int size){
		rosterListCapacity = size;
	}

	/**
	 * Public method to get the roster list
	 * @return roster list
	 */
	public DoublyLinkedList<Student> getRosterList(){
		return rosterList;
	}

	/**
	 * Public method to get the waitlist
	 * @return waitlist
	 */
	public DoublyLinkedList<Student> getWaitlist(){
		return waitlist;
	}
}
