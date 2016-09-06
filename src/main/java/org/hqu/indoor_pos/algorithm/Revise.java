package org.hqu.indoor_pos.algorithm;

/**
 * created on 2016年8月22日
 *
 * 对环境衰减因子进行修正
 *
 * @author  megagao
 * @version  0.0.1
 */
public class Revise {
	
	public double revise(double rssi,double d){
		return (-68-rssi)/(10*Math.log10(d));
	}
	
	public double revise(double rssi){
		return (-68-rssi)/(10*Math.log10(2));
	}
	
}
