package com.qt.solarpanelslos.activity;

import java.util.Stack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.baidu.mapapi.SDKInitializer;
import com.qt.solarpanelslos.R;
import com.qt.solarpanelslos.application.AppContext;
import com.qt.solarpanelslos.bean.Constant;
import com.qt.solarpanelslos.fragment.AuxiliaryFunFragment;
import com.qt.solarpanelslos.fragment.HFragmentManager;
import com.qt.solarpanelslos.utils.LogUtils;
import com.qt.solarpanelslos.utils.PhoneUtils;

public class MainActivity extends BaseActivity implements OnClickListener {
	private View mLeftContainer;
	private LinearLayout slidraw;
	private HFragmentManager mFragMgr = null;
	/**
	 * 构造广播监听类，监听 SDK key 验证以及网络异常广播
	 */
	private class SDKReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			//Log.d(LTAG, "action: " + s);
			Log.e("LTAG", "action: " + s);
			
			
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
			LogUtils.e("LTAG", "key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
				//ToastUtils.show(getApplicationContext(),"key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
			} else if (s
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				LogUtils.e("LTAG", "网络出错");
				//ToastUtils.show(getApplicationContext(),"网络出错");
			}
		}
	}

	private SDKReceiver mReceiver;
	private boolean flag = true;;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 注册 SDK 广播监听者
				IntentFilter iFilter = new IntentFilter();
				iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
				iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
				mReceiver = new SDKReceiver();
				registerReceiver(mReceiver, iFilter);
				//
		setContentView(R.layout.main_activity);
		ImageButton imgbtnApp=(ImageButton)findViewById(R.id.imgbtn_app);
		imgbtnApp.setOnClickListener(this);
		ImageButton imgbtnSet=(ImageButton)findViewById(R.id.imgbtn_setting);
		imgbtnSet.setOnClickListener(this);
		slidraw = (LinearLayout)findViewById(R.id.ll_slidingdraw);
		// 初始化过程中设置了root fragment
		mFragMgr = HFragmentManager.instance(this, R.id.fl_map, AuxiliaryFunFragment.class.getSimpleName());
		if (savedInstanceState != null) {
			initFromState(savedInstanceState);
		}
		displayMainFragment(new AuxiliaryFunFragment());
//		getTitleBar().setOnFullClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if(mLlLeft.getVisibility() == View.GONE){
//					mLlLeft.setVisibility(View.VISIBLE);
//				}else {
//					mLlLeft.setVisibility(View.GONE);
//				}
//			}
//		});

		//初始化屏幕宽度
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		AppContext.getInstance().setScreenWidth(outMetrics.widthPixels);
		LogUtils.d("MainActivity", "Density : "+PhoneUtils.getScreenDensity(this)+"width:"+outMetrics.widthPixels+"  height:"+outMetrics.heightPixels);
	}

	/* 从状态中初始化 */
	private void initFromState(Bundle savedInstanceState) {
		Stack<String> data = mFragMgr.getCacheStack();
		String[] saved = savedInstanceState.getStringArray("stack");
		if (saved != null) {
			for (int i = 0; i < saved.length; i++) {
				data.add(saved[i]);
			}
		} else {
			mFragMgr.setCacheStack();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveToState(outState);
	}

	/* 保存到状态 */
	private void saveToState(Bundle outState) {
		int len = mFragMgr.getCacheStack().size();
		String[] data = new String[len];
		outState.putStringArray("stack", mFragMgr.getCacheStack().toArray(data));
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mFragMgr != null) {
			mFragMgr.recycle();
		}
		if (mReceiver!=null) {
			unregisterReceiver(mReceiver);
			mReceiver=null;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	protected void displayMainFragment(Fragment fragment) {
		mFragMgr.display(fragment);
		mFragMgr.clearExceptCurrent();
		resetFragment();
	}

	protected void displayMainFragment(Fragment fragment, boolean restore) {
		mFragMgr.display(fragment, restore);
		mFragMgr.clearExceptCurrent();
		resetFragment();
	}

	public void resetFragment() {
		dimissKeyboard();
		
	}

	public void dimissKeyboard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		Intent intent=new Intent();
		
		switch (v.getId()) {
		case R.id.imgbtn_app:
			if (flag) {
				slidraw.setVisibility(View.VISIBLE);
				flag=false;
			}else {
				slidraw.setVisibility(View.GONE);
				flag=true;
			}
			
			
			break;
case R.id.imgbtn_setting:
	intent.setClass(MainActivity.this, SettingActivity.class);
	startActivity(intent);
			break;
		default:
			break;
		}
	}

}
