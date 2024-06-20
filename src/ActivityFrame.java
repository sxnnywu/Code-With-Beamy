// IMPORTS =============================================================================================================================

// GUI elements
import javax.swing.*;
import javax.swing.border.LineBorder;

// Designing GUI elements
import java.awt.Color;
import java.awt.Font;

// Playing audio
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

// Writing to the file
import java.io.BufferedWriter;
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
Description: This class creates the activity frame of the Code With Beamy Application
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
public class ActivityFrame extends JFrame implements ActionListener, MenuListener {
	
//	FIELDS =============================================================================================================================
	
//	Panel number
	static int panel = 1;
	
//	Check correctness of answers in each panel
	static int points = 0;
	
//	Instructions for each activity
	static String roomInstructionsArray[] = new String[4];	

//	Questions to be asked
	static Question questionArray[][] = new Question[4][5];
	
//	Music being played
	private static Clip audioClip;
	
//	Game timer
	private double seconds = 0;
	private double minutes = 0; 
	private Timer gameTimer = new Timer(1000, this); 
	
//	CREATE GUI ELEMENTS ================================================================================================================
	
//	Menu Bar 
	private JMenuBar menuBar = new JMenuBar();
	private JMenu homeMenu = new JMenu("Home");
	private JMenu conceptsMenu = new JMenu("Beamy's Knowledge Hub");
	private JMenu assessmentMenu = new JMenu("Beamy's Test of Wisdom");
	private JMenu logOutMenu = new JMenu("Log Out");
	
//	Panels 
	private JPanel introPanel = new JPanel();
	private JPanel gameroom1Panel = new JPanel();
	private JPanel gameroom2Panel = new JPanel();
	private JPanel gameroom3Panel = new JPanel();
	private JPanel gameroom4Panel = new JPanel();
	private JPanel escapedPanel = new JPanel();

//	Labels
	private JLabel room2StepArray[] = new JLabel[5];
	private JLabel titleLabel = new JLabel("Beamy's CodeQuest");
	private JLabel introLabel = new JLabel("<html> Welcome to Beamy's Code Quest! "
										 + "In this adventure, you'll be guided by Beamy on a journey to unlock the secrets of "
										 + "Object-Oriented Programming. "
										 + "To escape, you must complete a series of challenges that will test your OOP knowledge "
										 + "and coding skills. Let's get started!");
	private JLabel highTimeLabel = new JLabel("<html> You have not completed this activity. Play now!");
	private JLabel instructionsLabel = new JLabel();
	private JLabel room4StatementArray[] = new JLabel[5];
	private JLabel congratLabel = new JLabel("Congratulations!");
	private JLabel timeLabel = new JLabel();
	private JLabel escapeLabel = new JLabel("<html> You have successfully navigated through Beamy's Code Quest and escaped the OOP Escape Room. <br/>"
										  + "<br/>With each challenge, you have demonstrated your knowledge and understanding of Object-Oriented Programming. <br/>"
										  + "<br/>Beamy is proud of you, and you are now a certified OOP adventurer!");
	
