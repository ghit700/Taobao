package com.wu.taobao.tools;

import com.wu.taobao.R;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class LoadingDialog extends AlertDialog {

	Context mContext;
	TextView mLoadingText;
	String mMessage;
	protected LoadingDialog(Context context) {
		super(context);
		mContext=context;
	}

	public LoadingDialog(Context mContext, String mMessage) {
		super(mContext);
		this.mContext = mContext;
		this.mMessage = mMessage;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.dialog_loading);
		mLoadingText=(TextView)findViewById(R.id.loadingTv);
		setText(mMessage);
	}
	
	public void setText(String msg){
		mLoadingText.setText(msg);
	}

}
