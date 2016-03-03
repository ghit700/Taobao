package com.wu.taobao.model;

import java.util.ArrayList;

import com.wu.taobao.bean.CartOrder;
import com.wu.taobao.bean.Order;
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
public class OrderModel {
	Context mContext;

	SQLiteDatabase mWriteDatabase;
	SQLiteDatabase mReadDatabase;
	ArrayList<Order> mOrders;

	public OrderModel(Context mContext) {
		super();
		this.mContext = mContext;
		SqliteHelper mHelper = new SqliteHelper(mContext);
		mWriteDatabase = mHelper.getWritableDatabase();
		mReadDatabase = mHelper.getReadableDatabase();

		mOrders = new ArrayList<>();
	}

	// 查询所有的订单
	public ArrayList<Order> queryAll(int id) {

		Cursor cursor = mReadDatabase.rawQuery(
				"select * from orderALL where userid=?",
				new String[] { String.valueOf(id) });
		mOrders = new ArrayList<>();
		while (cursor.moveToNext()) {

			mOrders.add(new Order(cursor.getInt(0), cursor.getInt(1), cursor
					.getString(2), cursor.getString(3), cursor.getInt(4),
					cursor.getString(5), cursor.getDouble(6), cursor.getInt(7),
					cursor.getInt(8)));
		}
		return mOrders;
	}

	public ArrayList<Order> queryAll(int id, String name) {

		Cursor cursor = mReadDatabase.rawQuery(
				"select * from orderALL where userid=? and productname=?",
				new String[] { String.valueOf(id), name });
		mOrders = new ArrayList<>();
		while (cursor.moveToNext()) {

			mOrders.add(new Order(cursor.getInt(0), cursor.getInt(1), cursor
					.getString(2), cursor.getString(3), cursor.getInt(4),
					cursor.getString(5), cursor.getDouble(6), cursor.getInt(7),
					cursor.getInt(8)));
		}
		return mOrders;
	}

	public void delete(int id) {

		mWriteDatabase.execSQL("delete from orderALL where _id=? ",
				new Object[] { id });

	}

	// 插入订单

	public void insert(CartOrder cartOrder) {

		// 判断是否存在这个商品

		mWriteDatabase
				.execSQL(
						"insert into orderALL (shopid,shopname,pic,productid,productname,price,count,userid) "
								+ "values (?,?,?,?,?,?,?,?)", new Object[] {
								cartOrder.shopId, cartOrder.shopName,
								cartOrder.pic, cartOrder.id,
								cartOrder.productName, cartOrder.price,
								cartOrder.count, cartOrder.userId });

	}
}
