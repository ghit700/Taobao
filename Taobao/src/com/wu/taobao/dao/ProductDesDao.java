package com.wu.taobao.dao;

import android.os.Handler;

import com.wu.taobao.bean.ProductDescription;

public interface ProductDesDao {

	/**
	 * 通过商品的Id查询具体的商品详情界面
	 * @param productId
	 * @return
	 */
	

	ProductDescription queryDescription(int productId, Handler handler);

	
}
