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

import com.example.demo.dao.EvaluateDAO;
import com.example.demo.dao.PurchaseDAO;
import com.example.demo.dao.RegisterDAO;
import com.example.demo.dto.EvaluateDTO;
import com.example.demo.dto.PurchaseDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.RegisterDetail;
import com.example.demo.security.Role;

@Service
public class EvaluateService {

	@Autowired
	EvaluateDAO evaluateDAO;
	
	public void enroll(EvaluateDTO evaluateDTO){
		evaluateDAO.enroll(evaluateDTO);
	}
	
	public void delete(EvaluateDTO evaluateDTO){
		evaluateDAO.delete(evaluateDTO);
	}
	
	public String myStatus(HashMap<String, String> info){
		return evaluateDAO.myStatus(info);
	}
	
	public int already(HashMap<String, String> info){
		return evaluateDAO.already(info);
	}
	
	public int likeCount(EvaluateDTO evaluateDTO){
		return evaluateDAO.likeCount(evaluateDTO);
	}
	
	public int dislikeCount(EvaluateDTO evaluateDTO){
		return evaluateDAO.dislikeCount(evaluateDTO);
	}
	
}
