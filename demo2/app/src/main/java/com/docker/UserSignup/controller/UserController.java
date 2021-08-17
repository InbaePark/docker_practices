package com.docker.UserSignup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.AllArgsConstructor;

import com.docker.UserSignup.model.User;
import com.docker.UserSignup.model.UserLogin;
import com.docker.UserSignup.service.UserService;

@Controller
@SessionAttributes("user")
@AllArgsConstructor
public class UserController {
	
	private final UserService userService;

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index(Model model) {
		return "contents/index";
	}
		
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signup(Model model) {
		User user = new User();		
		model.addAttribute("user", user);		
		return "contents/signup";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result, Model model) {		
		if(result.hasErrors()) {
			return "contents/signup";
		} else if(userService.findByUserName(user.getUserName())) {
			model.addAttribute("message", "User Name exists. Try another user name");
			return "contents/signup";
		} else {
			userService.save(user);
			model.addAttribute("message", "Saved user details");
			return "redirect:login";
		}
	}

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(Model model) {			
		UserLogin userLogin = new UserLogin();		
		model.addAttribute("userLogin", userLogin);
		return "contents/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@Validated UserLogin userLogin, BindingResult result) {
		if (result.hasErrors()) {
			return "contents/login";
		} else {
			boolean found = userService.findByLogin(userLogin.getUserName(), userLogin.getPassword());
			if (found) {				
				return "contents/success";
			} else {				
				return "contents/failure";
			}
		}
		
	}
}
