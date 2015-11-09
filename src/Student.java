import java.text.Collator;
import java.util.Locale;

public class Student {
	/***			INSTANCE FIELDS 		***/
	private String firstName;
	private String lastName;


	/*** 			INSTANCE METHODS		***/
	/**
	 * Constructor
	 */
	public Student(String firstN, String lastN){
		this.firstName = firstN;
		this.lastName = lastN;
	}
	
	/**
	 * Compare this student's name and the student in parameter by alphabetical order
	 * @param s
	 * @return 0 if neither should be before the other, -1 if this student should be before s, 
	 * 1 if this student should be after s
	 */
	public int compareStudentName(Student s){

		if(compareStr(lastName,s.getLastName()) == 0){ 
			// when last names are equivalent,
			// compare the first names		
			return compareStr(firstName,s.getFirstName());
		}
		else{
			// compare last names only
			return compareStr(lastName,s.getLastName());
		}
	}
	
	/**
	 * helper method to compare 2 strings alphabetically, using Java's Collator class
	 * cre: http://docs.oracle.com/javase/6/docs/api/java/text/Collator.html
	 * @param str1
	 * @param str2
	 * @return -1 if str1 is before str2, 0 if two strings are equivalent, 1 if str1 is after str2
	 */
	private int compareStr(String str1, String str2){
		 Collator usCollator = Collator.getInstance(Locale.US);
		 usCollator.setStrength(Collator.PRIMARY);
		 return usCollator.compare(str1, str2);
	}
	
	/**
	 * 
	 * @return student's first name
	 */
	public String getFirstName(){
		return firstName;
	}
	
	/**
	 * 
	 * @return student's last name
	 */
	public String getLastName(){
		return lastName;
	}
	
	public String getStudentInfo(){
		return firstName + " " + lastName;
	}
}
