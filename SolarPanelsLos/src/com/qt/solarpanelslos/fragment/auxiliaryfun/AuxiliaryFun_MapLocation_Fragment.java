package com.qt.solarpanelslos.fragment.auxiliaryfun;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LLSInterface;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.qt.solarpanelslos.R;
import com.qt.solarpanelslos.activity.MainActivity;
import com.qt.solarpanelslos.fragment.BaseFragment;
import com.qt.solarpanelslos.utils.LogUtils;
import com.qt.solarpanelslos.utils.PhoneUtils;


/**
 * @type AuxiliaryFun_MapLocation_Fragment TODO
 * @author SJT
 * @time 2015-7-20上午10:50:08
 */
public class AuxiliaryFun_MapLocation_Fragment extends BaseFragment {
	private static final String TAG = "AuxiliaryFun_MapLocation_Fragment";
	private MainActivity mMainContainer = null;
	private int cmView;
	private String distance;
	private LatLng latLngPoint;
	private LatLng latLngPstart;
	private LatLng latLngPstop;
	

	// 定位相关
	private LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	boolean isFirstLoc = true;// 是否首次定位
	private LocationMode mCurrentMode;
	private BitmapDescriptor mCurrentMarker;
	private PolylineOptions polylineOption;
	private MarkerOptions startoption;
	private MarkerOptions stopoption;
	// UI相关
	private OnCheckedChangeListener radioButtonListener;

	private Button requestLocButton;
	private Button getDisStart;
	private Button getDisStop;
	private TextView tvwDistance;
	private Button reSet;

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

		// 定位初始化
		mLocClient = new LocationClient(getActivity());
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();// ?????
		
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//mMainContainer.showLeft();
		View view = inflater.inflate(R.layout.fragment_auxiliaryfun_maplocation, container, false);
		init(view);
		// 地图初始化
		mMapView = (MapView) view.findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 获取距离控件
		// tvwDistance = (TextView) view.findViewById(R.id.tvw_distance);

//		// 构造定位数据  
//		MyLocationData locData = new MyLocationData.Builder()  
//		    .accuracy(location.getRadius())  
//		    // 此处设置开发者获取到的方向信息，顺时针0-360  
//		    .direction(100).latitude(location.getLatitude())  
//		    .longitude(location.getLongitude()).build();  
//		// 设置定位数据  
//		mBaiduMap.setMyLocationData(locData);  
//		// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）  
//		mCurrentMarker = BitmapDescriptorFactory  
//		    .fromResource(R.drawable.icon_geo);  
//		MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);  
//		mBaiduMap.setMyLocationConfiguration();  
//		// 当不需要定位图层时关闭定位图层  
//		mBaiduMap.setMyLocationEnabled(false);
		
		
		
