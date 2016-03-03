package com.wu.taobao.tools;

import android.R.integer;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {

	public SqliteHelper(Context context) {
		super(context, "taobao", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table searchhistory (_id integer primary key autoincrement ,key text,time real)";
		db.execSQL(sql);
		sql = "create table cart(_id integer primary key autoincrement,"
				+ "shopid integer ," + "shopname text," + "pic text,"
				+ "productid integer," + "productname text," + "price real,"
				+ "count integer," + "ischeck integer," + "userid integer)";// 1代表勾选,0代表未选
		db.execSQL(sql);
		sql = "create table orderALL(_id integer primary key autoincrement,"
				+ "shopid integer ," + "shopname text," + "pic text,"
				+ "productid integer," + "productname text," + "price real,"
				+ "count integer," + " userid integer)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
