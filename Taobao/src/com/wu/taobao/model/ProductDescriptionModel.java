package com.wu.taobao.model;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.wu.taobao.bean.ProductDescription;
import com.wu.taobao.dao.ProductDesDao;

public class ProductDescriptionModel implements ProductDesDao {
	
	Context mContext;
	HttpUtils mHttpUtils;
	ProductDescription mProductDescription;

	public ProductDescriptionModel(Context mContext) {
		super();
		this.mContext = mContext;
		mHttpUtils=new HttpUtils();
	}


	@Override
	public ProductDescription queryDescription(int productId,final Handler handler) {
		StringBuffer url = new StringBuffer(
				"http://115.28.152.201:8080/Taobao/product");
		url = url.append("?id=").append(productId);
		
		mHttpUtils.send(HttpMethod.GET,url.toString(),new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Gson gson = new Gson();
				mProductDescription=gson.fromJson(arg0.result, ProductDescription.class);
				Message msg=new Message();
				msg.what=1;
				msg.obj=mProductDescription;
				handler.sendMessage(msg);
			}
		});
		return null;
	}

}
