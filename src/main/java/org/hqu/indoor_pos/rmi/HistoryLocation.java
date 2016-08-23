package org.hqu.indoor_pos.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import org.hqu.indoor_pos.bean.Location;

public interface HistoryLocation extends Remote {
	
	/**
	 * 查找所有历史位置，以最近时间向下排列
	 */
	public List<Location> findAllHistoryLocation() throws RemoteException; 
	
	/**
	 * 按员工id查询该用户所有历史位置
	 * @param empId
	 */
	public List<Location> findHisLocByEmpId(String empId) throws RemoteException;
	
	/**
	 * 按时间查询所有定位结果
	 * @param fromTime, toTime
	 */
	public List<Location> findHisLocByTime(String fromTime, String toTime) throws RemoteException;
	
	/**
	 * 按时间及员工id查询
	 * @param empId, fromTime, toTime
	 */
	public List<Location> findHisLocByEmpIdAndTime(String empId, String fromTime, String toTime) throws RemoteException;
	
	/**
	 * 按房间id查询历史位置
	 * @param roomId
	 */
	public List<Location> findHisLocByRoomId(Integer roomId) throws RemoteException;
	
	/**
	 * 按时间及房间id查询
	 * @param roomId
	 */
	public List<Location> findHisLocByRoomIdAndTime(Integer roomId, String fromTime, String toTime) throws RemoteException;
	
}
