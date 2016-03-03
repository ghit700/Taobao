package com.wu.taobao.fragment;

import java.util.ArrayList;

import android.R.array;
import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

import com.wu.taobao.MainActivity;
import com.wu.taobao.ProductActivity;
import com.wu.taobao.R;
import com.wu.taobao.adapter.CartAdapter;
import com.wu.taobao.adapter.CartAdapter.CartCallBack;
import com.wu.taobao.bean.CartOrder;
import com.wu.taobao.model.CartOrderModel;
import com.wu.taobao.model.OrderModel;

public class CarFragment extends Fragment implements CartCallBack,
		OnCheckedChangeListener, OnClickListener {

	CartAdapter mAdapter;
	ArrayList<CartOrder> mCartOrders;
	ListView mListView;
	TextView mCartPriceCount, mCartCount, mCartSetlle;
	OrderModel mOrderModel;
	Context mContext;
	CartOrderModel mCartOrderModel;
	CheckBox mCheckBox;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_car, container, false);

		mListView = (ListView) view.findViewById(R.id.list);
		mCartPriceCount = (TextView) view.findViewById(R.id.cartPriceCount);
		mContext = getActivity();
		mCheckBox = (CheckBox) view.findViewById(R.id.cartAllCheck);
		mCartCount = (TextView) view.findViewById(R.id.cartCount);
		mCartOrderModel = new CartOrderModel(mContext);
		mCartSetlle = (TextView) view.findViewById(R.id.cartSettle);
		mOrderModel = new OrderModel(mContext);
		mCartOrders = new ArrayList<>();

		mCartOrders = mCartOrderModel.query(mContext.getSharedPreferences(
				"User", 0).getInt("id", 0));

		mAdapter = new CartAdapter(mCartOrders, mContext, this);
		mListView.setAdapter(mAdapter);
		mCartCount.setText("购物车(" + mCartOrders.size() + ")");

		setListener();

		return view;
	}

	private void setListener() {
		mCheckBox.setOnCheckedChangeListener(this);
		mCartSetlle.setOnClickListener(this);

	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void delete(int id, final int position) {
		new AlertDialog.Builder(mContext).setTitle("你确定要删除吗?")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						ArrayList<CartOrder> arrayList = new ArrayList<>();
						arrayList.add(mCartOrders.get(position));
						mCartOrderModel.delete(arrayList);
						mCartOrders.remove(position);
						mAdapter.notifyDataSetChanged();
						mAdapter.sendMessageToActivity();
						mCartCount.setText("购物车(" + mCartOrders.size() + ")");

					}
				}).setNegativeButton("取消", null).create().show();

	}

	@Override
	public void countPrice(double price, int count) {

		mCartPriceCount.setText("$" + price);
		mCartSetlle.setText("结算(" + count + ")");
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		mAdapter.setData(isChecked);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cartSettle:
			new AlertDialog.Builder(mContext)
					.setMessage("确定要结算吗?")
					.setNegativeButton("取消", null)
					.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									ArrayList<CartOrder> cartorders = new ArrayList<>();

									for (int i = 0, j = 0; i < mCartOrders
											.size(); i++) {

										if (mCartOrders.get(i).isChecked) {
											mOrderModel.insert(mCartOrders
													.get(i));
											cartorders.add(mCartOrders.get(i));
											mCartOrders.remove(i--);
										}
									}

									mCartOrderModel.delete(cartorders);
									mCartCount.setText("购物车("
											+ mCartOrders.size() + ")");
									mCartSetlle.setText("结算(0)");
									mCheckBox.setChecked(false);
									mAdapter.notifyDataSetChanged();
								}
							}).create().show();
			break;

		default:
			break;
		}
	}

}
