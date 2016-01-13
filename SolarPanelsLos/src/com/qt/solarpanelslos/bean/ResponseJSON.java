package com.qt.solarpanelslos.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 存放返回值的单例对象
 * 
 * @author XueQi
 * 
 */
public class ResponseJSON {
	private static ResponseJSON m_intance = null;

	private List<Object> jsonObjects = new ArrayList<Object>();
	private int workCount;// 访问执行次数

	private ResponseJSON() {
		// TODO Auto-generated constructor stub
	}

	synchronized public static ResponseJSON getInstance() {
		if (m_intance == null) {
			m_intance = new ResponseJSON();
		}
		return m_intance;
	}

	public List<Object> getJsonObjects() {
		return jsonObjects;
	}

	public void setJsonObjects(List<Object> jsonObjects) {
		this.jsonObjects = jsonObjects;
	}

	public int getWorkCount() {
		return workCount;
	}

	public void setWorkCount(int workCount) {
		this.workCount = workCount;
	}

}
