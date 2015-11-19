package quiz.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "question", schema="app")
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String text;
	@ManyToMany(mappedBy = "questions")
	@JsonIgnore
	private List<Quiz> quizes;
	@OneToMany(mappedBy = "question")
	private List<Answer> answers;
	
	@Transient // Later we will add Submission entities
	private Answer givenAnswer;
	

	public Answer getGivenAnswer() {
		return givenAnswer;
	}
	
	public Answer getAnswer(int aid) {
		Answer a = null;
		for (Answer an : answers) {
			if (an.getId() == aid) {
				return an;
			}
		}
		return a;
	}

	public Answer getAnswer(String text) {
		Answer a = null;
		for (Answer an : answers) {
			if (an.getText().equals(text)) {
				return an;
			}
		}
		return a;
	}

	public void setGivenAnswer(String givenAnswer) {
		this.givenAnswer = getAnswer(givenAnswer);
	}

	public void setGivenAnswer(int answerId) {
		this.givenAnswer = getAnswer(answerId);//////double check
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
