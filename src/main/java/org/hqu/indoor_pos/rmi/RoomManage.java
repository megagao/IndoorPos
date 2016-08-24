package org.hqu.indoor_pos.rmi;

import java.util.List;

import org.hqu.indoor_pos.bean.RoomInfo;

public interface RoomManage {
	
	/**
	 * 查找所有房间信息
	 */
	public List<RoomInfo> findAllRoomInfo(); 
	
	/**
	 * 得到房间列表
	 */
	public List<String> findRoomList(); 
	
	/**
	 * 根据roomName查询房间信息
	 * @param roomName  
	 */
	public RoomInfo getRoomInfoByName(String roomName); 
	
	/**
	 * 根据roomID查询房间信息
	 * @param roomId
	 */
	public RoomInfo getRoomInfoById(Integer roomId);
	
	/**
	 * 保存房间信息
	 * @param roomInfo
	 */
	public boolean saveRoomInfo(RoomInfo roomInfo);
	
	/**
	 * 修改房间信息
	 * @param roomInfo
	 */
	public boolean updateRoomInfo(RoomInfo roomInfo);
	
	/**
	 * 删除房间信息
	 * @param roomId
	 */
	public boolean deleteRoomInfo(Integer roomId);

}
