package com.qt.solarpanelslos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {

	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;


	public DBAdapter(Context ctx) {
		context = ctx;
		// DBHelper = DatabaseHelper.Instance(context);  // 单例模式
		DBHelper = new DatabaseHelper(context); // 多例模式
	}

	public SQLiteDatabase open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return db;
	}

	public void close() {
		if (db.isOpen())
			db.close();
	}
	
	public Cursor select(String sql,String[] selectionArgs){
		db=DBHelper.getReadableDatabase();
		return db.rawQuery(sql, selectionArgs);
	}
	public void execSQL(String sql){
		db=DBHelper.getWritableDatabase();
		db.execSQL(sql);
	}
	public void execSQL(String sql,Object[] bindArgs){
		db=DBHelper.getWritableDatabase();
		db.execSQL(sql, bindArgs);
	}

	public Cursor select(String table_name, String[] fields, String condition,
			String[] Param, String Group, String Having, String orderby) {
		db = DBHelper.getReadableDatabase();
		
		while (db.isDbLockedByOtherThreads()) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return db.query(table_name, fields, condition, Param, Group, Having, orderby);
	}

	public long delete(String table_name, String condition, String[] Param) {
		db = DBHelper.getWritableDatabase();
		return db.delete(table_name, condition, Param);
	}

	public long insert(String table_name, String[] fields, String[] values) {
		db = DBHelper.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		for (int i = 0; i < fields.length; i++) {
			contentValues.put(fields[i], values[i]);
		}

		return db.insert(table_name, null, contentValues);
	}

	public long update(String table_name, String[] fields, String[] values,
			String condition, String[] Param) {
		db = DBHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		for (int i = 0; i < fields.length; i++) {
			contentValues.put(fields[i], values[i]);
		}

		return db.update(table_name, contentValues, condition, Param);

	}
	
	public long update(String table_name, ContentValues contentValues,
			String condition, String[] Param) {
		db = DBHelper.getWritableDatabase();

		return db.update(table_name, contentValues, condition, Param);

	}

}
