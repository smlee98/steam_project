package com.example.demo.security;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import com.sun.management.OperatingSystemMXBean;

public class GetInfo {
	
		public String toGB(long size) {
			return String.valueOf((int) (size / (1024 * 1024 * 1024)));
		}
	
		public String[] getDiskSpace() {
			File root = null;
			try {
				root = new File("/");
				String avgDisk = String.valueOf(100 - (int)(((double)root.getUsableSpace() / (double)root.getTotalSpace()) * 100));
				String[] list = new String[3];
				list[0] = toGB(root.getTotalSpace()); 
				list[1] = toGB(root.getUsableSpace());
				list[2] = avgDisk;
				
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public String[] getCPUProcess() {
			
			OperatingSystemMXBean osbean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();		
			String cpuUsage = String.format("%.2f", osbean.getSystemCpuLoad() * 100);
			String avgCPU = String.valueOf((int)(osbean.getSystemCpuLoad() * 100));
			String[] list = new String[2];
			list[0] = cpuUsage;
			list[1] = avgCPU;			
			
			return list;
		}
		
		public String[] getMemory() {
			OperatingSystemMXBean osbean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
			String usableMem = String.format("%.2f", (double)osbean.getFreePhysicalMemorySize() / (1024*1024*1024) );
			String fullMem = String.format("%.2f", (double)osbean.getTotalPhysicalMemorySize() / (1024*1024*1024) );
			String avgMem = String.valueOf((int)(((double)osbean.getFreePhysicalMemorySize() / (double)osbean.getTotalPhysicalMemorySize()) * 100));
			
			String[] list = new String[3];
			list[0] = fullMem;
			list[1] = usableMem;
			list[2] = avgMem;
			
			return list;
		}
}
