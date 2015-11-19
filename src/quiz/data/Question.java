package quiz.data;

import java.util.List;


public class Question {
	private int id;
	private String value;
	private List<Answer> answers;
	private String givenAnswer;

	public Question(int id, String value, List<Answer> answers) {
		this.id = id;
		this.value = value;
		this.answers = answers;
	}

	public int getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	void setValue(String value) {
		this.value = value;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public String getGivenAnswer() {
		return givenAnswer;
	}

	public void setGivenAnswer(String givenAnswer) {
		this.givenAnswer = givenAnswer;
	}

	public Answer getCorrectAnswer() {
		for (Answer answer : answers) {
			if (answer.isCorrect())
				return answer;
		}
		return null;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", value=" + value + ", answers="
				+ answers + ", givenAnswer=" + givenAnswer + "]";
	}
}
