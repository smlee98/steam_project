package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Format;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.http.MediaType;
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

import com.example.demo.dto.DownloadDTO;
import com.example.demo.dto.EvaluateDTO;
import com.example.demo.dto.PurchaseDTO;
import com.example.demo.dto.PurchaseDetail;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.RegisterDetail;
import com.example.demo.dto.UploadDTO;
import com.example.demo.dto.UploadDetail;
import com.example.demo.handler.LoginHandler;
import com.example.demo.security.GetInfo;
import com.example.demo.service.UploadService;

import com.example.demo.service.AuthService;
import com.example.demo.service.DashService;
import com.example.demo.service.DownloadService;
import com.example.demo.service.EvaluateService;
import com.example.demo.service.PurchaseService;
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
	@Autowired
	PurchaseService purchaseService;
	@Autowired
	DownloadService downService;
	@Autowired
	EvaluateService evalService;
	
	/* fragment 용 함수인데... 이렇게 모든 컨트롤러 호출은 비효율적인거 같긴하다... */
	public void getMoney(Model m) {
		RegisterDetail user = (RegisterDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String id = user.getId();
		System.out.println("id : "+ id);
		m.addAttribute("id", id);
		
		int money = purchaseService.getMoney(id);
		System.out.println("money : "+ money);
		m.addAttribute("money", money);
	}

	/* 권한 상관 없음 */	
	@RequestMapping(value="/main")
	public String main(Model m) {
		if(!(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals("anonymousUser")){
			getMoney(m);
		}
		if((SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals("anonymousUser")){
			m.addAttribute("auth", "none");
		}
		
		List<UploadDTO> list = upService.viewRecent();
		m.addAttribute("list", list);
		
		return "all/main";
	}
	
	@RequestMapping(value="/genre")
	public String genre(Model m, @RequestParam(value="category") String category, @RequestParam(value="curPage", required=false, defaultValue="1") int curPage) {
		if(!(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals("anonymousUser")){
			getMoney(m);
		}		

		List<UploadDTO> cnt = upService.genreCnt(category);
		int listCnt = cnt.size();
	
		Pagination pagination = new Pagination(listCnt, curPage);
		int index = pagination.getStartIndex();
		int pageSize = pagination.getPageSize();
		
		Map<String, Object> param = new HashMap<>();
		param.put("index", index);
		param.put("pageSize", pageSize);
		param.put("category", category);
		
		List<UploadDTO> genre = upService.viewGenre(param);
		
		System.out.println("genre : " + genre);
		System.out.println("index : " + index);
		System.out.println("pageSize : " + pageSize);
		
		getMoney(m);
		
		m.addAttribute("pagination", pagination);
		m.addAttribute("cnt", cnt);
		m.addAttribute("genre", genre);
		m.addAttribute("category", category);
		
		
		return "all/genre";
	}
	
	@RequestMapping(value="/search")
	public String search(Model m, @RequestParam(value = "keyword") String keyword, @RequestParam(value="curPage", required=false, defaultValue="1") int curPage) {
		if(!(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals("anonymousUser")){
			getMoney(m);
		}
		
		List<UploadDTO> cnt = upService.searchCnt(keyword);
		int listCnt = cnt.size();
	
		Pagination pagination = new Pagination(listCnt, curPage);
		int index = pagination.getStartIndex();
		int pageSize = pagination.getPageSize();
		
		Map<String, Object> param = new HashMap<>();
		param.put("index", index);
		param.put("pageSize", pageSize);
		param.put("keyword", keyword);
		
		List<UploadDTO> list = upService.searchList(param);
		
		System.out.println("list : " + list);
		System.out.println("index : " + index);
		System.out.println("pageSize : " + pageSize);
		
		getMoney(m);
		
		m.addAttribute("pagination", pagination);
		m.addAttribute("cnt", cnt);
		m.addAttribute("list", list);
		m.addAttribute("keyword", keyword);
		
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
	public String register(Model m, RegisterDTO resDTO, PurchaseDTO purchaseDTO) throws Exception{
		resService.joinUser(resDTO);
		
		String id = resDTO.getId();
		System.out.println("id : "+ id);
		m.addAttribute("id", id);
		
		purchaseService.enroll(purchaseDTO);
		System.out.println("purchaseDTO : "+ purchaseDTO);
		
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
		
		getMoney(m);
		
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
	public String game(Model m, UploadDTO upDTO, EvaluateDTO evaluateDTO, PurchaseDTO purchaseDTO, String id, String number, ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
		if(!(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals("anonymousUser")){
			getMoney(m);
			
			RegisterDetail user = (RegisterDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			id = user.getId();
			m.addAttribute("id", id);
			System.out.println("아이디 : " + id);
		}
		else {
			id = "anonymous";
			m.addAttribute("id", "anonymous");
		}
		
		upService.doFilter(servletRequest, servletResponse);
		
		List<UploadDTO> list = upService.gameDetail(number);
		m.addAttribute("list", list);
		
		number = evaluateDTO.getNumber();
		m.addAttribute("number", number);
		System.out.println("number : " + number);
		
		HashMap<String, String> info = new HashMap<String, String>();
		info.put("id", id);
		info.put("number", number);
		
		String myStatus = evalService.myStatus(info);
		m.addAttribute("myStatus", myStatus);
		int already = evalService.already(info);
		m.addAttribute("already", already);
		System.out.println("already : "+ already);
		
		int likeCount = evalService.likeCount(evaluateDTO);
		m.addAttribute("likeCount", likeCount);
		int dislikeCount = evalService.dislikeCount(evaluateDTO);
		m.addAttribute("dislikeCount", dislikeCount);
		
		return "all/game";
	}

	/* 유저 */
	@RequestMapping(value="/download")
	public ResponseEntity<Resource> download(DownloadDTO downDTO, PurchaseDTO purchaseDTO, int money, String file, String number, Model m) throws IOException {
		System.out.println("file : " + file);
		Path path = Paths.get(file);
		
		String filename = new String(((path.getFileName().toString()).split("_")[1].getBytes("UTF-8")), "ISO-8859-1");
		System.out.println("filename change " + filename);
		//String contentType = Files.probeContentType(path);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
		
		Resource resource = new InputStreamResource(Files.newInputStream(path));	
		
		purchaseService.setMoney(purchaseDTO, money);
		System.out.println("purchaseDTO : "+ purchaseDTO);
		
		downService.enroll(downDTO);
		
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value="user/purchase")
	public String purchaseList (Model m) {
		RegisterDetail user = (RegisterDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String id = user.getId();
		System.out.println("id : "+ id);
		m.addAttribute("id", id);
		
		List<DownloadDTO> list = downService.downloadList(id);
		m.addAttribute("list", list);
		
		getMoney(m);
		
		return "user/purchaseList";
	}

	@RequestMapping(value = "user/charge")
	public String charge (Model m) {
		RegisterDetail user = (RegisterDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String id = user.getId();
		System.out.println("id : "+ id);
		m.addAttribute("id", id);
		
		return "user/charge";
	}
	
	@RequestMapping(value = "/charge.do")
	public String charge (int money, PurchaseDTO purchaseDTO, Model m) {
		purchaseService.charge(purchaseDTO, money);
		System.out.println("purchaseDTO : "+ purchaseDTO);
		
		return "user/charge";
	}
	
	@RequestMapping(value = "/analyze")
	public String analyze (Model m, RegisterDTO registerDTO) {
		String id = registerDTO.getId();
		List<DownloadDTO> down = downService.downloadList(id);
		m.addAttribute("down", down);
		List<UploadDTO> up = upService.uploadList(id);
		m.addAttribute("up", up);
		
		int downCnt = downService.analyze(registerDTO);
		m.addAttribute("downCnt", downCnt);
		int upCnt = upService.analyze(registerDTO);
		m.addAttribute("upCnt", upCnt);
		int coin = purchaseService.analyze(registerDTO);
		m.addAttribute("coin",coin);
		
		return "super/analyze";
	}
	
	@RequestMapping(value = "/refresh")
	public String refresh (Model m) {
		
		getMoney(m);
		
		return "all/refresh";
	}
	
	@RequestMapping(value = "/evaluate")
	public String evaluate (EvaluateDTO evaluateDTO) {
		evalService.enroll(evaluateDTO);
		
		return "redirect:/refresh";
	}
	
	@RequestMapping(value = "/evaluate_del")
	public String evaluate_del (EvaluateDTO evaluateDTO) {
		evalService.delete(evaluateDTO);
		
		return "redirect:/refresh";
	}
	
	/* 관리자 */
	@RequestMapping(value="admin/upload", method=RequestMethod.GET)
	public String upload(Model m) {
		RegisterDetail user = (RegisterDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String id = user.getId();
		System.out.println("id : "+ id);
		m.addAttribute("id", id);
		
		getMoney(m);
		
		return "admin/upload";
	}
	
	@RequestMapping(value="admin/upload_my", method=RequestMethod.GET)
	public String upload_my(Model m) {
		RegisterDetail user = (RegisterDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String id = user.getId();
		System.out.println(id);
		
		getMoney(m);
		
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
		
		getMoney(m);
		
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
		
		getMoney(m);
		
		return "super/dashboard_1";
	}

	@RequestMapping(value="super/dashboard_2", method=RequestMethod.GET)
	public String dashboard_2(Model m) {
		int gameCount = dashService.getGameCount();
		m.addAttribute("gameCount", gameCount);
		
		getMoney(m);
		
		return "super/dashboard_2";
	}
	
	@RequestMapping(value="super/memberList", method=RequestMethod.GET)
	public String memberList(Model m, RegisterDTO registerDTO, @RequestParam(value="curPage", required=false, defaultValue="1") int curPage) {
		List<RegisterDTO> cnt = resService.memberCnt();
		int listCnt = cnt.size();
	
		Pagination pagination = new Pagination(listCnt, curPage);
		int index = pagination.getStartIndex();
		int pageSize = pagination.getPageSize();
		
		Map<String, Integer> param = new HashMap<>();
		param.put("index", index);
		param.put("pageSize", pageSize);
		
		List<RegisterDTO> list = resService.memberList(param);
		
		getMoney(m);
		
		m.addAttribute("pagination", pagination);
		m.addAttribute("cnt", cnt);
		m.addAttribute("list", list);
		
		return "super/memberList";
	}
}
