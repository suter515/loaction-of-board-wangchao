package com.qt.solarpanelslos.bean;

public class SolarPanelsLocation {
	private int _id;
	private String boardId;
	private double lat;
	private double lng;
	private String fromFactory;

	public int getId() {
		return _id;
	}

	public void setId(int id) {
		this._id = id;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getFormFactory() {
		return fromFactory;
	}

	public void setFormFactory(String formFactory) {
		this.fromFactory = formFactory;
	}

	public SolarPanelsLocation(int id, String boardId, double lat, double lng,
			String formFactory) {
		super();
		this._id = id;
		this.boardId = boardId;
		this.lat = lat;
		this.lng = lng;
		this.fromFactory = formFactory;
	}
	public SolarPanelsLocation(){
		
	}
}
