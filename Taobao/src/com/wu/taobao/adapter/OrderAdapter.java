package com.wu.taobao.adapter;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.wu.taobao.R;
import com.wu.taobao.bean.Order;
import com.wu.taobao.model.OrderModel;

public class OrderAdapter extends BaseAdapter {

	ArrayList<Order> mOrders;
	Context mContext;
	OrderModel mOrderModel;

	public OrderAdapter(ArrayList<Order> mOrders, Context mContext) {
		super();
		this.mOrders = mOrders;
		this.mContext = mContext;
		mOrderModel = new OrderModel(mContext);
	}

	@Override
	public int getCount() {

		return mOrders.size();
	}

	@Override
	public Object getItem(int position) {

		return mOrders.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	class ViewHolder {
		TextView orderPrice, productCount, productName, productPrice, shopName;
		ImageView productImg;
		Button delete;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View view;
		ViewHolder holder = null;
		if (convertView == null) {
			view = LayoutInflater.from(mContext).inflate(R.layout.order_item,
					null);
			convertView = view;
			holder = new ViewHolder();
			holder.orderPrice = (TextView) view.findViewById(R.id.orderPrice);
			holder.productCount = (TextView) view
					.findViewById(R.id.productCount);
			holder.productName = (TextView) view.findViewById(R.id.productName);
			holder.productPrice = (TextView) view
					.findViewById(R.id.productPrice);
			holder.shopName = (TextView) view.findViewById(R.id.shopName);
			holder.productImg = (ImageView) view.findViewById(R.id.productImg);
			holder.delete = (Button) view.findViewById(R.id.orderDelete);
			convertView.setTag(holder);

		} else {
			view = convertView;
			holder = (ViewHolder) convertView.getTag();
		}

		Order order = mOrders.get(position);

		holder.orderPrice.setText("$" + order.price);
		holder.productCount.setText("x" + order.count);
		holder.productName.setText(order.productName);
		holder.productPrice.setText("$" + order.price);
		holder.shopName.setText(order.shopName);

		BitmapUtils bitmapUtils = new BitmapUtils(mContext);
		bitmapUtils.display(holder.productImg, order.pic);

		holder.delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(mContext)
						.setTitle("确认删除订单?")
						.setMessage("删除之后将无法恢复")
						.setPositiveButton("确认",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										mOrderModel.delete(mOrders
												.get(position).id);
										mOrders.remove(position);
										notifyDataSetChanged();
									}
								}).setNegativeButton("取消", null).create()
						.show();
			}
		});

		return view;

	}
}
