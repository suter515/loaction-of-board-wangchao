package com.qt.solarpanelslos.fragment.auxiliaryfun;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.qt.solarpanelslos.R;
import com.qt.solarpanelslos.activity.MainActivity;
import com.qt.solarpanelslos.fragment.BaseFragment;
import com.qt.solarpanelslos.utils.LogUtils;
import com.qt.solarpanelslos.utils.ToastUtils;

/**
 * @type AuxiliaryFun_MapOffline_Fragment TODO
 * @author SJT
 * @time 2015-7-20上午10:56:28
 */
public class AuxiliaryFun_MapOffline_Fragment extends BaseFragment implements MKOfflineMapListener {
	private final static String TAG = "AuxiliaryFun_MapOffline_Fragment";
	private AuxiliaryFun_MapOffline_Fragment auxiliaryFun_MapOffline_Fragment;
	private MainActivity mMainContainer = null;
	private View v;
	private AuxiliaryFun_MapLocation_Fragment auxiliaryFun_MapLocation_Fragment;

	private MKOfflineMap mOffline = null;
	private TextView cidView;
	private TextView stateView;
	private EditText cityNameView;

	private LinearLayout lm;
	private LinearLayout cl;
	

	/**
	 * 已下载的离线地图信息列表
	 */
	private ArrayList<MKOLUpdateElement> localMapList = null;
	private LocalMapAdapter lAdapter = null;

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

		// 实现回调离线地图接口
//		mOffline = new MKOfflineMap();
//		mOffline.init(this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stubvie
		mOffline = new MKOfflineMap();
		mOffline.init(this);
		// 控件资料
		v = inflater.inflate(R.layout.fragment_auxiliaryfun_mapoffline, container, false);

		cl = (LinearLayout) v.findViewById(R.id.citylist_layout);
		lm = (LinearLayout) v.findViewById(R.id.localmap_layout);
		setupView(v);
		// 搜索离线需市
		Button btnSearch = (Button) v.findViewById(R.id.offline_search);
		btnSearch.setOnClickListener(listener);

		// 开始下载
		Button btnStart = (Button) v.findViewById(R.id.offline_start);
		btnStart.setOnClickListener(listener);
		// 从SD卡导入离线地图安装包
		Button btnImport = (Button) v.findViewById(R.id.importFromSDCard);
		btnImport.setOnClickListener(listener);
		// 停止下载
		Button btnStop = (Button) v.findViewById(R.id.offline_stop);
		btnStop.setOnClickListener(listener);
		// 删除下载
		Button btnDelete = (Button) v.findViewById(R.id.offline_delete);
		btnDelete.setOnClickListener(listener);
		// 切换至城市列表
		Button btnClickCity = (Button) v.findViewById(R.id.clickCityListButton);
		btnClickCity.setOnClickListener(listener);
		// 切换至下载管理列表
		Button btnClickLmap = (Button) v.findViewById(R.id.clickLocalMapListButton);
		btnClickLmap.setOnClickListener(listener);

