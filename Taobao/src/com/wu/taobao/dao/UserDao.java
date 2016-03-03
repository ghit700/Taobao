package com.wu.taobao.dao;

import android.os.Handler;

import com.wu.taobao.bean.User;

public interface UserDao {

	/**
	 * 注册
	 * 
	 * @param user
	 */

	/**
	 * 用户登录
	 * 
	 * @param user
	 */

	void register(User user, Handler handler);

	void login(User user, Handler handler);

}
