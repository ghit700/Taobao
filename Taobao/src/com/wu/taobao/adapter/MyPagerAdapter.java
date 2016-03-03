package com.wu.taobao.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

public class MyPagerAdapter extends PagerAdapter {

	ArrayList<ImageView> mImgs;

	public MyPagerAdapter(ArrayList<ImageView> mImgs) {
		super();
		this.mImgs = mImgs;
	}

	@Override
	public int getCount() {

		return mImgs.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {

		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(View container, int position) {

		((ViewPager) container).addView(mImgs.get(position));
		return mImgs.get(position);
	}

	@Override
	public void destroyItem(View container, int position, Object object) {

		((ViewPager) container).removeView(mImgs.get(position));
	}

}
