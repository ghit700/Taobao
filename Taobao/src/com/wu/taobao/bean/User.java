package com.wu.taobao.bean;

import android.R.integer;

/**
 * 用户
 * 
 * @author Administrator
 * 
 */
public class User {

	public String name;
	public String password;
	public int id;

	public User(String name, String password, int id) {
		super();
		this.name = name;
		this.password = password;
		this.id = id;
	}

	public User(String name, String password) {
		super();

		this.name = name;
		this.password = password;
	}

}
