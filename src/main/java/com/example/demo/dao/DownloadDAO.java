package com.example.demo.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.mapper.RegisterMapper;
import com.example.demo.dto.DownloadDTO;
import com.example.demo.dto.PurchaseDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.UploadDTO;

@Repository
public class DownloadDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int enroll(DownloadDTO downDTO){
		return sqlSession.insert("download.enroll", downDTO);
	}
	
	public List<DownloadDTO> downloadList(String id){
		return sqlSession.selectList("download.downloadList", id);
	}
}