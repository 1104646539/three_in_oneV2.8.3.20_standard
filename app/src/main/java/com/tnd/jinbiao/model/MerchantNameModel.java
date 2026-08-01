package com.tnd.jinbiao.model;

import com.lidroid.xutils.db.annotation.Transient;

public class MerchantNameModel {

	private int id;
	private String name;

	@Transient
	public boolean is_check;

	public boolean getIs_check() {
		return is_check;
	}

	public void setIs_check(boolean is_check) {
		this.is_check = is_check;
	}

	public MerchantNameModel(String name) {
		this.name = name;
	}

	public MerchantNameModel() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
