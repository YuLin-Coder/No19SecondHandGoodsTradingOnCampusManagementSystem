package com.ideabobo.model;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class Replay implements java.io.Serializable {

	// Fields
    private Integer id;
    private String pid;
    private String note;
    public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	private String uid;
    private String username;
    private String ndate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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