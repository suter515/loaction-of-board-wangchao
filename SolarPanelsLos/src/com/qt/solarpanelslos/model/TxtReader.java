package com.qt.solarpanelslos.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.qt.solarpanelslos.bean.SolarPanelsLocation;

public class TxtReader {

	/**
	 * ͨ��һ��InputStream��ȡ����
	 * 
	 * @param inputStream
	 * @return
	 */
	public static String getString(InputStream inputStream) {
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(inputStream, "gbk");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(inputStreamReader);
		StringBuffer sb = new StringBuffer("");
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	/**
	 * 
	 * @param inputStream
	 * @return
	 */
	public static ArrayList<SolarPanelsLocation> getTxtData(InputStream inputStream) {
		
		ArrayList<SolarPanelsLocation> spList=new ArrayList<SolarPanelsLocation>();
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(inputStream, "gbk");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(inputStreamReader);
		StringBuffer sb = new StringBuffer("");
		SolarPanelsLocation sPanelsLocation=new SolarPanelsLocation();
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				//sb.append(line);
				String[]data=line.split("\\t");
				sPanelsLocation.setBoardId(data[0]);
				//sPanelsLocation.setFormFactory(data[3]);
				sPanelsLocation.setLat(Double.valueOf(data[2]).doubleValue());
				sPanelsLocation.setLng(Double.valueOf(data[1]).doubleValue());
				spList.add(sPanelsLocation);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return spList;
	}
	/**
	 * 
	 * @param filepath
	 * @return
	 */
	public static ArrayList<SolarPanelsLocation> getTxtData(String filepath) {
		ArrayList<SolarPanelsLocation> spList=new ArrayList<SolarPanelsLocation>();
		File file = new File(filepath);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(fileInputStream, "gbk");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(inputStreamReader);
		StringBuffer sb = new StringBuffer("");
		SolarPanelsLocation sPanelsLocation=new SolarPanelsLocation();
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				//sb.append(line);
				String[]data=line.split("");
				sPanelsLocation.setBoardId(data[0]);
				//sPanelsLocation.setFormFactory(data[3]);
				sPanelsLocation.setLat(Double.valueOf(data[2]).doubleValue());
				sPanelsLocation.setLng(Double.valueOf(data[1]).doubleValue());
				spList.add(sPanelsLocation);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return spList;
	}
	/**
	 * ͨ��txt�ļ���·����ȡ������
	 * 
	 * @param filepath
	 * @return
	 */
	public static String getString(String filepath) {
		File file = new File(filepath);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return getString(fileInputStream);
	}
}
