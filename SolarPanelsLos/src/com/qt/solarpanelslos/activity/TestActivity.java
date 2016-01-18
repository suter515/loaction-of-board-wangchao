package com.qt.solarpanelslos.activity;

import com.baidu.location.f;
import com.qt.solarpanelslos.R;
import com.qt.solarpanelslos.bean.SolarPanelsLocation;
import com.qt.solarpanelslos.db.dao.SolarPanelsDao;
import com.qt.solarpanelslos.utils.StringUtils;
import com.qt.solarpanelslos.utils.ToastUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TestActivity extends Activity implements OnClickListener{

	private EditText edtLat;
	private EditText edtLng;
	private EditText edtFactory;
	private EditText edtBoardID;
	private SolarPanelsDao soPanelsDao;
	private TextView tvwDataCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_importdata);
		soPanelsDao = new SolarPanelsDao();
		
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		Button btnBack=(Button)findViewById(R.id.btn_callback);
		btnBack.setOnClickListener(this);
		//
		Button btnSubmitBod=(Button)findViewById(R.id.btn_submit_boardinfo);
		btnSubmitBod.setOnClickListener(this);
		edtLat = (EditText)findViewById(R.id.edt_lat);
		edtLng = (EditText)findViewById(R.id.edt_lng);
		edtFactory = (EditText)findViewById(R.id.edt_factory);
		edtBoardID = (EditText)findViewById(R.id.edt_boardID);
		tvwDataCount = (TextView)findViewById(R.id.tvw_data_now);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_submit_boardinfo:
			submitBoardInfo();
			break;
		case R.id.btn_callback:
			this.finish();
			break;
		default:
			break;
		}
	}

	private void submitBoardInfo() {
		// TODO Auto-generated method stub
		String bid=edtBoardID.getText().toString();
		String lat=edtLat.getText().toString();
		String lng=edtLng.getText().toString();
		String fact=edtFactory.getText().toString();
//		SolarPanelsLocation spLocation=new SolarPanelsLocation(
//				bid,
//				StringUtils.stringToDouble(lat),
//				StringUtils.stringToDouble(lng),
//				fact);
		
		SolarPanelsLocation spLocation=new SolarPanelsLocation();
		spLocation.setBoardId(bid);
		spLocation.setLat(StringUtils.stringToDouble(lat));
		spLocation.setLng(StringUtils.stringToDouble(lng));
		spLocation.setFormFactory(fact);
		if (!StringUtils.isBlank(bid)&&!StringUtils.isBlank(lat)&&!StringUtils.isBlank(lng)&&!StringUtils.isBlank(fact)
				) {
			soPanelsDao.insertSolarPanelsLocation(TestActivity.this, spLocation);
			ToastUtils.show(TestActivity.this, "导入成功");
		}else {
			ToastUtils.show(TestActivity.this, "请将数据填写完整");
		}
		
		int a=soPanelsDao.getMaxId(TestActivity.this);
		tvwDataCount.setText(String.valueOf(a));
	}

}
