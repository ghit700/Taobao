package com.wu.taobao.model;

import java.util.ArrayList;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wu.taobao.tools.SqliteHelper;

public class SearchHistoryModel {
	SqliteHelper mHelper;
	Context mContext;
	SQLiteDatabase mReadDatabase;
	SQLiteDatabase mWriteDatabase;

	public SearchHistoryModel(Context mContext) {
		super();
		this.mContext = mContext;
		this.mHelper = new SqliteHelper(mContext);
		this.mReadDatabase = mHelper.getReadableDatabase();
		this.mWriteDatabase = mHelper.getWritableDatabase();
	}

	public ArrayList<String> queryAll() {
		ArrayList<String> arrayList = new ArrayList<>();
		Cursor cursor = mReadDatabase.rawQuery(
				"select * from searchhistory order by time desc", null);
		while (cursor.moveToNext()) {

			arrayList.add(cursor.getString(1));
		}
		cursor.close();
		return arrayList;

	}

	public void update(String text) {
		mWriteDatabase.execSQL("update searchhistory set time=? where key=?",
				new Object[] { System.currentTimeMillis(), text });

	};

	public void insert(String text) {

		Cursor cursor = mReadDatabase.rawQuery("select * from searchhistory",
				null);
		while (cursor.moveToNext()) {

			if (cursor.getString(1).equals(text)) {
				update(text);
				return;
			}
		}
		cursor.close();
		mWriteDatabase.execSQL(
				"insert into searchhistory (key,time) values(?,?)",
				new Object[] { text, System.currentTimeMillis() });
	}

	public void delete(String text) {
		mWriteDatabase.execSQL("delete from searchhistory where key= '" + text
				+ "'");
	}

	public void deleteAll() {
		mWriteDatabase.execSQL("delete from searchhistory");
	}
}