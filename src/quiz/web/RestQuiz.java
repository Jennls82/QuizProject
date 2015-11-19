package quiz.web;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import quiz.entities.Question;
import quiz.entities.Quiz;
import quiz.entities.Account;

@RestController
@SessionAttributes(value = { "count", "quiz" })//add these model attributes as to the session..
public class RestQuiz {
	@PersistenceContext
	private EntityManager em;//as EMF for an entity.....

	@ModelAttribute("count")//declare model attribute.....
	public int getInitialCount() {//method to get the first count....
		return 0;
	}

	@ModelAttribute("quiz")//declare model attribute 
	public Quiz getInitialQuiz() {//method to get a fresh quiz...
		return null;//returns null so attributes can be reset....
	}
	
	@RequestMapping("/ResetQuiz.do")
	public ModelAndView reset(@ModelAttribute("count") int count, HttpSession session) {
			return new ModelAndView("redirect:index.jsp", "count", 0);// clear existing quiz object from the session, create new quiz, send back to index.....
	}


	@RequestMapping("/quiz/{quizId:[0-9]+}")//map quiz by number as quiz ids are numerical.....
	@ResponseBody
	public Quiz getQuiz(@PathVariable("quizId") int quizId, Model model) {
		Quiz q = em.find(Quiz.class, quizId);//get a quiz object, and its id...
		model.addAttribute("quiz", q);//set attribute quiz...
		model.addAttribute("count", 0);//set attribute count....
		System.out.println(quizId);//console print out id debugger.....
		return q;//quiz object 
	}

	@RequestMapping("/quiz/{quizId:[0-9]+}/question/{qid:[0-9]+}")//map to quiz question ids by number as quiz question ids are numerical.....
	@ResponseBody
	public Question getQuizQuestion(@PathVariable("quizId") int quizId, @PathVariable("qid") int qid,
			@ModelAttribute("quiz") Quiz quiz) {
		System.out.println(quizId + ", " + qid);//console print out to check ? ids.....
		return quiz.getQuestion(qid);//returns the ?s specific to a quiz id....
	}

	@RequestMapping("/quiz/{quizId:[0-9]+}/questionIds")//map to quiz question ids by number as quiz question ids are numerical.....
	@ResponseBody
	public ArrayList<Integer> getQuizQuestionIds(@PathVariable("quizId") int quizId,
			@ModelAttribute("quiz") Quiz quiz) {//uisn the same model attributes....
		ArrayList<Integer> qIdList = quiz.getQuestionIds();
		return qIdList;//create an array list if the questions....
	}

	@RequestMapping("/quiz/{quizId:[0-9]+}/results")//map to quiz id to a new url...
	@ResponseBody
	public List<Question> getQuizResults(@PathVariable("quizId") int quizId,
			@ModelAttribute("quiz") Quiz quiz) {//getting the same model attributes
		return quiz.getQuestions(); // send the ?s to results...
	}
	
	@RequestMapping("/quiz/quizname")//simple method to get the quiz name 
	@ResponseBody
	public String getQuizName(@ModelAttribute("quiz") Quiz quiz) {
		return quiz.getName();
	}

	// GET to query true/false
	@RequestMapping(value = "/quiz/{quizId:[0-9]+}/question/{qid:[0-9]+}/answer/{answerId:[0-9]+}",//mapped to answer url with a get method to retrieve data...
			        method = RequestMethod.GET)
	@ResponseBody
	public boolean checkAnswer(@PathVariable("quizId") int quizId, @PathVariable("qid") int qid,//finds the question id and check for the correct answer
			@PathVariable("answerId") int answerId, @ModelAttribute("quiz") Quiz quiz) {
		return quiz.checkAnswer(qid, answerId);//returns the correct answer...
	}


	@RequestMapping(value = "/quiz/{quizId:[0-9]+}/question/{qid:[0-9]+}/answer/{answerId:[0-9]+}",// PUT to actually submit answer
			        method = RequestMethod.PUT)
	@ResponseBody
	public void submitAnswer(@PathVariable("quizId") int quizId, @PathVariable("qid") int qid,
			@PathVariable("answerId") int answerId, @ModelAttribute("quiz") Quiz quiz) {
		String givenAnswer = "";
		givenAnswer = quiz.getQuestion(qid).getAnswer(answerId).getText();//set given answer...
		System.out.println("In submitAnswer, givenAnswer: " + givenAnswer);//console print out to check given answer debugger...
		quiz.getQuestion(qid).setGivenAnswer(givenAnswer);// UPDATE quiz OBJECT TO REFLECT ANSWER
//		if (qid >= quizId.getQuestions().size()) {
//			//return null;
//			System.out.println(quizId); *********************try to have server side dish out the ?s instead client side........
//			 
//		}
//		else{
//			//return quizId; 
//		}
	}
}
