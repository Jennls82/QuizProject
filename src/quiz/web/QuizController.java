package quiz.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import quiz.data.Answer;
import quiz.data.Question;
import quiz.data.Quiz;
import quiz.data.QuizDB;


@Controller
@SessionAttributes(value = { "count", "quiz", "userResponse" })
public class QuizController {
	@ModelAttribute("count")
	public int getInitialCount() {
		return 0;
	}

	@ModelAttribute("quiz")
	public Quiz getInitialQuiz() {
		return new QuizDB();
	}
	
	@ModelAttribute("userResponse")
	public ArrayList<String> getInitialResponse() {
		return new ArrayList<>();
	}
	
// trying to get back to the start of the quiz
	@RequestMapping("/quiz.do")
	public ModelAndView viewQuiz(){
				
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:index.html");
		mv.addObject("count", 0);

		return mv;
	}
	
	@RequestMapping("/DisplayName.do")
	public ModelAndView getQuizName(@ModelAttribute Quiz quiz) {
		System.out.println(quiz.getQuizName());
		return new ModelAndView("quizname", "name", quiz.getQuizName());

	}

	@RequestMapping("/DisplayNumber.do")
	public ModelAndView getNumberOfQuestions(@ModelAttribute Quiz num) {
		System.out.println(num.getNumberOfQuestions());
		return new ModelAndView("quiznumberquestions", "number", num.getNumberOfQuestions());

	}

	@RequestMapping("/DisplayQuestions.do")
	public ModelAndView getQuestions(@ModelAttribute("count") int count, @ModelAttribute("userResponse") ArrayList<String> userResponse, @ModelAttribute Quiz questions,
			String answers) {
		//System.out.println(answers);
		if (answers != null) {
			questions.getQuestions().get(count - 1)//error on this line 1x, never being run?
			.setGivenAnswer(answers);
			userResponse.add(answers);
		}

		if (count >= questions.getQuestions().size()) {
			System.out.println("going to results");
			System.out.println(questions.getResults());
			List<Object[]> summaryData = new ArrayList<Object[]>();
			List<Question> q = questions.getQuestions();
			for (Question currentQuestion : q) {
				Answer a = currentQuestion.getCorrectAnswer();
				Object[] questionData= {currentQuestion, a};
				summaryData.add(questionData);
			}
			
			return new ModelAndView("results", "results", summaryData);
		}
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("questions");
		mv.addObject("question", questions.getQuestions().get(count));
		mv.addObject("count", ++count);
		mv.addObject("userResponse", userResponse);
		return mv;
	}
		


	@RequestMapping("/DisplayResults.do")
	public ModelAndView getResults(@ModelAttribute Quiz results) {
		System.out.println(results.getResults());
		return new ModelAndView("results", "results", results.getResults());
	}

}
