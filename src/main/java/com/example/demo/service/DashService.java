package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.security.GetInfo;

@Service
public class DashService {
	
	@Autowired
	GetInfo getInfo;
	
	public String getFulldisk() {
		String fullDisk = getInfo.getDiskSpace()[0];
		String result = fullDisk;
		
		System.out.println("full disk : " + result + "GB");
		return result;
	}
	
	public String getUsabledisk() {
		String usableDisk = getInfo.getDiskSpace()[1];
		String result = usableDisk;
		
		System.out.println("usable disk : " + result + "GB");
		return result;
	}
	
	public String getHeapmemory() {
		String heapMemory = getInfo.getMemory()[0];
		String result = heapMemory;
		
		System.out.println("heap memory : " + result);
		return result;
	}
	
	public String getNonHeapmemory() {
		String nonheapMemory = getInfo.getMemory()[1];
		String result = nonheapMemory;
		
		System.out.println("non-heap memory : " + result);
		return result;
	}
}
