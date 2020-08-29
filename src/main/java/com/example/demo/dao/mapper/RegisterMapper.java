package com.example.demo.dao.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.RegisterDTO;

@Mapper
public interface RegisterMapper {
	void joinUser(RegisterDTO registerDTO);
	RegisterDTO findByID(String id);
	RegisterDTO validateMember(HashMap<String, String> map);
}
