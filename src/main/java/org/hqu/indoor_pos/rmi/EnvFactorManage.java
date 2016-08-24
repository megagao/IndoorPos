package org.hqu.indoor_pos.rmi;

import java.util.List;

import org.hqu.indoor_pos.bean.EnvFactor;

public interface EnvFactorManage {

	/**
	 * 查找所有房间的环境因子
	 */
	public List<EnvFactor> findAllEnvFactor(); 
	
	/**
	 * 根据房间id查找相应的环境因子
	 * @param roomId
	 */
	public EnvFactor findEnvFactorByRoomId(Integer roomId); 
	
	/**
	 * 保存环境因子
	 * @param envFactor
	 */
	public boolean saveEnvFactor(EnvFactor envFactor);
	
	/**
	 * 修改环境因子
	 * @param envFactor
	 */
	public boolean updateEnvFactor(EnvFactor envFactor);
	
	/**
	 * 删除环境因子
	 * @param roomId
	 */
	public boolean deleteEnvFactor(Integer roomId);
	
}
