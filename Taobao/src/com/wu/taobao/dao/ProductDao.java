package com.wu.taobao.dao;

import java.util.ArrayList;

import com.wu.taobao.bean.Product;

import android.R.integer;
import android.os.Handler;

public interface ProductDao {

	/**
	 * 显示所有
	 */
	ArrayList<Product> queryAll();

	/**
	 * 通过商品种类id查询
	 */
	ArrayList<Product> queryByCategory(int categoryId);

	/**
	 * 通过关键字查询
	 */

	ArrayList<Product> queryByKey(String str, Handler handler);
}
