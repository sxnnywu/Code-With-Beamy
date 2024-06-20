// IMPORTS =============================================================================================================================

// GUI elements
import javax.swing.*;
import javax.swing.border.LineBorder;

// Designing GUI elements
import java.awt.Color;
import java.awt.Font;

// Play audio
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.BufferedWriter;
// Writing to a file
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
// React to events
import java.awt.event.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/*
Name: Sunny Wu
Due Date: June 14, 2024
Course: ICS3U1-04 - Mr. Fernandes
Description: This class creates the assessment frame of the Code With Beamy Application
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
*/

// Define the class
@SuppressWarnings("serial")
public class AssessmentFrame extends JFrame implements ActionListener, MenuListener{

//	FIELDS =============================================================================================================================
	
//	Panel number
	static int panelNum = 1;
	
//	User's points out of 10
	static int points = 0;
	
//	User's grade as a percentage
	static int gradeNum;
	
//	Questions to be asked
	static Question questionArray[][] = new Question[2][5];
	
//	Music being played
	private static Clip audioClip;
	
//	CREATE GUI ELEMENTS ================================================================================================================
	
//	Panels
	private JPanel introPanel = new JPanel();
	private JPanel partAPanel = new JPanel();
	private JPanel partBPanel = new JPanel();
	private JPanel resultPanel = new JPanel();
	
//	Menu bar
	private JMenuBar menuBar = new JMenuBar();
	private JMenu homeMenu = new JMenu("Home");
	private JMenu conceptsMenu = new JMenu("Beamy's Knowledge Hub");
	private JMenu activityMenu = new JMenu("Beamy's CodeQuest");
	private JMenu logOutMenu = new JMenu("Log Out");
	
//	Labels
	private JLabel definitionLabelArray[] = new JLabel[5];
	private JLabel titleLabel = new JLabel("Beamy Wishes You Luck!");
	private JLabel introLabel = new JLabel("<html> Welcome to the final assessment! This assessment consists of two parts: Part A - Vocabulary "
										 + "Matching and Part B - Fill in the Blanks (Object Class and Methods). <br/>"
										 + "<br/>"
										 + "Follow the instructions carefully for each part to ensure accurate completion and grading.<br/>"
										 + "<br/>"
										 + "Your final gradeNum will be calculated based on the correctness of your answers in both Part A and Part B. Each correct <br/>"
										 + "answer contributes to your total score.");
	private JLabel scoreLabel = new JLabel();
	private JLabel titleALabel = new JLabel("Part A: Vocabulary");
	private JLabel instructionALabel = new JLabel("<html>Match each definition with its corresponding term. "
												+ "Use the dropdown menus provided to make your selections. "
												+ "Once you have matched all terms, click the \"next\" button to proceed.");
	private JLabel titleBLabel = new JLabel("Part B: Fill in the Blanks");
	private JLabel instructionBLabel = new JLabel("<html>Complete the code for the object class by filling in the blanks with the correct code. "
												+ "Ensure that your code follows Java syntax and conventions. "
												+ "Click the \"Submit\" button when you have finished filling in the blanks.");
	private JLabel gradeNumLabel = new JLabel();
	private JLabel resultLabel = new JLabel();
	private JLabel endLabel = new JLabel();
	
//	Images
	private ImageIcon unscaledBeamyImage = new ImageIcon("Images/beamy.png");
	private JLabel beamyImage = new JLabel(new ImageIcon(unscaledBeamyImage.getImage().getScaledInstance
											(300, 300, java.awt.Image.SCALE_SMOOTH)));
	private JLabel beamySmallImage = new JLabel(new ImageIcon(unscaledBeamyImage.getImage().getScaledInstance
											(100, 100, java.awt.Image.SCALE_SMOOTH)));
	ImageIcon unscaledCode = new ImageIcon("Images/Assessment/partBPanel.png");
	private JLabel codeImage = new JLabel(new ImageIcon(unscaledCode.getImage().getScaledInstance(800, 400, java.awt.Image.SCALE_SMOOTH)));
	
//	Buttons
	private JButton startButton = new JButton("START");
	private JButton backButton = new JButton("BACK");
	private JButton nextButton = new JButton("NEXT");
	private JButton submitButton = new JButton("SUBMIT");
	private JButton homeButton = new JButton("HOME");
	
//	Combo boxes
	@SuppressWarnings("unchecked")
	private JComboBox<String> termsComboBoxArray[] = new JComboBox[5];
	
//	Text fields
	private JTextField textFieldArray[] = new JTextField[5];
	
//	COLOURS TO BE USED =================================================================================================================
	private Color yellow = Color.decode("#FFF0AE");
	private Color turquoise = Color.decode("#45C9DD");
	private Color darkYellow = Color.decode("#FFC532");
	
//	CONSTRUCTOR ========================================================================================================================
	public AssessmentFrame() {
		
//		Set up the frame
		setTitle("Beamy's Test of Wisdom");
		setSize(1920, 1080);
		setLayout(null);
		
//		Background colour
//		https://stackoverflow.com/questions/1081486/setting-background-color-for-a-jframe
		getContentPane().setBackground(yellow);
		
//		Set up the menu bar
		setMenuBar();
		
//		Set up the panels
		introPanelSetup();
		partAPanelSetup();
		partBPanelSetup();
		resultPanelSetup();
		
//		Add the panels
		add(introPanel);
		add(partAPanel);
		add(partBPanel);
		add(resultPanel);
		
//		Only show the intro panel
		introPanel.setVisible(true);
		partAPanel.setVisible(false);
		partBPanel.setVisible(false);
		resultPanel.setVisible(false);
		
//		Close the program
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
//		Play the music
		playAudio("Audio/assessment.wav");
		
//		Show the frame
		setVisible(true);
	}
	
//	MENU BAR ===========================================================================================================================
	private void setMenuBar() {
		
//		Background colour
		menuBar.setBackground(turquoise);
		
//		Home menu
		menuBar.add(homeMenu);
		homeMenu.addMenuListener(this);
		
//		Concepts menu
		menuBar.add(conceptsMenu);
		conceptsMenu.addMenuListener(this);
		
//		Activity menu
		menuBar.add(activityMenu);
		activityMenu.addMenuListener(this);
		
//		Log out menu
		menuBar.add(logOutMenu);
		logOutMenu.addMenuListener(this);
		
//		Set menu bar
		setJMenuBar(menuBar);
	}

//	INTRO PANEL ========================================================================================================================
	public void introPanelSetup() {
		
//		Set up the panel
		introPanel.setBounds(0, 0, 1920, 1080);
		introPanel.setLayout(null);
		introPanel.setOpaque(false);
		
//		Title label
		titleLabel.setFont(new Font("Courier New", Font.BOLD, 80));
		titleLabel.setBounds(100, 100, 1500, 100);
		introPanel.add(titleLabel);
		
//		Beamy image
		beamyImage.setBounds(1180, 40, 300, 300);
		introPanel.add(beamyImage);
		
//		Intro message
		introLabel.setFont(new Font("Courier New", Font.PLAIN, 25));
		introLabel.setBounds(100, 150, 1000, 500);
		introPanel.add(introLabel);
		
//		Score label
		scoreLabel.setFont(new Font("Courier New", Font.BOLD, 28));
		if (CodeWithBeamyApplication.currentUser.getGrade() >= 0) 
			scoreLabel.setText(String.format("<html>Your score: %d%% <br/>Try again?", CodeWithBeamyApplication.currentUser.getGrade()));
		else
			scoreLabel.setText("<html>You have not completed the test. <br/>Try now?");
		scoreLabel.setBounds(100, 600, 800, 100);
		introPanel.add(scoreLabel);
		
//		Start button
		startButton.setBackground(Color.BLACK);
		startButton.setForeground(Color.WHITE);
		startButton.setBorder(new LineBorder(Color.WHITE)); // https://stackoverflow.com/questions/33954698/jbutton-change-default-border
		startButton.setFont(new Font("Courier New", Font.BOLD, 36));
		startButton.setBounds(1240, 650, 200, 80);
		startButton.addActionListener(this);
		introPanel.add(startButton);
	}
	
//	PART A PANEL ======================================================================================================================= 
	public void partAPanelSetup() {
		
//		Set up the panel
		partAPanel.setBounds(0, 0, 1920, 1080);
		partAPanel.setLayout(null);
		partAPanel.setOpaque(false);
		
//		Title label
		titleALabel.setFont(new Font("Courier New", Font.BOLD, 60));
		titleALabel.setBounds(180, 50, 1500, 100);
		partAPanel.add(titleALabel);
		
//		Beamy image
		beamySmallImage.setBounds(50, 50, 100, 100);
		partAPanel.add(beamySmallImage);
		
//		Instructions label
		instructionALabel.setFont(new Font("Courier New", Font.BOLD, 20));
		instructionALabel.setBounds(180, 50, 1300, 300);
		partAPanel.add(instructionALabel);
		
//		Button set up
		buttonSetup(1);
		
//		Question array
		questionSetup();
		
//		Labels + combo boxes
		contentASetup();
	}
	
//	BUTTON SET UP ======================================================================================================================
	public void buttonSetup(int panel) {
		
//		PART A
		if (panel == 2) {
			
//			Back button
			backButton.setBackground(Color.BLACK);
			backButton.setForeground(Color.WHITE);
			backButton.setBorder(new LineBorder(Color.WHITE)); // https://stackoverflow.com/questions/33954698/jbutton-change-default-border
			backButton.setFont(new Font("Courier New", Font.BOLD, 36));
			backButton.setBounds(90, 680, 200, 60);
			backButton.addActionListener(this);
			partAPanel.add(backButton);
			
//			Next button
			nextButton.setBackground(Color.BLACK);
			nextButton.setForeground(Color.WHITE);
			nextButton.setBorder(new LineBorder(Color.WHITE)); // https://stackoverflow.com/questions/33954698/jbutton-change-default-border
			nextButton.setFont(new Font("Courier New", Font.BOLD, 36));
			nextButton.setBounds(1240, 680, 200, 60);
			nextButton.addActionListener(this);
			partAPanel.add(nextButton);
		}
		
//		PART B
		else {
			
//			Submit button
			submitButton.setBackground(Color.BLACK);
			submitButton.setForeground(Color.WHITE);
			submitButton.setBorder(new LineBorder(Color.WHITE)); // https://stackoverflow.com/questions/33954698/jbutton-change-default-border
			submitButton.setFont(new Font("Courier New", Font.BOLD, 36));
			submitButton.setBounds(1240, 680, 200, 60);
			submitButton.addActionListener(this);
			partBPanel.add(submitButton);
		}
	}
	
//	QUESTION SET UP ====================================================================================================================
	public void questionSetup() {
		
//		Part A
		questionArray[0][0] = new Question(1, "Keywords that set the accessibility of classes, methods, and other members", 
											"Access Modifiers");
		questionArray[0][1] = new Question(1, "Diagram that describes the structure of an object", "UML Class Diagram");
		questionArray[0][2] = new Question(1, "Specifies the data a method requires to perform its task", "Parameter");
		questionArray[0][3] = new Question(1, "Category of variables made from objects", "Reference Type");
		questionArray[0][4] = new Question(1, "Boolean, byte, char, short, int, long, float, and double are all part of"
											+ " this category of variables", "Primitive Type");
		
//		Part B
		questionArray[1][0] = new Question(2, "", "int partNum");
		questionArray[1][1] = new Question(2, "", "public");
		questionArray[1][2] = new Question(2, "", "this.partDescription");
		questionArray[1][3] = new Question(2, "", "return partNum;");
		questionArray[1][4] = new Question(2, "", "int partNum");
	}
	
//	CONTENT A SET UP ===================================================================================================================
	public void contentASetup() {
		
//		Definitions 
		for (int index = 0; index < definitionLabelArray.length; index++) {
			definitionLabelArray[index] = new JLabel("   "+questionArray[0][index].getQuestion());
			definitionLabelArray[index].setOpaque(true);
			definitionLabelArray[index].setBackground(Color.WHITE);
			definitionLabelArray[index].setFont(new Font("Courier New", Font.PLAIN, 16));
			definitionLabelArray[index].setBounds(280, 70*index + 280, 1200, 50);
			partAPanel.add(definitionLabelArray[index]);
		}
		
//		Terms design
		for (int index = 0; index < termsComboBoxArray.length; index++) {
			termsComboBoxArray[index] = new JComboBox<String>();
			
			termsComboBoxArray[index].addItem("Class");
			termsComboBoxArray[index].addItem(questionArray[0][1].getAnswer());
			termsComboBoxArray[index].addItem("Object");
			termsComboBoxArray[index].addItem(questionArray[0][0].getAnswer());
			termsComboBoxArray[index].addItem("Instance");
			termsComboBoxArray[index].addItem(questionArray[0][3].getAnswer());
			termsComboBoxArray[index].addItem(questionArray[0][4].getAnswer());
			termsComboBoxArray[index].addItem("Argument");
			termsComboBoxArray[index].addItem(questionArray[0][2].getAnswer());
			termsComboBoxArray[index].addItem("Get Method");			
			termsComboBoxArray[index].setBackground(darkYellow);
			termsComboBoxArray[index].setFont(new Font("Courier New", Font.PLAIN, 16));
			termsComboBoxArray[index].setBounds(80, definitionLabelArray[index].getY(), 200, 50);
			partAPanel.add(termsComboBoxArray[index]);
		}
	}
	
//	PART B PANEL =======================================================================================================================
	public void partBPanelSetup() {
		
//		Set up the panel
		partBPanel.setBounds(0, 0, 1920, 1080);
		partBPanel.setLayout(null);
		partBPanel.setOpaque(false);
		
//		Title label
		titleBLabel.setFont(new Font("Courier New", Font.BOLD, 60));
		titleBLabel.setBounds(180, 50, 1500, 100);
		partBPanel.add(titleBLabel);
		
//		Instructions label
		instructionBLabel.setFont(new Font("Courier New", Font.BOLD, 20));
		instructionBLabel.setBounds(180, 50, 1300, 300);
		partBPanel.add(instructionBLabel);
		
//		Button set up
		buttonSetup(2);
		
//		Text fields
		contentBSetup();
		
//		Code image 
		codeImage.setBounds(370, 260, 800, 400);
		partBPanel.add(codeImage);
	}
	
//	CONTENT B SET UP ===================================================================================================================
	public void contentBSetup() {
		
//		Initialize and design 
		for (int index = 0; index < textFieldArray.length; index++) {
			textFieldArray[index] = new JTextField();
			textFieldArray[index].setOpaque(true);
			textFieldArray[index].setBackground(yellow);
			textFieldArray[index].setFont(new Font("Courier New", Font.PLAIN, 10));
		}
		
//		Set position
		textFieldArray[0].setBounds(458, 280, 70, 13);
		textFieldArray[1].setBounds(400, 365, 45, 13);
		textFieldArray[2].setBounds(430, 410, 130, 13);
		textFieldArray[3].setBounds(410, 542, 110, 13);
		textFieldArray[4].setBounds(557, 570, 75, 13);
		
//		Add to panel
		partBPanel.add(textFieldArray[0]);
		partBPanel.add(textFieldArray[1]);
		partBPanel.add(textFieldArray[2]);
		partBPanel.add(textFieldArray[3]);
		partBPanel.add(textFieldArray[4]);
	}
	
//	RESULT PANEL =======================================================================================================================
	public void resultPanelSetup() {

//		Set up the panel
		resultPanel.setBounds(0, 0, 1920, 1080);
		resultPanel.setLayout(null);
		resultPanel.setOpaque(false);
		
//		gradeNum label
		gradeNumLabel.setFont(new Font("Courier New", Font.BOLD, 80));
		gradeNumLabel.setBounds(100, 100, 1500, 100);
		resultPanel.add(gradeNumLabel);
		
//		Result label
		resultLabel.setFont(new Font("Courier New", Font.BOLD, 80));
		resultLabel.setBounds(100, 220, 1500, 100);
		resultPanel.add(resultLabel);
		
//		End message
		endLabel.setFont(new Font("Courier New", Font.PLAIN, 25));
		endLabel.setBounds(100, 250, 1000, 500);
		resultPanel.add(endLabel);
		
//		Home button
		homeButton.setBackground(Color.BLACK);
		homeButton.setForeground(Color.WHITE);
		homeButton.setBorder(new LineBorder(Color.WHITE)); // https://stackoverflow.com/questions/33954698/jbutton-change-default-border
		homeButton.setFont(new Font("Courier New", Font.BOLD, 36));
		homeButton.setBounds(1240, 650, 200, 80);
		homeButton.addActionListener(this);
		resultPanel.add(homeButton);
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

//      If an error occurs, print the error
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
		
//		https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java		
		audioClip.stop();
	    audioClip.close();
	}
	
//	ACTION LISTENER ====================================================================================================================
	@Override
	public void actionPerformed(ActionEvent event) {
		
//		If the user clicks the start button, close the introPanel and display the Part A Panel
		if (event.getSource() == startButton) {
			introPanel.setVisible(false);
			panelNum = 2;
			partAPanel.setVisible(true);
		}
		
//		If the user clicks the back button -----------------------------------------------------
		else if (event.getSource() == backButton){
			
//			If currently on Part A Panel, update panel # and go to intro panel
			if (panelNum == 2) {
				panelNum = 1;
				partAPanel.setVisible(false);
				introPanel.setVisible(true);
			}		
//			Otherwise if currently on Part B Panel, update panel # and go to part A panel
			else {
				panelNum = 2;
				partBPanel.setVisible(false);
				partBPanel.remove(beamySmallImage);
				partAPanel.add(beamySmallImage);
				partBPanel.remove(backButton);
				partAPanel.add(backButton);
				partAPanel.setVisible(true);
			}
		}
		
//		If the user clicks the next button -----------------------------------------------------
		else if (event.getSource() == nextButton) {
	
//			Go through all combo boxes and add a point for each correct answer
			for (int index = 0; index < termsComboBoxArray.length; index++) {
				if (termsComboBoxArray[index].getSelectedItem().equals(questionArray[0][index].getAnswer())) 
					points++;
			}
			
//			Display part B Panel
			panelNum = 3;
			partAPanel.setVisible(false);
			partAPanel.remove(beamySmallImage);
			partBPanel.add(beamySmallImage);
			partAPanel.remove(backButton);
			partBPanel.add(backButton);
			partBPanel.setVisible(true);
		}
		
//		If the user clicks the submit button ---------------------------------------------------
		else if (event.getSource() == submitButton) {
			
//			Go through all text fields and add a point for each correct answer
			for (int index = 0; index < textFieldArray.length; index++) {
				if (textFieldArray[index].getText().equals(questionArray[1][index].getAnswer()))
					points++;
			}
		
//			Calculate gradeNum
			gradeNum = points*10;
			gradeNumLabel.setText(gradeNum+"%");
			
//			Update user's grade
			CodeWithBeamyApplication.currentUser.setGrade(gradeNum);
			
//			Read grade to file
			updateFile();
			
//			Set result label and end label
			setResultLabel();
			
//			Display results panel
			panelNum = 3;
			introPanel.remove(beamyImage);
			resultPanel.add(beamyImage);
			partBPanel.setVisible(false);
			resultPanel.setVisible(true);
		}
		
//		If the user clicks the home button, close this frame and display the main frame---------
		else if (event.getSource() == homeButton) {
			setVisible(false);
			stopAudio("Audio/assessment.wav");
			new MainFrame();
		}
	}
	
//	RESULT LABEL =======================================================================================================================
	private void setResultLabel(){
//		If gradeNum is between 90-100
		if (gradeNum >= 90) {
			resultLabel.setText("Congratulations!");
			endLabel.setText("<html> Excellent work! You've mastered the concepts brilliantly and it shows in your high score. "
						   + "Your dedication and hard work have truly paid off. "
						   + "Keep up the great effort and continue to shine in your coding journey. "
						   + "Remember, consistent practice will keep your skills sharp. "
						   + "We're excited to see what amazing projects you'll create next!");
		}
//		If gradeNum is between 80-90
		else if (gradeNum >= 80) {
			resultLabel.setText("Amazing Work!");
			endLabel.setText("<html> Great job! You have a strong understanding of the material, and your hard work is evident. "
						   + "With a little more practice, you'll reach perfection. "
						   + "Keep up the great work and keep striving for excellence. "
						   + "Each step you take is bringing you closer to mastery. "
						   + "Stay focused and continue to challenge yourself. "
						   + "Don't forget to review the areas where you made mistakes; it's a great way to learn and grow. "
						   + "You're on the right path, and we believe in your potential to excel even further!");
		}
//		If gradeNum is between 70-80
		else if (gradeNum >= 70) {
			resultLabel.setText("You Did It!");
			endLabel.setText("<html> Well done! You're making solid progress and it's clear that you have a good grasp of the concepts. "
						   + "Keep studying and practicing, and you'll see even more improvement. "
						   + "Your determination and effort are paying off. "
						   + "Stay motivated and keep moving forward. With continued practice, you'll reach even greater heights! "
						   + "Take the time to revisit challenging topics and seek help if needed. "
						   + "Remember, every coder faces obstacles, and overcoming them makes you stronger. "
						   + "Keep pushing forward, you've got this!");
		}
//		If gradeNum is between 60-70
		else if (gradeNum >= 60) {
			resultLabel.setText("Good Effort!");
			endLabel.setText("<html> You're getting there! You're on the right track and your understanding is building. "
						   + "Review the material and keep practicing; you're closer to mastery than you think. "
						   + "Every bit of effort you put in now will pay off in the long run. "
						   + "Believe in yourself and keep pushing forward. "
						   + "You've got the potential to achieve great things! "
						   + "Don't be afraid to ask for help or use additional resources to strengthen your knowledge. "
						   + "Persistence and dedication will lead you to success. "
						   + "Keep your chin up and stay focused on your goals!");
		}
//		If gradeNum is below 60
		else {
			resultLabel.setText("So close!");
			endLabel.setText("<html> Don't worry, everyone learns at their own pace. "
						   + "Your effort is commendable, and with more review and practice, you'll improve significantly. "
						   + "Keep pushing forward and don't hesitate to revisit the lessons and activities. "
						   + "Remember, persistence is key to success. Stay positive and keep working hard. "
						   + "You've got this and we're here to support you every step of the way! "
						   + "Consider setting small, achievable goals to help build your confidence. "
						   + "Each step forward is progress, and with determination, you'll see great improvement. "
						   + "Keep believing in yourself and never give up!");
		}
	}
	
//	UPDATE FILE ========================================================================================================================
	private void updateFile() {
		
//		Write to file -------------------------------------------------------
		CodeWithBeamyApplication.currentUser.setGrade(gradeNum);
		
//	    Specify the file path and name
	    Path filePath = Paths.get("Data/users.txt");
	    
//	    Clear the file 
	    try {
	        Files.write(filePath, Collections.emptyList()); // https://stackoverflow.com/questions/6994518/how-to-delete-the-content-of-text-file-without-deleting-itself
	        
//	    Print any errors that occur
	    } catch (IOException error) {
	        System.out.println("An error occurred while clearing the file.");
	        error.printStackTrace();
	    }
	    
//	    Write the array list to the file
	    try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.WRITE)) {
	    	
	    	for (int index = 0; index < CodeWithBeamyApplication.usersArrayList.size(); index++) {
	    		User user = CodeWithBeamyApplication.usersArrayList.get(index);
	    		writer.write(user.getFirstName() + ", " + user.getLastName() + ", " + user.getUserName() + ", " + user.getPassword() 
	    					+ ", " + user.getChapter() + ", " + user.getEscapeTime() + ", " + user.getGrade() + "\n");
	    	}	    		
//	    Print any errors that occur
	    } catch (IOException error) {
	    	System.out.println("An error occurred while writing to the file.");
	    	error.printStackTrace();
	    }
	}
	
//	MENU LISTENERS =====================================================================================================================
	@Override
	public void menuSelected(MenuEvent menu) {
		
//		If the user clicks the homeMenu, close this frame and show the main frame
		if (menu.getSource() == homeMenu) {
			setVisible(false);
			stopAudio("Audio/assessment.wav");
			new MainFrame();
		}	
//		If the user clicks the conceptsMenu, close this frame and show the concepts frame
		else if (menu.getSource() == conceptsMenu) {
			setVisible(false);
			stopAudio("Audio/assessment.wav");
			new ConceptsFrame();
		}
//		If the user clicks the assessmentMenu, close this frame and show the assessment frame
		else if (menu.getSource() == activityMenu) {
			setVisible(false);
			stopAudio("Audio/assessment.wav");
			new ActivityFrame();
		}
		
//		If the user clicks the logOutMenu, close this frame and show the log in frame
		else if (menu.getSource() == logOutMenu) {
			setVisible(false);
			stopAudio("Audio/assessment.wav");
			new LoginFrame();
		}
	}

//	Unused
	@Override
	public void menuDeselected(MenuEvent e) {
	}
	@Override
	public void menuCanceled(MenuEvent e) {	
	}
} // End of class
