// IMPORTS =============================================================================================================================

// GUI elements
import javax.sound.sampled.Clip;
import javax.swing.*;

// Designing GUI elements
import java.awt.Color;
import java.awt.Font;

// Writing to a file
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;

// React to events
import java.awt.event.*;

/*
Name: Sunny Wu
Due Date: June 14, 2024
Course: ICS3U1-04 - Mr. Fernandes
Description: This class creates the log in frame of the Code With Beamy Application
Major Skills: 
* Swing GUI
* Scaling images
* Decoding Colours
* Creating + calling methods
* Implementing action listeners and menu listeners
* Arrays
*/

// Define the class
@SuppressWarnings("serial")
public class RegisterFrame extends JFrame implements ActionListener{
	
//	FIELDS =============================================================================================================================
	
//	Music being played
	private static Clip audioClip;
	
//	User being added
	private static User newUser = new User();
	
//	CREATE GUI ELEMENTS ================================================================================================================
	
//	Panels
	private JPanel leftPanel = new JPanel();
	private JPanel rightPanel = new JPanel();
	
//	Images
	private ImageIcon unscaledBeamyImage = new ImageIcon("Images/beamy.png");
	private JLabel beamyImage = new JLabel(new ImageIcon(unscaledBeamyImage.getImage().getScaledInstance
											(200, 200, java.awt.Image.SCALE_SMOOTH)));
	
//	Labels
	private JLabel titleLabel = new JLabel("Log in to get started");
	private JLabel firstNameLabel = new JLabel("First name");
	private JLabel lastNameLabel = new JLabel("Last name");
	private JLabel usernameLabel = new JLabel("Username");
	private JLabel passwordLabel = new JLabel("Password");
	private JLabel confirmPasswordLabel = new JLabel("Confirm password");
	private JLabel welcomeLabel = new JLabel("<html> Welcome to <br/> Code With Beamy!");
	
//	Buttons
	private JButton signupButton = new JButton("Create Account");
	private JButton loginButton = new JButton("<html> Already have an account? <br/> Click here to log in now!");
	
//	Text fields
	private JTextField firstNameTextField = new JTextField();
	private JTextField lastNameTextField = new JTextField();
	private JTextField usernameTextField = new JTextField();
	private JPasswordField passwordField = new JPasswordField(); // https://stackoverflow.com/questions/19755259/hide-show-password-in-a-jtextfield-java-swing
	private JPasswordField confirmPasswordField = new JPasswordField(); // https://stackoverflow.com/questions/19755259/hide-show-password-in-a-jtextfield-java-swing
	
//	COLOURS TO BE USED =================================================================================================================
	private Color yellow = Color.decode("#FFF0AE");
	private Color turquoise = Color.decode("#45C9DD");
	private Color darkYellow = Color.decode("#FFC532");
	
//	CONSTRUCTOR ========================================================================================================================
	public RegisterFrame() {
		
//		Set up the frame
		setTitle("Beamy's Test of Wisdom");
		setSize(1920, 1080);
		setLayout(null);
		
//		Background colour
//		https://stackoverflow.com/questions/1081486/setting-background-color-for-a-jframe
		getContentPane().setBackground(yellow);
		
//		Add contents to the frame
		addContents();
		
//		Close the program
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
//		Show the frame
		setVisible(true);
	}
	
//	STOP AUDIO ========================================================================================================================
	private void stopAudio(String fileName) {
		
//		https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java		
		audioClip.stop();
	    audioClip.close();
	}
	
//	ADD CONTENTS ======================================================================================================================
	private void addContents() {
		
//		Set up the panels
		leftPanelSetup();
		rightPanelSetup();
		
//		Right panel shows up on top of left panel
		add(rightPanel);
		add(leftPanel);
	}
	
//	LEFT PANEL =========================================================================================================================
	private void leftPanelSetup() {
		
//		Set up the panel
		leftPanel.setBounds(100, 80, 1300, 630);
		leftPanel.setLayout(null);
		leftPanel.setOpaque(true);
		leftPanel.setBackground(turquoise);
		
//		Title label
		titleLabel.setFont(new Font("Courier New", Font.BOLD, 36));
		titleLabel.setBounds(25, 10, 600, 80);
		leftPanel.add(titleLabel);
		
//		First name label
		firstNameLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
		firstNameLabel.setBounds(30, 100, 200, 30);
		leftPanel.add(firstNameLabel);
		
//		First name text field
		firstNameTextField.setFont(new Font("Courier New", Font.PLAIN, 20));
		firstNameTextField.setBounds(30, 135, 870, 30);
		leftPanel.add(firstNameTextField);
		
//		Last name label
		lastNameLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
		lastNameLabel.setBounds(30, 185, 200, 30);
		leftPanel.add(lastNameLabel);
		
//		Last name text field
		lastNameTextField.setFont(new Font("Courier New", Font.PLAIN, 20));
		lastNameTextField.setBounds(30, 220, 870, 30);
		leftPanel.add(lastNameTextField);
		
//		User name label
		usernameLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
		usernameLabel.setBounds(30, 270, 200, 30);
		leftPanel.add(usernameLabel);
		
//		User name text field
		usernameTextField.setFont(new Font("Courier New", Font.PLAIN, 20));
		usernameTextField.setBounds(30, 305, 870, 30);
		leftPanel.add(usernameTextField);
		
//		Password label
		passwordLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
		passwordLabel.setBounds(30, 355, 200, 30);
		leftPanel.add(passwordLabel);
		
//		Password text field
		passwordField.setFont(new Font("Courier New", Font.PLAIN, 20));
		passwordField.setBounds(30, 390, 870, 30);
		leftPanel.add(passwordField);
		
//		Confirm password label
		confirmPasswordLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
		confirmPasswordLabel.setBounds(30, 440, 200, 30);
		leftPanel.add(confirmPasswordLabel);
		
//		Confirm password text field
		confirmPasswordField.setFont(new Font("Courier New", Font.PLAIN, 20));
		confirmPasswordField.setBounds(30, 475, 870, 30);
		leftPanel.add(confirmPasswordField);
		
//		Sign in button
		signupButton.setBackground(darkYellow);
		signupButton.setFont(new Font("Courier New", Font.BOLD, 25));
		signupButton.setBounds(300, 550, 300, 50);
		signupButton.addActionListener(this);
		leftPanel.add(signupButton);
	}
	
//	RIGHT PANEL ========================================================================================================================
	private void rightPanelSetup() {
		
//		Set up the panel
		rightPanel.setBounds(1025, 110, 350, 570);
		rightPanel.setLayout(null);
		rightPanel.setOpaque(true);
		rightPanel.setBackground(yellow);
		
//		Welcome label
		welcomeLabel.setFont(new Font("Courier New", Font.BOLD, 30));
		welcomeLabel.setBounds(25, 30, 600, 80);
		rightPanel.add(welcomeLabel);
		
//		Beamy image
		beamyImage.setBounds(80, 170, 200, 200);
		rightPanel.add(beamyImage);
		
//		Log in button
		loginButton.setBackground(yellow);
		loginButton.setFont(new Font("Courier New", Font.PLAIN, 18));
		loginButton.setBorderPainted(false); // https://stackoverflow.com/questions/2713190/how-to-remove-border-around-buttons
		loginButton.setBounds(0, 480, 350, 80);
		loginButton.addActionListener(this);
		rightPanel.add(loginButton);
	}
	
//	ACTION LISTENER ====================================================================================================================
	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void actionPerformed(ActionEvent event) {
		
//		If the user clicks the sign up button
		if (event.getSource() == signupButton) {
			
//			Store the info entered
			String firstName = firstNameTextField.getText();
			String lastName = lastNameTextField.getText();
			String userName = usernameTextField.getText();
			String password1 = String.valueOf(passwordField.getPassword()); // https://stackoverflow.com/questions/19755259/hide-show-password-in-a-jtextfield-java-swing
			String password2 = String.valueOf(confirmPasswordField.getPassword()); // https://stackoverflow.com/questions/19755259/hide-show-password-in-a-jtextfield-java-swing
			
//			If the username has already been taken, display a message to inform the user
			if (CodeWithBeamyApplication.usersArrayList.contains(userName)) 
				JOptionPane.showMessageDialog(null, "Oops! This username has already been used.");
			
//			If passwords don't match, display a message to inform the user
			else if (!password1.equals(password2)) 
				JOptionPane.showMessageDialog(null, "Oops! Make sure your passwords match.");
			
//			Otherwise, store the user as a new user in the array list + read them into the file
			else {
				newUser = new User(firstName, lastName, userName, password1, 1, 0, -1);
				CodeWithBeamyApplication.usersArrayList.add(newUser);
				setVisible(false);
				addUserToFile();
				new LoginFrame();
			}
		}
		
//		If the user clicks the log in button, close this frame and display the log in frame
		if (event.getSource() == loginButton) {
			setVisible(false);
			stopAudio("Audio/home.wav");
			new LoginFrame();
		}
	}
	
//	ADD USER TO FILE ===================================================================================================================
	private static void addUserToFile() {		
		
//	    Specify the file path and name
	    Path filePath = Paths.get("Data/users.txt");
	    
//	    Clear the file 
	    try {
	        Files.write(filePath, Collections.emptyList()); // https://stackoverflow.com/questions/6994518/how-to-delete-the-content-of-text-file-without-deleting-itself
	        
//	    Print any errors that occur
	    } catch (IOException error) {
	        error.printStackTrace();
	    }
	    
//	    Write the array list to the file
	    try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.WRITE)) {
    	
//	    	Go through entire array list
	    	for (int index = 0; index < CodeWithBeamyApplication.usersArrayList.size(); index++) {
	    	
//	    		Temporary user
	    		User user = CodeWithBeamyApplication.usersArrayList.get(index);
	   
	    		writer.write(user.getFirstName() + ", " + user.getLastName() + ", " + user.getUserName() + ", " + user.getPassword() 
	    					+ ", " + user.getChapter() + ", " + user.getEscapeTime() + ", " + user.getGrade() + "\n");
	    	}	    		
//	    Print any errors that occur
	    } catch (IOException error) {
	    	error.printStackTrace();
	    }
	}
}
