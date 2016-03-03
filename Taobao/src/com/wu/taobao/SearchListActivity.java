package com.wu.taobao;

import java.util.ArrayList;

import com.wu.taobao.adapter.SearchListAdapter;
import com.wu.taobao.bean.Product;
import com.wu.taobao.model.ProductModel;

import android.app.Notification.Action;
import android.app.usage.UsageEvents.Event;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class SearchListActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener {

	ListView mListView;
	SearchListAdapter mAdapter;
	ArrayList<Product> mProducts;
	EditText mEditText;
	ImageView mBack;
	ProductModel mProductModel;
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				mProducts = (ArrayList<Product>) msg.obj;

				mAdapter = new SearchListAdapter(mProducts, mContext);
				mListView.setAdapter(mAdapter);

			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchlist);

		String key = "";
		if (getIntent() != null) {
			key = getIntent().getStringExtra("key");
		}
		mProductModel = new ProductModel();
		mProductModel.queryByKey(key, mHandler);

		mEditText = (EditText) findViewById(R.id.search);
		mBack = (ImageView) findViewById(R.id.searchBack);
		mProducts = new ArrayList<>();
		mListView = (ListView) findViewById(R.id.searchList);

		mEditText.setText(key);
		
		mEditText.setFocusable(false);
		mEditText.setFocusableInTouchMode(false);

		setListener();

	}

	private void setListener() {
		mEditText.setOnClickListener(this);
		mBack.setOnClickListener(this);
		mListView.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.search:
			intent = new Intent(mContext, SearchActivity.class);
			intent.putExtra("str", mEditText.getText().toString());
			startActivity(intent);
			finish();

			break;
		case R.id.searchBack:
			intent = new Intent(mContext, SearchActivity.class);
			intent.putExtra("str", mEditText.getText().toString());
			startActivity(intent);
			finish();
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(mContext, ProductActivity.class);
		intent.putExtra("productId", mProducts.get(position).id);
		startActivity(intent);
	}

	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			Intent intent = new Intent(mContext, SearchActivity.class);
			intent.putExtra("str", mEditText.getText().toString());
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
		return true;
	};

}
