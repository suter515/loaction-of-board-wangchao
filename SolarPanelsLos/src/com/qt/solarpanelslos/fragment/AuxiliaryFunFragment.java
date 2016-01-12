package com.qt.solarpanelslos.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.qt.solarpanelslos.R;
import com.qt.solarpanelslos.activity.MainActivity;
import com.qt.solarpanelslos.fragment.auxiliaryfun.AuxiliaryFun_MapLocation_Fragment;
import com.qt.solarpanelslos.fragment.auxiliaryfun.AuxiliaryFun_MapOffline_Fragment;
import com.qt.solarpanelslos.utils.LogUtils;

/**
 * 
 * @type 辅助功能 TODO
 * @author sjt
 * @time
 */
public class AuxiliaryFunFragment extends BaseFragment implements OnClickListener {
	private static final String TAG = "AuxiliaryFunFragment";

	private MainActivity mMainContainer = null;
	private TextView tvwDistance;
	private String distance;
	
	private Fragment auxiliaryFun_MapLocation_Fragment = null;
	private Fragment auxiliaryFun_MapOffline_Fragment = null;
	private View v;
	private MyReceiver receiver;

	private ImageButton imgBtnMap;
	//private SDKReceiver mReceiver;

	private ImageButton imgBtnMapOffline;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mMainContainer = (MainActivity) activity;
	}

	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		receiver = new MyReceiver();
		IntentFilter filter = new IntentFilter("android.intent.action.MY_BROADCAST");
		mMainContainer.registerReceiver(receiver, filter);
////fragment内常用的localbroadcast
//		//LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
//		// 注册 SDK 广播监听者
//		IntentFilter iFilter = new IntentFilter();
//		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
//		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
//		mReceiver = new SDKReceiver();
//		mMainContainer.registerReceiver(mReceiver, iFilter);
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_auxiliaryfun, container, false);
		imgBtnMap = (ImageButton)v.findViewById(R.id.imgbtn_map);
		imgBtnMap.setOnClickListener(this);
		imgBtnMapOffline = (ImageButton)v.findViewById(R.id.imgbtn_mapoffline);
		imgBtnMapOffline.setOnClickListener(this);
		//mMainContainer.showLeft();
		// tvwDistance = (TextView) v.findViewById(R.id.tvw_distance_auxiliary);
		setupView(v);
		return v;
	}

	/**
	 * 建立视图
	 * 
	 * @param v
	 */

	private void setupView(View v) {
		// TODO Auto-generated method stub
		LogUtils.e("AuxiliaryFunFragment", "AuxiliaryFunFragment");
		auxiliaryFun_MapLocation_Fragment = new AuxiliaryFun_MapLocation_Fragment();
//		mHorizontalListView = (HorizontalListView) v.findViewById(R.id.auxiliaryfun_list);
//		String titles[] = { "地图定位", "离线下载" };
		 tvwDistance = (TextView)v.findViewById(R.id.tvw_distance_auxiliary);
		 //tvwDistance.setText("欢迎使用百度地图Android SDK v" + VersionInfo.getApiVersion());
//		mAdapter = new AuxiliaryFunListAdapter(AppContext.getInstance(), titles);
//		mHorizontalListView.setAdapter(mAdapter);
//		mAdapter.setSelectIndex(0);

		showFragment(auxiliaryFun_MapLocation_Fragment);

		

	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
//		receiver = new MyReceiver();
//		IntentFilter filter = new IntentFilter("android.intent.action.MY_BROADCAST");
//		mMainContainer.registerReceiver(receiver, filter);
////fragment内常用的localbroadcast
//		//LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
//		// 注册 SDK 广播监听者
//		IntentFilter iFilter = new IntentFilter();
//		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
//		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
//		mReceiver = new SDKReceiver();
//		mMainContainer.registerReceiver(mReceiver, iFilter);
	}

	/**
	 * 展示fragment,替换
	 */

	private void showFragment(Fragment f) {
		// TODO Auto-generated method stub
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.fl_auxiliaryfun_container, f);
		fragmentTransaction.commit();
	}

	private class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			distance = intent.getStringExtra("distance");
			 tvwDistance.setText("距离："+distance+"米");
		}
	}

//	/**
//	 * 构造广播监听类，监听 SDK key 验证以及网络异常广播
//	 */
//	private class SDKReceiver extends BroadcastReceiver {
//		
//		public void onReceive(Context context, Intent intent) {
//			String s = intent.getAction();
//			 tvwDistance = (TextView)v.findViewById(R.id.tvw_distance_auxiliary);
//			 LogUtils.e(TAG, "action:"+s);
//			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
//				LogUtils.e("SDK_key", s);
//				 tvwDistance.setText("key 验证出错!");
//				//ToastUtils.show(getActivity(), "key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
//			} else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
//				LogUtils.e("SDK_key", s);
//				 tvwDistance.setText("网络出错");
//				//ToastUtils.show(getActivity(), "网络出错");
//			}
//		}
//	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (receiver != null) {
			mMainContainer.unregisterReceiver(receiver);
			receiver = null;
		}
//		if (mReceiver!=null) {
//			mMainContainer.unregisterReceiver(mReceiver);
//			mReceiver=null;
//		}
	}



@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.imgbtn_map:
		showFragment(auxiliaryFun_MapLocation_Fragment);
		break;
case R.id.imgbtn_mapoffline:
	if (auxiliaryFun_MapOffline_Fragment == null) {
		auxiliaryFun_MapOffline_Fragment = new AuxiliaryFun_MapOffline_Fragment();
	}
	showFragment(auxiliaryFun_MapOffline_Fragment);	
		break;
	default:
		break;
	}
}

}
