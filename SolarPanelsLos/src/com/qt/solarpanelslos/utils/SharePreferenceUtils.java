package com.qt.solarpanelslos.utils;

import android.content.Context;

public class SharePreferenceUtils {
	private static SharePreference mSharePreference;

	public static SharePreference getSharePreference(Context context){
		if(mSharePreference!=null)
		{
			return mSharePreference;
		}else{
			return mSharePreference = new SharePreference(context);
		}
	}
	
}
