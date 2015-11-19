package logIn;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import quiz.entities.Account;


@RestController
@SessionAttributes(value = {"User"})
public class LoginController {
	@PersistenceContext
	private EntityManager em;
	

	 @RequestMapping("login.do")
     public ModelAndView getUser(@RequestParam("username") String un, @RequestParam("password") String p) {
    		//method to get a user from the database
    		String select =	"SELECT a FROM Account a WHERE a.username = :user AND a.password = :pass";//query statement to find a user and a password...
    		Query query = em.createQuery(select).setParameter("user", un).setParameter("pass", p);//create an entity w/ the entered params...
    		List<Account> accountList = query.getResultList();
           
    		boolean status;
			if(accountList.isEmpty()) {//if account list is empty...
                System.out.println("user not in database.");//console print out to verify user not found debugger...
                status = false;
            }
            else {
                System.out.println("successful login.");//console print out to verify user was found debugger...
                status = true;
           }
            if(status == true) {//when user found....
                Account a = accountList.get(0);//set the account to the first user returned as that was the fund in the database....
                
                //session.setAttribute("User", player);...add user to session in the future**************
                return new ModelAndView("redirect:index.jsp", "user", un);//return an instance of this user to a session and send them where they can select a quiz
            }
            else {
                return new ModelAndView("redirect:login.html");//try again to login  ************add msg for bad login!!!!!!!!!!!!!!!!!!!!!
            }
    }
}

