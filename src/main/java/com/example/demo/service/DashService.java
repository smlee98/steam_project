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
	
	public String getFullMem() {
		String fullMem = getInfo.getMemory()[0];
		String result = fullMem;
		
		return result;
	}
	
	public String getUsableMem() {
		String usableMem = getInfo.getMemory()[1];
		String result = usableMem;
		
		return result;
	}
	
	public int getGameCount() {
		int gameCount = upDAO.getGameCount();
		int result = gameCount;
		
		return result;
	}
	
	public String[] getAvgData() {
		String avgDisk = getInfo.getDiskSpace()[2];
		String avgCpu = getInfo.getCPUProcess()[1];
		String avgMem = getInfo.getMemory()[2];
		
		String[] list = new String[3];
		list[0] = avgDisk;
		list[1] = avgCpu;
		list[2] = avgMem;
		
		return list;
	}
}