	//	Buttons
	private JButton submitArray[] = new JButton[4];
	private JButton startButton = new JButton("Start");
	private JButton room3CodeArray[] = new JButton[5];
	private JButton homeButton = new JButton("Back Home");
	
//	Images
	private JLabel backgroundArray[] = new JLabel[6];
	private JLabel iconArray[] = new JLabel[4];
	private ImageIcon unscaledBeamyImage = new ImageIcon("Images/beamy.png");
	private JLabel beamyImage = new JLabel(new ImageIcon(unscaledBeamyImage.getImage().getScaledInstance(350, 350, java.awt.Image.SCALE_SMOOTH)));
	ImageIcon unscaledCode = new ImageIcon("Images/Escape Room/activityCode.png");
	private JLabel codeImage = new JLabel(new ImageIcon(unscaledCode.getImage().getScaledInstance(1000, 500, java.awt.Image.SCALE_SMOOTH)));
	
//	Text fields
	private JTextField room1AnswerArray[] = new JTextField[3];
	private JTextField room3Answer = new JTextField();
	
//	Combo boxes
	@SuppressWarnings("unchecked")
	private JComboBox<String> room2AnswerArray[] = new JComboBox[5];
	@SuppressWarnings("unchecked")
	private JComboBox<String> room4AnswerArray[] = new JComboBox[5];
	
//	COLOURS TO BE USED =================================================================================================================
	private Color yellow = Color.decode("#FFF0AE");
	private Color turquoise = Color.decode("#45C9DD");
	
//	CONSTRUCTOR ========================================================================================================================
	public ActivityFrame() {
		
//		Set up the frame
		setTitle("Beamy's Knowledge Hub");
		setSize(1920, 1080);
		setLayout(null);
		
//		Set up the menu bar
		setMenuBar();
		
//		Set up the background image array
		backgroundSetup();
		
//		Set up icons array
		iconSetup();
		
//		Set up instructions array 
		instructionsSetup();
		
//		Set up submit button array
		submitSetup();
		
//		Set up the intro panel
		introPanelSetup();
		introPanel.setVisible(true);
		
//		Close the program
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
//		Play music
		playAudio("Audio/activity.wav");
		
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
		
//		Activity menu
		menuBar.add(conceptsMenu);
		conceptsMenu.addMenuListener(this);
		
//		Assessment menu
		menuBar.add(assessmentMenu);
		assessmentMenu.addMenuListener(this);
		
//		Log out menu
		menuBar.add(logOutMenu);
		logOutMenu.addMenuListener(this);
		
//		Set menu bar
		setJMenuBar(menuBar);
	}
	
//	BACKGROUND IMAGE ARRAY =============================================================================================================
	private void backgroundSetup() {
		
//		For each panel, initialize the array, scale the image, and set the bounds
		for (int index = 0; index < backgroundArray.length; index++) {
			ImageIcon unscaledBackground = new ImageIcon("Images/Escape Room/panel"+(index+1)+".png");
			backgroundArray[index] = new JLabel(new ImageIcon(unscaledBackground.getImage().getScaledInstance(1600, 900, java.awt.Image.SCALE_SMOOTH)));
			backgroundArray[index].setBounds(0, 0, 1600, 900);
		}
	}
	
//	ICONS ARRAY ========================================================================================================================
	private void iconSetup() {
	
//		For each icon, initialize the array, scale the icon, and set the bounds
		for (int index = 0; index < iconArray.length; index++) {		
			ImageIcon unscaledIcon = new ImageIcon("Images/Escape Room/icon"+(index+1)+".png");
			iconArray[index] = new JLabel(new ImageIcon(unscaledIcon.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH)));
			iconArray[index].setBounds(30, 50, 150, 150);
		}
	}
	
//	INSTRUCTIONS LABEL ARRAY ===========================================================================================================
	private void instructionsSetup() {
		
//		Initialize instructions of each room and set its text
		roomInstructionsArray[0] = "<html> Beamy has discovered an ancient, enchanted codebook, but some of the vital keywords are missing! Without <br/>"
								 + "these keywords, the codebook's magic can't be fully harnessed. <br/>"
								 + "<br/>"
								 + "Your task is to fill in the blanks and restore the codebook's power:";
		roomInstructionsArray[1] = "<html> Having restored the codebook, Beamy needs your help to organize the scattered pages of the Codex. The Codex <br/>"
								 + "contains the blueprint for creating powerful classes, but the steps are all jumbled up! <br/>"
								 + "<br/>"
								 + "Put the steps in the correct order to unlock the next part of your journey:";
		roomInstructionsArray[2] = "<html> Beamy has ventured into the Debugging Dungeon, where sinister bugs and errors lurk in every corner! <br/>"
								 + "<br/>"
								 + "To escape, you must find the incorrect code and fix the bugs (errors):";
		roomInstructionsArray[3] = "<html> You’ve reached the Gate of Truth! You’re so close to escaping: <br/>"
								 + "<br/>"
								 + "Answer the true or false questions about OOP to unlock the path forward.";
	}
	
