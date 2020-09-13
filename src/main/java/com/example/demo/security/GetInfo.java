package com.example.demo.security;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;

public class GetInfo {
	
		public String[] getDiskSpace() {
			File root = null;
			try {
				root = new File("/");
				String[] list = new String[2];
				list[0] = toGB(root.getTotalSpace()); 
				list[1] = toGB(root.getUsableSpace());
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	    
		public String toGB(long size) {
			return String.valueOf((int) (size / (1024 * 1024 * 1024)));
		}
		
		/*
		public String[] getCPUProcess() throws Exception {
			
			OperatingSystemMXBean osbean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();		
			String cpuUsage = String.format("%.2f", osbean.getSystemCpuLoad() * 100);
			String[] list = new String[2];
			list[0] = cpuUsage;
			
			return list;
		}
		*/
		
		public String[] getMemory() {
			MemoryMXBean membean = (MemoryMXBean) ManagementFactory.getMemoryMXBean();
			MemoryUsage heap = membean.getHeapMemoryUsage();
			MemoryUsage nonheap = membean.getNonHeapMemoryUsage();

			long heapUsed = heap.getUsed();
			long nonheapUsed = nonheap.getUsed();
			
			String[] list = new String[2];
			list[0] = String.valueOf(heapUsed);
			list[1] = String.valueOf(nonheapUsed);
			return list;
		}
}
