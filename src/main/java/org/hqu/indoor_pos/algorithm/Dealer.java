package org.hqu.indoor_pos.algorithm;


import org.hqu.indoor_pos.bean.Location;

/**
 * created on 2016年8月22日
 *
 * 定位算法的父接口
 *
 * @author  megagao
 * @version  0.0.1
 */
public interface Dealer {
	
	/**
	 * 求定位终端坐标
	 *
	 * @param  str  接收到的一组基站组成的字符串(格式为“id,rssi;id,rssi........id,rssi;terminalID”)
	 * @return  返回定位结果对象
	 */
	public Location getLocation(String str);
	
}
