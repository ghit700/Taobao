package com.wu.taobao.adapter;

import java.util.ArrayList;

import com.lidroid.xutils.BitmapUtils;
import com.wu.taobao.R;
import com.wu.taobao.bean.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchListAdapter extends BaseAdapter {

	ArrayList<Product> mProducts;
	Context mContext;

	public SearchListAdapter(ArrayList<Product> mProducts, Context mContext) {
		super();
		this.mProducts = mProducts;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {

		return mProducts.size();
	}

	@Override
	public Object getItem(int position) {

		return mProducts.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	class ViewHolder {
		TextView productName, productPrice;
		ImageView productImg, more;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		// ViewHolder holder=null;
		// if (convertView == null) {
		// view = LayoutInflater.from(mContext).inflate(
		// R.layout.searchlist_item, parent, false);
		// convertView = view;
		// holder=new ViewHolder();
		// holder.productName = (TextView) view.findViewById(R.id.productName1);
		// holder.productPrice = (TextView)
		// view.findViewById(R.id.productPrice);
		// holder.productImg = (ImageView) view.findViewById(R.id.productImg);
		// holder.more = (ImageView) view.findViewById(R.id.more);
		// convertView.setTag(holder);
		// } else {
		// view = convertView;
		// holder = (ViewHolder) convertView.getTag();
		// }
		view = LayoutInflater.from(mContext).inflate(R.layout.searchlist_item,
				parent, false);

		TextView productName, productPrice;
		ImageView productImg, more;

		productName = (TextView) view.findViewById(R.id.productName1);
		productPrice = (TextView) view.findViewById(R.id.productPrice);
		productImg = (ImageView) view.findViewById(R.id.productImg);
		Product product = mProducts.get(position);
		BitmapUtils bitmapUtils = new BitmapUtils(mContext);
		bitmapUtils.display(productImg, product.pic);
		productName.setText(product.name);
		productPrice.setText(String.valueOf(product.price));

		return view;
	}

}
