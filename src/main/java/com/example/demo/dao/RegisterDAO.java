package com.example.demo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.RegisterDTO;

@Repository
public class RegisterDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	public void joinUser(RegisterDTO resDTO) {
		sqlSession.insert("register.joinUser", resDTO);
	}
	
	public int checkID(String id) {			
		int result = sqlSession.selectOne("register.checkID", id);
		return result;
	}
	
	public RegisterDTO findByID(String id) {
		return sqlSession.selectOne("register.findByID", id);
	}
	
	public List<RegisterDTO> memberList(Map<String, Integer> param){
		return sqlSession.selectList("register.memberList", param);
	}
	
	public List<RegisterDTO> memberCnt(){
		return sqlSession.selectList("register.memberCnt");
	}
	
	public void myPage(RegisterDTO resDTO){
		sqlSession.update("register.myPage", resDTO);
	}
}