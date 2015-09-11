package quiz.entities;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String text;
	@ManyToMany(mappedBy = "questions")
	private List<Quiz> quizes;
	@OneToMany(mappedBy = "question")
	private List<Answer> answers;
	
	@Transient // Later we will add Submission entities
	private String givenAnswer;
	

	public String getGivenAnswer() {
		return givenAnswer;
	}

	public void setGivenAnswer(String givenAnswer) {
		this.givenAnswer = givenAnswer;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public List<Quiz> getQuizes() {
		return quizes;
	}

	public void setQuizes(List<Quiz> quizes) {
		this.quizes = quizes;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
		for (Answer answer : answers) {
			if(!answer.getQuestion().equals(this))
				answer.setQuestion(this);
		}
	}

	public void addAnswer(Answer a) {
		if (!answers.contains(a)) {
			answers.add(a);
		}
		if(!a.getQuestion().equals(this))
		a.setQuestion(this);
	}

	public Answer getCorrectAnswer() {
		for (Answer answer : answers) {
			if (answer.isCorrect()) {
				return answer;
			}
		}
		return null;
	}
}
