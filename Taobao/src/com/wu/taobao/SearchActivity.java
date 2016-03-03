package com.wu.taobao;

import java.util.ArrayList;

import com.wu.taobao.model.SearchHistoryModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SearchActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener, OnItemLongClickListener {

	EditText mSearch;
	Button mSearchBtn, mClearHistoryBtn;;
	ImageView mBack;
	ListView mListView;
	ArrayAdapter<String> mAdapter;
	ArrayList<String> mArrayList;
	SearchHistoryModel mHistory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		String str = "";
		if (getIntent() != null) {
			str = getIntent().getStringExtra("str");
		}

		mSearch = (EditText) findViewById(R.id.search);
		mHistory = new SearchHistoryModel(mContext);
		mSearchBtn = (Button) findViewById(R.id.searchBtn);
		mClearHistoryBtn = (Button) findViewById(R.id.clearHistoryBtn);
	
		mBack = (ImageView) findViewById(R.id.searchBack);
		mListView = (ListView) findViewById(R.id.searchHistoryList);

		mSearch.setText(str);

		mSearch.setFocusable(true); // 用键盘是否能获得焦点
		mSearch.setFocusableInTouchMode(true); // 触摸是否能获得焦点
		mSearch.requestFocus(); // 获取焦点
		InputMethodManager inputManager = (InputMethodManager) mSearch.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		mArrayList = new ArrayList<>();
		mArrayList = mHistory.queryAll();
		mAdapter = new ArrayAdapter<>(mContext,
				android.R.layout.simple_list_item_1, mArrayList);

		mListView.setAdapter(mAdapter);
		setListViewHeightBasedOnChildren(mListView);

		setListenner();

	}

	private void setListenner() {

		mBack.setOnClickListener(this);
		mSearchBtn.setOnClickListener(this);
	
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this);
		mClearHistoryBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.searchBack:
			finish();
			break;
		case R.id.searchBtn:
			if (!mSearch.getText().toString().equals("")) {
				mHistory.insert(mSearch.getText().toString());

			}else {
				mSearch.setText("Haier");
			}
			Intent intent = new Intent(mContext, SearchListActivity.class);
			intent.putExtra("key", mSearch.getText().toString());
			startActivity(intent);
			finish();
			break;
		case R.id.clearHistoryBtn:
			mArrayList.clear();
			mAdapter.notifyDataSetChanged();
			mHistory.deleteAll();
			setListViewHeightBasedOnChildren(mListView);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			final int position, long id) {
		new AlertDialog.Builder(mContext).setMessage("是否删除这条记录")
				.setPositiveButton("确认", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mHistory.delete(mArrayList.get(position));
						mArrayList.remove(position);
						mAdapter.notifyDataSetChanged();
					}
				}).setNegativeButton("取消", null).create().show();
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mHistory.update(mArrayList.get(position));
		Intent intent = new Intent(mContext, SearchListActivity.class);
		intent.putExtra("key", mArrayList.get(position));
		startActivity(intent);
		finish();

	}

	public void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			// listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			// 计算子项View 的宽高
			listItem.measure(0, 0);
			// 统计所有子项的总高度
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

}