		OnMapClickListener onMapClickListener = new OnMapClickListener() {

			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onMapClick(LatLng arg0) {
				// TODO Auto-generated method stub
				latLngPoint = arg0;
				// LogUtils.i("sjt", arg0);
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

				builder.setTitle("请选择");
				final String[] sex = { "起点", "终点" };

				// 设置一个单项选择下拉框
				// 那个都不设置（-1）
				builder.setSingleChoiceItems(sex, -1, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							latLngPstart = latLngPoint;

							// 构建开始点图标
							BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_start_map);
							// 构建MarkerOption，用于在地图上添加Marker
							startoption = new MarkerOptions().position(latLngPstart).icon(bitmap);
							// 在地图上添加Marker，并显示
							mBaiduMap.addOverlay(startoption);
							break;

						case 1:
							latLngPstop = latLngPoint;

							// 构建终点图标
							BitmapDescriptor bitmap2 = BitmapDescriptorFactory
									.fromResource(R.drawable.icon_destnation_map);
							// 构建MarkerOption，用于在地图上添加Marker
							stopoption = new MarkerOptions().position(latLngPstop).icon(bitmap2);
							// 在地图上添加Marker，并显示
							mBaiduMap.addOverlay(stopoption);
							break;
						}

					}
				});
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (latLngPstop != null) {
							// 画线
							List<LatLng> pts = new ArrayList<LatLng>();
							pts.add(latLngPstart);
							pts.add(latLngPstop);
							// polylineOption.points(pts).color(0xffff0000);
							polylineOption = new PolylineOptions().points(pts).color(0xffff0000);
							// 在地图上添加折线Option，用于显示
							mBaiduMap.addOverlay(polylineOption);
							// 计算起始点的距离
							// Math.round(DistanceUtil.getDistance(latLngPstop,
							// latLngPstart));
							distance = Long.toString(Math.round(DistanceUtil.getDistance(latLngPstop, latLngPstart)));
							// distance =
							// Double.toString(DistanceUtil.getDistance(latLngPstop,
							// latLngPstart));
							// tvwDistance.setText("距离：" + distance + "米");


							// 得到距离
							Intent intent = new Intent();
							intent.putExtra("distance", distance);
							intent.setAction("android.intent.action.MY_BROADCAST");
							// 发送一个广播
							mMainContainer.sendBroadcast(intent);

						}

						dialog.dismiss();
					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

				AlertDialog dialog = builder.create();

				dialog.show();
				Window dialogWindow = dialog.getWindow();
				WindowManager.LayoutParams lp = dialogWindow.getAttributes();
				dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
				lp.x = 10; // 新位置X坐标
				lp.y = 10; // 新位置Y坐标

				// 获取当前屏幕的宽度高度
				DisplayMetrics outMetrics = new DisplayMetrics();
				mMainContainer.getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
				LogUtils.d("lll", "Density : " + PhoneUtils.getScreenDensity(mMainContainer) + "width:"
						+ outMetrics.widthPixels + "  height:" + outMetrics.heightPixels);
				lp.width = (int) (outMetrics.widthPixels * 0.5);
				lp.height = (int) (outMetrics.heightPixels * 0.4);
				// WindowManager m =
				// getActivity().getWindow().getWindowManager();
				// Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
				// lp.height = (int) (d.getHeight() * 0.3); // 高度设置为屏幕的0.6
				// lp.width = (int) (d.getWidth() * 0.3); // 宽度设置为屏幕的0.65
				dialogWindow.setAttributes(lp);
			}
		};
		mBaiduMap.setOnMapClickListener(onMapClickListener);
		return view;
	}

	private void init(View view) {

		requestLocButton = (Button) view.findViewById(R.id.button1);

		mCurrentMode = LocationMode.NORMAL;
		requestLocButton.setText("普通");
		OnClickListener btnClickListener = new OnClickListener() {
			public void onClick(View v) {
				switch (mCurrentMode) {
				case NORMAL:
					requestLocButton.setText("跟随");
					mCurrentMode = LocationMode.FOLLOWING;
					mBaiduMap
							.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker));
					break;
				case COMPASS:
					requestLocButton.setText("普通");
					mCurrentMode = LocationMode.NORMAL;
					mBaiduMap
							.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker));
					break;
				case FOLLOWING:
					requestLocButton.setText("罗盘");
					mCurrentMode = LocationMode.COMPASS;
					mBaiduMap
							.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker));
					break;
				}
			}
		};
		requestLocButton.setOnClickListener(btnClickListener);

		RadioGroup group = (RadioGroup) view.findViewById(R.id.radioGroup);
		radioButtonListener = new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.defaulticon) {
					// 传入null则，恢复默认图标
					mCurrentMarker = null;
					mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
				}
				if (checkedId == R.id.customicon) {
					// 修改为自定义marker
					mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
					mBaiduMap
							.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker));
				}
			}
		};
		group.setOnCheckedChangeListener(radioButtonListener);

		reSet = (Button) view.findViewById(R.id.btn_reset);
		reSet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// mBaiduMap.addOverlay(polylineOption).remove();
				// mBaiduMap.addOverlay(stopoption).remove();
				// mBaiduMap.addOverlay(startoption).remove();
				//
				mBaiduMap.clear();
			}
		});
	}

	/**
	 * 定位SDK监听函数
	 */
	private class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
			// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
	

	@Override
	public void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	public void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		// LogUtils.i("AuxiliaryFunFragment","onDestroy");
		// LogUtils.i("AuxiliaryFunFragment",
		// "onDestroyisFirstLoc:"+isFirstLoc);
		isFirstLoc = true;
		if (mLocClient != null) {
			// 退出时销毁定位
			mLocClient.stop();

		}
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}

}
