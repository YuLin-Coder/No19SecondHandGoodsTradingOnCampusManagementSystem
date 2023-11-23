package com.ideabobo.model;

/**
 * Shop entity. @author MyEclipse Persistence Tools
 */

public class Shop implements java.io.Serializable {

	// Fields

	private Integer id;
	private String sname;
	private String img;
	private String note;
	private String address;
	private String longitude;
	private String latitude;
	private String tel;
	private String ownid;
    private String passwd;

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    // Constructors

	public String getOwnid() {
		return ownid;
	}

	public void setOwnid(String ownid) {
		this.ownid = ownid;
	}

	/** default constructor */
	public Shop() {
	}

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    /** full constructor */


	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}