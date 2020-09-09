package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
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
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.UploadDAO;
import com.example.demo.dto.UploadDTO;

@Service
public class UploadService {

	@Autowired
	UploadDAO upDAO;

	public void uploadGame(UploadDTO upDTO) throws Exception{

		System.out.println(upDTO);
		upDAO.uploadGame(upDTO);
	}
	
	public void fileSet(UploadDTO upDTO, MultipartFile mf, HttpSession session) throws Exception{
		long time = System.currentTimeMillis();
		final String path = session.getServletContext().getRealPath("/resources/upload");
		mf = upDTO.getFiles();
		
		System.out.println("time : "+time);
		System.out.println("path : "+path);
		System.out.println("mf : "+mf);
		
		if(!mf.isEmpty()) {
			String orgName = mf.getOriginalFilename();
			String newName = orgName+time+mf.getSize();
			File files =  new File(path + File.separator+newName);
			upDTO.setOrgfile(orgName);
			upDTO.setNewfile(newName);
			mf.transferTo(files);
			
			System.out.println("orgName : "+orgName);
			System.out.println("newName : "+newName);
			System.out.println("files : "+files);
		}
		
		upDAO.setNewFile(upDTO);
	}
}
