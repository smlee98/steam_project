package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.extras.springsecurity5.util.SpringSecurityContextUtils;

import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.RegisterDetail;
import com.example.demo.dto.UploadDTO;
import com.example.demo.security.GetInfo;
import com.example.demo.service.UploadService;
import com.example.demo.service.AuthService;
import com.example.demo.service.DashService;
import com.example.demo.service.RegisterService;

@Controller
public class MainController {

	@Autowired
	RegisterService resService;
	@Autowired
	AuthService authService;
	@Autowired
	UploadService upService;
	@Autowired
	DashService dashService;

	/* 권한 상관 X */
	@RequestMapping(value="/main")
	public String main(Model m) {
		List<UploadDTO> list = upService.viewRecent();
		m.addAttribute("list", list);
		
		return "all/main";
	}
	
	@RequestMapping(value="/genre")
	public String genre(Model m, String category) {
		List<UploadDTO> genre = upService.viewGenre(category);
		m.addAttribute("genre", genre);
		m.addAttribute("category", category);
		
		return "all/genre";
	}
	
	@RequestMapping(value="/search")
	public String search(@RequestParam(value = "keyword") String keyword, Model m) {
		List<UploadDTO> list = upService.searchList(keyword);
		m.addAttribute("list", list);
		System.out.println(list);
		return "all/search";
	}
	
	@RequestMapping(value="/login")
	public String login(Model m, RegisterDTO resDTO) {		
		return "all/login";
	}

	@RequestMapping(value="/register")
	public String register() {
		return "all/register";
	}

	@RequestMapping(value="/register.do")
	public String register(Model m, RegisterDTO resDTO) throws Exception{
		resService.joinUser(resDTO);
		
		String id = resDTO.getId();
		System.out.println("id : "+ id);
		m.addAttribute("id", id);
		
		String authCode = authService.authMail(id);
		System.out.println("authCode : "+ authCode);
		m.addAttribute("code", authCode);
		
		return "all/authmail";
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public int idCheck(@RequestParam("id") String id) {

		int result = resService.userIDCheck(id);

		return result;

	}
	
	@RequestMapping(value="/mypage", method=RequestMethod.GET)
	public String mypage_admin(Model m, RegisterDTO resDTO) throws Exception { 
		RegisterDetail user = (RegisterDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("user : "+ user);
		m.addAttribute("user", user);
		
		return "all/mypage";
	}
	
	@RequestMapping(value = "/mypage.do", method = RequestMethod.POST)
	public String mypage(RegisterDTO resDTO, HttpSession session) {
		resService.myPage(resDTO);
		session.invalidate();
		return "redirect:main";
	}

	@RequestMapping(value="/authmail.do", method = RequestMethod.POST)
	public String authcode(RegisterDTO resDTO) throws Exception {
		String id = resDTO.getId();
		System.out.println("id : "+ id);
		authService.authSuccess(resDTO);
		return "all/main";
	}
	
	@RequestMapping(value = "/findpw", method = RequestMethod.GET)
	public String findpw() throws Exception {
		return "all/findPw";
	}
	
	@RequestMapping(value="/authpw", method = RequestMethod.POST)
	public String authpw(Model m, RegisterDTO resDTO) throws Exception {
		String id = resDTO.getId();
		System.out.println("id : "+ id);
		m.addAttribute("id", id);
		
		String authCode = authService.authpw(id);
		System.out.println("authCode : "+ authCode);
		m.addAttribute("code", authCode);
		
		return "all/authPw";
	}
	
	@RequestMapping(value="/authpw.do", method = RequestMethod.POST)
	public String changepw(RegisterDTO resDTO) throws Exception {
		authService.pwSuccess(resDTO);
		return "all/main";
	}


	@RequestMapping(value="/game", method=RequestMethod.GET)
	public String game(Model m, UploadDTO upDTO, ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
		String number = String.valueOf(upDTO.getNumber());
		System.out.println(number);
		
		RegisterDetail user = (RegisterDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("user : "+ user);
		m.addAttribute("user", user);
		
		upService.doFilter(servletRequest, servletResponse);
		
		List<UploadDTO> list = upService.gameDetail();
		m.addAttribute("list", list);
		return "all/game";
	}

	/* 유저 */

	/* 관리자 */
	@RequestMapping(value="admin/upload", method=RequestMethod.GET)
	public String upload(Model m) {
		RegisterDetail user = (RegisterDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String id = user.getId();
		System.out.println("id : "+ id);
		m.addAttribute("id", id);
		
		return "admin/upload";
	}
	
	@RequestMapping(value="admin/upload_my", method=RequestMethod.GET)
	public String upload_my(Model m) {
		RegisterDetail user = (RegisterDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String id = user.getId();
		
		System.out.println(id);
		
		List<UploadDTO> list = upService.uploadList(id);
		m.addAttribute("list", list);
		
		return "admin/uploadList";
	}
	
	@RequestMapping(value="admin/upload.do", method = RequestMethod.POST)
	public String upload(Model m, MultipartFile mf, MultipartFile mf2, HttpSession session, UploadDTO upDTO) throws Exception{		
		upService.uploadGame(upDTO);
		upService.fileSet(upDTO, mf, session);
		upService.thumbSet(upDTO, mf2, session);
		
		String name = upDTO.getName();
		System.out.println("name : "+ name);
		m.addAttribute("name", name);
		
		return "all/main";
	}

	/* 슈퍼 관리자 */

	@RequestMapping(value="super/dashboard_1", method=RequestMethod.GET)
	public String dashboard_1(Model m) {		
		String fulldisk = dashService.getFulldisk();
		String usabledisk = dashService.getUsabledisk();
		String cpuprocess = dashService.getCpuprocess();
		String fullmemory = dashService.getFullMem();
		String usablememory = dashService.getUsableMem();
		String[] avgdata = dashService.getAvgData();
		
		m.addAttribute("fulldisk", fulldisk);
		m.addAttribute("usabledisk", usabledisk);
		m.addAttribute("cpuprocess", cpuprocess);
		m.addAttribute("fullmemory", fullmemory);
		m.addAttribute("usablememory", usablememory);
		m.addAttribute("avgdata", avgdata);
		
		return "super/dashboard_1";
	}

	@RequestMapping(value="super/dashboard_2", method=RequestMethod.GET)
	public String dashboard_2(Model m) {
		int gameCount = dashService.getGameCount();
		m.addAttribute("gameCount", gameCount);
		
		return "super/dashboard_2";
	}
	
	@RequestMapping(value="super/memberList", method=RequestMethod.GET)
	public String memberList(Model m) {
		List<RegisterDTO> list = resService.memberList();
		m.addAttribute("list", list);
		
		return "super/memberList";
	}
}
