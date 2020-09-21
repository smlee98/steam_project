package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.extras.springsecurity5.util.SpringSecurityContextUtils;

import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.RegisterDetail;
import com.example.demo.dto.UploadDTO;
import com.example.demo.dto.UploadDetail;
import com.example.demo.handler.LoginHandler;
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
	
	@GetMapping("/login")
	public String Login() {
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
		return "redirect:/main";
	}

	@RequestMapping(value="/authmail.do", method = RequestMethod.POST)
	public String authcode(RegisterDTO resDTO) throws Exception {
		String id = resDTO.getId();
		System.out.println("id : "+ id);
		authService.authSuccess(resDTO);
		return "redirect:/main";
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
		return "redirect:/main";
	}


	@RequestMapping(value="/game", method=RequestMethod.GET)
	public String game(Model m, UploadDTO upDTO, RegisterDTO resDTO, String number, ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
		if(!(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals("anonymousUser")){
			RegisterDetail user = (RegisterDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String id = user.getId();
			System.out.println("id : "+ id);
			m.addAttribute("id", id);
		}
		else {
			m.addAttribute("id", "anonymous");
		}
		
		upService.doFilter(servletRequest, servletResponse);
		
		List<UploadDTO> list = upService.gameDetail(number);
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
		
		return "redirect:/main";
	}
	
	@RequestMapping(value="admin/mod_upload", method=RequestMethod.GET)
	public String modUpload(Model m, String number) {
		List<UploadDTO> list = upService.gameDetail(number);
		m.addAttribute("list", list);
		
		return "admin/mod_upload";
	}
	
	@RequestMapping(value="admin/mod_upload.do", method=RequestMethod.POST)
	public String modUpload(Model m, MultipartFile mf, MultipartFile mf2, HttpSession session, UploadDTO upDTO) throws Exception{
		upService.modUpload(upDTO);
		upService.modFileSet(upDTO, mf, session);
		upService.modThumbSet(upDTO, mf2, session);
		
		return "redirect:/admin/upload_my";
	}
	
	@RequestMapping(value="admin/del_upload", method=RequestMethod.GET)
	public String delUpload(Model m, UploadDTO upDTO) throws Exception{
		upService.delUpload(upDTO);
		
		return "redirect:/admin/upload_my";
	}
	
	@RequestMapping(value="admin/download")
	public ResponseEntity<Resource> download(String file) throws IOException {
		System.out.println("file : " + file);
		Path path = Paths.get(file);
		//String contentType = Files.probeContentType(path);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + path.getFileName().toString());
		
		Resource resource = new InputStreamResource(Files.newInputStream(path));
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
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
