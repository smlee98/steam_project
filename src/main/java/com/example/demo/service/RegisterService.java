package com.example.demo.service;

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

import com.example.demo.dao.RegisterDAO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.RegisterDetail;
import com.example.demo.security.Role;

@Service
public class RegisterService implements UserDetailsService {
	
	@Autowired
	RegisterDAO resDAO;
		
	public void joinUser(RegisterDTO resDTO) throws Exception{
		resDTO.setPassword(passwordEncoder().encode(resDTO.getPassword()));
		resDTO.setAuth("X");
		
		if (resDTO.getId().equals("admin@gridone.co.kr")) {
			resDTO.setRole(Role.SUPER.getValue());
		} else {
			resDTO.setRole(Role.USER.getValue());
		}
		
		System.out.println(resDTO);
		resDAO.joinUser(resDTO);
	}
	
	public void authSuccess(RegisterDTO resDTO) throws Exception{
		resDTO.setAuth("O");
		System.out.println("인증 완료됨 : "+resDTO);
		resDAO.authSuccess(resDTO);
	}
	
	public String authMail(String id) {		
		String host = "smtp.naver.com"; // SMTP 서버 정보를 설정한다.
		String user = "dark_pig@naver.com"; // 네이버일 경우 네이버 계정, gmail경우 gmail 계정
		String password = "######";   // 패스워드
		

		//인증번호 랜덤 값
		int ran = new Random().nextInt(900000) + 100000;
		String authCode = String.valueOf(ran);

		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.auth", "true"); 

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(id));

			// 메일 제목
			message.setSubject("SteamProject 인증메일입니다!");
			// 메일 내용
			message.setText("인증 코드는 " + authCode + "입니다.");
			// send the message
			Transport.send(message);
			System.out.println("Success Message Send");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return authCode;
	}

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException{
		
		System.out.println("loadUserByUsername : "+id);
		
		RegisterDTO resDTO = resDAO.findByID(id);
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		System.out.println("resDTO : "+resDTO);
		
		if (resDTO.getRole().equals(Role.SUPER.getValue())) {
			authorities.add(new SimpleGrantedAuthority(Role.SUPER.getValue()));
		} else if (resDTO.getRole().equals(Role.ADMIN.getValue())) {
			authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
		}
		
		return new RegisterDetail(resDTO);
	}
	
	public PasswordEncoder passwordEncoder() 
	{
		return new StandardPasswordEncoder();
	}
}
