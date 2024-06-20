/*
Name: Sunny Wu
Due Date: June 14, 2024
Course: ICS3U1-04 - Mr. Fernandes
Description: This class creates the User object, which will be used throughout the application
Major Skills: Object-oriented programming
*/

// Define the class
public class User {
	
//	FIELDS
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private int chapter;
	private double escapeTime;
	private int grade;
	
//	EMPTY CONSTRUCTOR
	public User() {
	}
	
//	CONSTRUCTOR
	public User(String firstName, String lastName, String userName, String password, int chapter, double escapeTime, int grade) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.chapter = chapter;
		this.escapeTime = escapeTime;
		this.grade = grade;
	}

//	GETTERS + SETTERS 
	
//	First name
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFirstName() {
		return firstName;
	}

//	Last name
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLastName() {
		return lastName;
	}
	
//	User name
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	
//	Password
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}

//	Chapter
	public void setChapter(int chapter) {
		this.chapter = chapter;
	}
	public int getChapter() {
		return chapter;
	}

//	Escape time
	public void setEscapeTime(double escapeTime) {
		this.escapeTime = escapeTime;
	}
	public double getEscapeTime() {
		return escapeTime;
	}

//	Grade
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getGrade() {
		return grade;
	}

//	TO STRING
	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName + ", password="
				+ password + ", chapter=" + chapter + ", escapeTime=" + escapeTime + ", grade=" + grade + "]";
	}
	
}
