package com.cafe24.jblog.vo;

import org.springframework.web.multipart.MultipartFile;

public class BlogVO {
	private Long no;
	private String name;
	private String logo;
	
	public BlogVO() {}

	public BlogVO(Long no, String name, String logo) {
		super();
		this.no = no;
		this.name = name;
		this.logo = logo;
	}

	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Override
	public String toString() {
		return "LogoVO [no=" + no + ", name=" + name + ", logo=" + logo + "]";
	}
}
