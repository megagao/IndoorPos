package org.hqu.indoor_pos.rmi;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hqu.indoor_pos.bean.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

public class LoginInfoManageImpl implements LoginInfoManage{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public LoginInfoManageImpl() throws RemoteException {
		super();
	}

	/**
	 * 查找所有用户信息
	 */
	@Override
	public List<LoginUser> findAllLoginUser() {

		return this.jdbcTemplate.query("select * from login",   
                new RowMapper<LoginUser>(){  
              
                    @Override  
                    public LoginUser mapRow(ResultSet rs, int rowNum) throws SQLException {  
                    	LoginUser loginUser = new LoginUser(rs.getString(1), rs.getString(2), 
                    			rs.getString(3), rs.getString(4));
                        return loginUser;  
                    }  
        });  
	}

	/**
	 * 保存用户
	 * @param loginUser
	 */
	@Override
	public boolean saveLoginUser(LoginUser loginUser) {
		
		try {
			this.jdbcTemplate.update("insert into login values (?, ?, ?, ?)",   
	                new Object[]{loginUser.getUserId(), loginUser.getUsername(),
						loginUser.getPassword(), loginUser.getRole()}); 
		} catch (Exception e) {
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
	public boolean updateLoginUser(final LoginUser loginUser) {
		
		try {
			this.jdbcTemplate.update(  
					"update login set username = ?, password = ?, role = ? where user_id = ?",   
	                new PreparedStatementSetter(){  
	                    @Override  
	                    public void setValues(PreparedStatement ps) throws SQLException {  
	                        ps.setString(1, loginUser.getUsername());
	                        ps.setString(2, loginUser.getPassword());
	                        ps.setString(3, loginUser.getRole());
	                        ps.setString(4, loginUser.getUserId());
	                    }  
	                }  
	        );  
		} catch (Exception e) {
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
	public boolean deleteLoginUser(final String userId) {
		
		try {
			this.jdbcTemplate.update(  
					"delete from login  where user_id = ?",   
	                new PreparedStatementSetter(){  
	                    @Override  
	                    public void setValues(PreparedStatement ps) throws SQLException {  
	                        ps.setString(1,userId);
	                    }  
	                }  
	        );  
		} catch (Exception e) {
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
	public LoginUser getLoginUserById(final String userId) {
		
		return (LoginUser) this.jdbcTemplate.queryForObject(  
                "select * from login where user_id = ?",   
                new Object[]{userId},  
                new RowMapper<LoginUser>(){  
  
                    @Override  
                    public LoginUser mapRow(ResultSet rs,int rowNum)throws SQLException {  
                    	LoginUser loginUser = new LoginUser(userId, rs.getString(2),
                    			rs.getString(3), rs.getString(4));  
                        return loginUser;  
                    }  
        }); 
	}

}
