package com.example.demo.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.UploadDTO;
import com.example.demo.service.UploadService;
import com.example.demo.service.AuthService;
import com.example.demo.service.RegisterService;

@Controller
public class MainController {

	@Autowired
	RegisterService resService;
	@Autowired
	AuthService authService;
	@Autowired
	UploadService upService;

	/* 권한 상관 X */
	@RequestMapping(value="/main")
	public String main() {
		return "all/main";
	}

	@RequestMapping(value="/login")
	public String login() {
		return "all/login";
	}

	@RequestMapping(value="/register")
	public String register() {
		return "all/register";
	}

	@RequestMapping(value="/register.do")
	public String register(Model m, @RequestParam("id")String id, @RequestParam("password")String password, @RequestParam("name")String name, @RequestParam("gender")String gender, @RequestParam("address")String address, @RequestParam("phone")String phone, @RequestParam("favorite")String favorite) throws Exception{
		RegisterDTO resDTO= new RegisterDTO(id, password, name, gender, address, phone, favorite);
		resService.joinUser(resDTO);
		m.addAttribute("id", id);
		return "all/authmail";
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public int idCheck(@RequestParam("id") String id) {

		int result = resService.userIDCheck(id);

		return result;

	}
	
	@RequestMapping(value = "/findpw.do", method = RequestMethod.POST)
	@ResponseBody
	public String findpw() throws Exception {

		return "/findpw.do";

	}

	@RequestMapping(value="/authmail", method = RequestMethod.GET)
	@ResponseBody
	public String authmail(String id){
		String authCode = authService.authMail(id);
		
		System.out.println("authCode : "+ authCode);
		return authCode;
	}

	@RequestMapping(value="/authmail.do", method = RequestMethod.POST)
	public String authcode(String id) throws Exception {
		RegisterDTO resDTO = new RegisterDTO(id);
		authService.authSuccess(resDTO);
		return "all/main";
	}


	@RequestMapping(value="/game_1", method=RequestMethod.GET)
	public String game_1() {
		return "all/game_1";
	}

	/* 유저 */
	@RequestMapping(value="user/main_user", method=RequestMethod.GET)
	public String main_user() {
		return "user/main_user";
	}

	@RequestMapping(value="user/mypage_user", method=RequestMethod.GET)
	public String mypage_user() {
		return "user/mypage_user";
	}

	/* 관리자 */

	@RequestMapping(value="admin/main_admin", method=RequestMethod.GET)
	public String main_admin() {
		return "admin/main_admin";
	}

	@RequestMapping(value="admin/mypage_admin", method=RequestMethod.GET)
	public String mypage_admin() {
		return "admin/mypage_admin";
	}

	@RequestMapping(value="admin/upload", method=RequestMethod.GET)
	public String upload() {
		return "admin/upload";
	}
	
	@RequestMapping(value="admin/upload.do", method = RequestMethod.POST)
	public String upload(Model m, MultipartFile mf, HttpSession session, @RequestParam("orgfile")String orgfile, @RequestParam("thumbnail")String thumbnail, @RequestParam("name")String name, @RequestParam("category")String category, @RequestParam("version")String version, @RequestParam("amount")int amount, @RequestParam("explain")String explain, @RequestParam("files")MultipartFile files) throws Exception{
		UploadDTO upDTO= new UploadDTO(orgfile, thumbnail, name, category, version, amount, explain, files);
		upService.uploadGame(upDTO);
		upService.fileSet(upDTO, mf, session);
		
		m.addAttribute("name", name);
		
		return "admin/main_admin";
	}

	/* 슈퍼 관리자 */

	@RequestMapping(value="super/main_super", method=RequestMethod.GET)
	public String main_super() {
		return "super/main_super";
	}

	@RequestMapping(value="super/dashboard_1", method=RequestMethod.GET)
	public String dashboard_1() {
		return "super/dashboard_1";
	}

	@RequestMapping(value="super/dashboard_2", method=RequestMethod.GET)
	public String dashboard_2() {
		return "super/dashboard_2";
	}
}
