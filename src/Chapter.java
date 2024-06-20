// IMPORTS 
import javax.swing.ImageIcon; // Allows us to create the ImageIcon field

/*
Name: Sunny Wu
Due Date: June 14, 2024
Course: ICS3U1-04 - Mr. Fernandes
Description: This class creates the Chapter object, which will be used in the Content-Frame
Major Skills: Object-oriented programming
*/

// Define the class
public class Chapter {
	
//	FIELDS 

	private String title;
	private String content;
	private ImageIcon sampleCode;
	private String link;
	
//	EMPTY CONSTRUCTOR
	public Chapter() {
	}
	
//	CONSTRUCTOR
	public Chapter(String title, String content, ImageIcon sampleCode, String link) {
		super();
		this.title = title;
		this.content = content;
		this.sampleCode = sampleCode;
		this.link = link;
	}

//	GETTERS + SETTERS
	
//	Title
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}

//	Content
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}

//	Sample Code
	public void setSampleCode(ImageIcon sampleCode) {
		this.sampleCode = sampleCode;
	}
	public ImageIcon getSampleCode() {
		return sampleCode;
	}
	
//	Link
	public void setLink(String link) {
		this.link = link;
	}
	public String getLink() {
		return link;
	}

//	TO STRING
	@Override
	public String toString() {
		return "Chapter [title=" + title + ", content=" + content + ", sampleCode=" + sampleCode + ", link=" + link
				+ "]";
	}

} // End of class
