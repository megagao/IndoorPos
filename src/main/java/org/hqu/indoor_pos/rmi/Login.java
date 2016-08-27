package org.hqu.indoor_pos.rmi;

import org.hqu.indoor_pos.bean.LoginUser;

/**
 * created on 2016年8月27日
 *
 * @description: 登录服务接口
 *
 * @author: megagao
 * @version: 0.0.1
 */
public interface Login {
	
	/**
	 * @description: 登录
	 *
	 * @param: str
	 * @return: 登录人员
	 */
	public LoginUser login(String str); 
	
}
