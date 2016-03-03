package com.wu.taobao.bean;

import java.util.ArrayList;

public class ProductDescription {

	public int id;
	public String name;
	public ArrayList<String> imgUrls;
	public String description;
	public double price;
	public int categoryId;
	public String categoryName;
	public int shopId;
	public String shopName;
	public ProductDescription(int id, String name, ArrayList<String> imgUrls,
			String description, double price, int categoryId,
			String categoryName, int shopId, String shopName) {
		super();
		this.id = id;
		this.name = name;
		this.imgUrls = imgUrls;
		this.description = description;
		this.price = price;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.shopId = shopId;
		this.shopName = shopName;
	}
	

}
