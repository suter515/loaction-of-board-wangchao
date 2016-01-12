package com.qt.solarpanelslos.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.qt.solarpanelslos.bean.Constant;
import com.qt.solarpanelslos.utils.LogUtils;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = Constant.DBName;
	private static final int DATABASE_VERSION = Constant.DBVersion;
	private static DatabaseHelper instance;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static DatabaseHelper Instance(Context context) {
		if (instance == null) {
			instance = new DatabaseHelper(context);
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		LogUtils.i("DatabaseHelper=========", "QTSNP.db onCreate");

		// 太阳能电池板信息（位置）表
		String sql1 = "CREATE TABLE IF NOT EXISTS SolarPanelsLos(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "boardId TEXT NOT NULL, lat TEXT NOT NULL, lng TEXT  NOT NULL, fromFactory TEXT NOT NULL)";
		db.execSQL(sql1);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		LogUtils.i("DatabaseHelper=========", "QTSNP.db onUpgrade");

		db.execSQL("DROP TABLE IF EXISTS SolarPanelsLos");
		onCreate(db);

	}

}
