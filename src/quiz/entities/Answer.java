package quiz.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String text;
	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;
	private String isCorrect;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
		if (!question.getAnswers().contains(this)) {
			question.addAnswer(this);
		}
	}

	public int getId() {
		return id;
	}

	public void setIsCorrect(boolean b) {
		if (b == true) {
			isCorrect = "Y";
		} else {
			isCorrect = "N";
		}
	}

	public boolean isCorrect() {
		if (isCorrect.equals("Y") || isCorrect.equals("y")) {
			return true;
		}
		return false;
	}

}
