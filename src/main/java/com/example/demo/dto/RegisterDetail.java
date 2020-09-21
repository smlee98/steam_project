package com.example.demo.dto;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class RegisterDetail extends User {
	
	private static final long serialVersionUID = 6585900015829992304L;
	String id;
	String password;
	String name;
	String gender;
	String address;
	String phone;
	String favorite;
	String role;
	String auth;
	
	public RegisterDetail(RegisterDTO user) {
		super(
				user.getId(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole())
			);
		this.id = user.getId();
		this.password = user.getPassword();
		this.name = user.getName();
		this.gender = user.getGender();
		this.address = user.getAddress();
		this.phone = user.getPhone();
		this.favorite = user.getFavorite();
		this.role = user.getRole();
		this.auth = user.getAuth();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFavorite() {
		return favorite;
	}

	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	@Override
	public String toString() {
		return "RegisterDetail [id=" + id + ", password=" + password + ", name=" + name + ", gender=" + gender
				+ ", address=" + address + ", phone=" + phone + ", favorite=" + favorite + ", role=" + role + "]";
	}
	
}