//	INSTRUCTIONS LABEL SET UP ==========================================================================================================
	private void instructionsLabelSetup(int panel) {
		
		instructionsLabel.setText(roomInstructionsArray[panel-2]);
		instructionsLabel.setForeground(Color.WHITE);
		instructionsLabel.setFont(new Font("Courier New", Font.BOLD, 18));
		instructionsLabel.setBounds(200, 20, 1700, 120);
	}
	
//	SUBMIT BUTTON ARRAY ================================================================================================================
	private void submitSetup() {
		
//		Initialize the array
		for (int index = 0; index < submitArray.length; index++) 
			submitArray[index] = new JButton("Submit");
	}
	
//	SUBMIT BUTTON SET UP ===============================================================================================================
	private void submitButtonSetup(int panel) {
		
		submitArray[panel-2].setContentAreaFilled(false);
		submitArray[panel-2].setBorder(new LineBorder(Color.WHITE)); // https://stackoverflow.com/questions/33954698/jbutton-change-default-border
		submitArray[panel-2].setForeground(Color.WHITE);
		submitArray[panel-2].setFont(new Font("Courier New", Font.BOLD, 30));
		submitArray[panel-2].setBounds(700, 675, 200, 50);
		submitArray[panel-2].addActionListener(this);
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
		
//	https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java		
		audioClip.stop();
        audioClip.close();
	}

//	INTRO PANEL ========================================================================================================================
	private void introPanelSetup() {
		
//		Update the panel #
		panel = 1;
		
//		Set up the panel
		introPanel.setBounds(0, 0, 1920, 1080);
		introPanel.setLayout(null);
		
//		Title label
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Courier New", Font.BOLD, 90));
		titleLabel.setBounds(80, 50, 1920, 100);
		introPanel.add(titleLabel);
		
//		Beamy image
		beamyImage.setBounds(1150, 50, 350, 350);
		introPanel.add(beamyImage);
		
//		Intro message
		introLabel.setForeground(Color.WHITE);
		introLabel.setFont(new Font("Courier New", Font.PLAIN, 30));
		introLabel.setBounds(80, 250, 800, 300);
		introPanel.add(introLabel);
		
//		High time label
		highTimeLabel.setForeground(Color.WHITE);
		if (CodeWithBeamyApplication.currentUser.getEscapeTime() >= 60)
			highTimeLabel.setText(String.format("Your time: %.0f min %.0f secs. Can you beat it?", 
					Math.floor(CodeWithBeamyApplication.currentUser.getEscapeTime()/60), // https://stackoverflow.com/questions/53860132/java-how-to-do-floor-division
									CodeWithBeamyApplication.currentUser.getEscapeTime()%60));
		else if (CodeWithBeamyApplication.currentUser.getEscapeTime() > 0)
			highTimeLabel.setText(String.format("Your time: %.0f secs. Can you beat it?", CodeWithBeamyApplication.currentUser.getEscapeTime()));
		highTimeLabel.setFont(new Font("Courier New", Font.BOLD, 32));
		highTimeLabel.setBounds(100, 600, 700, 80);
		introPanel.add(highTimeLabel);
		
//		Start button
		startButton.setContentAreaFilled(false);
		startButton.setBorder(new LineBorder(Color.WHITE)); // https://stackoverflow.com/questions/33954698/jbutton-change-default-border
		startButton.setForeground(Color.WHITE);
		startButton.setFont(new Font("Courier New", Font.BOLD, 40));
		startButton.setBounds(1230, 650, 220, 70);
		startButton.addActionListener(this);
		introPanel.add(startButton);
		
//		Set the background image
		introPanel.add(backgroundArray[panel - 1]);
		
//		Add the panel
		add(introPanel);
	}

