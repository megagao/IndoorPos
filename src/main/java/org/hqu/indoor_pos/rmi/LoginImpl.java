package org.hqu.indoor_pos.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hqu.indoor_pos.bean.LoginUser;
import org.hqu.indoor_pos.util.DBUtil;

public class LoginImpl extends UnicastRemoteObject implements Login{

	private static final long serialVersionUID = 1L;

	public LoginImpl() throws RemoteException {
		super();
	}

	/**
	 * 登录
	 * @param str
	 */
	@Override
	public LoginUser login(String str) throws RemoteException {
		
		LoginUser li = null;
		
		String[] str1 = str.split(",");
		
		Connection conn = DBUtil.getConnection();
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from login where username = ? and password = ?");
			stat.setString(1, str1[0]);
			stat.setString(2, str1[1]);
			ResultSet rs = stat.executeQuery();
			if(rs.next()){
				li = new LoginUser(rs.getString(2), rs.getString(3), rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return li;
	}

}
