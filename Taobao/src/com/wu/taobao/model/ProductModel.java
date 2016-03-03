package com.wu.taobao.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.wu.taobao.bean.Product;
import com.wu.taobao.dao.ProductDao;
import com.wu.taobao.dao.ProductDesDao;

public class ProductModel implements ProductDao {

	ArrayList<Product> mProducts;
	HttpUtils mHttpUtils;
	Gson mGson;

	public ProductModel() {
		mHttpUtils = new HttpUtils();
		mGson = new Gson();

	}

	@Override
	public ArrayList<Product> queryAll() {

		return null;
	}

	@Override
	public ArrayList<Product> queryByCategory(int categoryId) {

		return null;
	}

	@Override
	public ArrayList<Product> queryByKey(String str,final Handler handler) {
		mProducts = new ArrayList<>();
		StringBuffer url = new StringBuffer(
				"http://115.28.152.201:8080/Taobao/product");
		url = url.append("?key=").append(str);
//		mHttpUtils.configResponseTextCharset("utf-8");
		mHttpUtils.send(HttpMethod.GET, url.toString(),
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {

						JSONArray jsonArray;
						
						

						try {
							jsonArray = new JSONArray(arg0.result);
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject object;

								object = jsonArray.getJSONObject(i);
								mProducts.add(new Product(object.getInt("id"),
										object.getString("name"), object
												.getString("pic"), object
												.getDouble("price"), object
												.getInt("categoryId"), object
												.getString("categoryName"),
										object.getInt("shopId"), object
												.getString("shopName")));
								
							}
							Message msg=new Message();
							msg.obj=mProducts;
							msg.what=1;
							handler.sendMessage(msg);
						} catch (JSONException e1) {

							e1.printStackTrace();
						}

						
					}
				});
		return mProducts;
	}

}