//	GAMEROOM 1 PANEL ===================================================================================================================
	private void gameroom1PanelSetup() {
		
//		Update panel #
		panel = 2;
	
//		Set up the panel
		gameroom1Panel.setBounds(0, 0, 1920, 1080);
		gameroom1Panel.setLayout(null);
		
//		Icon 
		gameroom1Panel.add(iconArray[panel - 2]);
		
//		Instructions label
		instructionsLabelSetup(panel);
		gameroom1Panel.add(instructionsLabel);
		
//		Code image 
		codeImage.setBounds(300, 150, 1000, 500);
		gameroom1Panel.add(codeImage);
		
//		Set up questions array
		room1QuestionSetup();
		
//		Text fields
		room1BlankSetup();
		room1TextFieldSetup();
		
//		Submit button
		submitButtonSetup(panel);
		gameroom1Panel.add(submitArray[panel-2]);
		
//		Set the background image
		gameroom1Panel.add(backgroundArray[panel - 1]);
		
//		Add the panel
		add(gameroom1Panel);
	}
	
//	ROOM 1 QUESTIONS ===================================================================================================================
	private void room1QuestionSetup() {
		
		questionArray[0][0] = new Question(1, "blank1", "salary");
		questionArray[0][1] = new Question(1, "blank2", "String");
		questionArray[0][2] = new Question(1, "blank3", "setLastName");
	}
	
//	ROOM 1 TEXT FIELD ARRAY ============================================================================================================
	private void room1BlankSetup() {
		
//		Initialize the array
		for (int index = 0; index < room1AnswerArray.length; index++) {
			room1AnswerArray[index] = new JTextField();
			room1AnswerArray[0].setBackground(yellow);
		}
	}
	
//	ROOM 1 BLANKS ======================================================================================================================
	private void room1TextFieldSetup() {
	
//		Blank 1
		room1AnswerArray[0].setBounds(478, 260, 57, 20);
		gameroom1Panel.add(room1AnswerArray[0]);	
		
//		Blank 2
		room1AnswerArray[1].setBounds(583, 433, 60, 20);
		gameroom1Panel.add(room1AnswerArray[1]);
		
//		Blank 3
		room1AnswerArray[2].setBounds(458, 585, 107, 20);
		gameroom1Panel.add(room1AnswerArray[2]);
	}
	
//	GAMEROOM 2 PANEL ===================================================================================================================
	private void gameroom2PanelSetup() {
		
//		Update panel #
		panel = 3;
		
//		Set up the panel
		gameroom2Panel.setBounds(0, 0, 1920, 1080);
		gameroom2Panel.setLayout(null);
		
//		Icon 
		gameroom2Panel.add(iconArray[panel - 2]);
		
//		Instructions label
		instructionsLabelSetup(panel);
		gameroom2Panel.add(instructionsLabel);
		
//		Set up questions array
		room2QuestionSetup();
		
//		Step labels array
		room2StepSetup();
		
//		Combo boxes array
		room2ComboSetup();
		
//		Place the labels & combo boxes 
		room2VisualSetup();
		
//		Submit button
		submitButtonSetup(panel);
		gameroom2Panel.add(submitArray[panel-2]);
		
//		Set the background image
		gameroom2Panel.add(backgroundArray[panel - 1]);
		
//		Add the panel
		add(gameroom2Panel);
	}
	
//	ROOM 2 QUESTIONS ===================================================================================================================
	private void room2QuestionSetup() {
		
		questionArray[1][0] = new Question(2, "toString", "4");
		questionArray[1][1] = new Question(2, "Constructor", "2");
		questionArray[1][2] = new Question(2, "Getters + Setters", "3");
		questionArray[1][3] = new Question(2, "Utility", "5");
		questionArray[1][4] = new Question(2, "Fields", "1");
	}
	
//	ROOM 2 LABEL ARRAY =================================================================================================================
	private void room2StepSetup() {
		
		room2StepArray[0] = new JLabel("   toString");
		room2StepArray[1] = new JLabel("   Constructor");
		room2StepArray[2] = new JLabel("   Getters + Setters");
		room2StepArray[3] = new JLabel("   Utility");
		room2StepArray[4] = new JLabel("   Fields");
	}
	
//	ROOM 2 COMBO BOX ARRAY =============================================================================================================
	private void room2ComboSetup() {
	
		for (int index = 0; index < room2AnswerArray.length; index++) {
			room2AnswerArray[index] = new JComboBox<String>();
			room2AnswerArray[index].addItem("1");
			room2AnswerArray[index].addItem("2");
			room2AnswerArray[index].addItem("3");
			room2AnswerArray[index].addItem("4");
			room2AnswerArray[index].addItem("5");
		}
	}
	
