package quiz.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name= "quiz", schema="app")
public class Quiz {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@ManyToMany
	@JoinTable(name = "QUIZ_QUESTION", schema = "app", joinColumns = @JoinColumn(name = "QUIZ_ID") , inverseJoinColumns = @JoinColumn(name = "QUESTION_ID") )
	private List<Question> questions;
	
	@JoinTable(name = "QUIZ_SUBMISSION", joinColumns = @JoinColumn(name = "QUIZ_ID") , inverseJoinColumns = @JoinColumn(name = "USER_ID") )
	private List<Account> accounts;

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
	
	public Question getQuestion(int qid) {
		Question q = null;
		for (Question qu : questions) {
			if (qu.getId() == qid) {
				return qu;
			}
		}
		return q;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public int getNumberOfQuestions() {
		return questions.size();
	}
	
	@JsonProperty
	public ArrayList<Integer> getQuestionIds() {
		ArrayList<Integer> qids = new ArrayList<>();
		for (Question q : questions) {
			qids.add(q.getId());
		}
		return qids;
	}
	
	public boolean checkAnswer(int questionId, int answerId) {
		//return this.questions.get(questionId).getAnswers().get(answerId).isCorrect();
		for (Answer an : getQuestion(questionId).getAnswers()) {
			if (an.isCorrect()) {return true;}
		}
		return false;
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
			if (question.getCorrectAnswer().getText().equals(question.getGivenAnswer())) {
				builder.append(" --> CORRECT" + "\n");
			} else {
				builder.append(" --> INCORRECT" + "\n");
			}
			builder.append("\n");
		}
		return builder.toString();
	}

}
