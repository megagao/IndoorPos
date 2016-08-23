package org.hqu.indoor_pos.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import org.hqu.indoor_pos.bean.RoomInfo;

public interface RoomManage extends Remote {
	
	/**
	 * 查找所有房间信息
	 */
	public List<RoomInfo> findAllRoomInfo() throws RemoteException; 
	
	/**
	 * 得到房间列表
	 */
	public List<String> findRoomList() throws RemoteException; 
	
	/**
	 * 根据roomName查询房间信息
	 * @param roomName  
	 */
	public RoomInfo getRoomInfoByName(String roomName) throws RemoteException; 
	
	/**
	 * 根据roomID查询房间信息
	 * @param roomId
	 */
	public RoomInfo getRoomInfoById(Integer roomId) throws RemoteException;
	
	/**
	 * 保存房间信息
	 * @param roomInfo
	 */
	public boolean saveRoomInfo(RoomInfo roomInfo) throws RemoteException;
	
	/**
	 * 修改房间信息
	 * @param roomInfo
	 */
	public boolean updateRoomInfo(RoomInfo roomInfo) throws RemoteException;
	
	/**
	 * 删除房间信息
	 * @param roomId
	 */
	public boolean deleteRoomInfo(Integer roomId) throws RemoteException;

}