//	ROOM 2 LABELS + COMBO BOXES ========================================================================================================
	private void room2VisualSetup() {
		
		for (int index = 0; index < questionArray[1].length; index++) {
			
//			Labels
			room2StepArray[index].setOpaque(true);
			room2StepArray[index].setBackground(Color.BLACK);
			room2StepArray[index].setForeground(Color.WHITE);
			room2StepArray[index].setFont(new Font("Courier New", Font.PLAIN, 20));
			room2StepArray[index].setBounds(320, index*80 + 230, 1000, 60);
			gameroom2Panel.add(room2StepArray[index]);
			
//			Combo Box
			room2AnswerArray[index].setBackground(Color.BLACK);
			room2AnswerArray[index].setForeground(Color.WHITE);
			room2AnswerArray[index].setFont(new Font("Courier New", Font.BOLD, 20));
			room2AnswerArray[index].setBounds(200, room2StepArray[index].getY(), 120, 50);
			gameroom2Panel.add(room2AnswerArray[index]);
		}
	}
	
//	GAMEROOM 3 PANEL ===================================================================================================================
	private void gameroom3PanelSetup() {
		
//		Update panel #
		panel = 4;
		
//		Set up the panel
		gameroom3Panel.setBounds(0, 0, 1920, 1080);
		gameroom3Panel.setLayout(null);
		
//		Icon 
		gameroom3Panel.add(iconArray[panel - 2]);
		
//		Instructions label
		instructionsLabelSetup(panel);
		gameroom3Panel.add(instructionsLabel);
		
//		Set up the button array
		room3CodeSetup();
		
//		Place the buttons
		room3ButtonPlacement();
		
//		Text fields
		room3Answer.setBackground(Color.WHITE);
		room3Answer.setFont(new Font("Courier New", Font.BOLD, 23));
		room3Answer.setBounds(250, 550, 1070, 60);
	
//		Submit button
		submitButtonSetup(panel);
		gameroom3Panel.add(submitArray[panel-2]);
		
//		Set the background image 
		gameroom3Panel.add(backgroundArray[panel - 1]);
		
//		Add the panel
		add(gameroom3Panel);
	}
	
//	ROOM 3 BUTTONS =====================================================================================================================
	private void room3CodeSetup() {
		
		room3CodeArray[0] = new JButton("private String name;");
		room3CodeArray[1] = new JButton("public void greet() { System.out.println(\"Hello, my name is \" + name); }");
		room3CodeArray[2] = new JButton("public boolean isAdult() { return age >= 18; }");
		room3CodeArray[3] = new JButton("public void setName(name) { this.name = name; }");
		room3CodeArray[4] = new JButton("public int getAge() { return age; }");	
	}
	
//	ROOM 3 BUTTON PLACEMENT ============================================================================================================
	private void room3ButtonPlacement() {
		
		for (int index = 0; index < questionArray[2].length; index++) {
			room3CodeArray[index].setOpaque(true);
			room3CodeArray[index].setBackground(Color.BLACK);
			room3CodeArray[index].setForeground(Color.WHITE);
			room3CodeArray[index].setFont(new Font("Courier New", Font.PLAIN, 20));
			room3CodeArray[index].setBounds(250, index*80 + 150, 1070, 60);
			room3CodeArray[index].addActionListener(this);
			gameroom3Panel.add(room3CodeArray[index]);
		}
	}
	
//	GAMEROOM 4 PANEL ===================================================================================================================
	private void gameroom4PanelSetup() {
		
//		Update panel #
		panel = 5;
		
//		Set up the panel
		gameroom4Panel.setBounds(0, 0, 1920, 1080);
		gameroom4Panel.setLayout(null);
			
//		Icon 
		gameroom4Panel.add(iconArray[panel - 2]);
		
//		Instructions label
		instructionsLabelSetup(panel);
		gameroom4Panel.add(instructionsLabel);
			
//		Set up questions array
		room4QuestionSetup();
		
//		Step labels array
		room4StatementSetup();
		
//		Combo boxes array
		room4ComboSetup();
		
//		Place the labels & combo boxes 
		room4VisualSetup();
		
//		Submit button
		submitButtonSetup(panel);
		gameroom4Panel.add(submitArray[panel-2]);
		
//		Set the background image
		gameroom4Panel.add(backgroundArray[panel - 1]);
		
//		Add the panel
		add(gameroom4Panel);
	}
	