		return v;

	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int cityid = Integer.parseInt(cidView.getText().toString());
			switch (v.getId()) {
			case R.id.offline_search:
				ArrayList<MKOLSearchRecord> records = mOffline.searchCity(cityNameView.getText().toString());
				if (records == null || records.size() != 1)
					return;
				cidView.setText(String.valueOf(records.get(0).cityID));
				break;
			case R.id.offline_start:

				mOffline.start(cityid);
				ToastUtils.show(mMainContainer, "开始下载离线地图. cityid: " + cityid);
				lm.setVisibility(View.VISIBLE);
				cl.setVisibility(View.GONE);
				updateView();
				break;
			case R.id.offline_stop:

				mOffline.pause(cityid);
				ToastUtils.show(mMainContainer, "暂停下载离线地图. cityid: " + cityid);
				updateView();
				break;
			case R.id.importFromSDCard:
				int num = mOffline.importOfflineData();
				String msg = "";
				if (num == 0) {
					msg = "没有导入离线包，这可能是离线包放置位置不正确，或离线包已经导入过";
				} else {
					msg = String.format("成功导入 %d 个离线包，可以在下载管理查看", num);
				}
				ToastUtils.show(mMainContainer, msg);

				updateView();
				break;
			case R.id.offline_delete:
				mOffline.remove(cityid);
				ToastUtils.show(mMainContainer, "删除离线地图. cityid: " + cityid);
				updateView();
				break;
			case R.id.clickCityListButton:

				lm.setVisibility(View.GONE);
				cl.setVisibility(View.VISIBLE);
				break;
			case R.id.clickLocalMapListButton:

				lm.setVisibility(View.VISIBLE);
				cl.setVisibility(View.GONE);
				break;
			default:
				break;
			}

		}
	};

	private void setupView(View view) {

		cidView = (TextView) view.findViewById(R.id.cityid);
		cityNameView = (EditText) view.findViewById(R.id.city);
		stateView = (TextView) view.findViewById(R.id.state);

		ListView hotCityList = (ListView) view.findViewById(R.id.hotcitylist);
		ArrayList<String> hotCities = new ArrayList<String>();
		// 获取热闹城市列表
		ArrayList<MKOLSearchRecord> records1 = mOffline.getHotCityList();

		if (records1 != null) {
			LogUtils.i(TAG, "arraylist:" + records1.size());
			for (MKOLSearchRecord r : records1) {
				hotCities.add(r.cityName + "(" + r.cityID + ")" + "   --" + this.formatDataSize(r.size));
			}
		}
		ListAdapter hAdapter = (ListAdapter) new ArrayAdapter<String>(mMainContainer,
				android.R.layout.simple_list_item_1, hotCities);
		hotCityList.setAdapter(hAdapter);

		ListView allCityList = (ListView) view.findViewById(R.id.allcitylist);
		// 获取所有支持离线地图的城市
		ArrayList<String> allCities = new ArrayList<String>();
		ArrayList<MKOLSearchRecord> records2 = mOffline.getOfflineCityList();
		if (records1 != null) {
			for (MKOLSearchRecord r : records2) {
				allCities.add(r.cityName + "(" + r.cityID + ")" + "   --" + this.formatDataSize(r.size));
			}
		}
		ListAdapter aAdapter = (ListAdapter) new ArrayAdapter<String>(mMainContainer,
				android.R.layout.simple_list_item_1, allCities);
		allCityList.setAdapter(aAdapter);
		lm.setVisibility(View.GONE);
		cl.setVisibility(View.VISIBLE);

		// 获取已下过的离线地图信息
		localMapList = mOffline.getAllUpdateInfo();
		if (localMapList == null) {
			localMapList = new ArrayList<MKOLUpdateElement>();
		}

		ListView localMapListView = (ListView) view.findViewById(R.id.localmaplist);
		lAdapter = new LocalMapAdapter();
		localMapListView.setAdapter(lAdapter);

	}

	/**
	 * 更新状态显示
	 */
	public void updateView() {
		localMapList = mOffline.getAllUpdateInfo();
		if (localMapList == null) {
			localMapList = new ArrayList<MKOLUpdateElement>();
		}
		lAdapter.notifyDataSetChanged();
	}

	

	@Override
	public void onPause() {
		int cityid = Integer.parseInt(cidView.getText().toString());
		MKOLUpdateElement temp = mOffline.getUpdateInfo(cityid);
		if (temp != null && temp.status == MKOLUpdateElement.DOWNLOADING) {
			mOffline.pause(cityid);
		}
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	public String formatDataSize(int size) {
		String ret = "";
		if (size < (1024 * 1024)) {
			ret = String.format("%dK", size / 1024);
		} else {
			ret = String.format("%.1fM", size / (1024 * 1024.0));
		}
		return ret;
	}

	@Override
	public void onDestroy() {
		/**
		 * 退出时，销毁离线地图模块
		 */
		mOffline.destroy();
		super.onDestroy();
	}

	@Override
	public void onGetOfflineMapState(int type, int state) {
		LogUtils.e("onGetOfflineMapState", "onGetOfflineMapState");
		switch (type) {
		case MKOfflineMap.TYPE_DOWNLOAD_UPDATE: {
			MKOLUpdateElement update = mOffline.getUpdateInfo(state);
			// 处理下载进度更新提示
			if (update != null) {
				stateView.setText(String.format("%s : %d%%", update.cityName, update.ratio));
				LogUtils.e("sjt", Integer.toString(update.ratio));
				updateView();
			}

		}
			break;
		case MKOfflineMap.TYPE_NEW_OFFLINE:
			// 有新离线地图安装
			Log.d("OfflineDemo", String.format("add offlinemap num:%d", state));
			break;
		case MKOfflineMap.TYPE_VER_UPDATE:
			// 版本更新提示
			// MKOLUpdateElement e = mOffline.getUpdateInfo(state);

			break;
		}

	}

	/**
	 * 离线地图管理列表适配器
	 */
	private class LocalMapAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return localMapList.size();
		}

		@Override
		public Object getItem(int index) {
			return localMapList.get(index);
		}

		@Override
		public long getItemId(int index) {
			return index;
		}

		@Override
		public View getView(int index, View view, ViewGroup arg2) {
			MKOLUpdateElement e = (MKOLUpdateElement) getItem(index);
			view = View.inflate(mMainContainer, R.layout.fragment_offline_localmap_list, null);
			initViewItem(view, e);
			return view;
		}

		public void initViewItem(View view, final MKOLUpdateElement e) {
			Button display = (Button) view.findViewById(R.id.localmap_list_display);
			Button remove = (Button) view.findViewById(R.id.localmap_list_remove);
			TextView title = (TextView) view.findViewById(R.id.localmap_list_title);
			TextView update = (TextView) view.findViewById(R.id.localmap_list_update);
			TextView ratio = (TextView) view.findViewById(R.id.localmap_list_ratio);
			ratio.setText(e.ratio + "%");
			title.setText(e.cityName);
			if (e.update) {
				update.setText("可更新");
			} else {
				update.setText("最新");
			}
			if (e.ratio != 100) {
				display.setEnabled(false);
			} else {
				display.setEnabled(true);
			}
			remove.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					mOffline.remove(e.cityID);
					updateView();
				}
			});
			display.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// Intent intent = new Intent();
					// intent.putExtra("x", e.geoPt.longitude);
					// intent.putExtra("y", e.geoPt.latitude);
					// intent.setClass( AuxiliaryFun_MapOffline_Fragment.mM,
					// AuxiliaryFun_MapLocation_Fragment.class);
					// startActivity(intent);

					//
					// FragmentTransaction fragmentTransaction =
					// getFragmentManager().beginTransaction();
					// fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					// fragmentTransaction.replace(R.id.fl_auxiliaryfun_container,
					// auxiliaryFun_MapLocation_Fragment);
					// fragmentTransaction.commit();
				}
			});
		}

	}

}
