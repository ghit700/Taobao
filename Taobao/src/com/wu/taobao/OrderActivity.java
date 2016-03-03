package com.wu.taobao;

import java.util.ArrayList;

import com.wu.taobao.adapter.OrderAdapter;
import com.wu.taobao.bean.Order;
import com.wu.taobao.model.OrderModel;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class OrderActivity extends BaseActivity implements OnClickListener {

	ListView mListView;
	TextView mOrderBack;
	ImageView mOrderSeach;
	ArrayList<Order> mOrders;
	OrderModel mOrderModel;
	OrderAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);

		mListView = (ListView) findViewById(R.id.orderLv);
		mOrderBack = (TextView) findViewById(R.id.orderBack);
		mOrderSeach = (ImageView) findViewById(R.id.orderSearch);
		mOrderModel = new OrderModel(mContext);
		mOrders = mOrderModel.queryAll(getSharedPreferences("User", 0).getInt(
				"id", 0));
		mAdapter = new OrderAdapter(mOrders, mContext);

		mListView.setAdapter(mAdapter);
		
		setListener();

	}

	private void setListener() {
		mOrderBack.setOnClickListener(this);
		mOrderSeach.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.orderBack:
			finish();
			break;
		case R.id.orderSearch:
			
			break;

		default:
			break;
		}
	}
}
