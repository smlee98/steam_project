package com.example.demo.dto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.multipart.MultipartFile;

public class PurchaseDetail {
	String id;
	int money;
	
	public PurchaseDetail(PurchaseDTO purchase) {
		this.money = purchase.getMoney();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "PurchaseDetail [id=" + id + ", money=" + money + "]";
	}
	
}
