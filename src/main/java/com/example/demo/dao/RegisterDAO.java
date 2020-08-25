package com.example.demo.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.RegisterDTO;

@Repository
public class RegisterDAO {
	@Autowired
	private SqlSession sqlSession;
	
	public void insertOne(RegisterDTO registerDTO) {
		sqlSession.insert("register.insertOne", registerDTO);
	}
}
