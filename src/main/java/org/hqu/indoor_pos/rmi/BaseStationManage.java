package org.hqu.indoor_pos.rmi;

import java.util.List;

import org.hqu.indoor_pos.bean.BaseStation;

public interface BaseStationManage {
	
	/**
	 * 查找所有基站
	 */
	public List<BaseStation> findAllBaseStation(); 

	/**
	 * 根据房间id查找房间内的基站
	 * @param roomId
	 */
	public List<BaseStation> findBaseStationByRoomId(Integer roomId); 
	
	/**
	 * 保存基站
	 * @param baseStation
	 */
	public boolean saveBaseStation(BaseStation baseStation);
	
	/**
	 * 修改基站信息
	 * @param baseStation
	 */
	public boolean updateBaseStation(BaseStation baseStation);
	
	/**
	 * 删除基站
	 * @param baseId
	 */
	public boolean deleteBaseStation(String baseId);
	
	/**
	 * 根据基站id查询基站
	 * @param baseId
	 */
	public BaseStation getBaseStationById(String baseId);
	
}
