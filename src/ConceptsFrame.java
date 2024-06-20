// IMPORTS =============================================================================================================================

// GUI elements
import javax.swing.*;
import javax.swing.border.LineBorder;

// Designing GUI elements
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;

// Playing audio
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

// Reading the file
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

// React to events
import java.awt.event.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/*
Name: Sunny Wu
Due Date: June 14, 2024
Course: ICS3U1-04 - Mr. Fernandes
Description: This class creates the concepts frame of the Code With Beamy Application
Major Skills: 
* Swing GUI
* Scaling images
* Decoding Colours
* Creating + calling methods
* Implementing action listeners and menu listeners
* Running totals
* Writing to a file
* Array lists
* Playing audio
* Counted + conditional loops
* If else statements
*/

// Define the class
@SuppressWarnings("serial")
public class ConceptsFrame extends JFrame implements ActionListener, MenuListener {
	
//	FIELDS =============================================================================================================================
	
//	Content
	static String content;
	
//	Array of chapters
	static Chapter chapterArray[] = new Chapter[8];
	
//	Music being played
	private static Clip audioClip;
	
//	GUI ELEMENTS =======================================================================================================================
	
//	Menu bar
	private JMenuBar menuBar = new JMenuBar();
	private JMenu homeMenu = new JMenu("Home");
	private JMenu activityMenu = new JMenu("Beamy's CodeQuest");
	private JMenu assessmentMenu = new JMenu("Beamy's Test of Wisdom");
	private JMenu logOutMenu = new JMenu("Log Out");
	
//	Labels
	private JLabel titleLabel = new JLabel();
	private JLabel contentLabel = new JLabel();
	
//	Images
	private ImageIcon unscaledBeamyImage = new ImageIcon("Images/beamy.png");
	private JLabel beamyImage = new JLabel(new ImageIcon(unscaledBeamyImage.getImage().getScaledInstance
											(100, 100, java.awt.Image.SCALE_SMOOTH)));
	private JLabel sampleCodeImage = new JLabel(new ImageIcon());
	
//	Buttons
	private JButton backButton = new JButton("BACK");
	private JButton nextButton = new JButton("NEXT");
	private JButton moreButton = new JButton("LEARN MORE");
	
//	COLOURS TO BE USED =================================================================================================================
	private Color yellow = Color.decode("#FFF0AE");
	private Color turquoise = Color.decode("#45C9DD");
	private Color darkYellow = Color.decode("#FFC532");
	
//	CONSTRUCTOR ========================================================================================================================	
	public ConceptsFrame() {
		
//		Set up the frame
		setTitle("Beamy's Knowledge Hub");
		setSize(1920, 1080);
		setLayout(null);
		
//		Background colour
//		https://stackoverflow.com/questions/1081486/setting-background-color-for-a-jframe
		getContentPane().setBackground(yellow);
		
//		Set up the menu bar
		setMenuBar();
		
//		Beamy image
		beamyImage.setBounds(50, 50, 100, 100);
		add(beamyImage);
		
//		Set up the chapter array
		fileInput(); 
		
//		Set up the content
		contentSetup(CodeWithBeamyApplication.currentUser.getChapter());
		
//		Add action listeners to buttons only once
		backButton.addActionListener(this);
		moreButton.addActionListener(this);
		nextButton.addActionListener(this);
		
//		Close the program
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
//		Play music
		playAudio("Audio/concept.wav");
		
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
	
//	FILE INPUT =========================================================================================================================
	private void fileInput() {
		
		try {
			
			Scanner input = new Scanner(new File("Data/content.txt"));
			
//			Seperate values by line
			input.useDelimiter("## ");
			
//			Repeat for each laptop
			for (int index = 0; index < chapterArray.length; index++) {
			
//				Set the title and content
				chapterArray[index] = new Chapter();
				chapterArray[index].setTitle(input.next());
				chapterArray[index].setContent(input.next());
				ImageIcon unscaledSampleCode = new ImageIcon("Images/Chapters/chapter"+(index+1)+".png");
				chapterArray[index].setSampleCode(new ImageIcon(unscaledSampleCode.getImage().getScaledInstance(600, 400, java.awt.Image.SCALE_SMOOTH)));
				chapterArray[index].setLink(input.next());
				
//				If the last character of the link is a line break, remove it
//				https://stackoverflow.com/questions/7438612/how-to-remove-the-last-character-from-a-string
//				https://stackoverflow.com/questions/5163785/how-do-i-get-the-last-character-of-a-string
				if (chapterArray[index].getLink().charAt(chapterArray[index].getLink().length() - 1) == 10) // 10 is the acsii value of a line break 
					chapterArray[index].setLink(chapterArray[index].getLink().substring(0, chapterArray[index].getLink().length() - 2));
			}
			
//			Close the scanner
			input.close();
			
		} catch (FileNotFoundException error) {
			
//			If the file isn't found display the error message
			System.out.println("File Error: " + error); 
		}
	}
	
//	CONTENT SET UP =====================================================================================================================
	private void contentSetup(int chapter) {
		
//		Clear the frame
//		https://stackoverflow.com/questions/7117332/dynamically-remove-component-from-jpanel
		getContentPane().removeAll();
		
//		Title label
		titleLabel.setText(chapterArray[chapter-1].getTitle());
		titleLabel.setFont(new Font("Courier New", Font.BOLD, 50));
		titleLabel.setBounds(300, 50, 1920, 100);
		add(titleLabel);
		
//		Content label
		content = chapterArray[chapter-1].getContent();
		content = "<html>" + content.replaceAll("\n", "<br>") + "</html>"; // https://docs.oracle.com/javase/tutorial/uiswing/components/html.html
		contentLabel.setText(content);
		contentLabel.setFont(new Font("Courier New", Font.PLAIN, 16));
		contentLabel.setBounds(100, 200, 600, 400);
		add(contentLabel);
		
//		Sample 
		sampleCodeImage.setIcon(chapterArray[chapter-1].getSampleCode());
		sampleCodeImage.setBounds(800, 200, 600, 400);
		add(sampleCodeImage);
		
//		Set up the buttons
		buttonSetup();
	}
	
//	BUTTON SET UP ======================================================================================================================
	private void buttonSetup() {
		
//		Back button
		backButton.setBackground(darkYellow);
		backButton.setBorder(new LineBorder(Color.BLACK)); // https://stackoverflow.com/questions/33954698/jbutton-change-default-border
		backButton.setFont(new Font("Courier New", Font.BOLD, 25));
		backButton.setBounds(50, 670, 150, 50);
		add(backButton);
		
//		More button
		moreButton.setBackground(Color.BLACK);
		moreButton.setForeground(Color.WHITE);
		moreButton.setBorder(new LineBorder(Color.WHITE)); // https://stackoverflow.com/questions/33954698/jbutton-change-default-border
		moreButton.setFont(new Font("Courier New", Font.BOLD, 25));
		moreButton.setBounds(650, 670, 200, 60);
		add(moreButton);
		
//		Next button
		nextButton.setBackground(darkYellow);
		nextButton.setBorder(new LineBorder(Color.BLACK)); // https://stackoverflow.com/questions/33954698/jbutton-change-default-border
		nextButton.setFont(new Font("Courier New", Font.BOLD, 25));
		nextButton.setBounds(1330, 670, 150, 50);
		add(nextButton);
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
		
//		https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java		
		audioClip.stop();
	    audioClip.close();
	}
	
//	ACTION LISTENER =====================================================================================================================
	@Override
	public void actionPerformed(ActionEvent event) {
		
//		If the user clicks on the back button
		if (event.getSource() == backButton) {

//			If the current chapter is 1, hide this frame and display the MainFrame
			if (CodeWithBeamyApplication.currentUser.getChapter() == 1) {
				setVisible(false);
				stopAudio("Audio/concept.wav");
				new MainFrame();
			}
			
//			Otherwise, decrease the chapter # and re-add the contents
			else {
				CodeWithBeamyApplication.currentUser.setChapter(CodeWithBeamyApplication.currentUser.getChapter() - 1);
				setVisible(false);
				contentSetup(CodeWithBeamyApplication.currentUser.getChapter());
				setVisible(true);
			}
		}
		
//		If the user clicks the buy Button, open the web browser through the hyperlink of the chapter
		if (event.getSource() == moreButton) {		
			
			if(Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().browse(new URI(chapterArray[CodeWithBeamyApplication.currentUser.getChapter()-1].getLink()));
				} catch (IOException error) {
					error.printStackTrace();
			    } catch (URISyntaxException error) {
			    	error.printStackTrace();
			    }
			}
		}
		
//		If the user clicks on the next button
		if (event.getSource() == nextButton) {

//			If the current chapter is 8, hide this frame and display the MainFrame
			if (CodeWithBeamyApplication.currentUser.getChapter() == 8) {
				setVisible(false);
				stopAudio("Audio/concept.wav");
				new MainFrame();
			}
			
//			Otherwise, decrease the chapter # and re-add the contents
			else {
				CodeWithBeamyApplication.currentUser.setChapter(CodeWithBeamyApplication.currentUser.getChapter() + 1);
				setVisible(false);
				contentSetup(CodeWithBeamyApplication.currentUser.getChapter());
				setVisible(true);
			}
		}
	}
	
//	MENU LISTENER ======================================================================================================================
	@Override
	public void menuSelected(MenuEvent menu) {
		
//		If the user clicks the homeMenu, close this frame and show the main frame
		if (menu.getSource() == homeMenu) {
			setVisible(false);
			stopAudio("Audio/concept.wav");
			new MainFrame();
		}
		
//		If the user clicks the activityMenu, close this frame and show the activity frame
		if (menu.getSource() == activityMenu) {
			setVisible(false);
			stopAudio("Audio/concept.wav");
			new ActivityFrame();
		}
		
//		If the user clicks the assessmentMenu, close this frame and show the assessment frame
		if (menu.getSource() == assessmentMenu) {
			setVisible(false);
			stopAudio("Audio/concept.wav");
			new AssessmentFrame();
		}
		
//		If the user clicks the logOutMenu, close this frame and show the login frame
		if (menu.getSource() == logOutMenu) {
			setVisible(false);
			stopAudio("Audio/concept.wav");
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
