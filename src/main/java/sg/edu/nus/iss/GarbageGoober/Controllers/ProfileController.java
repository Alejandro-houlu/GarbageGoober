package sg.edu.nus.iss.GarbageGoober.Controllers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.iss.GarbageGoober.Config.MyUserDetails;
import sg.edu.nus.iss.GarbageGoober.Models.RecyclingList;
import sg.edu.nus.iss.GarbageGoober.Models.User;
import sg.edu.nus.iss.GarbageGoober.Services.RecycleInterface;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    RecycleInterface recycleSvc;

    @GetMapping("/")
    public ModelAndView home(@AuthenticationPrincipal MyUserDetails userDetails){
        ModelAndView mav = new ModelAndView("dashboard.html");
        User user = userDetails.getUser();
        List<RecyclingList> lists = recycleSvc.findAllByRecycler(user);
        lists = lists.stream().limit(5).collect(Collectors.toList());
        
        mav.addObject("user", user);
        mav.addObject("lists", lists);

        

        return mav;
    }
    
}
