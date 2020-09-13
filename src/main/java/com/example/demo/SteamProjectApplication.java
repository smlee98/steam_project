package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.demo.security.GetInfo;

@SpringBootApplication
public class SteamProjectApplication extends GetInfo{

	public static void main(String[] args) {
		SpringApplication.run(SteamProjectApplication.class, args);
	}

}
