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
	
	@RequestMapping(value="/mypage_user", method=RequestMethod.GET)
	public String mypage_user() {
		return "mypage_user";
	}
	
	@RequestMapping(value="/mypage_admin", method=RequestMethod.GET)
	public String mypage_admin() {
		return "mypage_admin";
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
	
	@RequestMapping(value="/upload", method=RequestMethod.GET)
	public String upload() {
		return "upload";
	}
	
	@RequestMapping(value="/dashboard_1", method=RequestMethod.GET)
	public String dashboard_1() {
		return "dashboard_1";
	}
	
	@RequestMapping(value="/dashboard_2", method=RequestMethod.GET)
	public String dashboard_2() {
		return "dashboard_2";
	}
}
