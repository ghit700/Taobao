package com.wu.taobao.model;

import java.util.ArrayList;

import com.wu.taobao.bean.CartOrder;
import com.wu.taobao.bean.Product;
import com.wu.taobao.tools.SqliteHelper;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 进行购物车订单的数据库操作
 * 
 * @author nan
 * 
 */
public class CartOrderModel {
	Context mContext;

	SQLiteDatabase mWriteDatabase;
	SQLiteDatabase mReadDatabase;
	ArrayList<CartOrder> mCartOrders;

	public CartOrderModel(Context mContext) {
		super();
		this.mContext = mContext;
		SqliteHelper mHelper = new SqliteHelper(mContext);
		mWriteDatabase = mHelper.getWritableDatabase();
		mReadDatabase = mHelper.getReadableDatabase();

		mCartOrders = new ArrayList<>();
	}

	// 查询所有的订单
	public ArrayList<CartOrder> query(int id) {

		Cursor cursor = mReadDatabase.rawQuery(
				"select * from cart where userid=?",
				new String[] { String.valueOf(id) });
		mCartOrders = new ArrayList<>();
		while (cursor.moveToNext()) {

			
			mCartOrders.add(new CartOrder(cursor.getInt(0), cursor.getInt(1),
					cursor.getString(2), cursor.getInt(4), cursor.getString(5),
					cursor.getDouble(6), cursor.getInt(7), false, cursor
							.getString(3), cursor.getInt(9)));
		}
		return mCartOrders;
	}

	// 更新所有的订单 :假如在这个数据库中已经存在某种商品,再次加入,只要更新数量即可.
	public void update(Product product, int count) {
		int originalCount = 0;
		Cursor cursor = mReadDatabase.rawQuery(
				"select count from cart where productid=?",
				new String[] { String.valueOf(product.id) });

		if (cursor.moveToNext()) {
			originalCount = cursor.getInt(0);
		}
		cursor.close();
		count += originalCount;
		mWriteDatabase.execSQL("update cart set count=? where productid=? ",
				new Object[] { count, product.id });
	}

	public void updateCount(int productId, int count) {
		mWriteDatabase.execSQL("update cart set count=? where productid=? ",
				new Object[] { count, productId });
	}

	// 删除订单 :在你结算所有金额之后,从数据库中删除对应的商品
	public void delete(ArrayList<CartOrder> orders) {
		for (int i = 0; i < orders.size(); i++) {
			mWriteDatabase.execSQL("delete from cart where _id=? ",
					new Object[] { orders.get(i).id });
		}

	}

	// 插入订单

	public void insert(Product product, int count, int userId) {

		// 判断是否存在这个商品

		if ((mReadDatabase.rawQuery("select * from cart where productid=? and userid=?",
				new String[] { String.valueOf(product.id),String.valueOf(userId) })).moveToNext()) {
			update(product, count);
			

		} else {
			mWriteDatabase
					.execSQL(
							"insert into cart (shopid,shopname,pic,productid,productname,price,count,userid) "
									+ "values (?,?,?,?,?,?,?,?)", new Object[] {
									product.shopId, product.shopName,
									product.pic, product.id, product.name,
									product.price, count, userId });
			
		}

	}
}
