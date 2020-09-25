package com.example.demo.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.PurchaseDTO;
import com.example.demo.dto.RegisterDTO;

@Repository
public class PurchaseDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int enroll(PurchaseDTO purchaseDTO){
		return sqlSession.insert("purchase.enroll", purchaseDTO);
	}
	
	public int getMoney(String id){
		return sqlSession.selectOne("purchase.getMoney", id);
	}
	
	public void setMoney(PurchaseDTO purchaseDTO){
		sqlSession.update("purchase.setMoney", purchaseDTO);
	}
	
	public void charge(PurchaseDTO purchaseDTO){
		sqlSession.update("purchase.charge", purchaseDTO);
	}
	
}