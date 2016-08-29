package org.hqu.indoor_pos.rmi;

import java.util.List;

import org.hqu.indoor_pos.bean.RoomInfo;

/**
 * created on 2016年8月27日
 *
 * @description: 房间管理服务接口
 *
 * @author: megagao
 * @version: 0.0.1
 */
public interface RoomManage {
	
	/**
	 * @description: 查找所有房间信息
	 *
	 * @return: 所有房间信息
	 */
	public List<RoomInfo> findAllRoomInfo(); 
	
	/**
	 * @description: 得到房间列表
	 *
	 * @return: 房间列表
	 */
	public List<String> findRoomList(); 
	
	/**
	 * @description: 根据roomName查询房间信息
	 *
	 * @param： roomName
	 * @return: 房间信息
	 */
	public RoomInfo getRoomInfoByName(String roomName); 
	
	/**
	 * @description: 根据roomID查询房间信息
	 *
	 * @param： roomId
	 * @return: 房间信息
	 */
	public RoomInfo getRoomInfoById(Integer roomId);
	
	/**
	 * @description: 保存房间信息
	 *
	 * @param: roomInfo
	 * @return: 是否成功
	 */
	public boolean saveRoomInfo(RoomInfo roomInfo);
	
	/**
	 * @description: 修改房间信息
	 *
	 * @param: roomInfo
	 * @return: 是否成功
	 */
	public boolean updateRoomInfo(RoomInfo roomInfo);
	
	/**
	 * @description: 删除房间信息
	 *
	 * @param: roomId
	 * @return: 是否成功
	 */
	public boolean deleteRoomInfo(Integer roomId);

}