//	ROOM 4 QUESTIONS ===================================================================================================================
	private void room4QuestionSetup() {
		
		questionArray[3][0] = new Question(2, "Fields in a class represent the attributes of objects.", "True");
		questionArray[3][1] = new Question(2, "Getters and setters are used to create object instances in Java.", "False");
		questionArray[3][2] = new Question(2, "Private methods in a class can be accessed from any other class.", "False");
		questionArray[3][3] = new Question(2, "Static variables can be accessed throughout the class.", "True");
		questionArray[3][4] = new Question(2, "Constructors are used to initialize the state of an object in Java.", "True");
	}
	
//	ROOM 4 LABEL ARRAY =================================================================================================================
	private void room4StatementSetup() {
		
		for (int index = 0; index < room4StatementArray.length; index++)
			room4StatementArray[index] = new JLabel("   "+ questionArray[3][index]);
	}
	
//	ROOM 4 COMBO BOX ARRAY =============================================================================================================
	private void room4ComboSetup() {
		
		for (int index = 0; index < room4AnswerArray.length; index++) {
			room4AnswerArray[index] = new JComboBox<String>();
			room4AnswerArray[index].addItem("True");
			room4AnswerArray[index].addItem("False");
		}
	}
	
//	ROOM 4 VISUAL SET UP ===============================================================================================================
	private void room4VisualSetup() {
		for (int index = 0; index < questionArray[3].length; index++) {
		
//			Labels
			room4StatementArray[index].setOpaque(true);
			room4StatementArray[index].setBackground(Color.BLACK);
			room4StatementArray[index].setForeground(Color.WHITE);
			room4StatementArray[index].setFont(new Font("Courier New", Font.PLAIN, 20));
			room4StatementArray[index].setBounds(320, index*80 + 230, 1000, 60);
			gameroom4Panel.add(room4StatementArray[index]);
		
//			Combo Box
			room4AnswerArray[index].setBackground(Color.BLACK);
			room4AnswerArray[index].setForeground(Color.WHITE);
			room4AnswerArray[index].setFont(new Font("Courier New", Font.BOLD, 20));
			room4AnswerArray[index].setBounds(200, room4StatementArray[index].getY(), 120, 50);
			gameroom4Panel.add(room4AnswerArray[index]);
		}
	}
	
//	ESCAPED PANEL ======================================================================================================================
	private void escapedPanelSetup() {
		
//		Update panel #
		panel = 6;
		
//		Set up the panel
		escapedPanel.setBounds(0, 0, 1920, 1080);
		escapedPanel.setLayout(null);
		
//		Congrat label
		congratLabel.setForeground(Color.WHITE);
		congratLabel.setFont(new Font("Courier New", Font.BOLD, 90));
		congratLabel.setBounds(80, 50, 1920, 100);
		escapedPanel.add(congratLabel);
		
//		Time label
		timeLabel.setForeground(Color.WHITE);
		timeLabel.setFont(new Font("Courier New", Font.BOLD, 50));
		timeLabel.setBounds(80, 180, 1920, 100);
		escapedPanel.add(timeLabel);
		
//		Beamy image
		beamyImage.setBounds(1150, 50, 350, 350);
		escapedPanel.add(beamyImage);
		
//		Escape label
		escapeLabel.setForeground(Color.WHITE);
		escapeLabel.setFont(new Font("Courier New", Font.PLAIN, 27));
		escapeLabel.setBounds(80, 300, 770, 320);
		escapedPanel.add(escapeLabel);
		
//		Home button
		homeButton.setContentAreaFilled(false);
		homeButton.setBorder(new LineBorder(Color.WHITE)); // https://stackoverflow.com/questions/33954698/jbutton-change-default-border
		homeButton.setForeground(Color.WHITE);
		homeButton.setFont(new Font("Courier New", Font.BOLD, 35));
		homeButton.setBounds(1230, 650, 250, 70);
		homeButton.addActionListener(this);
		escapedPanel.add(homeButton);
		
//		Set the background image
		escapedPanel.add(backgroundArray[panel - 1]);
		
//		Add the panel
		add(escapedPanel);
	}
	
