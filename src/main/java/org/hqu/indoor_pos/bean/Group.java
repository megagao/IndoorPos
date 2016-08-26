package org.hqu.indoor_pos.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * created on 2016年8月25日
 *
 * @description: 封装的分组bean
 *
 * @author: megagao
 * @version: 0.0.1
 */
public class Group {
	
	/*rssi值列表*/
	private List<Integer> rssis = new ArrayList<Integer>();

	public List<Integer> getRssis() {
		return rssis;
	}

	public void setRssis(List<Integer> rssis) {
		this.rssis = rssis;
	}
}
