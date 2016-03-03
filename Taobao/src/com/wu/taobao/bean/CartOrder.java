package com.wu.taobao.bean;

import android.R.integer;

//购物车里面的订单
public class CartOrder {

	public int id; // 购物车订单号
	public int shopId;// 店铺号
	public String shopName;// 店铺名

	public String pic;
	public int productId;// 商品id
	public String productName;// 商品名
	public double price;// 价格
	public int count;// 数量
	
	public int userId;

	public boolean isChecked;// 勾选或者没勾选

	public CartOrder(int id, int shopId, String shopName, int productId,
			String productName, double price, int count, boolean isChecked,String pic,int userId) {
		super();
		this.id = id;
		this.shopId = shopId;
		this.shopName = shopName;
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.count = count;
		this.isChecked = isChecked;
		this.pic=pic;
		this.userId=userId;
	}
	
	
}
