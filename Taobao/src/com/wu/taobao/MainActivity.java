package com.wu.taobao;

import com.wu.taobao.fragment.AccountFragment;
import com.wu.taobao.fragment.CarFragment;
import com.wu.taobao.fragment.HomeFragment;

import android.R.drawable;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements OnClickListener {

	Fragment mCarFragment, mHomeFragment, mAccountFragment, mCurrentFragment;
	TextView mCar, mAccount, mHome;
	int mFlag = 1;
	SharedPreferences mPreferences;
	Editor mEditor;
	String mName, mPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mCarFragment = new CarFragment();
		mHomeFragment = new HomeFragment();
		mAccountFragment = new AccountFragment();

		mCar = (TextView) findViewById(R.id.guideCar);
		mAccount = (TextView) findViewById(R.id.guideAccount);
		mHome = (TextView) findViewById(R.id.guideHome);
		mPreferences = getSharedPreferences("User", 0);

		mEditor = mPreferences.edit();

		mName = mPreferences.getString("name", "");
		mPassword = mPreferences.getString("password", "");

		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragmentMain, mHomeFragment).commit();
		mCurrentFragment = mHomeFragment;

		if (getIntent().getIntExtra("flag", 1) == 2) {
			if (mName.equals("") && mPassword.equals("")) {
				Intent intent = new Intent(mContext, LoginActivity.class);
				intent.putExtra("stye", 1);
				startActivity(intent);
				finish();
			}
			changeFragment(mCar, R.drawable.guide_cart_on, mCarFragment, mFlag);

			mFlag = 2;

		} else if (getIntent().getIntExtra("flag", 1) == 3) {

			changeFragment(mAccount, R.drawable.guide_account_on,
					mAccountFragment, mFlag);
			mFlag = 3;

		}

		setListener();

	}

	private void setListener() {
		mAccount.setOnClickListener(this);
		mHome.setOnClickListener(this);
		mCar.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.guideHome:
			changeFragment((TextView) v, R.drawable.guide_home_on,
					mHomeFragment, mFlag);
			mFlag = 1;
			break;
		case R.id.guideCar:
			if (mName.equals("") && mPassword.equals("")) {
				Intent intent = new Intent(mContext, LoginActivity.class);
				intent.putExtra("stye", 1);
				startActivity(intent);
				finish();
			}
			changeFragment((TextView) v, R.drawable.guide_cart_on,
					mCarFragment, mFlag);
			mFlag = 2;
			break;
		case R.id.guideAccount:
			if (mName.equals("") && mPassword.equals("")) {

				Intent intent = new Intent(mContext, LoginActivity.class);
				intent.putExtra("stye", 2);
				startActivity(intent);
				finish();
			}
			changeFragment((TextView) v, R.drawable.guide_account_on,
					mAccountFragment, mFlag);
			mFlag = 3;
			break;

		default:
			break;
		}
	}

	private void changeFragment(TextView v, int drawableId, Fragment fragment,
			int flag) {

		Drawable drawable;
		boolean isSame = false;
		switch (flag) {
		case 1:
			mHome.setTextColor(getResources().getColor(R.color.black));
			drawable = getResources().getDrawable(R.drawable.guide_home_nm);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			mHome.setCompoundDrawables(null, drawable, null, null);

			getSupportFragmentManager().beginTransaction().hide(mHomeFragment)
					.commit();

			break;
		case 2:
			mCar.setTextColor(getResources().getColor(R.color.black));
			drawable = getResources().getDrawable(R.drawable.guide_cart_nm);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			mCar.setCompoundDrawables(null, drawable, null, null);
			if (!(fragment == mCarFragment)) {
				getSupportFragmentManager().beginTransaction()
						.remove(mCarFragment).commit();
			} else {
				isSame = true;
			}

			break;
		case 3:
			mAccount.setTextColor(getResources().getColor(R.color.black));
			drawable = getResources().getDrawable(R.drawable.guide_account_nm);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			mAccount.setCompoundDrawables(null, drawable, null, null);
			if (!(fragment == mAccountFragment)) {
				getSupportFragmentManager().beginTransaction()
						.remove(mAccountFragment).commit();
			} else {
				isSame = true;
			}

			break;

		default:
			break;
		}
		v.setTextColor(getResources().getColor(R.color.orange));
		drawable = getResources().getDrawable(drawableId);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		v.setCompoundDrawables(null, drawable, null, null);

		if (fragment == mHomeFragment) {
			getSupportFragmentManager().beginTransaction().show(mHomeFragment)
					.commit();
		} else if (!isSame) {

			getSupportFragmentManager().beginTransaction()
					.add(R.id.fragmentMain, fragment).commit();
		}
		mCurrentFragment = fragment;

	}



}
