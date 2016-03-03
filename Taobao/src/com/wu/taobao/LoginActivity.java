package com.wu.taobao;

import java.net.NetPermission;

import com.wu.taobao.bean.User;
import com.wu.taobao.model.UserModel;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends BaseActivity implements OnClickListener,
		TextWatcher {

	EditText mName, mPassword;
	Button mLoginBtn, mRegisterBtn;
	TextView mBack;
	UserModel mUserModel;
	int mStye;
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				dismissDialog();

				Editor mEditor = getSharedPreferences("User", 0).edit();

				mEditor.putString("name", mName.getText().toString());
				mEditor.putString("password", mPassword.getText().toString());
				mEditor.putInt("id", msg.arg1);
				mEditor.commit();

				Intent intent = new Intent(mContext, MainActivity.class);
				if (mStye == 1) {
					intent.putExtra("flag", 2);
					startActivity(intent);
				} else if (mStye == 2) {
					intent.putExtra("flag", 3);
					startActivity(intent);
				} else if (mStye == 3) {
					setResult(1);
					finish();
				} else {
					startActivity(intent);
				}
				finish();
				break;
			case 100:
				dismissDialog();
				new AlertDialog.Builder(mContext).setTitle("登录失败")
						.setMessage("用户名或密码错误").setNegativeButton("确认", null)
						.create().show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mName = (EditText) findViewById(R.id.loginName);
		mPassword = (EditText) findViewById(R.id.loginPassword);
		mLoginBtn = (Button) findViewById(R.id.loginBtn);
		mBack = (TextView) findViewById(R.id.loginBack);
		mRegisterBtn = (Button) findViewById(R.id.register);
		mUserModel = new UserModel(mContext);
		if (getIntent() != null) {
			mStye = getIntent().getIntExtra("stye", 0);
		}

		setListener();

	}

	private void setListener() {
		mLoginBtn.setOnClickListener(this);
		mBack.setOnClickListener(this);
		mRegisterBtn.setOnClickListener(this);
		mPassword.addTextChangedListener(this);
		mName.addTextChangedListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.loginBtn:
			showLoadingDialog("登录中");
			mUserModel.login(new User(mName.getText().toString(), mPassword
					.getText().toString()), mHandler);
			break;
		case R.id.loginBack:
			if (mStye == 3) {
				finish();
			} else {
				startActivity(new Intent(LoginActivity.this, MainActivity.class));
				finish();
			}

			break;
		case R.id.register:
			startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (mStye == 3) {
				finish();
			} else {
				startActivity(new Intent(LoginActivity.this, MainActivity.class));
				finish();
			}
			break;

		default:
			break;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (!mName.getText().toString().equals("")
				&& !mPassword.getText().toString().equals("")) {
			mLoginBtn.setTextColor(getResources().getColor(R.color.white));
		} else {
			mLoginBtn.setTextColor(getResources().getColor(R.color.gray));
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

}
