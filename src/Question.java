/*
Name: Sunny Wu
Due Date: June 14, 2024
Course: ICS3U1-04 - Mr. Fernandes
Description: This class creates the Chapter object, which will be used in the Content-Frame
Major Skills: Object-oriented programming
*/

// Define the class
public class Question {
	
//	FIELDS
	private int level;
	private String question;
	private String answer;
	
//	CONSTRUCTOR 
	public Question(int level, String question, String answer) {
		super();
		this.level = level;
		this.question = question;
		this.answer = answer;
	}

//	GETTERS + SETTERS
	
//	Level
	public void setLevel(int level) {
		this.level = level;
	}
	public int getLevel() {
		return level;
	}

//	Question
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getQuestion() {
		return question;
	}

//	Answer
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getAnswer() {
		return answer;
	}

//	TO STRING
	@Override
	public String toString() {
		return "Question [level=" + level + ", question=" + question + ", answer=" + answer + "]";
	}

} // End of class
