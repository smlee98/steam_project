package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.RegisterDAO;
import com.example.demo.dto.RegisterDTO;

@Service
public class RegisterService {
	
	@Autowired
	RegisterDAO resDAO;
	
	public void insertOne(RegisterDTO resDTO) {
		resDAO.insertOne(resDTO);
	}
}
