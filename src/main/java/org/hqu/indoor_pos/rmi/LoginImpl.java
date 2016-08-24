package org.hqu.indoor_pos.rmi;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hqu.indoor_pos.bean.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class LoginImpl implements Login{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public LoginImpl() throws RemoteException {
		super();
	}

	/**
	 * 登录
	 * @param str
	 */
	@Override
	public LoginUser login(String str) {
		
		String[] str1 = str.split(",");
		try{
			LoginUser loginUser = (LoginUser) this.jdbcTemplate.queryForObject(  
	                "select * from login where username = ? and password = ?",   
	                new Object[]{str1[0], str1[1]},  
	                new RowMapper<LoginUser>(){  
	  
	                    @Override  
	                    public LoginUser mapRow(ResultSet rs,int rowNum)throws SQLException {  
	                    	LoginUser lu = new LoginUser(rs.getString(2), rs.getString(3), rs.getString(4));  
	                        return lu;  
	                    }  
	        });
			return loginUser;
		}catch(Exception e){
			return null;
		}
	}
}
