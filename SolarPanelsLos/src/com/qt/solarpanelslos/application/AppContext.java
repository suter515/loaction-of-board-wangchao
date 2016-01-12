package com.qt.solarpanelslos.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.baidu.mapapi.SDKInitializer;
import com.qt.solarpanelslos.db.DatabaseHelper;


/**
 * 全局应用程序类
 */
public class AppContext extends Application {
	
	private static AppContext mInstance;
	private int screenWidth;
	

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//SDKInitializer.initialize(this);
		mInstance = this;
		SDKInitializer.initialize(mInstance);
		// 创建数据库
		DatabaseHelper dbHelper = new DatabaseHelper(mInstance);  
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if(db!=null) {
			db.close();
		}
	}
	
	public static AppContext getInstance() {
		return mInstance;
	}
	
	public void setScreenWidth(int width){
		this.screenWidth = width;
	}
	
	public int getScreenWidth(){
		return this.screenWidth;
	}

}
