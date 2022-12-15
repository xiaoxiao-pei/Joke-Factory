package com.PPZ.JokeBar.Controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.PPZ.JokeBar.dto.JokeRepository;
import com.PPZ.JokeBar.dto.UserRepository;
import com.PPZ.JokeBar.model.Joke;
import com.PPZ.JokeBar.model.User;
import com.PPZ.JokeBar.service.joke.Joke_Service;
import com.PPZ.JokeBar.service.user.User_service;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JokeRepository jokeRepo;
	
	@Autowired
	private User_service userService;
	
	@Autowired
	private Joke_Service jokeService;
	
//user register	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "user/user_signup";
	}
	
	
	@PostMapping("/process_register")
	public String processRegister(@Valid User user, BindingResult bindingResult, Model model) {
		
		// check if user(email) already exist
		if (userRepo.findByEmail(user.getEmail()) != null) {
			bindingResult.addError(new FieldError("user", "email", "Email already in use"));
		}

		// check if retype password matches
		if (!user.getRePassword().equals(user.getPasswordUnHashed())) {
            bindingResult.addError(new FieldError("user", "rePassword", "Password doesn't match"));
        }

        if (bindingResult.hasErrors()) {
            return "user/user_signup";
        }

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPasswordUnHashed());
		user.setPassword(encodedPassword);
	    
		//set current date as join date
		user.setJoinDate(new Date());

		userRepo.save(user);

		model.addAttribute("user", user);
		return "user/user_signin";
	}

//user login
	@GetMapping("/user/login")
	public String showUserLogin() {
		return "user/user_login";
	}
	
	@GetMapping("/user/home")
	public String userDashboard(Model model) {
		//get current user 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();	
		User user = userRepo.findByEmail(currentPrincipalName);
		
		model.addAttribute("fullname",user.getName());
		model.addAttribute("currentUser", user);
		
		List<Joke> listJokes = jokeRepo.findAll();
		model.addAttribute("listJokes", listJokes);
		return "user/user_dashboard";
	}
	
//crud current user
	@RequestMapping("/user/editAccount")
	public ModelAndView showEditAdminPage() {
		ModelAndView mav = new ModelAndView("user/user_editMyInfo");
		
		//get current user 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();	

		User user = userRepo.findByEmail(currentPrincipalName);
		mav.addObject("user", user);
		return mav;
	}
	
	@RequestMapping(value = "/user/saveUser", method = RequestMethod.POST)
	public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
            return "user/user_editMyInfo";
        }
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPasswordUnHashed());
        user.setPassword(encodedPassword);
        
      //copy not null fields from user to originalUser, then save originalUser
        User originalUser = userRepo.findById(user.getId()).get();
        
        SharedMethod.copyNotNullFields(user, originalUser);
        
		userService.save(originalUser);

		return "redirect:/user/home";
	}

	
