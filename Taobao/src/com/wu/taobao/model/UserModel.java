package com.wu.taobao.model;

import android.R.integer;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.wu.taobao.bean.User;
import com.wu.taobao.dao.UserDao;

public class UserModel implements UserDao {

	HttpUtils mUtils;
	Context mContext;
	Gson mGson;

	public UserModel(Context mContext) {
		super();
		this.mContext = mContext;
		mUtils = new HttpUtils();
		mGson = new Gson();
	}

	@Override
	public void register(User user, final Handler handler) {
		StringBuffer url = new StringBuffer(
				"http://115.28.152.201:8080/Taobao/register");
		url = url.append("?name=").append(user.name).append("&password=")
				.append(user.password);
		mUtils.send(HttpMethod.GET, url.toString(),
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String str = arg0.result;

						if (str.equals("0")) {
							handler.sendEmptyMessage(3);
						} else {

							handler.sendEmptyMessage(2);

						}
					}
				});

	}

	@Override
	public void login(User user, final Handler handler) {
		StringBuffer url = new StringBuffer(
				"http://115.28.152.201:8080/Taobao/login");
		url = url.append("?name=").append(user.name).append("&password=")
				.append(user.password);
		mUtils.send(HttpMethod.GET, url.toString(),
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String str = arg0.result;

						if (str.equals("0")) {
							handler.sendEmptyMessage(100);
						} else {
							Message msg = new Message();

							msg.arg1 = Integer.parseInt(str);
							msg.what = 1;
							handler.sendMessage(msg);
						}
					}
				});
	}

}
