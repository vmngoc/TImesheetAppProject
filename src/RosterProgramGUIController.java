import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

public class RosterProgramGUIController extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/*** 			INSTANCE FIELDS			***/
	private RosterManager manager; // contains the roster and waitlist, and associated methods
	// GUI components
	private JComboBox<String> actionMenu;
	private JButton confirmBtn; // button for confirming the action
	private JTextField lastName; // allows user to enter last name
	private JTextField firstName; // allows user to enter first name
	private JTextArea rosterListView; // displaying the roster list
	private JTextArea waitlistView; // displaying the waitlist
	
	
	/*** 			INSTANCE METHODS		***/
	/**
	 * Constructor
	 */
	public RosterProgramGUIController() {
		super( new BorderLayout());
		manager = new RosterManager();
		initGUI();
	}
	
	/**
	 * initiate all GUI components
	 */
	public void initGUI(){
		this.add(createTextInputPanel(), BorderLayout.NORTH);
		this.add(createListView(), BorderLayout.CENTER);
		this.add(createActionPanel(), BorderLayout.SOUTH);
	}
	
	/**
	 * Create text fields for user input of first and last name
	 * @return a JPanel with 2 JTextField for input of first name and last name
	 */
	public JPanel createTextInputPanel(){
		JPanel p = new JPanel( new FlowLayout() );
		
		// create JTextField for input of first and last name
		firstName = new JTextField("Firstname");
		firstName.setPreferredSize(new Dimension(200, 30));
		lastName = new JTextField("Lastname");
		lastName.setPreferredSize(new Dimension(200, 30));
		
		// add the JTextField to JPanel p
		p.add(firstName);
		p.add(lastName);
		
		return p;
	}
	
	/**
	 * Create a JPanel to display roster list and waitlist
	 * @return a JPanel with the JTextArea displaying roster list and waitlist
	 */
	public JPanel createListView(){
		JPanel p = new JPanel(new BorderLayout());
		
		// create labels for the 2 lists and add to p
		JLabel rosterLabel = new JLabel("Registered Students (Capacity: 15)");
		JLabel waitlistLabel = new JLabel("Waitlisted Students");
		JPanel labelPanel = new JPanel(new GridLayout(0,2));
		labelPanel.add(rosterLabel);
		labelPanel.add(waitlistLabel);
		
		p.add(labelPanel, BorderLayout.NORTH);
		
		// creat a view panel to hold the 2 list view
		JPanel viewPanel = new JPanel(new GridLayout(0,2));
		rosterListView = new JTextArea();
		waitlistView = new JTextArea();
		viewPanel.add(rosterListView);
		viewPanel.add(waitlistView);
		
		p.add(viewPanel, BorderLayout.CENTER);
		return p;
	}
	
	
	/**
	 * Create a panel with a drop-down action menu and a confirm button
	 * @return a JPanel with an action menu and a confirm button
	 */
	public JPanel createActionPanel(){
		JPanel p = new JPanel(new FlowLayout());
		
		String[] actions = {"Register for this course","Drop this course","Withdraw from the waitlist"};
		// create the combo box for action menu
		actionMenu = new JComboBox<String>( actions );
		actionMenu.setSelectedIndex(0);
		actionMenu.addActionListener(actionMenu);
		
		// add the combo box to the JPanel p
		p.add(actionMenu);
		
		// create the confirm button
		createConfirmBtn();
		// add the confirm button to the JPanel p
		p.add(confirmBtn);
		
		return p;
	}
	
	/**
	 * Create the confirm button and add action listener
	 */
	public void createConfirmBtn(){
		confirmBtn = new JButton("Confirm");
		confirmBtn.addActionListener(
				//anonymous action listener class to call restart
				new ActionListener()
				{
					public void actionPerformed( ActionEvent e )
					{  
						// create an instance of students from the input names
						Student s = new Student(firstName.getText(),lastName.getText());
						
						// implement the action selected in actionMenu
						switch( actionMenu.getSelectedIndex() ){
						case 0: manager.addStudent(s);;
						break;
						case 1: manager.removeStudent(s, manager.getRosterList());
						break;
						case 2: manager.removeStudent(s, manager.getWaitlist());
						break;
						}			
						refreshListView();		
					}
				}
				);
	}
	
	/**
	 * Refresh the text displaying roster list and waitlist
	 */
	public void refreshListView(){ 
		rosterListView.setText(listStr(manager.getRosterList()));
		waitlistView.setText(listStr(manager.getWaitlist()));
	}

	/**
	 * helper method to get all students' info in a list
	 * @param l
	 * @return all students' info in the list
	 */
	private String listStr(DoublyLinkedList<Student> l){
		String str = "";
		DoublyLinkedListNode<Student> tmp = (DoublyLinkedListNode<Student>) l.getFirstNode();
		while(tmp != null) {
			str += "\n" + tmp.getData().getStudentInfo();
			tmp = (DoublyLinkedListNode<Student>)tmp.getNext();
		}
		return str;
	}
}
