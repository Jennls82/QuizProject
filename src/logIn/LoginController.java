package logIn;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam("player") String player, @RequestParam("password") String p) {
        ModelAndView mv = new ModelAndView("profile");
        User u = new User(player, p);
        UserManager um = new UserManager();
        
        if(um.getUser(u) != null) {
            mv.addObject("userDto", u);
            return mv;
        }
        else {
            return new ModelAndView("badlogin");
        }
    }
}
