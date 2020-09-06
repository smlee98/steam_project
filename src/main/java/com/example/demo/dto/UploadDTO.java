package com.example.demo.dto;

public class UploadDTO {
	int number;
	String file;
	String thumbnail;
	String name;
	String category;
	String version;
	int amount;
	String explain;
	
	public UploadDTO(String file, String thumbnail, String name, String category, String version, int amount,
			String explain) {
		this.file = file;
		this.thumbnail = thumbnail;
		this.name = name;
		this.category = category;
		this.version = version;
		this.amount = amount;
		this.explain = explain;
	}
	
	public UploadDTO(int number, String file, String thumbnail, String name, String category, String version,
			int amount, String explain) {
		this.number = number;
		this.file = file;
		this.thumbnail = thumbnail;
		this.name = name;
		this.category = category;
		this.version = version;
		this.amount = amount;
		this.explain = explain;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	
	@Override
	public String toString() {
		return "UploadDTO [number=" + number + ", file=" + file + ", thumbnail=" + thumbnail + ", name=" + name
				+ ", category=" + category + ", version=" + version + ", amount=" + amount + ", explain=" + explain
				+ "]";
	}
	
	
}
