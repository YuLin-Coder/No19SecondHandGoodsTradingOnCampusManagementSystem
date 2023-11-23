package com.ideabobo.model;

/**
 * Dingzuo entity. @author MyEclipse Persistence Tools
 */

public class Dingzuo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String openid;
	private String username;
	private String renshu;
	private String xingming;
	private String shouji;
	private String shijian;
	private String todate;
	private String beizhu;
	private String shopid;
	private String shopname;
	private String ndate;

	// Constructors

	/** default constructor */
	public Dingzuo() {
	}

	/** full constructor */
	public Dingzuo(String openid, String username, String renshu,
			String xingming, String shouji, String shijian, String todate,
			String beizhu, String shopid, String shopname, String ndate) {
		this.openid = openid;
		this.username = username;
		this.renshu = renshu;
		this.xingming = xingming;
		this.shouji = shouji;
		this.shijian = shijian;
		this.todate = todate;
		this.beizhu = beizhu;
		this.shopid = shopid;
		this.shopname = shopname;
		this.ndate = ndate;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRenshu() {
		return this.renshu;
	}

	public void setRenshu(String renshu) {
		this.renshu = renshu;
	}

	public String getXingming() {
		return this.xingming;
	}

	public void setXingming(String xingming) {
		this.xingming = xingming;
	}

	public String getShouji() {
		return this.shouji;
	}

	public void setShouji(String shouji) {
		this.shouji = shouji;
	}

	public String getShijian() {
		return this.shijian;
	}

	public void setShijian(String shijian) {
		this.shijian = shijian;
	}

	public String getTodate() {
		return this.todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

	public String getBeizhu() {
		return this.beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	public String getShopid() {
		return this.shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid;
	}

	public String getShopname() {
		return this.shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getNdate() {
		return this.ndate;
	}

	public void setNdate(String ndate) {
		this.ndate = ndate;
	}

}