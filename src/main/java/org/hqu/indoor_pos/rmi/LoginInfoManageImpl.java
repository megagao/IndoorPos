package org.hqu.indoor_pos.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hqu.indoor_pos.bean.LoginUser;
import org.hqu.indoor_pos.util.DBUtil;

public class LoginInfoManageImpl extends UnicastRemoteObject implements LoginInfoManage{

	private static final long serialVersionUID = 1L;

	private Connection conn = DBUtil.getConnection();
	
	public LoginInfoManageImpl() throws RemoteException {
		super();
	}

	/**
	 * 查找所有用户信息
	 */
	@Override
	public List<LoginUser> findAllLoginUser() throws RemoteException {

		List<LoginUser> loginUsers = new ArrayList<LoginUser>();
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from login");
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				LoginUser loginUser = new LoginUser(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
				loginUsers.add(loginUser);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loginUsers;
	}

	/**
	 * 保存用户
	 * @param loginUser
	 */
	@Override
	public boolean saveLoginUser(LoginUser loginUser) throws RemoteException {
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("insert into login values (?, ?, ?, ?)");
			stat.setString(1, loginUser.getUserId());
			stat.setString(2, loginUser.getUsername());
			stat.setString(3, loginUser.getPassword());
			stat.setString(4, loginUser.getRole());
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 修改用户
	 * @param loginUser
	 */
	@Override
	public boolean updateLoginUser(LoginUser loginUser) throws RemoteException {
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("update login set username = ?, password = ?, role = ? where user_id = ?");
			stat.setString(1, loginUser.getUsername());
			stat.setString(2, loginUser.getPassword());
			stat.setString(3, loginUser.getRole());
			stat.setString(4, loginUser.getUserId());
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 删除用户
	 * @param userId
	 */
	@Override
	public boolean deleteLoginUser(String userId) throws RemoteException {
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("delete from login  where user_id = ?");
			stat.setString(1,userId);
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 根据用户名查询用户
	 * @param userId
	 */
	@Override
	public LoginUser getLoginUserById(String userId) throws RemoteException {
		
		LoginUser loginUser = null;
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from login where user_id = ?");
			stat.setString(1, userId);
			ResultSet rs = stat.executeQuery();
			rs.next();
			loginUser = new LoginUser(userId, rs.getString(2), rs.getString(3), rs.getString(4));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loginUser;
	}

}
