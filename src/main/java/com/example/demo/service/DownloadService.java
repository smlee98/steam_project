package com.example.demo.service;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.dao.DownloadDAO;
import com.example.demo.dao.PurchaseDAO;
import com.example.demo.dao.RegisterDAO;
import com.example.demo.dto.DownloadDTO;
import com.example.demo.dto.PurchaseDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.RegisterDetail;
import com.example.demo.dto.UploadDTO;
import com.example.demo.security.Role;

@Service
public class DownloadService {

	@Autowired
	DownloadDAO downDAO;
	
	public void enroll(DownloadDTO downDTO){
		SimpleDateFormat format1 = new SimpleDateFormat ("yyyy년 MM월 dd일 HH시 mm분 ss초");
		Date time = new Date();		
		String orgtime = format1.format(time);
		
		downDTO.setTime(orgtime);
		
		System.out.println(downDTO);
		downDAO.enroll(downDTO);
	}
	
	public List<DownloadDTO> downloadList(String id){		
		return downDAO.downloadList(id);
	}
	
	public int analyze(RegisterDTO registerDTO){		
		return downDAO.downCnt(registerDTO);
	}
}