//crud his jokes
	@RequestMapping("/user/listJoke")
	public String viewJoke(Model model, Principal principal) {
		User currentUser = userRepo.findByEmail(principal.getName());
		
		List<Joke> listJokes = jokeService.listJokesByUser(currentUser);
		model.addAttribute("listJokes", listJokes);
		return "user/user_listJoke";
	}

	@RequestMapping("/user/addjoke")
	public String showNewJokePage(Model model) {
		Joke joke = new Joke();
		model.addAttribute("joke", joke);
		return "user/user_addjoke";
	}
	
	@RequestMapping("/duplicate")
	public String showDuplicatePage(Model model) {
		Joke joke = new Joke();
		model.addAttribute("joke", joke);
		return "duplicate";
	}
	
	@RequestMapping(value = "/user/savejoke", method = RequestMethod.POST)
	public String saveJoke(@Valid @ModelAttribute("joke") Joke joke, BindingResult bindingResult, Principal principal) {
		
		if (bindingResult.hasErrors()) {
            return "/user/user_addjoke";
        }
		
		User currentUser = userRepo.findByEmail(principal.getName());
		

		String s = joke.getJokecontent().toLowerCase();
		
		StringBuilder sb = new StringBuilder();
		
        for (int i = 0; i < s.length(); i++) {
            if(Character.isLetter(s.charAt(i)))
                sb.append(s.charAt(i));     
        }
		
        StringBuilder sb2 = new StringBuilder();
        
        sb2.append(sb.charAt(2));
        sb2.append(sb.charAt(5));
        sb2.append(sb.charAt(10));
        sb2.append(sb.charAt(16));
        sb2.append(sb.charAt(sb.length() - 16));
        sb2.append(sb.charAt(sb.length() - 10));
        sb2.append(sb.charAt(sb.length() - 5));
        sb2.append(sb.charAt(sb.length() - 1));  
        
        String s2 = sb2.toString();

       if (jokeRepo.findByFingerprint(s2) != null)
       {
    	   return"user/user_duplicate";
       }
        
       joke.setFingerprint(sb2.toString());
       joke.setUser(currentUser);
       jokeService.add(joke);
       
     //add 5 to user's credit
       currentUser.setCredit(currentUser.getCredit()+5);
       userRepo.save(currentUser);

	return "redirect:/user/listJoke";
	}
	
	@RequestMapping(value = "/user/updatejoke", method = RequestMethod.POST)
	public String updateJoke(@ModelAttribute("joke") Joke joke, Principal principal) {
		
		User currentUser = userRepo.findByEmail(principal.getName());
		

		String s = joke.getJokecontent().toLowerCase();
		
		StringBuilder sb = new StringBuilder();
		
        for (int i = 0; i < s.length(); i++) {
            if(Character.isLetter(s.charAt(i)))
                sb.append(s.charAt(i));     
        }
		
        StringBuilder sb2 = new StringBuilder();
        
        sb2.append(sb.charAt(2));
        sb2.append(sb.charAt(5));
        sb2.append(sb.charAt(10));
        sb2.append(sb.charAt(16));
        sb2.append(sb.charAt(sb.length() - 16));
        sb2.append(sb.charAt(sb.length() - 10));
        sb2.append(sb.charAt(sb.length() - 5));
        sb2.append(sb.charAt(sb.length() - 1));  
        
        String s2 = sb2.toString();

       if ((jokeRepo.findByFingerprint(s2) != null)&&(jokeRepo.findByFingerprint(s2).getJokeid() != joke.getJokeid()))
       {
    	   return"user/user_duplicate";
       }
        
       joke.setFingerprint(sb2.toString());
       joke.setUser(currentUser);
       jokeService.add(joke);

	return "redirect:/user/listJoke";
	}

	
	@RequestMapping("/user/editjoke/{jokeid}")
	public ModelAndView showEditJokePage(@PathVariable(name = "jokeid") int id) {
		ModelAndView jokemav = new ModelAndView("user/user_editjoke");
		Joke joke = jokeService.get(id);
		jokemav.addObject("joke", joke);
		return jokemav;
	}

	@RequestMapping("/user/deletejoke/{jokeid}")
	public String deleteJoke(@PathVariable(name = "jokeid") int id) {
		jokeService.delete(id);
		return "redirect:/user/listJoke";
	}

//give a like
	@RequestMapping(value = "/user/giveALike/{jokeid}")
	public String giveALike(Principal principal, @PathVariable(name = "jokeid") int jokeid) {
		
		//minus 5 from current user's credit
		User currentUser = userRepo.findByEmail(principal.getName());	
		currentUser.setCredit(currentUser.getCredit()-5);
		userRepo.save(currentUser);
		
		//add 5 to joke's score
		Joke joke = jokeRepo.findById(jokeid).get();
		joke.setScores(joke.getScores()+5);
		jokeRepo.save(joke);
		
		//add 5 to author's credit
		User author = joke.getUser();
		author.setCredit(author.getCredit()+5);
		userRepo.save(author);
		
		return "redirect:/user/home";
	}
		
}
