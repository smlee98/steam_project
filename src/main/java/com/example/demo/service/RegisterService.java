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

	public List<RegisterDTO> memberList(){
		return resDAO.memberList();
	}
	
	public void myPage(RegisterDTO resDTO){
		resDAO.myPage(resDTO);
	}
	
	public int userIDCheck(String id) {

		int result = resDAO.checkID(id);

		return result;
	}
	
	public String login(RegisterDTO resDTO) {

		String role = resDTO.getRole();
		
		System.out.println(role);

		return role;
	}


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
		
		System.out.println(id+"의 Role = "+resDTO.getRole());
		System.out.println(id+"의 인증 상태 : "+resDTO.getAuth());

		return new RegisterDetail(resDTO);
	}

	public PasswordEncoder passwordEncoder() 
	{
		return new StandardPasswordEncoder();
	}
}
