package com.example.demo.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.mapper.RegisterMapper;
import com.example.demo.dto.RegisterDTO;

@Repository
public class RegisterDAO implements RegisterMapper{
	
	@Autowired
	private SqlSession sqlSession;
	
	public void joinUser(RegisterDTO registerDTO) {
		sqlSession.insert("register.joinUser", registerDTO);
	}
	
	public RegisterDTO findByID(String id) {
		return sqlSession.selectOne("register.findByID", id);
	}
	
	@Override
	public RegisterDTO validateMember(HashMap<String, String> map) {
		return sqlSession.selectOne("register.validateMember", map);
	}
	
	
	public void login(RegisterDTO registerDTO) {			
		sqlSession.selectOne("register.login", registerDTO);
	}
}
