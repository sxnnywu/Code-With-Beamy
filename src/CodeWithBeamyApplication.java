// IMPORTS
import java.util.ArrayList;

/*
PROJECT HEADER

Name: Sunny Wu
Due Date: June 14, 2024
Course: ICS3U1-04 - Mr. Fernandes

Description: Code With Beamy is a comprehensive application where users can learn about Java's Object-Oriented Programming in a fun and
			 interactive way. It features Beamy - a sun mascot who guides the user along the way, options to visit external sources for
			 more clarification on topics, a daring escape room activity and a final test to assess the user's understanding. The experience
			 is completely personalized, saving the user's learning progress, escape time, and assessment grade. 
Features:
* LoginFrame and RegisterFrame, a User object was created and each user is saved onto a user.txt file and the usersArrayList
* A timer is used in the escape room activity to keep track of how long the user takes
* In the ConceptsFrame users have the option of clicking "Learn More" which opens an external web browser to YouTube videos surrounding the 
  topic of that chapter
* Personalization to the user - ConceptsFrame will remember the chapter the user was last on, ActivityFrame will remember the user's last
  escape time, and AssessmentFrame will remember the user's last grade
* JMenuBar so that user's can go between frames or log out
* Background music

Major Skills:
* Swing GUI
* Scaling images
* Decoding Colours
* Creating + calling methods
* Implementing action listeners and menu listeners
* 2D Arrays
* Running totals
* Writing to a file
* Array lists
* Playing audio
* Counted + conditional loops
* If else statements
* Object-oriented programming
* Math calculations

Areas of Concern:
* Icon on gameroom2Panel of the EscapeFrame does not show up
* Text field on gameroom3Panel does not show up unless you minimize the window and then reopen the window
* If you try to create an account with a username that already exists, the program will not prevent this from happening
* Would also add sound effects for a button being clicked in the future
* Would add hints for the ActivityFrame in the future
*/

// Define the class
public class CodeWithBeamyApplication {
	
//	Array list of users to be used throughout the application
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList<User> usersArrayList = new ArrayList();
	
//	Username of current user
	public static User currentUser;
	
//	Main method
	public static void main(String[] args) {
		
		new LoginFrame();
//		new RegisterFrame();
//		new MainFrame();
//		new ConceptsFrame();
//		new ActivityFrame();
//		new AssessmentFrame();
	}
} // End of class
