package org.hqu.indoor_pos.rmi;

import java.util.List;

import org.hqu.indoor_pos.bean.LoginUser;

public interface LoginInfoManage {

	/**
	 * 查找所有用户信息
	 */
	public List<LoginUser> findAllLoginUser(); 
	
	/**
	 * 保存用户
	 * @param loginUser
	 */
	public boolean saveLoginUser(LoginUser loginUser);
	
	/**
	 * 修改用户
	 * @param loginUser
	 */
	public boolean updateLoginUser(LoginUser loginUser);
	
	/**
	 * 删除用户
	 * @param userId
	 */
	public boolean deleteLoginUser(String userId);
	
	/**
	 * 根据用户名查询用户
	 * @param userId
	 */
	public LoginUser getLoginUserById(String userId);
	
}
