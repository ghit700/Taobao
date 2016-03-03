package com.wu.taobao;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import com.wu.taobao.tools.LoadingDialog;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class BaseActivity extends FragmentActivity {
	public Context mContext;
	
	LoadingDialog mLoadingDialog;
	SharedPreferences mPreferences;
	int mUserId;
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				dismissDialog();
				setResult(1);
				finish();
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			default:
				break;
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mContext = this;
		mPreferences=getSharedPreferences("User", 0);
		mUserId=mPreferences.getInt("id", 0);
	}

	public void showLoadingDialog(String text) {
		mLoadingDialog = new LoadingDialog(mContext, text);
		Window window = mLoadingDialog.getWindow();
		window.setGravity(Gravity.CENTER);
		mLoadingDialog.show();
	}

	public void dismissDialog() {
		if (mLoadingDialog != null) {
			mLoadingDialog.dismiss();
		}
	}

}
