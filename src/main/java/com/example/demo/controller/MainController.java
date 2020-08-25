package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.RegisterDTO;
import com.example.demo.service.RegisterService;

@Controller
public class MainController {
	
	@Autowired
	RegisterService resService;
	
	@RequestMapping(value="/main")
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
	
	@RequestMapping(value="/register")
	public String register() {
		return "register";
	}
	
	@RequestMapping(value="/register.do")
	public String register(@RequestParam("id")String id, @RequestParam("password")String password, @RequestParam("name")String name, @RequestParam("gender")String gender, @RequestParam("address")String address, @RequestParam("phone")String phone, @RequestParam("favorite")String favorite) {
		RegisterDTO resDTO= new RegisterDTO(id, password, name, gender, address, phone, favorite);	
		System.out.println("id="+id+"password="+password+"name="+name+"gender="+gender+"address="+address+"phone="+phone+"favorite="+favorite);
		resService.insertOne(resDTO);

		return "main";
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
