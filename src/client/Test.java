package client;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import quiz.entities.Answer;
import quiz.entities.Question;
import quiz.entities.Quiz;

public class Test {
	private static EntityManagerFactory emf = null;
	private static EntityManager em = null;

	public static void main(String[] args) throws Exception {
		emf = Persistence.createEntityManagerFactory("QuizPersistenceUnit");
		em = emf.createEntityManager();

		Quiz q = em.find(Quiz.class, 1);
		System.out.println(q.getName());

		Collection<Question> qs = q.getQuestions();
		for (Question question : qs) {
			System.out.println(question.getText());


			Collection<Answer> answers = question.getAnswers();
			for (Answer an : answers) {
				System.out.println(an.getText() + " " + an.isCorrect());
				

			}

		}
	}
}
