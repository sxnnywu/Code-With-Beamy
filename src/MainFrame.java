// IMPORTS =============================================================================================================================

// GUI elements
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
// Audio clip
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

// Designing GUI elements
import java.awt.*;

// React to events
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/*
Name: Sunny Wu
Due Date: June 14, 2024
Course: ICS3U1-04 - Mr. Fernandes
Description: This class creates the main menu frame of the Code With Beamy Application
Major Skills: 
* Swing GUI
* Scaling images
* Decoding Colours
* Creating + calling methods
* Implementing action listeners and menu listeners
* Array lists
* Playing audio
* Counted + conditional loops
* If else statements
*/

// Define the class
@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener, MenuListener{

//	FIELDS =============================================================================================================================
	
//	Music being played
	private static Clip audioClip;
	
//	CREATE GUI ELEMENTS ================================================================================================================

//	Title panel
	private JPanel titlePanel = new JPanel();
	
//	Menu Bar
	private JMenuBar menuBar = new JMenuBar();
	private JMenu conceptsMenu = new JMenu("Beamy's Knowledge Hub");
	private JMenu activityMenu = new JMenu("Beamy's CodeQuest");
	private JMenu assessmentMenu = new JMenu("Beamy's Test of Wisdom");
	private JMenu logOutMenu = new JMenu("Log Out");
	
//	Text for the welcome label
	static String welcomeText = ("<html>Welcome to Code With Beamy! This interactive application is designed <br/>"
								+ "to make learning Java's object-oriented programming concepts fun and engaging.<br/>"
								+ "<br/>"
								+ "Click one of the options to dive into our lessons, activities, and quizzes with Beamy! <br/> "
								+ "Let's embark on this coding adventure together and unlock the world of Java programming!");
	
//	Labels
	private JLabel titleLabel = new JLabel(String.format("Code With Beamy ~ Welcome back, %s", CodeWithBeamyApplication.currentUser.getFirstName()));
	private JLabel welcomeLabel = new JLabel(welcomeText);
	
//	Images
	private ImageIcon unscaledBeamyImage = new ImageIcon("Images/beamy.png");
	private JLabel beamyImage = new JLabel(new ImageIcon(unscaledBeamyImage.getImage().getScaledInstance(450, 450, java.awt.Image.SCALE_SMOOTH)));
	
//	Buttons
	private JButton conceptsButton = new JButton("Beamy's Knowledge Hub");
	private JButton activityButton = new JButton("Beamy's CodeQuest");
	private JButton assessmentButton = new JButton("Beamy's Test of Wisdom");
	
//	COLOURS TO BE USED =================================================================================================================
	private Color yellow = Color.decode("#FFF0AE");
	private Color turquoise = Color.decode("#45C9DD");
	private Color darkYellow = Color.decode("#FFC532");
	
//	CONSTRUCTOR METHOD ==================================================================================================================
	public MainFrame() {
		
//		Set up the frame
		setTitle("Code With Beamy");
		setSize(1920, 1080);
		setLayout(null);
		
//		Background colour
//		https://stackoverflow.com/questions/1081486/setting-background-color-for-a-jframe
		getContentPane().setBackground(yellow);
		
//		Menu bar
		setMenuBar();
		
//		Set up the title panel
		titlePanelSetup();
		
//		Welcome label
		welcomeLabel.setForeground(Color.BLACK);
		welcomeLabel.setFont(new Font("Courier New", Font.PLAIN, 18));
		welcomeLabel.setBounds(600, 250, 800, 200);
		add(welcomeLabel);
		
//		Beamy image
		beamyImage.setBounds(60, 240, 450, 450);
		add(beamyImage);
		
//		Set up the 3 buttons
		buttonSetup();
		
//		Play music
		playAudio("Audio/home.wav");
		
//		Close the program
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
//		Make the frame appear
		setVisible(true);
	}
	
//	MENU BAR ===========================================================================================================================
	private void setMenuBar() {
	
//		Background colour
		menuBar.setBackground(turquoise);
		
//		Concepts menu
		menuBar.add(conceptsMenu);
		conceptsMenu.addMenuListener(this);
		
//		Activity menu
		menuBar.add(activityMenu);
		activityMenu.addMenuListener(this);
		
//		Assessment menu
		menuBar.add(assessmentMenu);
		assessmentMenu.addMenuListener(this);
		
//		Log out menu
		menuBar.add(logOutMenu);
		logOutMenu.addMenuListener(this);
		
//		Set menu bar
		setJMenuBar(menuBar);
	}
	
//	TITLE PANEL ========================================================================================================================
 	private void titlePanelSetup() {
		
//		Set up the panel 
		titlePanel.setOpaque(true);
		titlePanel.setBackground(turquoise);
		titlePanel.setBounds(0, 0, 1920, 200);
		titlePanel.setLayout(null);
		
//		Title label
		titleLabel.setForeground(Color.BLACK);
		titleLabel.setFont(new Font("Courier New", Font.BOLD, 50));
		titleLabel.setBounds(100, 50, 1920, 100);
		titlePanel.add(titleLabel);
		
//		Add the panel to the frame
		add(titlePanel); 
	}

//	BUTTONS ============================================================================================================================
	private void buttonSetup() {
		
//		Concepts button
		conceptsButton.setBackground(darkYellow);
		conceptsButton.setForeground(Color.BLACK);
		conceptsButton.setBorder(new LineBorder(Color.BLACK)); // https://stackoverflow.com/questions/33954698/jbutton-change-default-border
		conceptsButton.setFont(new Font("Courier New", Font.BOLD, 23));
		conceptsButton.setBounds(600, 520, 800, 50);
		conceptsButton.addActionListener(this);
		add(conceptsButton);
		
//		Activity button
		activityButton.setBackground(darkYellow);
		activityButton.setForeground(Color.BLACK);
		activityButton.setBorder(new LineBorder(Color.BLACK)); // https://stackoverflow.com/questions/33954698/jbutton-change-default-border
		activityButton.setFont(new Font("Courier New", Font.BOLD, 23));
		activityButton.setBounds(600, 600, 800, 50);
		activityButton.addActionListener(this);
		add(activityButton);
		
//		Assessment button
		assessmentButton.setBackground(darkYellow);
		assessmentButton.setForeground(Color.BLACK);
		assessmentButton.setBorder(new LineBorder(Color.BLACK)); // https://stackoverflow.com/questions/33954698/jbutton-change-default-border
		assessmentButton.setFont(new Font("Courier New", Font.BOLD, 23));
		assessmentButton.setBounds(600, 680, 800, 50);
		assessmentButton.addActionListener(this);
		add(assessmentButton);
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
	
//	ACTION LISTENER ====================================================================================================================
	@Override
	public void actionPerformed(ActionEvent event) {
		
//		If the user clicks the conceptsButton, close this frame and show the concepts frame
		if (event.getSource() == conceptsButton) {
			setVisible(false);
			stopAudio("Audio/home.wav");
			new ConceptsFrame();
		}
		
//		If the user clicks the activityButton, close this frame and show the activity frame
		if (event.getSource() == activityButton) {
			setVisible(false);
			stopAudio("Audio/home.wav");
			new ActivityFrame();
		}
		
//		If the user clicks the assessmentButton, close this frame and show the assessment frame
		if (event.getSource() == assessmentButton) {
			setVisible(false);
			stopAudio("Audio/home.wav");
			new AssessmentFrame();
		}
	}

//	MENU LISTENER ======================================================================================================================
	@Override
	public void menuSelected(MenuEvent menu) {
		
//		If the user clicks the conceptsMenu, close this frame and show the concepts frame
		if (menu.getSource() == conceptsMenu) {
			setVisible(false);
			stopAudio("Audio/home.wav");
			new ConceptsFrame();
		}
		
//		If the user clicks the activityMenu, close this frame and show the activity frame
		if (menu.getSource() == activityMenu) {
			setVisible(false);
			stopAudio("Audio/home.wav");
			new ActivityFrame();
		}
		
//		If the user clicks the assessmentMenu, close this frame and show the assessment frame
		if (menu.getSource() == assessmentMenu) {
			setVisible(false);
			stopAudio("Audio/home.wav");
			new AssessmentFrame();
		}
		
//		If the user clicks the logOutMenu, close this frame and show the login frame
		if (menu.getSource() == logOutMenu) {
			setVisible(false);
			stopAudio("Audio/home.wav");
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
