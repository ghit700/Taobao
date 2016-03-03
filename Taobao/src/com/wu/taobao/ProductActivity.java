package com.wu.taobao;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.wu.taobao.adapter.MyPagerAdapter;
import com.wu.taobao.bean.CartOrder;
import com.wu.taobao.bean.Product;
import com.wu.taobao.bean.ProductDescription;
import com.wu.taobao.model.CartOrderModel;
import com.wu.taobao.model.OrderModel;
import com.wu.taobao.model.ProductDescriptionModel;

public class ProductActivity extends BaseActivity implements OnClickListener,
		OnPageChangeListener {

	TextView mProductName, mProductPrice, mCart, mBuy, mCartProductPrice, mSub,
			mCartCount, mAdd;
	Button mConfrimBtn;

	int mCount = 1, mFlag;
	LinearLayout mll;
	ViewPager mPager;
	ImageView mBackImg, mCartImg, mProductImg, mCloseCart;
	CartOrderModel mCartOrderModel;
	ProductDescriptionModel mProductDescriptionModel;
	ProductDescription mProductDescription;
	Product mProduct;
	ArrayList<String> mImgUrls;
	ArrayList<ImageView> mImgs;
	ArrayList<ImageView> mRounds;
	BitmapUtils bitmapUtils;
	RelativeLayout mRl, mrl1;
	OrderModel mOrderModel;
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				mProductDescription = (ProductDescription) msg.obj;
				mImgUrls = mProductDescription.imgUrls;
				mProductName.setText(mProductDescription.name);
				mProductPrice.setText("$" + mProductDescription.price);
				mCartProductPrice.setText("$" + mProductDescription.price);

				mImgs = new ArrayList<>();
				mRounds = new ArrayList<>();
				bitmapUtils = new BitmapUtils(mContext);
				bitmapUtils.display(mProductImg, mImgUrls.get(0));
				for (int i = 0; i < mImgUrls.size(); i++) {
					ImageView imageView = new ImageView(mContext);
					imageView.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.MATCH_PARENT));

					bitmapUtils.display(imageView, mImgUrls.get(i));
					mImgs.add(imageView);
				}
				for (int i = 0; i < mImgUrls.size(); i++) {

					ImageView imageView = new ImageView(mContext);
					imageView.setLayoutParams(new LayoutParams(20, 20));
					imageView.setPadding(40, 0, 40, 0);
					if (i == 0) {
						imageView
								.setBackgroundResource(R.drawable.radiobutton_on);
					} else {
						imageView
								.setBackgroundResource(R.drawable.radiobutton_nm);
					}
					mRounds.add(imageView);
					mll.addView(mRounds.get(i));

				}

				mPager.setAdapter(new MyPagerAdapter(mImgs));

				mProduct = new Product(mProductDescription.id,
						mProductDescription.name, mImgUrls.get(0),
						mProductDescription.price,
						mProductDescription.categoryId,
						mProductDescription.categoryName,
						mProductDescription.shopId,
						mProductDescription.shopName);
			}
		};

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);

		int id = 0;
		if (getIntent() != null) {
			id = getIntent().getIntExtra("productId", 1);
		}

		mProductDescriptionModel = new ProductDescriptionModel(mContext);
		mProductName = (TextView) findViewById(R.id.productName);
		mProductPrice = (TextView) findViewById(R.id.productPrice);
		mCart = (TextView) findViewById(R.id.joinCart);
		mBuy = (TextView) findViewById(R.id.buy);
		mBackImg = (ImageView) findViewById(R.id.back);
		mCartImg = (ImageView) findViewById(R.id.cart);
		mCartOrderModel = new CartOrderModel(mContext);
		mPager = (ViewPager) findViewById(R.id.pages);
		mll = (LinearLayout) findViewById(R.id.ll);
		mRl = (RelativeLayout) findViewById(R.id.Rl);
		mrl1 = (RelativeLayout) findViewById(R.id.rl1);
		mProductImg = (ImageView) findViewById(R.id.productImg);
		mCartProductPrice = (TextView) findViewById(R.id.productPrice1);
		mSub = (TextView) findViewById(R.id.sub);
		mCartCount = (TextView) findViewById(R.id.count);
		mAdd = (TextView) findViewById(R.id.add);
		mConfrimBtn = (Button) findViewById(R.id.confirmBtn);
		mCloseCart = (ImageView) findViewById(R.id.closeCart);
		mOrderModel = new OrderModel(mContext);
		

		mProductDescriptionModel.queryDescription(id, mHandler);
		setListener();
	}

	private void setListener() {
		mCart.setOnClickListener(this);
		mBuy.setOnClickListener(this);
		mBackImg.setOnClickListener(this);
		mCartImg.setOnClickListener(this);
		mPager.setOnPageChangeListener(this);
		mAdd.setOnClickListener(this);
		mSub.setOnClickListener(this);
		mConfrimBtn.setOnClickListener(this);
		mCloseCart.setOnClickListener(this);
		mrl1.setOnClickListener(this);
		mRl.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Animation animation = AnimationUtils.loadAnimation(mContext,
				R.anim.cart_translate);
		;
		switch (v.getId()) {
		case R.id.joinCart:

			mrl1.setVisibility(View.VISIBLE);
			mCartCount.setText("1");
			mRl.startAnimation(animation);
			mFlag = 0;
			break;
		case R.id.buy:
			mrl1.setVisibility(View.VISIBLE);
			mCartCount.setText("1");
			mRl.startAnimation(animation);
			mFlag = 1;

			break;
		case R.id.back:
			finish();
			break;
		case R.id.cart:
			Intent intent = new Intent(mContext, MainActivity.class);
			intent.putExtra("flag", 2);
			startActivity(intent);
			break;
		case R.id.add:
			mCount++;
			mCartCount.setText(String.valueOf(mCount));
			break;
		case R.id.sub:
			if (mCount > 1) {
				mCount--;
				mCartCount.setText(String.valueOf(mCount));
			}
			break;
		case R.id.confirmBtn:
			if (mUserId == 0) {

				new AlertDialog.Builder(mContext)
						.setMessage("您还没登录,不能购买,请先登录!")
						.setPositiveButton("确认",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Intent intent = new Intent(mContext,
												LoginActivity.class);
										intent.putExtra("stye", 3);
										startActivityForResult(intent, 1);
									}
								}).create().show();
			} else {
				if (mFlag == 0) {
					mCartOrderModel.insert(mProduct, mCount, mUserId);
					Toast.makeText(mContext, "添加成功,在购物车等亲哦", 1).show();
				} else {
					mOrderModel.insert(new CartOrder(1, mProduct.shopId,
							mProduct.shopName, mProduct.id, mProduct.name,
							mProduct.price, mCount, false, mProduct.pic,
							mUserId));
					Toast.makeText(mContext, "购买成功", 1).show();
				}

				mrl1.setVisibility(View.GONE);
			}

			break;
		case R.id.closeCart:
			mrl1.setVisibility(View.GONE);
			break;
		case R.id.rl1:
			mrl1.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		for (int i = 0; i < mImgUrls.size(); i++) {
			if (i == arg0) {
				mRounds.get(i).setBackgroundResource(R.drawable.radiobutton_on);
			} else {
				mRounds.get(i).setBackgroundResource(R.drawable.radiobutton_nm);
			}
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {

		if (arg0 == 1 && arg1 == 1) {
			mUserId = getSharedPreferences("User", 0).getInt("id", 0);
		}
		super.onActivityResult(arg0, arg1, arg2);
	}

}
