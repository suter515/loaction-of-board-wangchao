package com.qt.solarpanelslos.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.qt.solarpanelslos.R;
import com.qt.solarpanelslos.bean.Constant;



/**
 * 
 * @type UseHelpActivity
 * 帮助界面
 * @author new
 * @time 2015年6月8日下午4:30:43
 */
public class UseHelpActivity extends BaseActivity {
	private WebView wv_usehelp_content;
	private static  String USEHELP_FILE = Constant.Help_File_Path;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_usehelp);

		wv_usehelp_content = (WebView) findViewById(R.id.wv_usehelp_content);
		wv_usehelp_content.loadUrl(USEHELP_FILE); 
		wv_usehelp_content.getSettings().setJavaScriptEnabled(true);
		wv_usehelp_content.getSettings().setBuiltInZoomControls(true);
		wv_usehelp_content.getSettings().setSupportZoom(true);
		
	}


}
