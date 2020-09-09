package com.example.demo.dto;
import org.springframework.web.multipart.MultipartFile;

public class UploadDTO {
	int number;
	String orgfile;
	String newfile;
	String thumbnail;
	String name;
	String category;
	String version;
	int amount;
	String explain;
	
	private MultipartFile files;
	
	public UploadDTO(String orgfile, String thumbnail, String name, String category, String version, int amount,
			String explain, MultipartFile files) {
		this.orgfile = orgfile;
		this.thumbnail = thumbnail;
		this.name = name;
		this.category = category;
		this.version = version;
		this.amount = amount;
		this.explain = explain;
		this.files = files;
	}
	
	public UploadDTO(int number, String orgfile, String thumbnail, String name, String category, String version,
			int amount, String explain) {
		this.number = number;
		this.orgfile = orgfile;
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

	public String getOrgfile() {
		return orgfile;
	}

	public void setOrgfile(String orgfile) {
		this.orgfile = orgfile;
	}

	public String getNewfile() {
		return newfile;
	}

	public void setNewfile(String newfile) {
		this.newfile = newfile;
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

	public MultipartFile getFiles() {
		return files;
	}

	public void setFiles(MultipartFile files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return "UploadDTO [number=" + number + ", orgfile=" + orgfile + ", newfile=" + newfile + ", thumbnail="
				+ thumbnail + ", name=" + name + ", category=" + category + ", version=" + version + ", amount="
				+ amount + ", explain=" + explain + "]";
	}
	
}
