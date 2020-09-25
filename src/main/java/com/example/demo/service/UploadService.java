package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.UploadDAO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.RegisterDetail;
import com.example.demo.dto.UploadDTO;

@PropertySource("classpath:/application.properties")
@Service
public class UploadService {

	@Autowired
	UploadDAO upDAO;
	@Autowired
	ApplicationContext context;
	
	public List<UploadDTO> uploadList(String id){		
		return upDAO.uploadList(id);
	}
	
	public List<UploadDTO> gameDetail(String number){		
		return upDAO.gameDetail(number);
	}
	
	public List<UploadDTO> viewRecent(){		
		return upDAO.viewRecent();
	}
	
	public List<UploadDTO> viewGenre(String category){		
		return upDAO.viewGenre(category);
	}

	public void uploadGame(UploadDTO upDTO) throws Exception{
		SimpleDateFormat format1 = new SimpleDateFormat ("yyyy년 MM월 dd일 HH시 mm분 ss초");
		Date time = new Date();		
		String orgtime = format1.format(time);
		
		upDTO.setOrgdate(orgtime);
		upDTO.setNewdate(orgtime);
		
		System.out.println(upDTO);
		upDAO.uploadGame(upDTO);
	}
	
	public void fileSet(UploadDTO upDTO, MultipartFile mf, HttpSession session) throws Exception{
		long time = System.currentTimeMillis();
		final String path = context.getEnvironment().getProperty("steam.project.path-zip");
		mf = upDTO.getFiles();
		
		if(!mf.isEmpty()) {
			String orgName = mf.getOriginalFilename();
			String newName = path + "\\" + time + "_" + orgName;
			File files =  new File(path + File.separator + time + "_" + orgName);
			upDTO.setOrgfile(orgName);
			upDTO.setNewfile(newName);
			mf.transferTo(files);
		}
		
		upDAO.setNewFile(upDTO);
	}
	
	public void thumbSet(UploadDTO upDTO, MultipartFile mf2, HttpSession session) throws Exception{
		long time = System.currentTimeMillis();
		final String path = context.getEnvironment().getProperty("steam.project.path-thumbnail");
		mf2 = upDTO.getThumbs();
		
		if(!mf2.isEmpty()) {
			String orgName = mf2.getOriginalFilename();
			String newName = path + "\\" + time + "_" + orgName;
			File thumbnail =  new File(path + File.separator + time + "_" + orgName);
			upDTO.setThumbnail(newName);
			mf2.transferTo(thumbnail);
		}
		
		upDAO.setThumbFile(upDTO);
	}
	
	public void modUpload (UploadDTO upDTO) throws Exception{		
		SimpleDateFormat format1 = new SimpleDateFormat ("yyyy년 MM월 dd일 HH시 mm분 ss초");
		Date time = new Date();		
		String orgtime = format1.format(time);
		
		upDTO.setNewdate(orgtime);
		
		System.out.println(upDTO);
		upDAO.modUpload(upDTO);
	}
	
	public void modFileSet(UploadDTO upDTO, MultipartFile mf, HttpSession session) throws Exception{
		long time = System.currentTimeMillis();
		final String path = context.getEnvironment().getProperty("steam.project.path-zip");
		mf = upDTO.getFiles();
		
		if(!mf.isEmpty()) {
			String orgName = mf.getOriginalFilename();
			String newName = path + "\\" + time + "_" + orgName;
			File files =  new File(path + File.separator + time + "_" + orgName);
			upDTO.setOrgfile(orgName);
			upDTO.setNewfile(newName);
			mf.transferTo(files);
		}
		
		upDAO.modNewFile(upDTO);
	}
	
	public void modThumbSet(UploadDTO upDTO, MultipartFile mf2, HttpSession session) throws Exception{
		long time = System.currentTimeMillis();
		final String path = context.getEnvironment().getProperty("steam.project.path-thumbnail");
		mf2 = upDTO.getThumbs();
		
		if(!mf2.isEmpty()) {
			String orgName = mf2.getOriginalFilename();
			String newName = path + "\\" + time + "_" + orgName;
			File thumbnail =  new File(path + File.separator + time + "_" + orgName);
			upDTO.setThumbnail(newName);
			mf2.transferTo(thumbnail);
		}
		
		upDAO.modThumbFile(upDTO);
	}
	
	public void delUpload(UploadDTO upDTO) {
		upDAO.delUpload(upDTO);
	}
	
	@Transactional
	public List<UploadDTO> searchList(String keyword){
		return upDAO.searchList(keyword);
	}
	
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.setHeader("X-Frame-Options", "ALLOW-FROM https://www.youtube.com/");
	}
}
