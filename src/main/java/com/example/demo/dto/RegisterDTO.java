package com.example.demo.dto;

public class RegisterDTO {
	int number;
	String id;
	String password;
	String name;
	String gender;
	String address;
	String phone;
	String favorite;
	String role;
	String auth;
	
	int curPage;

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
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

	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
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

	@Override
	public String toString() {
		return "RegisterDTO [number=" + number + ", id=" + id + ", password=" + password + ", name=" + name
				+ ", gender=" + gender + ", address=" + address + ", phone=" + phone + ", favorite=" + favorite
				+ ", role=" + role + ", auth=" + auth + "]";
	}
}
