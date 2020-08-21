package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	@RequestMapping(value="/main", method=RequestMethod.GET)
	public String main() {
		return "main";
	}
	
	@RequestMapping(value="/main_user", method=RequestMethod.GET)
	public String main_user() {
		return "main_user";
	}
	
	@RequestMapping(value="/main_admin", method=RequestMethod.GET)
	public String main_admin() {
		return "main_admin";
	}
	
	@RequestMapping(value="/main_super", method=RequestMethod.GET)
	public String main_super() {
		return "main_super";
	}
	
	@RequestMapping(value="/mypage", method=RequestMethod.GET)
	public String mypage() {
		return "mypage";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String register() {
		return "register";
	}
	
	@RequestMapping(value="/game_1", method=RequestMethod.GET)
	public String game_1() {
		return "game_1";
	}
}
