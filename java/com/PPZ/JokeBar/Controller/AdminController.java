package com.PPZ.JokeBar.Controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.PPZ.JokeBar.dto.AdminRepository;
import com.PPZ.JokeBar.dto.JokeRepository;
import com.PPZ.JokeBar.dto.UserRepository;
import com.PPZ.JokeBar.model.Admin;
import com.PPZ.JokeBar.model.Joke;
import com.PPZ.JokeBar.model.UpdateGroup;
import com.PPZ.JokeBar.model.User;
import com.PPZ.JokeBar.service.admin.Admin_service;
import com.PPZ.JokeBar.service.joke.Joke_Service;
import com.PPZ.JokeBar.service.user.User_service;



@Controller
public class AdminController {
	private static final List<Joke> PageRequest = null;

	@Autowired
	private Admin_service adminService;
	
	@Autowired
	private User_service userService;
	
	@Autowired
	private Joke_Service jokeService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AdminRepository adminRepo;
	
	@Autowired
	private JokeRepository jokeRepo;
	
	
	@GetMapping("")
	public String viewHomePage(Model model) {
		List<Joke> listJokes = jokeService.listAll();
		model.addAttribute("listJokes", listJokes);
		return "home";
	}

	@GetMapping("/admin/login")
	public String showAdminLogin() {
		return "admin/admin_login";
	}
	
	@GetMapping("/admin/home")
	public String adminDashboard(Model model) {
		List<Joke> listJokes = jokeService.listAll();
		model.addAttribute("listJokes", listJokes);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();	
		Admin admin = adminRepo.findById(currentPrincipalName).get();
		
		model.addAttribute("adminname", admin.getFirstname()+" "+admin.getLastname());
		
		return "admin/admin_dashboard";
	}
	
//crud admin
	@RequestMapping("/admin/editAccount")
	public ModelAndView showEditAdminPage() {
		ModelAndView mav = new ModelAndView("admin/admin_editAccount");
		
		//get current admin 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();	
		Admin admin = adminService.get(currentPrincipalName);
  
		mav.addObject("admin", admin);
		return mav;
	}
	
	@RequestMapping(value = "/admin/saveAdmin", method = RequestMethod.POST)
	public String saveAdmin(@Valid @ModelAttribute("admin") Admin admin, BindingResult bindingResult) {
 
        // check if retype password match
        if (!admin.getRePassword().equals(admin.getPasswordUnHashed())) {
            bindingResult.addError(new FieldError("admin", "rePassword", "Password doesn't match"));
        }
        
        if (bindingResult.hasErrors()) {
            return "/admin/admin_editAccount";
        }
        
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(admin.getPasswordUnHashed());
        admin.setPassword(encodedPassword);
		adminService.save(admin);
		return "redirect:/admin/home";
	}
	
//crud user
	@GetMapping("/admin/listUser")
	public String showlistUsers(Model model) {
		List<User> listUsers = userService.ListAll();
		model.addAttribute("listUsers", listUsers);
		return "admin/admin_listUser";
	}
	
	@RequestMapping("/admin/deleteUser/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id) {
		userService.delete(id);
		return "redirect:/admin/listUser";
	}
	
	@RequestMapping("/admin/editUser/{id}")
	public ModelAndView showEditUserPage(@PathVariable(name = "id") Integer id) {
		ModelAndView mav = new ModelAndView("admin/admin_editUser");
		User user = userService.get(id);
		mav.addObject("user",user);
		return mav;
	}
	
	@RequestMapping(value = "/admin/saveUser", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user")  @ConvertGroup(from = Default.class, to=UpdateGroup.class)User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPasswordUnHashed());
        user.setPassword(encodedPassword);
        
        //copy not null fields from user to originalUser, then save originalUser
        User originalUser = userRepo.findById(user.getId()).get();
        
        SharedMethod.copyNotNullFields(user, originalUser);
        
		userService.save(originalUser);
		
		return "redirect:/admin/listUser";
	}

//crud joke
	@GetMapping("/admin/listJoke")
	public String showlistJokes(Model model) {
		List<Joke> listJokes = jokeService.listAll();
		model.addAttribute("listJokes", listJokes);
		return "admin/admin_listJoke";
	}
	
	@RequestMapping("/admin/editJoke/{id}")
	public ModelAndView showEditJokePage(@PathVariable(name = "id") Integer id) {
		ModelAndView mav = new ModelAndView("admin/admin_editJoke");	
		Joke joke = jokeService.get(id);
		mav.addObject("joke",joke);
		return mav;
	}
	
	@RequestMapping(value = "/admin/saveJoke", method = RequestMethod.POST)
	public String saveJoke(@ModelAttribute("joke")  @ConvertGroup(from = Default.class, to = UpdateGroup.class)Joke joke) {
        
        //copy not null fields from user to originalUser, then save originalUser
        Joke originalJoke = jokeRepo.findById(joke.getJokeid()).get();
        
        SharedMethod.copyNotNullFields(joke, originalJoke);
        
		jokeService.add(originalJoke);
		
		return "redirect:/admin/listJoke";
	}
	
	@RequestMapping("/admin/deletejoke/{id}")
	public String deleteJoke(@PathVariable(name = "id") int id) {
		jokeService.delete(id);
		return "redirect:/admin/listJoke";
	}
	
	

}