//	ACTION LISTENER ====================================================================================================================
	@Override
	public void actionPerformed(ActionEvent event) {
		
//		If the user clicks the start button, close this panel and display the gameroom 1 panel + start the timer 
		if (event.getSource() == startButton) {	
			introPanel.setVisible(false);
			gameroom1PanelSetup();
			gameroom1Panel.setVisible(true);
			gameTimer.start();
		}
		
//		If the timer ticks, update the time ---------------------------------------------------------------------
		if (event.getSource() == gameTimer) 
			seconds++;
		
//		If the user clicks the submit button --------------------------------------------------------------------
		for (int index = 0; index < submitArray.length; index++) {
			if (event.getSource() == submitArray[index]) {

				if (index == 0) // Gameroom 1
					gameroom1Check();
				
				else if (index == 1) // Gameroom 2
					gameroom2Check();
				
				else if (index == 2) // Gameroom 3
					gameroom3Check();
				
				else // Gameroom 4
					gameroom4Check();
			}
		}
		
//		Gameroom 3 buttons	
		for (int index = 0; index < questionArray[2].length; index++) {
			
//				If the user clicks a line of code
				if (event.getSource() == room3CodeArray[index]) {
					
//					Correct line of code
					if (index == 3) {	
						
//						Tell the user to enter what the line of code should be
						JOptionPane.showMessageDialog(null, "Good choice - Now, please enter the corrected line of code!");
//						Add a text field
						gameroom3Panel.add(room3Answer);
					}
					
//					If the user clicks the incorrect line of code
					else {
//						Display a pop up message to inform the user
						JOptionPane.showMessageDialog(null, "Oops - Recheck your work!");
					}
				}
		}
		
//		If the user clicks the home button, close this frame and show the MainFrame ------------------------------
		if (event.getSource() == homeButton) {
			setVisible(false);
			stopAudio("Audio/activity.wav");
			new MainFrame();
		}
	}	
	
//	GAMEROOM 1 CHECK ===================================================================================================================
	private void gameroom1Check() {
		
		points = 0;
		
//		Check all text box fields - if text entered in the blank is correct, add a point
		for (int index1 = 0; index1 < room1AnswerArray.length; index1++) {	
			if (room1AnswerArray[index1].getText().equals(questionArray[0][index1].getAnswer()))
				points++;
		}
		
//		If all answers are correct, display a pop up message to inform the user and move on to next panel
		if (points == 3) {
			JOptionPane.showMessageDialog(null, "Correct - Good job!");
			gameroom1Panel.setVisible(false);
			gameroom1Panel.removeAll();
			gameroom2PanelSetup();
			add(gameroom2Panel);
			gameroom2Panel.setVisible(true);
		}

//		If not, display a pop up message to inform the user
		else 
			JOptionPane.showMessageDialog(null, "Oops - Recheck your work!");
	}
	
//	GAMEROOM 2 CHECK ===================================================================================================================
	private void gameroom2Check() {
		
		points = 0;
		
//		Check all combo boxes - if the chosen step # is corrrect, add a point
		for (int index1 = 0; index1 < room2AnswerArray.length; index1++) {
			if (room2AnswerArray[index1].getSelectedItem().equals(questionArray[1][index1].getAnswer()))
				points++;
		}

//		If all answers are correct, display a pop up message to inform the user and move on to next panel
		if (points == 5) {
			JOptionPane.showMessageDialog(null, "Correct - Good job!");
			gameroom2Panel.setVisible(false);
			gameroom2Panel.removeAll();
			gameroom3PanelSetup();
			add(gameroom3Panel);
			gameroom3Panel.setVisible(true);
		}

//		If not, display a pop up message to inform the user
		else 
			JOptionPane.showMessageDialog(null, "Oops - Recheck your work!");
	}
	
