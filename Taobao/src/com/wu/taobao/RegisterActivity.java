package com.wu.taobao;

import com.wu.taobao.bean.User;
import com.wu.taobao.model.UserModel;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends BaseActivity implements OnClickListener,
		TextWatcher {

	EditText mName, mPassword, mPasswordCheck;
	Button mRegisterBtn;
	TextView mBack;
	UserModel mUserModel;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 2:
				dismissDialog();
				finish();
				break;
			case 3:
				dismissDialog();
				new AlertDialog.Builder(mContext).setTitle("注册失败")
						.setMessage("用户名已存在").setPositiveButton("确认", null)
						.create().show();
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		mName = (EditText) findViewById(R.id.registerName);
		mPassword = (EditText) findViewById(R.id.registerPassword);
		mPasswordCheck = (EditText) findViewById(R.id.registerPasswordCheck);
		mRegisterBtn = (Button) findViewById(R.id.registerBtn);
		mBack = (TextView) findViewById(R.id.registerBack);
		mUserModel = new UserModel(mContext);

		setListener();
	}

	private void setListener() {
		mRegisterBtn.setOnClickListener(this);
		mBack.setOnClickListener(this);
		mName.addTextChangedListener(this);
		mPassword.addTextChangedListener(this);
		mPasswordCheck.addTextChangedListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.registerBtn:
			if (!mPassword.getText().toString()
					.equals(mPasswordCheck.getText().toString())) {
				new AlertDialog.Builder(mContext).setMessage("密码不一致")
						.setPositiveButton("确认", null).create().show();
			} else {
				showLoadingDialog("注册中");
				mUserModel.register(new User(mName.getText().toString(),
						mPassword.getText().toString()), mHandler);
			}

			break;
		case R.id.registerBack:
			// startActivity(new Intent(RegisterActivity.this,
			// LoginActivity.class));
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (!mName.getText().toString().equals("")
				&& !mPassword.getText().toString().equals("")
				&& !mPasswordCheck.getText().toString().equals("")) {
			mRegisterBtn.setTextColor(getResources().getColor(R.color.white));
		} else {
			mRegisterBtn.setTextColor(getResources().getColor(R.color.gray));
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

}
