package sg.edu.nus.iss.GarbageGoober.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.iss.GarbageGoober.Config.MyUserDetails;
import sg.edu.nus.iss.GarbageGoober.Models.User;

@Controller
@RequestMapping("/recycle")
public class RecycleController {

    @GetMapping("/")
    public ModelAndView home(@AuthenticationPrincipal MyUserDetails userDetails){
        ModelAndView mav = new ModelAndView("recycleHome.html");

        User user = userDetails.getUser();
        mav.addObject("user", user);
        mav.setStatus(HttpStatus.OK);

        return mav;
    }

    @GetMapping("/createList")
    public ModelAndView createList(@AuthenticationPrincipal MyUserDetails userDetails){
        ModelAndView mav = new ModelAndView("recycleForm.html");
        User user = userDetails.getUser();
        mav.addObject("user", user);

        return mav;
    }
    
}
