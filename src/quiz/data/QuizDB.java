package quiz.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.persistence.Persistence;
import javax.sql.DataSource;

public class QuizDB implements Quiz {
	private List<Question> questions;
	private Connection conn;
	private String quizName = "";

	public QuizDB() {
		this(1); // Load Quiz ID 1 by default.
		// this("Horror Film Quiz"); // Load Quiz by name

	}

	public QuizDB(int quizId) {
		String URL = "jdbc:derby://localhost:1527/quiz";

		// String URL = "jdbc:sqlite:" + System.getProperty("user.home") +
		// "/SD/Databases/quiz.db";

		try {

			InitialContext ctxt = new InitialContext();
			DataSource ds = (DataSource) ctxt.lookup("java:comp/env/jdbc/quiz");
			conn = ds.getConnection();
			String questionSQL = "SELECT name FROM quiz WHERE id = ?";

			PreparedStatement pstmt = conn.prepareStatement(questionSQL);
			pstmt.setInt(1, quizId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				quizName = rs.getString(1);
			}
			rs.close();
			pstmt.close();

			loadQuestionsByQuizId(conn, quizId);
		} catch (Exception e) {
			System.err.println(e);
			throw new RuntimeException(e);
		}
	}

	public QuizDB(String quizName) {
		String URL = "jdbc:derby://localhost:1527/quiz";

		// String URL = "jdbc:sqlite:" + System.getProperty("user.home") +
		// "/SD/Databases/quiz.db";

		try {

			InitialContext ctxt = new InitialContext();
			DataSource ds = (DataSource) ctxt.lookup("java:comp/env/jdbc/quiz");
			conn = ds.getConnection();
			this.quizName = quizName;

			loadQuestionsByQuizName(conn, quizName);
		} catch (Exception e) {
			System.err.println(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getQuizName() {
		return quizName;
	}

	private void loadQuestionsByQuizName(Connection conn, String quizName) throws SQLException {

		// emf=Persistence.createEntityManagerFactory("Quiz");
		// em = emf.createEntityManager();
		// QuizFacade qf= new
		// QuizFacade(em);//////////////////////////////////////////////////////////////

		String questionSQL = "SELECT qu.id, text " + "FROM quiz q, question qu, quiz_question qq "
				+ "WHERE q.id = qq.quiz_id " + "AND qu.id = qq.question_id " + "AND q.name = ?";

		PreparedStatement pstmt = conn.prepareStatement(questionSQL);
		pstmt.setString(1, quizName);
		ResultSet rs = pstmt.executeQuery();

		questions = new ArrayList<>();
		while (rs.next()) {
			int questionId = rs.getInt(1);
			String questionText = rs.getString(2);

			List<Answer> answers = new ArrayList<>();
			String answerSQL = "SELECT text, iscorrect " + "FROM answer " + "WHERE question_id = ?";
			PreparedStatement pstmt2 = conn.prepareStatement(answerSQL);
			pstmt2.setInt(1, questionId);
			ResultSet rs2 = pstmt2.executeQuery();
			while (rs2.next()) {
				String answerText = rs2.getString(1);
				boolean isCorrect = rs2.getBoolean(2);
				answers.add(new Answer(answerText, isCorrect));
			}
			rs2.close();
			pstmt2.close();

			Question question = new Question(questionId, questionText, answers);
			questions.add(question);
		}
		rs.close();
		pstmt.close();
	}

	private void loadQuestionsByQuizId(Connection conn, int quizId) throws SQLException {

		String questionSQL = "SELECT qu.id, text " + "FROM quiz q, question qu, quiz_question qq "
				+ "WHERE q.id = qq.quiz_id " + "AND qu.id = qq.question_id " + "AND q.id = ?";

		PreparedStatement pstmt = conn.prepareStatement(questionSQL);
		pstmt.setInt(1, quizId);
		ResultSet rs = pstmt.executeQuery();

		questions = new ArrayList<>();
		while (rs.next()) {
			int questionId = rs.getInt(1);
			String questionText = rs.getString(2);

			List<Answer> answers = new ArrayList<>();
			String answerSQL = "SELECT text, iscorrect " + "FROM answer " + "WHERE question_id = ?";
			PreparedStatement pstmt2 = conn.prepareStatement(answerSQL);
			pstmt2.setInt(1, questionId);
			ResultSet rs2 = pstmt2.executeQuery();
			while (rs2.next()) {
				String answerText = rs2.getString(1);
				boolean isCorrect = rs2.getBoolean(2);
				answers.add(new Answer(answerText, isCorrect));
			}
			rs2.close();
			pstmt2.close();

			Question question = new Question(questionId, questionText, answers);
			questions.add(question);
		}
		rs.close();
		pstmt.close();
	}

	public int getNumberOfQuestions() {
		return questions.size();
	}

	public List<Question> getQuestions() {
		return questions;
	}

	void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	@Override
	public boolean checkAnswer(int questionId, int answerId) {
		return this.questions.get(questionId).getAnswers().get(answerId).isCorrect();
	}

	public String getResults() {
		StringBuilder builder = new StringBuilder(1024);
		for (Question question : questions) {
			builder.append("Question: " + question.getValue() + "\n");
			List<Answer> answers = question.getAnswers();
			for (Answer answer : answers) {
				if (answer.isCorrect()) {
					builder.append("  *");
				} else {
					builder.append("   ");
				}
				builder.append("Answer: " + answer.getValue() + "\n");
			}
			builder.append("User Answer: " + question.getGivenAnswer());
			if (question.getCorrectAnswer().getValue().equals(question.getGivenAnswer())) {
				builder.append(" --> CORRECT" + "\n");
			} else {
				builder.append(" --> INCORRECT" + "\n");
			}
			builder.append("\n");
		}
		return builder.toString();
	}
}
