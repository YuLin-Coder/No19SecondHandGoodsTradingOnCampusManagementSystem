package com.ideabobo.model;

/**
 * Vip entity. @author MyEclipse Persistence Tools
 */

public class Vip implements java.io.Serializable {

	// Fields

	private Integer id;
	private String qq;
	private String sex;
	private String mobile;
	private String openid;
	private String vname;
	private String wname;

	// Constructors

	/** default constructor */
	public Vip() {
	}

	/** full constructor */
	public Vip(String qq, String sex, String mobile, String openid,
			String vname, String wname) {
		this.qq = qq;
		this.sex = sex;
		this.mobile = mobile;
		this.openid = openid;
		this.vname = vname;
		this.wname = wname;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getVname() {
		return this.vname;
	}

	public void setVname(String vname) {
		this.vname = vname;
	}

	public String getWname() {
		return this.wname;
	}

	public void setWname(String wname) {
		this.wname = wname;
	}

}