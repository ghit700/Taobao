package com.wu.taobao.bean;

import android.R.integer;

public class Product {
	public int id;
	public String name;
	public String pic;
	public double price;
	public int categoryId;
	public String categoryName;
	public int shopId;
	public String shopName;
	
	public Product(int id, String name, String pic, double price,
			int categoryId, String categoryName, int shopId, String shopName) {
		super();
		this.id = id;
		this.name = name;
		this.pic = pic;
		this.price = price;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.shopId = shopId;
		this.shopName = shopName;
	}
	

	

}
