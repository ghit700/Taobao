package com.wu.taobao.fragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wu.taobao.MainActivity;
import com.wu.taobao.R;
import com.wu.taobao.SearchActivity;
import com.wu.taobao.adapter.MyPagerAdapter;

public class HomeFragment extends Fragment implements OnPageChangeListener {

	ViewPager mPager;
	LinearLayout mLl,ll_text1,ll_text2;
	String[] texts=new String[]{"学朴槿惠穿衣风格","能穿出N中风格的打底衫","卫衣+小短裤,穿出时尚风格","奇葩零食大盘点"};
	private int index=2;
	private int flag=0;//0代表 text1 out 
	ArrayList<ImageView> mImgs;
	ArrayList<ImageView> mRounds;
	int[] imgs = new int[] { R.drawable.m1, R.drawable.m2, R.drawable.m3,
			R.drawable.m4, R.drawable.m5 };
	Context mContext;
	int mIndex=0;
	EditText mHomeSearch;
	Animation in,out;
	TextView ll_text1_tv,ll_text2_tv;
	Timer timer;
	

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_home, container, false);

		mPager = (ViewPager) view.findViewById(R.id.pager);
		mLl = (LinearLayout) view.findViewById(R.id.ll);
		mContext = getActivity();
		mHomeSearch = (EditText)view.findViewById(R.id.homeSearch);
		ll_text1=(LinearLayout) view.findViewById(R.id.ll_text1);
		ll_text2=(LinearLayout) view.findViewById(R.id.ll_text2);
		ll_text1_tv=(TextView) view.findViewById(R.id.ll_text1_tv);
		ll_text2_tv=(TextView) view.findViewById(R.id.ll_text2_tv);
		
		in=AnimationUtils.loadAnimation(getActivity(), R.anim.in);
		out=AnimationUtils.loadAnimation(getActivity(), R.anim.out);
		
		timer=new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						if (flag==0) {
							ll_text1.startAnimation(out);
							ll_text2_tv.setText(texts[index++%texts.length]);
							ll_text2.startAnimation(in);
							flag=1;
						}else {
							ll_text1_tv.setText(texts[index++%texts.length]);
							ll_text1.startAnimation(in);
							ll_text2.startAnimation(out);
							flag=0;
						}
					}
				});
			}
		}, 0,5000);
		
		mHomeSearch.setFocusable(false);
		mHomeSearch.setFocusableInTouchMode(false);
		
		
		
		
		
		mImgs = new ArrayList<>();
		mRounds = new ArrayList<>();

		for (int i = 0; i < imgs.length; i++) {
			ImageView imageView = new ImageView(mContext);
			imageView.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			imageView.setBackgroundResource(imgs[i]);
			mImgs.add(imageView);
		}
		for (int i = 0; i < imgs.length; i++) {
			
			ImageView imageView = new ImageView(mContext);
			imageView.setLayoutParams(new LayoutParams(10, 10));
			imageView.setPadding(20, 0, 20, 0);
			if (i == 0) {
				imageView.setBackgroundResource(R.drawable.radiobutton_on);
			} else {
				imageView.setBackgroundResource(R.drawable.radiobutton_nm);
			}
			mRounds.add(imageView);
			mLl.addView(mRounds.get(i));

		}

		mPager.setAdapter(new MyPagerAdapter(mImgs));

		mPager.setOnPageChangeListener(this);
		
		mPager.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mIndex++;
				mPager.setCurrentItem(mIndex%imgs.length);
				mPager.postDelayed(this,5000);
			}
		}, 5000);
		
		mHomeSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext,SearchActivity.class));
			}
		});

		return view;
	}
	
	@Override
	public void onDestroyView() {
		
		super.onDestroyView();
		timer.cancel();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		for (int i = 0; i < imgs.length; i++) {
			if (i == arg0) {
				mRounds.get(i).setBackgroundResource(R.drawable.radiobutton_on);
			} else {
				mRounds.get(i).setBackgroundResource(R.drawable.radiobutton_nm);
			}
		}
	}

}
