package com.wu.taobao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.wu.taobao.manager.NetManager;
import com.wu.taobao.view.CircleImageView;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UserActivity extends BaseActivity implements OnClickListener {

	TextView mUserBack;
	com.wu.taobao.view.CircleImageView mUserImg;
	Button mExitBtn;
	RelativeLayout mUserPassword, mUserGet;

	private String mUserName;
	private String mUserIconName;

	private HttpUtils httpUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);

		mUserBack = (TextView) findViewById(R.id.userBack);
		mUserImg = (com.wu.taobao.view.CircleImageView) findViewById(R.id.userImg);
		mExitBtn = (Button) findViewById(R.id.exit);
		mUserPassword = (RelativeLayout) findViewById(R.id.userPassword);
		mUserGet = (RelativeLayout) findViewById(R.id.userGet);

		mUserName = mContext.getSharedPreferences("User", 0).getString("name",
				"");
		mUserIconName = mUserName.hashCode() + ".jpg";
		httpUtils = new HttpUtils();

		if (new File(mContext.getFilesDir() + "/" + mUserIconName).exists()) {
			Bitmap bitmap = BitmapFactory.decodeFile(mContext.getFilesDir()
					+ "/" + mUserIconName);
			mUserImg.setBitmap(bitmap);

		}

		setListener();

	}

	private void setListener() {
		mUserBack.setOnClickListener(this);
		mUserPassword.setOnClickListener(this);
		mUserGet.setOnClickListener(this);
		mExitBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.userBack:
			finish();
			break;
		case R.id.userPassword:

			break;
		case R.id.userGet:
			showMyDialog();
			break;
		case R.id.exit:
			mContext.getSharedPreferences("User", 0).edit().clear().commit();
			startActivity(new Intent(mContext, LoginActivity.class));
			break;
		default:
			break;
		}

	}

	private void showMyDialog() {

		final AlertDialog dialog = new AlertDialog.Builder(mContext).create();

		View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_user,
				null);

		LinearLayout pic = (LinearLayout) view.findViewById(R.id.pic);
		LinearLayout camera = (LinearLayout) view.findViewById(R.id.camera);

		pic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
				intent.setDataAndType(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent, 1);
				dialog.dismiss();

			}
		});

		camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				startActivityForResult(intent, 2);
				dialog.dismiss();
			}
		});

		dialog.setView(view);

		dialog.show();

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		if (arg2 != null) {
			if (arg0 == 1) {

				Uri uri = arg2.getData();

				Bitmap bitmap = null;
				try {
					bitmap = MediaStore.Images.Media.getBitmap(
							getContentResolver(), uri);

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				recyclerBitmap(mUserImg);

				Bitmap bitmap2 = Bitmap
						.createScaledBitmap(bitmap, 70, 70, false);

				mUserImg.setBitmap(bitmap2);
				uploadImg(bitmap2);

			} else if (arg0 == 2) {

				Bitmap bitmap = (Bitmap) arg2.getExtras().get("data");

				recyclerBitmap(mUserImg);

				mUserImg.setBitmap(bitmap);
				uploadImg(bitmap);
			}
		}

	}

	private void uploadImg(Bitmap bitmap) {
		File file = new File(mContext.getFilesDir() + "/" + mUserIconName);

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

		uploadNet(file);
	}

	private void uploadNet(File file) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("file", file);
		showLoadingDialog("上传头像中");
		httpUtils.send(HttpMethod.POST,
				"http://115.28.152.201:8080/hello/hello", params,
				new RequestCallBack<String>() {

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {

						super.onLoading(total, current, isUploading);
						if (!isUploading) {
							dismissDialog();
						}

					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		recyclerBitmap(mUserImg);
	}

	private void recyclerBitmap(ImageView mUserImg) {

		Bitmap bitmap = ((CircleImageView) mUserImg).getBitmap();
		if (bitmap != null && !bitmap.isRecycled()) {

			bitmap.recycle();
		}
	}

}
