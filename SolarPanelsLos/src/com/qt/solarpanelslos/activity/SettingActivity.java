package com.qt.solarpanelslos.activity;

import org.apache.cordova.CordovaActivity;

import com.qt.solarpanelslos.utils.LogUtils;

import android.content.Intent;
import android.os.Bundle;

public class SettingActivity extends CordovaActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.init();
		// Set by <content src="index.html" /> in config.xml
		super.loadUrl("file:///android_asset/www/index.html");
		// super.loadUrl("file:///android_asset/www/index.html")
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

	}
}
