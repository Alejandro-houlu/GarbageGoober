package sg.edu.nus.iss.GarbageGoober.Controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.iss.GarbageGoober.Config.MyUserDetails;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping("/")
    public ModelAndView home(@AuthenticationPrincipal MyUserDetails userDetails){
        ModelAndView mav = new ModelAndView("dashboard.html");

        return mav;
    }
    
}
