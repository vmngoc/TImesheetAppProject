import javax.swing.JFrame;

public class RosterProgramApplication {

	public static void main(String[] args) {		
		// create a new JFrame to hold a new controller instance
		JFrame rosterFrame = new JFrame("Student Roster Program");
		
		// set size
		rosterFrame.setSize( 800, 600 );

		// make a new controller instance and add it
		rosterFrame.add( new RosterProgramGUIController( ) );

		// exit normally on closing the window
		rosterFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		// show frame
		rosterFrame.setVisible( true );	
	}
}
