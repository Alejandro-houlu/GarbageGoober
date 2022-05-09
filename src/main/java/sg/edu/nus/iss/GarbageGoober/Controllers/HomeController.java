package sg.edu.nus.iss.GarbageGoober.Controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

	@RequestMapping("/login")
	public String login(Model model) { 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null|| authentication instanceof AnonymousAuthenticationToken) {
			return"login";
		}
		return "redirect:/index";
		
	}
    
}
