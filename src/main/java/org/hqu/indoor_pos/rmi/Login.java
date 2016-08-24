package org.hqu.indoor_pos.rmi;

import org.hqu.indoor_pos.bean.LoginUser;

public interface Login {
	
	/**
	 * 登录
	 * @param str
	 */
	public LoginUser login(String str); 
	
}
