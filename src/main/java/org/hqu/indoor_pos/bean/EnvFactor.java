package org.hqu.indoor_pos.bean;

import java.io.Serializable;

/**
 * <p>封装的环境影响因素对象</p>
 * <p>在进行定位的程序中，从数据库中或直接赋值得到这三个因素的值，然后用这三个值为类变量赋值。
 * 定位的算法通过类变量拿到这三个值进行定位运算。</p>
 * @author megagao
 */
public class EnvFactor implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/*房间id*/
	private Integer roomId;
	
	/* 高度补偿值*/
	private Double height;
	
	/*环境衰减因子*/
	private Double n;
	
	/*一米处接收到的rssi值*/
	private Double p0;
	
	public EnvFactor(Integer roomId, Double height, Double n, Double p0) {
		super();
		this.roomId = roomId;
		this.height = height;
		this.n = n;
		this.p0 = p0;
	}
	
	public EnvFactor() {
		super();
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getN() {
		return n;
	}

	public void setN(Double n) {
		this.n = n;
	}

	public Double getP0() {
		return p0;
	}

	public void setP0(Double p0) {
		this.p0 = p0;
	}

}
