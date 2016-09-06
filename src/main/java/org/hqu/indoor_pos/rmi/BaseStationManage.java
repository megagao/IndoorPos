package org.hqu.indoor_pos.rmi;

import java.util.List;

import org.hqu.indoor_pos.bean.BaseStation;

/**
 * created on 2016年8月26日
 *
 * 基站管理服务接口
 *
 * @author  megagao
 * @version  0.0.1
 */
public interface BaseStationManage {
	
	/**
	 * 查找所有基站
	 *
	 * @return  所有基站信息
	 */
	public List<BaseStation> findAllBaseStation(); 

	/**
	 * 根据房间id查找房间内的基站
	 *
	 * @param  roomId
	 * @return  基站列表
	 */
	public List<BaseStation> findBaseStationByRoomId(Integer roomId); 
	
	/**
	 * 保存基站
	 *
	 * @param  baseStation
	 * @return  是否成功
	 */
	public boolean saveBaseStation(BaseStation baseStation);
	
	/**
	 * 修改基站信息
	 *
	 * @param  baseStation
	 * @return  是否成功
	 */
	public boolean updateBaseStation(BaseStation baseStation);
	
	/**
	 * 删除基站
	 *
	 * @param  baseId
	 * @return  是否成功
	 */
	public boolean deleteBaseStation(String baseId);
	
	/**
	 * 根据基站id查询基站
	 *
	 * @param  baseId
	 * @return  基站信息
	 */
	public BaseStation getBaseStationById(String baseId);
	
}
