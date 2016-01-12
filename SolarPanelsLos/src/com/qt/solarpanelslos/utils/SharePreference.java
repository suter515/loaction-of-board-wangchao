package com.qt.solarpanelslos.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePreference {
	private static final String USER_SHARE = "qtsnp_share";
	private static final String USER_NAME = "user_name";
	private static final String USER_PASSWORD = "user_password";
	private static final String REMEBER_PASSWORD = "remeber_password";

	private SharedPreferences mPreference;
	private Editor mEditor;

	public SharePreference(Context context) {
		mPreference = context.getSharedPreferences(USER_SHARE, Context.MODE_PRIVATE);
		mEditor = mPreference.edit();
	}

	public void setUser(String name, String password) {
		mEditor.putString(USER_NAME, name);
		mEditor.putString(USER_PASSWORD, password);
		mEditor.commit();
	}

	public String getUserName() {
		return mPreference.getString(USER_NAME, "");
	}

	public String getUserPassword() {
		return mPreference.getString(USER_PASSWORD, "");
	}

	public void setRemeberPassword(boolean isRemeber) {
		mEditor.putBoolean(REMEBER_PASSWORD, isRemeber);
		mEditor.commit();
	}

	public boolean isRemeberPassword() {
		return mPreference.getBoolean(REMEBER_PASSWORD, true);
	}

	public void resetUser(){
		mEditor.remove(USER_NAME);
		mEditor.remove(USER_PASSWORD);
		mEditor.remove(REMEBER_PASSWORD);
		mEditor.commit();
	}
}
