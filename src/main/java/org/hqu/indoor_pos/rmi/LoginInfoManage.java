package org.hqu.indoor_pos.rmi;

import java.util.List;

import org.hqu.indoor_pos.bean.LoginUser;

/**
 * created on 2016年8月27日
 *
 * 登录人员管理服务接口
 *
 * @author  megagao
 * @version  0.0.1
 */
public interface LoginInfoManage {

	/**
	 * 查找所有登录人员
	 *
	 * @return  所有登录人员
	 */
	public List<LoginUser> findAllLoginUser(); 
	
	/**
	 * 保存登录人员
	 *
	 * @param  loginUser
	 * @return  是否成功
	 */
	public boolean saveLoginUser(LoginUser loginUser);
	
	/**
	 * 修改登录人员
	 *
	 * @param  loginUser
	 * @return  是否成功
	 */
	public boolean updateLoginUser(LoginUser loginUser);
	
	/**
	 * 删除登录人员
	 *
	 * @param  userId
	 * @return  是否成功
	 */
	public boolean deleteLoginUser(String userId);
	
	/**
	 * 根据用户名查询登录人员
	 *
	 * @param  userId
	 * @return  登录人员
	 */
	public LoginUser getLoginUserById(String userId);
	
}
