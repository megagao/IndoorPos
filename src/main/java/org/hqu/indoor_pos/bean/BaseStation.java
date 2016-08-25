package org.hqu.indoor_pos.bean;

import java.io.Serializable;

/**
 * 
 * created on 2016年8月25日
 *
 * @description: 基站bean
 *
 * @author: megagao
 * @version: 0.0.1
 */
public class BaseStation implements Serializable{

	private static final long serialVersionUID = 1L;

	/*基站ID*/
	private String baseId;
	
	/*房间ID*/
	private Integer roomId;
	
	/*基站坐标*/
	private Double xAxis;
	
	private Double yAxis;
	
	public BaseStation(String baseId, Integer roomId, Double xAxis, Double yAxis) {
		super();
		this.baseId = baseId;
		this.roomId = roomId;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
	}

	public BaseStation() {
		
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Double getxAxis() {
		return xAxis;
	}

	public void setxAxis(Double xAxis) {
		this.xAxis = xAxis;
	}

	public Double getyAxis() {
		return yAxis;
	}

	public void setyAxis(Double yAxis) {
		this.yAxis = yAxis;
	}

	@Override
	public String toString() {
		return "该基站id为：" + baseId + "，所在房间为："
				+ roomId + "，x轴坐标为：" + xAxis + "，y轴坐标为:" + yAxis + "]";
	}
	
	
}
