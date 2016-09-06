package org.hqu.indoor_pos.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * created on 2016年8月25日
 *
 * 定位结果bean
 *
 * @author  megagao
 * @version  0.0.1
 */
public class Location implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/*员工id*/
	private String empId;
	
	/*所处房间id*/
	private Integer roomId;
	
	/*x轴坐标*/
	private Double xAxis;
	
	/*y轴坐标*/
	private Double yAxis;
	
	/*时间戳*/
	private Timestamp timeStamp;

	public Location(String empId, Integer roomId, Double xAxis, Double yAxis,
			Timestamp timeStamp) {
		super();
		this.empId = empId;
		this.roomId = roomId;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.timeStamp = timeStamp;
	}

	public Location() {
		super();
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getEmpId() {
		return empId;
	}
	
	public void setEmpId(String empId) {
		this.empId = empId;
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

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return  empId +"在"+ timeStamp + "时的位置是" + xAxis + ","
				+ yAxis;
	}
	
}
