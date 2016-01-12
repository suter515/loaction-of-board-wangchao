package com.qt.solarpanelslos.db.dao;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.qt.solarpanelslos.bean.SolarPanelsLocation;
import com.qt.solarpanelslos.db.DBAdapter;
import com.qt.solarpanelslos.utils.LogUtils;
import com.qt.solarpanelslos.utils.TimeUtils;



public class SolarPanelsDao {
	
private static String fields []={"_id","boardId","lat","lng","formFactory"};
private static String[] fields_up ={"boardId","lat","lng","formFactory"};

/**
 * 查询OrdinaryFamilyCensus表 返回list
 */
public static ArrayList<SolarPanelsLocation> querySolarPanelsDaoList(Context context, String flag,
		String id) {
	ArrayList<SolarPanelsLocation> list1 = new ArrayList<SolarPanelsLocation>();

	DBAdapter dbAdapter = null;
	try {
		dbAdapter = new DBAdapter(context);
		String strWhere = " where flag=?";
		if (id != null && !id.equals("")) {
			strWhere = strWhere + "and _id=?";
		}
		// 查询OrdinaryFamilyCensus
		String sql = "select * from SolarPanelsLos" + strWhere + " order by boardId";

		String[] args;
		if (id != null && !id.equals("")) {
			args = new String[] { flag, id };
		} else {
			args = new String[] { flag };
		}
		/*
		 * 0 _id INTEGER PRIMARY KEY AUTOINCREMENT," 1 "QuHuaCode TEXT NOT
		 * NULL, 2 QuHuaName TEXT NOT NULL, 3 PuChaAreaCode TEXT NOT NULL, 4
		 * HuCode TEXT NOT NULL," 5 "FormJson TEXT DEFAULT '', 6 SaveTime
		 * TEXT DEFAULT '3000-12-31 23:59:59', 7 Progress INTEGER DEFAULT 0,
		 * 8 Flag INTEGER DEFAULT 0, 9 Note TEXT DEFAULT '')";
		 */
		Cursor cursor = dbAdapter.select(sql, args);
		SolarPanelsLocation obj;
		while (cursor.moveToNext()) {
			obj = new SolarPanelsLocation();
			obj.setId(cursor.getInt(0));
			obj.setBoardId(cursor.getString(1));
			obj.setLat(Double.valueOf(cursor.getString(2)).doubleValue());
			obj.setLng(Double.valueOf(cursor.getString(3)).doubleValue());
			obj.setFormFactory(cursor.getString(4));
			

			list1.add(obj);
		}
		cursor.close();
	} catch (Exception ex) {
		LogUtils.i("SolarPanelsDao querySolarPanelsDaoList===", ex.toString());
	} finally {
		if (dbAdapter != null)
			dbAdapter.close();
	}
	return list1;
}

/**
 * 查询OrdinaryFamilyCensus表 返回list
 */
public static ArrayList<SolarPanelsLocation> querySolarPanelsLocationList(Context context, String id) {
	LogUtils.d("DAO", "get by id : " + id);
	ArrayList<SolarPanelsLocation> list1 = new ArrayList<SolarPanelsLocation>();

	DBAdapter dbAdapter = null;
	try {
		dbAdapter = new DBAdapter(context);
		String strWhere = "";
		if (id != null && !id.equals("")) {
			strWhere = " where _id=?";
		}
		// 查询OrdinaryFamilyCensus
		String sql = "select * from SolarPanelsLos" + strWhere;

		String[] args;
		if (id != null && !id.equals("")) {
			args = new String[] { id };
		} else {
			args = new String[] {};
		}
		
		Cursor cursor = dbAdapter.select(sql, args);
		SolarPanelsLocation obj;
		while (cursor.moveToNext()) {
			obj = new SolarPanelsLocation();
			obj.setId(cursor.getInt(0));
			obj.setBoardId(cursor.getString(1));
			obj.setLat(Double.valueOf(cursor.getString(2)).doubleValue());
			obj.setLng(Double.valueOf(cursor.getString(3)).doubleValue());
			obj.setFormFactory(cursor.getString(4));

			list1.add(obj);
		}
		cursor.close();
	} catch (Exception ex) {
		LogUtils.i("SolarPanelsDao querySolarPanelsLocationList===", ex.toString());
	} finally {
		if (dbAdapter != null)
			dbAdapter.close();
	}
	return list1;
}

/**
 * 查询OrdinaryFamilyCensus表 返回list
 */
public static ArrayList<SolarPanelsLocation> querySolarPanelsLocationList(Context context, String flag,
		int start, int count) {
	ArrayList<SolarPanelsLocation> list1 = new ArrayList<SolarPanelsLocation>();

	DBAdapter dbAdapter = null;
	try {
		dbAdapter = new DBAdapter(context);
		String strWhere = " where flag=?";
		String strLimit = "";
		if (count > 0 && start >= 0) {
			strLimit = "limit " + start + "," + count;
		}
		// 查询OrdinaryFamilyCensus
		String sql = "select * from SolarPanelsLos" + strWhere + " order by boardId " + strLimit;

		String[] args;
		args = new String[] { flag };
		
		Cursor cursor = dbAdapter.select(sql, args);
		SolarPanelsLocation obj;
		while (cursor.moveToNext()) {
			obj = new SolarPanelsLocation();
			obj.setId(cursor.getInt(0));
			obj.setBoardId(cursor.getString(1));
			obj.setLat(Double.valueOf(cursor.getString(2)).doubleValue());
			obj.setLng(Double.valueOf(cursor.getString(3)).doubleValue());
			obj.setFormFactory(cursor.getString(4));

			list1.add(obj);
		}
		cursor.close();
	} catch (Exception ex) {
		LogUtils.i("MonitorFunDao queryOrdinaryFamilyCensusList===", ex.toString());
	} finally {
		if (dbAdapter != null)
			dbAdapter.close();
	}
	return list1;
}

public static long insertSolarPanelsLocation(Context context, SolarPanelsLocation dto) {
	DBAdapter dbAdapter = null;
	long n = -1;
	try {
		dbAdapter = new DBAdapter(context);
		String[] values = { null, dto.getBoardId(), Double.toString(dto.getLat()), Double.toString(dto.getLng()), dto.getFormFactory()};
		n = dbAdapter.insert("SolarPanelsLos", fields, values);
	} catch (Exception ex) {
		LogUtils.i("SolarPanelsDao insertSolarPanelsLocation===", ex.toString());
	} finally {
		if (dbAdapter != null)
			dbAdapter.close();
	}
	return n;
}

public static void deleteSolarPanelsLocation(Context context, String id) {
	DBAdapter dbAdapter = null;
	try {
		dbAdapter = new DBAdapter(context);
		String[] params = { id };
		dbAdapter.delete("SolarPanelsLos", "_id=?", params);
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		if (dbAdapter != null)
			dbAdapter.close();
	}
}

public static void updateOrdinaryFamilyCensus(Context context, String id, SolarPanelsLocation dto) {
	DBAdapter dbAdapter = null;
	try {
		dbAdapter = new DBAdapter(context);
		String[] values = { null, dto.getBoardId(), Double.toString(dto.getLat()), Double.toString(dto.getLng()), dto.getFormFactory()};
		String[] params = { id };
		dbAdapter.update("SolarPanelsLos", fields_up, values, "_id=?", params);

	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		if (dbAdapter != null)
			dbAdapter.close();
	}
}

public static int getUnDoNumber(Context context) {
	int num = 0;
	DBAdapter dbAdapter = null;
	try {
		dbAdapter = new DBAdapter(context);
		// 查询OrdinaryFamilyCensus
		String sql = "select count(_id) from SolarPanelsLos where Flag=0";

		Cursor cursor = dbAdapter.select(sql, null);

		if (cursor.moveToFirst()) {
			num = cursor.getInt(0);
		}
		cursor.close();
	} catch (Exception ex) {
		LogUtils.i("SolarPanelsDao getUnDoNumber===", ex.toString());
	} finally {
		if (dbAdapter != null)
			dbAdapter.close();
	}
	return num;
}

public static int getDoNumber(Context context) {
	int num = 0;
	DBAdapter dbAdapter = null;
	try {
		dbAdapter = new DBAdapter(context);
		// 查询OrdinaryFamilyCensus
		String sql = "select count(_id) from SolarPanelsLos where Flag=1";

		Cursor cursor = dbAdapter.select(sql, null);
		if (cursor.moveToFirst()) {
			num = cursor.getInt(0);
		}
		cursor.close();
	} catch (Exception ex) {
		LogUtils.i("SolarPanelsDao getDoNumber===", ex.toString());
	} finally {
		if (dbAdapter != null)
			dbAdapter.close();
	}
	return num;
}

public static int getMaxId(Context context) {
	int num = 0;
	DBAdapter dbAdapter = null;
	try {
		dbAdapter = new DBAdapter(context);
		// 查询OrdinaryFamilyCensus
		String sql = "select max(_id) from SolarPanelsLos";

		Cursor cursor = dbAdapter.select(sql, null);
		if (cursor.moveToFirst()) {
			num = cursor.getInt(0);
		}
		cursor.close();
	} catch (Exception ex) {
		LogUtils.i("SolarPanelsDao getMaxId===", ex.toString());
	} finally {
		if (dbAdapter != null)
			dbAdapter.close();
	}
	return num;
}
}
