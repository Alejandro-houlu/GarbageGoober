package sg.edu.nus.iss.GarbageGoober.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.iss.GarbageGoober.Models.User;
import sg.edu.nus.iss.GarbageGoober.Services.UserInterface;

@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	UserInterface userSvc;

	@RequestMapping("/login")
	public String login(Model model) { 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null|| authentication instanceof AnonymousAuthenticationToken) {
			return"login";
		}

		return "redirect:/";
		
	}

	@GetMapping("/signUp")
	public ModelAndView signUp(Model model){

		ModelAndView mav = new ModelAndView("signupForm.html");
		User user = new User();
		List<String> countries = new ArrayList<>();
		countries.add("Singapore");countries.add("Malaysia");

		mav.addObject("user", user);
		mav.addObject("countries", countries);

		mav.setStatus(HttpStatus.OK);

		return mav;
	}

	@PostMapping("/saveUser")
	public ModelAndView saveUser(@ModelAttribute User user, @RequestParam String country, @RequestBody MultipartFile rawImage){
		ModelAndView mav = new ModelAndView("login.html");
		String imgUrl = userSvc.uploadImageTos3(user, rawImage);
		user.setProfilePicUrl(imgUrl);

		System.out.println(user);
		System.out.println(country);

		userSvc.saveNewUser(user);

		return mav;
	}
    
}
