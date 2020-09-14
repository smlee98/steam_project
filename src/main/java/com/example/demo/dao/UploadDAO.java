package com.example.demo.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.mapper.RegisterMapper;
import com.example.demo.dto.UploadDTO;

@Repository
public class UploadDAO{
	
	@Autowired
	private SqlSession sqlSession;
	
	public int getGameCount() {
		int count = sqlSession.selectOne("upload.getgamecount");
		return count;
	}
	
	public void uploadGame(UploadDTO UploadDTO) {
		sqlSession.insert("upload.enroll", UploadDTO);
	}
	
	public void setNewFile(UploadDTO UploadDTO) {
		sqlSession.update("upload.newfileName", UploadDTO);
	}
	
	public void setThumbFile(UploadDTO UploadDTO) {
		sqlSession.update("upload.newthumbName", UploadDTO);
	}
}