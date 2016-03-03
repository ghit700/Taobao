package com.wu.taobao.fragment;

import java.io.File;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.wu.taobao.OrderActivity;
import com.wu.taobao.R;
import com.wu.taobao.UserActivity;
import com.wu.taobao.manager.NetManager;
import com.wu.taobao.view.CircleImageView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountFragment extends Fragment implements OnClickListener {

	TextView mName, mAllOrder;
	com.wu.taobao.view.CircleImageView mUserImg;
	Context mContext;
	private String mUserName;
	private String mUserIconName;

	private HttpUtils httpUtils;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_account, container,
				false);
		mContext = getActivity();
		mName = (TextView) view.findViewById(R.id.name);
		mAllOrder = (TextView) view.findViewById(R.id.allOrder);
		mUserImg = (com.wu.taobao.view.CircleImageView) view
				.findViewById(R.id.userImg);

		mUserName = getActivity().getSharedPreferences("User", 0).getString(
				"name", "");
		mUserIconName = mUserName.hashCode() + ".jpg";
		httpUtils = new HttpUtils();

		NetManager netManager = new NetManager(mContext);

		if (netManager.hasNetworkConnection()) {
			httpUtils.download("http://115.28.152.201:8080/hello/temp/"
					+ mUserIconName, mContext.getFilesDir() + "/"
					+ mUserIconName, new RequestCallBack<File>() {

				@Override
				public void onSuccess(ResponseInfo<File> arg0) {
					recyclerBitmap(mUserImg);
					Log.i("wu", "success");
					changeIconImg();
				}

				@Override
				public void onFailure(HttpException arg0, String arg1) {
				}
			});
		}

		mName.setText(mUserName);

		setListener();
		return view;
	}
	
	@Override
	public void onResume() {
		
		super.onResume();
		changeIconImg();
	}

	public void changeIconImg() {

		
		recyclerBitmap(mUserImg);
		if (new File(mContext.getFilesDir() + "/" + mUserIconName).exists()) {
			Bitmap bitmap = BitmapFactory.decodeFile(mContext.getFilesDir()
					+ "/" + mUserIconName);
			mUserImg.setBitmap(bitmap);
			mUserImg.invalidate();
		}
	}

	private void recyclerBitmap(ImageView mUserImg) {

		
		Bitmap bitmap = ((CircleImageView) mUserImg).getBitmap();
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
		}
	}

	private void setListener() {
		mUserImg.setOnClickListener(this);
		mAllOrder.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.userImg:
			startActivity(new Intent(mContext, UserActivity.class));
			break;
		case R.id.allOrder:
			startActivity(new Intent(mContext, OrderActivity.class));
			break;

		default:
			break;
		}
	}

}
