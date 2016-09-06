package org.hqu.indoor_pos.rmi;

import java.util.List;

import org.hqu.indoor_pos.bean.Location;

/**
 * created on 2016年8月26日
 *
 * 历史位置管理服务接口
 *
 * @author  megagao
 * @version  0.0.1
 */
public interface HistoryLocation {
	
	/**
	 * 查找所有历史位置
	 *
	 * @return  所有历史位置，以最近时间向下排列
	 */
	public List<Location> findAllHistoryLocation(); 
	
	/**
	 * 按员工id查询该用户所有历史位置
	 *
	 * @param  empId
	 * @return  该员工的所有历史位置，以最近时间向下排列
	 */
	public List<Location> findHisLocByEmpId(String empId);
	
	/**
	 * 按时间查询所有定位结果
	 *
	 * @param  fromTime
	 * @param  toTime
	 * @return  该时间段的所有历史位置
	 */
	public List<Location> findHisLocByTime(String fromTime, String toTime);
	
	/**
	 * 按时间及员工id查询
	 *
	 * @param  empId
	 * @param  fromTime
	 * @param  toTime 
	 * @return  该时间段的所有历史位置
	 */
	public List<Location> findHisLocByEmpIdAndTime(String empId, String fromTime, String toTime);
	
	/**
	 * 按房间id查询历史位置
	 *
	 * @param  roomId
	 * @return  该房间的所有历史位置
	 */
	public List<Location> findHisLocByRoomId(Integer roomId);
	
	/**
	 * 按时间及房间id查询
	 *
	 * @param  roomId
	 * @param  fromTime
	 * @param  toTime
	 * @return  该时间段内该房间的所有历史位置
	 */
	public List<Location> findHisLocByRoomIdAndTime(Integer roomId, String fromTime, String toTime);
	
}
