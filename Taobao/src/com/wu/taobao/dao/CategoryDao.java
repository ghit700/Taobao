package com.wu.taobao.dao;

import java.util.ArrayList;

import com.wu.taobao.bean.Category;

public interface CategoryDao {

	/**
	 * 查询所有的类别
	 * 
	 * @return
	 */
	ArrayList<Category> queryAll();

	/**
	 * 通过类别的id,查询类别的名称
	 * 
	 * @return
	 */
	Category queryById();
}