//	GAMEROOM 3 CHECK ===================================================================================================================
	private void gameroom3Check() {
		
//		If they haven't chosen the right line of code yet, inform them
		if (!room3Answer.isVisible()) {
			JOptionPane.showMessageDialog(null, "Oops - Make sure to click on the line of code that has an error!");
		}
	
//		Otherwise if they have and the text entered in room3Answer text field is correct, inform the user and move to next panel
		else if (room3Answer.getText().equals("public void setName(String name) { this.name = name; }")){
			JOptionPane.showMessageDialog(null, "Correct - Good job!");
			gameroom3Panel.setVisible(false);
			gameroom3Panel.removeAll();
			gameroom4PanelSetup();
			add(gameroom4Panel);
			gameroom4Panel.setVisible(true);
		}
	
//		Otherwise if the text is incorrect, inform the user
		else {
			JOptionPane.showMessageDialog(null, "Oops - Recheck your work!");	
		}
	}
		
//	GAMEROOM 4 CHECK ===================================================================================================================
	private void gameroom4Check() {
		
		points = 0;
		
//		Check all combo boxes - if the chosen step # is corrrect, add a point
		for (int index1 = 0; index1 < room4AnswerArray.length; index1++) {
			if (room4AnswerArray[index1].getSelectedItem().equals(questionArray[3][index1].getAnswer()))
				points++;
		}
	
//		If all answers are correct, display a pop up message to inform the user, find the escape time, and move on to next panel
		if (points == 5) {
			gameTimer.stop();
			updateEscapeTime();
			JOptionPane.showMessageDialog(null, "Correct - Good job!");
			gameroom4Panel.setVisible(false);
			gameroom4Panel.removeAll();
			escapedPanelSetup();
			add(escapedPanel);
			escapedPanel.setVisible(true);
		}
	
//		If not, display a pop up message to inform the user
		else 
			JOptionPane.showMessageDialog(null, "Oops - Recheck your work!");	
	}
	
//	ESCAPE TIME ========================================================================================================================
	private void updateEscapeTime() {
		
//		Write to file -------------------------------------------------------
		CodeWithBeamyApplication.currentUser.setEscapeTime(seconds);
		
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
		
//		Time label ----------------------------------------------------------
		if (seconds >  60) {
			minutes = Math.floor(seconds/60); // https://stackoverflow.com/questions/53860132/java-how-to-do-floor-division
			seconds %= 60;
			timeLabel.setText(String.format("Your Time: %.0f min %.0f secs", minutes, seconds));
		}
		else
			timeLabel.setText("Your Time: " + Double.toString(seconds) + " secs ");

	}
		
//  MENU LISTENER ======================================================================================================================
	@Override
	public void menuSelected(MenuEvent menu) {
		
//		If the user clicks the homeMenu, close this frame and show the main frame
		if (menu.getSource() == homeMenu) {
			setVisible(false);
			stopAudio("Audio/activity.wav");
			new MainFrame();
		}	
//		If the user clicks the conceptsMenu, close this frame and show the concepts frame
		if (menu.getSource() == conceptsMenu) {
			setVisible(false);
			stopAudio("Audio/activity.wav");
			new ConceptsFrame();
		}
//		If the user clicks the assessmentMenu, close this frame and show the assessment frame
		if (menu.getSource() == assessmentMenu) {
			setVisible(false);
			stopAudio("Audio/activity.wav");
			new AssessmentFrame();
		}
		
//		If the user clicks the logOutMenu, close this frame and show the login frame
		if (menu.getSource() == logOutMenu) {
			setVisible(false);
			stopAudio("Audio/activity.wav");
			new LoginFrame();
		}
	}
	
//	Unused
	@Override
	public void menuDeselected(MenuEvent menu) {
	}
	@Override
	public void menuCanceled(MenuEvent e) {	
	}


} // End of class
