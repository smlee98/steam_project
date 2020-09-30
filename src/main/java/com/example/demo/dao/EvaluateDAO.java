package com.example.demo.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.EvaluateDTO;
import com.example.demo.dto.PurchaseDTO;
import com.example.demo.dto.RegisterDTO;

@Repository
public class EvaluateDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	public void enroll(EvaluateDTO evaluateDTO){
		sqlSession.insert("evaluate.enroll", evaluateDTO);
	}
	
	public void delete(EvaluateDTO evaluateDTO){
		sqlSession.delete("evaluate.delete", evaluateDTO);
	}
	
	public String myStatus(HashMap<String, String> info){
		return sqlSession.selectOne("evaluate.myStatus", info);
	}
	
	public int already(HashMap<String, String> info){
		return sqlSession.selectOne("evaluate.already", info);
	}
	
	public int likeCount(EvaluateDTO evaluateDTO){
		return sqlSession.selectOne("evaluate.likeCount", evaluateDTO);
	}
	
	public int dislikeCount(EvaluateDTO evaluateDTO){
		return sqlSession.selectOne("evaluate.dislikeCount", evaluateDTO);
	}
	
}