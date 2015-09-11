package quiz.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import quiz.entities.Answer;
import quiz.entities.Question;
import quiz.entities.Quiz;


@Controller
@SessionAttributes(value = { "count", "quiz"/*, "userResponse"*/ })
public class QuizController {
	@PersistenceContext
	private EntityManager em;
	
	
	@ModelAttribute("count")
	public int getInitialCount() {
		return 0;
	}

	@ModelAttribute("quiz")
	public Quiz getInitialQuiz() {
		return em.find(Quiz.class, 1);
	}
	
//	@ModelAttribute("userResponse")
//	public ArrayList<String> getInitialResponse() {
//		return new ArrayList<>();
//	}
	
// trying to get back to the start of the quiz
	@RequestMapping("/quiz.do")
	public ModelAndView viewQuiz(){
				
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:index.html");
		mv.addObject("count", 0);

		return mv;
	}
	
	@RequestMapping("/DisplayName.do")
	public ModelAndView getQuizName(@ModelAttribute("quiz") Quiz quiz) {
		String name = quiz.getName();
		System.out.println(name);
		return new ModelAndView("quizname", "name", name);
	}

	@RequestMapping("/DisplayNumber.do")
	public ModelAndView getNumberOfQuestions(@ModelAttribute Quiz quiz) {
		int num = quiz.getNumberOfQuestions();
		return new ModelAndView("quiznumberquestions", "number", num);//.getNumberOfQuestions());

	}
	
	@RequestMapping("/DisplayQuestions.do")
	public ModelAndView getQuestions(@ModelAttribute("count") int count, 
			/*@ModelAttribute("userResponse") ArrayList<String> userResponse,*/ 
			@ModelAttribute Quiz quiz,
			String answers) {
		
		//System.out.println(answers);
		List <Question> qs = quiz.getQuestions();
		// If data was submitted from the user  
		if (answers != null) {
			// get the previous question and set the user's answer to it
			qs.get(count - 1).setGivenAnswer(answers);
			//userResponse.add(answers);
		}

		// if you have reached the end of the quiz
		if (count >= quiz.getQuestions().size()) {
			//System.out.println("going to results");
			//System.out.println(quiz.getResults());
			List<String[]> summaryData = new ArrayList<String[]>();
			for (Question currentQuestion : qs) {
				System.out.println(currentQuestion.getGivenAnswer() + "!");
				String rightAnswer = currentQuestion.getCorrectAnswer().getText();
				String givenAnswer = currentQuestion.getGivenAnswer();
				String[] questionData= {currentQuestion.getText(), rightAnswer, givenAnswer};
				summaryData.add(questionData);
			}
			
			return new ModelAndView("results", "results", summaryData);
		}
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("questions");
		mv.addObject("question", quiz.getQuestions().get(count));
		mv.addObject("count", ++count);
		//mv.addObject("userResponse", userResponse);
		return mv;
	}
		


	@RequestMapping("/DisplayResults.do")
	public ModelAndView getResults(@ModelAttribute quiz.data.Quiz results) {
		//Object results = em.find(Quiz.class, 1).getResults();
		System.out.println(results.getResults());
		return new ModelAndView("results", "results", results.getResults());
		//return new ModelAndView("results", "results", results);
	}

}
