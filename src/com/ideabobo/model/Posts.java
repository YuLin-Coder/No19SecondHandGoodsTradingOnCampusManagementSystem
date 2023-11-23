package com.ideabobo.model;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class Posts implements java.io.Serializable {

	// Fields
    private Integer id;
    private String title;
    private String note;
    private String uid;
    private String username;
    private String ndate;
    private String img;
    private String type;
    
    
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNdate() {
		return ndate;
	}
	public void setNdate(String ndate) {
		this.ndate = ndate;
	}

   
}