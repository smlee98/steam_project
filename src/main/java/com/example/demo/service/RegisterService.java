package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.RegisterDAO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.RegisterDetail;
import com.example.demo.security.Role;
import com.example.demo.security.CryptoUtil;

@Service
public class RegisterService implements UserDetailsService {
	
	@Autowired
	RegisterDAO resDAO;
		
	public void joinUser(RegisterDTO resDTO) throws Exception{
		resDTO.setPassword(CryptoUtil.sha256(resDTO.getPassword()));
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
		RegisterDTO resDTO = resDAO.findByID(id);
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		if (resDTO.getRole().equals(Role.SUPER.getValue())) {
			authorities.add(new SimpleGrantedAuthority(Role.SUPER.getValue()));
		} else if (resDTO.getRole().equals(Role.ADMIN.getValue())) {
			authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
		}
		
		return new RegisterDetail(resDTO);
	}
	
	public void login(RegisterDTO resDTO) {
			
		resDAO.login(resDTO);
	}
}
