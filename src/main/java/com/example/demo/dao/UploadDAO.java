package com.example.demo.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.mapper.RegisterMapper;
import com.example.demo.dto.RegisterDTO;
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
	
	public List<UploadDTO> uploadList(String id){
		return sqlSession.selectList("upload.uploadList", id);
	}
	
	public List<UploadDTO> searchList(String keyword){
		return sqlSession.selectList("upload.searchList", keyword);
	}
	
	public List<UploadDTO> gameDetail(){
		return sqlSession.selectList("upload.gameDetail");
	}
	
	public List<UploadDTO> viewRecent(){
		return sqlSession.selectList("upload.viewRecent");
	}
	
	public List<UploadDTO> viewGenre(String category){
		return sqlSession.selectList("upload.viewGenre", category);
	}
}