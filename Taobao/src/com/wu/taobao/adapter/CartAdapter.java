package com.wu.taobao.adapter;

import java.util.ArrayList;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.wu.taobao.ProductActivity;
import com.wu.taobao.R;
import com.wu.taobao.bean.CartOrder;
import com.wu.taobao.model.CartOrderModel;

public class CartAdapter extends BaseAdapter {

	ArrayList<CartOrder> mCartOrders;
	Context mContext;
	CartCallBack mCartCallBack;
	CartOrderModel mCartOrderModel;

	double mPrice = 0;

	public interface CartCallBack {
		void delete(int id, int position);

		void countPrice(double price, int count);
	}

	public CartAdapter(ArrayList<CartOrder> mCartOrders, Context mContext,
			CartCallBack mCartCallBack) {
		super();
		this.mCartOrders = mCartOrders;
		this.mContext = mContext;
		this.mCartCallBack = mCartCallBack;
		mCartOrderModel = new CartOrderModel(mContext);

	}

	@Override
	public int getCount() {

		return mCartOrders.size();
	}

	@Override
	public Object getItem(int position) {

		return mCartOrders.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	class ViewHolder {

		TextView add, sub, count, edit, listShopName, productName,
				productPrice, productCount, delete;
		LinearLayout ll2;
		RelativeLayout ll1;
		CheckBox listCheck;
		ImageView listShopImg, ProductImg;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {


		final TextView count;
		TextView add, sub;
		final TextView edit;
		TextView listShopName, productName, productPrice;
		final TextView productCount;
		TextView delete;
		final LinearLayout ll2;
		final RelativeLayout ll1;
		CheckBox listCheck;
		ImageView listShopImg, ProductImg;

		View view = LayoutInflater.from(mContext).inflate(R.layout.cart_item,
				null);
		edit = (TextView) view.findViewById(R.id.listEdit);
		ll1 = (RelativeLayout) view.findViewById(R.id.ll1);
		ll2 = (LinearLayout) view.findViewById(R.id.ll10);
		add = (TextView) view.findViewById(R.id.add);
		count = (TextView) view.findViewById(R.id.count);
		sub = (TextView) view.findViewById(R.id.sub);
		listShopName = (TextView) view.findViewById(R.id.listShopName);
		productName = (TextView) view.findViewById(R.id.productName);
		productCount = (TextView) view.findViewById(R.id.productCount);
		productPrice = (TextView) view.findViewById(R.id.productPrice);
		delete = (TextView) view.findViewById(R.id.delete);
		listCheck = (CheckBox) view.findViewById(R.id.listCheck);
		listShopImg = (ImageView) view.findViewById(R.id.listShopImg);
		ProductImg = (ImageView) view.findViewById(R.id.productImg);

		final CartOrder cartOrder = mCartOrders.get(position);

		BitmapUtils bitmapUtils = new BitmapUtils(mContext);
		bitmapUtils.display(ProductImg, cartOrder.pic);

		productName.setText(cartOrder.productName);
		listShopName.setText(cartOrder.shopName);
		productCount.setText("x" + cartOrder.count);
		count.setText(String.valueOf(cartOrder.count));
		productPrice.setText("$ " + cartOrder.price);

		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int count1 = mCartOrders.get(position).count;
				mCartOrders.get(position).count++;
				count1++;
				count.setText(String.valueOf(count1));
				productCount.setText("x" + count1);
			}
		});
		sub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int count1 = mCartOrders.get(position).count;
				if (count1 > 1) {
					mCartOrders.get(position).count--;
					count1--;
					count.setText(String.valueOf(count1));
					productCount.setText("x" + count1);
				} else {
					Toast.makeText(mContext, "宝贝已经不能再减少了", 1).show();
				}

			}
		});

		edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (edit.getText().toString().equals("编辑")) {
					ll1.setVisibility(View.GONE);
					ll2.setVisibility(View.VISIBLE);
					edit.setText("完成");
				} else {
					mCartOrderModel.updateCount(
							mCartOrders.get(position).productId,
							mCartOrders.get(position).count);
					ll1.setVisibility(View.VISIBLE);
					ll2.setVisibility(View.GONE);
					edit.setText("编辑");
				}

			}
		});

		listCheck.setChecked(mCartOrders.get(position).isChecked);
		listCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				mCartOrders.get(position).isChecked = isChecked;
				sendMessageToActivity();
			}
		});

		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCartCallBack.delete(cartOrder.id, position);
				// ArrayList<CartOrder> arrayList = new ArrayList<>();
				// arrayList.add(mCartOrders.get(position));
				// mCartOrderModel.delete(arrayList);
				// mCartOrders.clear();
				// mCartOrders=mCartOrderModel.query();
				// notifyDataSetChanged();
			}
		});

		ll1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, ProductActivity.class);
				intent.putExtra("productId",
						mCartOrders.get(position).productId);
				mContext.startActivity(intent);
			}
		});

		ProductImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, ProductActivity.class);
				intent.putExtra("productId",
						mCartOrders.get(position).productId);
				mContext.startActivity(intent);
			}
		});

		return view;
	}

	public void sendMessageToActivity() {
		double allPrice = 0;
		int count = 0;

		for (int i = 0; i < mCartOrders.size(); i++) {

			CartOrder order1 = mCartOrders.get(i);
			if (order1.isChecked) {
				count++;
				allPrice = allPrice + order1.price * order1.count;
			}
		}

		mCartCallBack.countPrice(allPrice, count);

	}

	public void setData(boolean isChecked) {

		Log.i("wu", "setData"+mCartOrders.size()+" "+isChecked);
		for (int i = 0; i < mCartOrders.size(); i++) {

			CartOrder order1 = mCartOrders.get(i);
			order1.isChecked = isChecked;
			notifyDataSetChanged();
		}

		sendMessageToActivity();

	}
	
	
}
