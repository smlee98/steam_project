package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UploadDAO;
import com.example.demo.security.GetInfo;

@Service
public class DashService {
	
	@Autowired
	GetInfo getInfo;
	@Autowired
	UploadDAO upDAO;
	
	public String getFulldisk() {
		String fullDisk = getInfo.getDiskSpace()[0];
		String result = fullDisk;
		
		return result;
	}
	
	public String getUsabledisk() {
		String usableDisk = getInfo.getDiskSpace()[1];
		String result = usableDisk;
		
		return result;
	}
	
	public String getCpuprocess() {
		String cpuProcess = getInfo.getCPUProcess()[0];
		String result = cpuProcess;
		
		return result;
	}
	
	public String getHeapmemory() {
		String heapMemory = getInfo.getMemory()[0];
		String result = heapMemory;
		
		return result;
	}
	
	public String getNonHeapmemory() {
		String nonheapMemory = getInfo.getMemory()[1];
		String result = nonheapMemory;
		
		return result;
	}
	
	public int getGameCount() {
		int gameCount = upDAO.getGameCount();
		int result = gameCount;
		
		return result;
	}
}
