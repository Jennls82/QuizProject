package quiz.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


@Entity
public class Quiz {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@ManyToMany
    @JoinTable(name="QUIZ_QUESTION", 
               joinColumns=@JoinColumn(name="QUIZ_ID"),
               inverseJoinColumns = @JoinColumn(name="QUESTION_ID"))
	private List <Question> questions;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

		public int getNumberOfQuestions() {
		return questions.size();
	}

		public String getResults() {
		StringBuilder builder = new StringBuilder(1024);
		for (Question question : questions) {
			builder.append("Question: " + question.getText() + "\n");
			List<Answer> answers = question.getAnswers();
			for (Answer answer : answers) {
				if (answer.isCorrect()) {
					builder.append("  *");
				} else {
					builder.append("   ");
				}
				builder.append("Answer: " + answer.getText() + "\n");
			}
			builder.append("User Answer: " + question.getGivenAnswer());
			if (question.getCorrectAnswer().getText()
					.equals(question.getGivenAnswer())) {
				builder.append(" --> CORRECT" + "\n");
			} else {
				builder.append(" --> INCORRECT" + "\n");
			}
			builder.append("\n");
		}
		return builder.toString();
	}
	

}
