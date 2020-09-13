package com.example.demo.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.RegisterDTO;

@Repository
public class AuthDAO {
	@Autowired
	private SqlSession sqlSession;
	
	public void pwSuccess(RegisterDTO registerDTO) {
		sqlSession.update("auth.pwSuccess", registerDTO);
	}
	
	public void authSuccess(RegisterDTO registerDTO) {
		sqlSession.update("auth.authSuccess", registerDTO);
	}
	
	public void authCreate(RegisterDTO registerDTO) {
		sqlSession.insert("auth.authCreate", registerDTO);
	}
	
	public void authUpdate(RegisterDTO registerDTO) {
		sqlSession.update("auth.authUpdate", registerDTO);
	}
	
	public void authDelete(RegisterDTO registerDTO) {
		sqlSession.delete("auth.authDelete", registerDTO);
	}
}
