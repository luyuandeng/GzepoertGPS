package com.gzeport.app.gps.pojo;

import java.math.BigDecimal;

/**
 * Account entity. @author MyEclipse Persistence Tools
 */

public class CarNo implements java.io.Serializable {

	// Fields

	private int id;
	private String plate;
	private String redkey;

	// Constructors

	/** default constructor */
	public CarNo() {
	}

	/** full constructor */
	public CarNo(int id,String plate,String redkey) {
		this.id = id;
		this.plate=plate;
		this.redkey=redkey;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getRedkey() {
		return redkey;
	}

	public void setRedkey(String redkey) {
		this.redkey = redkey;
	}


}