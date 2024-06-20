// IMPORTS =============================================================================================================================

// GUI elements
import javax.swing.*;

// Designing GUI elements
import java.awt.Color;
import java.awt.Font;

// Playing music
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

// React to events
import java.awt.event.*;

// Reading files
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
* Implementing action listeners
* Reading a file
* Writing to a file
* Array lists
* Password fields
* Playing audio
* Counted + conditional loops
* If else statements
*/

// Define the class
@SuppressWarnings("serial")
public class LoginFrame extends JFrame implements ActionListener{
	
//	FIELDS =============================================================================================================================
	
//	Music being played
	private static Clip audioClip;
	
//	CREATE GUI ELEMENTS ================================================================================================================
	
//	Panels
	private JPanel loginPanel = new JPanel();
	
//	Images
	private ImageIcon unscaledBeamyImage = new ImageIcon("Images/beamy.png");
	private JLabel beamyImage = new JLabel(new ImageIcon(unscaledBeamyImage.getImage().getScaledInstance
											(200, 200, java.awt.Image.SCALE_SMOOTH)));
	
//	Labels
	private JLabel titleLabel = new JLabel("Log in to get started");
	private JLabel usernameLabel = new JLabel("Username");
	private JLabel passwordLabel = new JLabel("Password");
	
//	Buttons
	private JButton signinButton = new JButton("Sign me in");
	private JButton registerButton = new JButton("<html> Don't have an account? <br/> Click here to sign up now!");
	
//	Text fields
	private JTextField usernameTextField = new JTextField();
	private JPasswordField passwordField = new JPasswordField(); // https://stackoverflow.com/questions/19755259/hide-show-password-in-a-jtextfield-java-swing
	
//	COLOURS TO BE USED =================================================================================================================
	private Color yellow = Color.decode("#FFF0AE");
	private Color turquoise = Color.decode("#45C9DD");
	private Color darkYellow = Color.decode("#FFC532");
	
//	CONSTRUCTOR ========================================================================================================================
	public LoginFrame() {
			
//			Set up the frame
			setTitle("Beamy's Test of Wisdom");
			setSize(1920, 1080);
			setLayout(null);
			
//			Background colour
//			https://stackoverflow.com/questions/1081486/setting-background-color-for-a-jframe
			getContentPane().setBackground(yellow);
			
//			Read in the existing users
			loadUserFile(CodeWithBeamyApplication.usersArrayList);
			
//			Add contents to the frame
			addContents();
			
//			Close the program
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
			
//			Play the music
			playAudio("Audio/home.wav");
			
//			Show the frame
			setVisible(true);
	}
	
//	READ EXISTING USERS ================================================================================================================
	private void loadUserFile(ArrayList<User> arrayList) {
		
//		Clear the array list
		arrayList.clear(); // https://www.google.com/search?q=how+to+clear+an+array+list+in+java&oq=how+to+clear+an+array+list+in&gs_lcrp=EgZjaHJvbWUqBwgAEAAYgAQyBwgAEAAYgAQyBggBEEUYOTIICAIQABgWGB4yCAgDEAAYFhgeMgoIBBAAGAoYFhgeMggIBRAAGBYYHjIKCAYQABgKGBYYHjIKCAcQABgKGBYYHjIICAgQABgWGB4yCAgJEAAYFhgeqAIAsAIA&sourceid=chrome&ie=UTF-8
		
		try {
			
			Scanner input = new Scanner(new File("Data/users.txt"));
			
//			Seperate values by line
//			input.useDelimiter(", |\r\n");
			input.useDelimiter("[,\\s]+"); // https://stackoverflow.com/questions/225337/how-to-split-a-string-with-any-whitespace-chars-as-delimiters
			
//			Repeat for until no more users left
			while (input.hasNext()) {
			
//				Create a user for the current line 
				User user = new User();
				user.setFirstName(input.next());
				user.setLastName(input.next());
				user.setUserName(input.next());
				user.setPassword(input.next());
				user.setChapter(Integer.parseInt(input.next())); // https://stackoverflow.com/questions/5585779/how-do-i-convert-a-string-to-an-int-in-java
				user.setEscapeTime(Double.parseDouble(input.next())); // https://stackoverflow.com/questions/5769669/convert-string-to-double-in-java
				user.setGrade(Integer.parseInt(input.next())); // https://stackoverflow.com/questions/5585779/how-do-i-convert-a-string-to-an-int-in-java
				
//				Add this user to the full list of scores
				arrayList.add(user);
			}
			
//			Close the scanner
			input.close();
			
//		If the file isn't found display the error message
		} catch (FileNotFoundException error) {			
			System.out.println("File Error: " + error); 
		}
	}
	
//	ADD CONTENTS ======================================================================================================================
	private void addContents() {
		
//		Beamy image
		beamyImage.setBounds(50, 50, 200, 200);
		add(beamyImage);
		
//		Login panel
		loginPanelSetup();
		add(loginPanel);
		
//		Register button
		registerButton.setBackground(yellow);
		registerButton.setFont(new Font("Courier New", Font.BOLD, 20));
		registerButton.setBorderPainted(false); // https://stackoverflow.com/questions/2713190/how-to-remove-border-around-buttons
		registerButton.setBounds(500, 620, 500, 80);
		registerButton.addActionListener(this);
		add(registerButton);
	}
	
//	LOGIN PANEL ========================================================================================================================
	private void loginPanelSetup() {
		
//		Set up the panel
		loginPanel.setBounds(450, 200, 600, 400);
		loginPanel.setLayout(null);
		loginPanel.setOpaque(true);
		loginPanel.setBackground(turquoise);
		
//		Title label
		titleLabel.setFont(new Font("Courier New", Font.BOLD, 36));
		titleLabel.setBounds(75, 10, 600, 80);
		loginPanel.add(titleLabel);
		
//		User name label
		usernameLabel.setFont(new Font("Courier New", Font.PLAIN, 30));
		usernameLabel.setBounds(30, 140, 200, 40);
		loginPanel.add(usernameLabel);
		
//		User name text field
		usernameTextField.setFont(new Font("Courier New", Font.PLAIN, 20));
		usernameTextField.setBounds(220, 140, 300, 40);
		loginPanel.add(usernameTextField);
		
//		Password label
		passwordLabel.setFont(new Font("Courier New", Font.PLAIN, 30));
		passwordLabel.setBounds(30, 230, 200, 40);
		loginPanel.add(passwordLabel);
		
//		Password text field
		passwordField.setFont(new Font("Courier New", Font.PLAIN, 20));
		passwordField.setBounds(220, 230, 300, 40);
		loginPanel.add(passwordField);
		
//		Sign in button
		signinButton.setBackground(darkYellow);
		signinButton.setFont(new Font("Courier New", Font.BOLD, 25));
		signinButton.setBounds(150, 320, 300, 50);
		signinButton.addActionListener(this);
		loginPanel.add(signinButton);
	}

//	PLAY AUDIO =========================================================================================================================
	private void playAudio(String fileName) {
		
//	https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java   	

        try {
            
//        	Read the file
            File audioFile = new File(fileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);

//          Loop the music
            audioClip.loop(Clip.LOOP_CONTINUOUSLY); // https://stackoverflow.com/questions/8979914/audio-clip-wont-loop-continuously

//          If an error occurs, print the error
            } catch (UnsupportedAudioFileException error) {
                error.printStackTrace();
            } catch (LineUnavailableException error) { 
            	error.printStackTrace();
            } catch (IOException error) {
            	error.printStackTrace();
            }
	}	
	
//	STOP AUDIO ========================================================================================================================
	private void stopAudio(String fileName) {
		
//	https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java		
		audioClip.stop();
        audioClip.close();
	}
	
//	ACTION LISTENER ====================================================================================================================
	@Override
	public void actionPerformed(ActionEvent event) {
		
//		If the user clicks the sign in button
		if (event.getSource() == signinButton) {
			
//			Store the user and password entered
			String username = usernameTextField.getText();
			String password = String.valueOf(passwordField.getPassword()); // https://stackoverflow.com/questions/19755259/hide-show-password-in-a-jtextfield-java-swing
			
//			Whether or not the user exists
			boolean userExists = false;
			
//			Go through the list of users
			for (int index = 0; index < CodeWithBeamyApplication.usersArrayList.size(); index++) { // https://stackoverflow.com/questions/12970484/java-for-loop-with-an-arraylist
				
//				If the username that has been entered is found
				if (CodeWithBeamyApplication.usersArrayList.get(index).getUserName().equals(username)) { // https://www.geeksforgeeks.org/arraylist-get-method-java-examples/
					
//					User does exist
					userExists = true;
					
//					If the password is also equal
					if (CodeWithBeamyApplication.usersArrayList.get(index).getPassword().equals(password)) { // https://stackoverflow.com/questions/19755259/hide-show-password-in-a-jtextfield-java-swing
						
//						Display a message to inform the user
						JOptionPane.showMessageDialog(null, "Login successful!");
						
//						Update current user
						CodeWithBeamyApplication.currentUser = new User();
						CodeWithBeamyApplication.currentUser = CodeWithBeamyApplication.usersArrayList.get(index);
						
//						Close this panel and display the main frame
						setVisible(false);
						stopAudio("Audio/home.wav");
						new MainFrame();
					}
					
//					Otherwise display a message to inform the user that their password is incorrect
					else 
						JOptionPane.showMessageDialog(null, "Login unsuccessful: Incorrect Password");
				}
			}
			
//			If the user cannot be found display a message to inform the user
			if (!userExists) {
				JOptionPane.showMessageDialog(null, "Login unsuccessful: User not found");
			}
		}
		
//		If the user clicks the register button, close this frame and display the register frame
		if (event.getSource() == registerButton) {
			setVisible(false);
			new RegisterFrame();
		}
	}

} // End of class
