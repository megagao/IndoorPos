package org.hqu.indoor_pos.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import org.hqu.indoor_pos.bean.LoginUser;

public interface LoginInfoManage extends Remote {

	/**
	 * 查找所有用户信息
	 */
	public List<LoginUser> findAllLoginUser() throws RemoteException; 
	
	/**
	 * 保存用户
	 * @param loginUser
	 */
	public boolean saveLoginUser(LoginUser loginUser) throws RemoteException;
	
	/**
	 * 修改用户
	 * @param loginUser
	 */
	public boolean updateLoginUser(LoginUser loginUser) throws RemoteException;
	
	/**
	 * 删除用户
	 * @param userId
	 */
	public boolean deleteLoginUser(String userId) throws RemoteException;
	
	/**
	 * 根据用户名查询用户
	 * @param userId
	 */
	public LoginUser getLoginUserById(String userId) throws RemoteException;
	
}
