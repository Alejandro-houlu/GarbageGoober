package sg.edu.nus.iss.GarbageGoober.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.iss.GarbageGoober.Models.Address;
import sg.edu.nus.iss.GarbageGoober.Models.User;
import sg.edu.nus.iss.GarbageGoober.Repositories.UserRepository;
import sg.edu.nus.iss.GarbageGoober.Services.LocationInterface;
import sg.edu.nus.iss.GarbageGoober.Services.UserInterface;

@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	UserInterface userSvc;

	@Autowired
	LocationInterface locationSvc;

	@RequestMapping("/login")
	public String login(Model model) { 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null|| authentication instanceof AnonymousAuthenticationToken) {
			model.addAttribute("errMessages", "");
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
	@Transactional
	public ModelAndView saveUser(@ModelAttribute User user, @RequestParam String country, @RequestParam Integer postalCode, @RequestBody MultipartFile rawImage, BindingResult result){
		ModelAndView mav = new ModelAndView("login.html");
		Optional<Address> opt = locationSvc.getAddress(country, postalCode);
		List<String> errMessages = new ArrayList<>();
		if(opt.isEmpty()){
			String message = "The address entered is invalid, Please try again";
			errMessages.add(message);
		}
		String message = userSvc.validateUserEmail(user);
		if(!message.isEmpty()){
			errMessages.add(message);
		}
		if(errMessages.size()>0){
			mav.addObject("errMessages", errMessages);
			return mav;
		}

		if(rawImage.getOriginalFilename().isEmpty()){
			user.setProfilePicUrl("/images/default-avatar.jpg");
		}
		else{
		String imgUrl = userSvc.uploadImageTos3(user, rawImage);
		user.setProfilePicUrl(imgUrl);
		}
		Address address = opt.get();
		address.setPostalCode(postalCode);
		address.setUser(user);

		System.out.println(user);
		System.out.println(address);

		if(!userSvc.saveNewUser(user) || !locationSvc.saveNewAddress(address)){
			throw new IllegalArgumentException("Operation fail");
		}
		mav.addObject("errMessages", "");

		return mav;
	}
    
}
