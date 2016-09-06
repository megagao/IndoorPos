package org.hqu.indoor_pos.bean;
	
/**
 * created on 2016年8月25日
 *
 * 坐标bean
 *
 * @author  megagao
 * @version  0.0.1
 */
public class Coordinate {
	
	/*横坐标*/
	private double x;
	
	/*纵坐标*/
	private double y;
		
		
	@Override
	public String toString() {
		return "坐标Coordinate为 [x=" + x + ", y=" + y + "]";
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

}
