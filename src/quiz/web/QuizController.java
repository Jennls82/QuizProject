//package quiz.web;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.SessionAttributes;
//import org.springframework.web.servlet.ModelAndView;
//
//import quiz.entities.Answer;
//import quiz.entities.Question;
//import quiz.entities.Quiz;
//
//@Controller
//@SessionAttributes(value = { "count", "quiz"})
//public class QuizController {
//	@PersistenceContext
//	private EntityManager em;
//
//	@ModelAttribute("count")
//	public int getInitialCount() {
//		return 0;
//	}
//
//	@ModelAttribute("quiz")
//	public Quiz getInitialQuiz() {
//		return em.find(Quiz.class, 1);
//	}
//
//	@RequestMapping("/quiz.do")
//	public ModelAndView viewQuiz() {
//
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("redirect:index.jsp");
//		mv.addObject("count", 0);
//
//		return mv;
//	}
//
//	@RequestMapping("/DisplayName.do")
//	public ModelAndView getQuizName(@ModelAttribute("quiz") Quiz quiz) {
//		String name = quiz.getName();
//		System.out.println(name);
//		return new ModelAndView("quizname", "name", name);
//	}
//
////	@RequestMapping("/ResetQuiz.do")
////	public ModelAndView reset(@ModelAttribute("count") int count) {
////		return new ModelAndView("redirect:index", "count", 0);
////	}
//
//	@RequestMapping("/DisplayNumber.do")
//	public ModelAndView getNumberOfQuestions(@ModelAttribute("quiz") Quiz quiz) {
//		int num = quiz.getNumberOfQuestions();
//		return new ModelAndView("quiznumberquestions", "number", num);
//
//	}
//
//	@RequestMapping("/DisplayQuestions.do")
//	public ModelAndView getQuestions(@ModelAttribute("count") int count, @ModelAttribute("quiz") Quiz quiz,
//			String answers) {
//
//		List<Question> qs = quiz.getQuestions();
//		// If data was submitted from the user
//		if (answers != null) {
//			// get the previous question and set the user's answer to it
//			qs.get(count - 1).setGivenAnswer(answers);
//
//		}
//
//		// if you have reached the end of the quiz
//		if (count >= quiz.getQuestions().size()) {
//
//			List<String[]> summaryData = new ArrayList<String[]>();
//			for (Question currentQuestion : qs) {
//				System.out.println(currentQuestion.getGivenAnswer() + "!");
//				String rightAnswer = currentQuestion.getCorrectAnswer().getText();
//				String givenAnswer = currentQuestion.getGivenAnswer();
//				String[] questionData = { currentQuestion.getText(), rightAnswer, givenAnswer };
//				summaryData.add(questionData);
//			
//			}
//			System.out.println(summaryData);
//			return new ModelAndView("results", "results", summaryData);
//		}
//
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("questions");
//		mv.addObject("question", quiz.getQuestions().get(count));
//		mv.addObject("count", ++count);
//		return mv;
//	}
//
//}
