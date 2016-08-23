package org.hqu.indoor_pos.bean;

import java.io.Serializable;

/**
 * <p>封装的基站对象</p>
 * @author megagao
 */
public class BleBase implements Comparable<BleBase>,Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/* 基站id*/
	private String id;  
	
	/* 接收到的信号强度*/
	private Integer rssi;  
	
	public BleBase(String string, int rssi) {
		this.id = string;
		this.rssi =  rssi;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getRssi() {
		return rssi;
	}
	public void setRssi(Integer rssi) {
		this.rssi = rssi;
	}
	
	public double getDistance(double height, double n, double p0){
		
		/*基站到定位终端的直线距离*/
		double rawDistance;
		
		rawDistance =Math.pow(10, (p0-rssi)/(10*n));
		
		/*基站到定位终端的水平距离*/
		return Math.sqrt(Math.pow(rawDistance, 2)-Math.pow(height, 2));
	}
	
	@Override
	public int compareTo(BleBase base) {
		// TODO Auto-generated method stub
		if(rssi>base.rssi){
			return 1;
		}else{
			return -1;
		}
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "该基站ID为："+id+",信号强度值为："+rssi;
	}
	
	
	
}


