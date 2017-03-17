package org.hqu.indoor_pos.rmi;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hqu.indoor_pos.bean.EnvFactor;
import org.hqu.indoor_pos.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

/**
 * created on 2016年8月26日
 *
 * 环境因子管理服务接口实现类
 *
 * @author  megagao
 * @version  0.0.1
 */
public class EnvFactorManageImpl implements EnvFactorManage{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public EnvFactorManageImpl() throws RemoteException {
		super();
	}

	@Override
	public List<EnvFactor> findAllEnvFactor() {
		return this.jdbcTemplate.query("select * from env_factor",   
                new RowMapper<EnvFactor>(){  
              
                    @Override  
                    public EnvFactor mapRow(ResultSet rs, int rowNum) throws SQLException {  
                    	EnvFactor envFactor = new EnvFactor(rs.getInt(1), rs.getDouble(2), rs.getDouble(3), rs.getDouble(4));
                        return envFactor;  
                    }  
        });  
	}

	@Override
	public EnvFactor findEnvFactorByRoomId(Integer roomId) {
		return (EnvFactor) this.jdbcTemplate.queryForObject(  
                "select * from env_factor where room_id = ?",   
                new Object[]{roomId},  
                new RowMapper<EnvFactor>(){  
  
                    @Override  
                    public EnvFactor mapRow(ResultSet rs,int rowNum)throws SQLException {  
                    	EnvFactor envFactor = new EnvFactor(rs.getInt(1), rs.getDouble(2), rs.getDouble(3), rs.getDouble(4));
                        return envFactor;  
                    }  
        }); 
	}

	@Override
	public boolean saveEnvFactor(EnvFactor envFactor) {
		this.jdbcTemplate.update("insert into env_factor (room_id, height, atten_factor, p0) values (?, ?, ?, ?)",   
                new Object[]{envFactor.getRoomId(), envFactor.getHeight(), envFactor.getN(), envFactor.getP0()}); 
		
		try {
			this.jdbcTemplate.update("insert into env_factor (room_id, height, atten_factor, p0) values (?, ?, ?, ?)",   
	                new Object[]{envFactor.getRoomId(), envFactor.getHeight(), envFactor.getN(), envFactor.getP0()}); 
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		Server.envFactors.put(envFactor.getRoomId(), new Double[]{envFactor.getHeight(), envFactor.getN(), envFactor.getP0()});
		return true;
	}

	@Override
	public boolean updateEnvFactor(final EnvFactor envFactor) {
		try {
			this.jdbcTemplate.update(  
					"update env_factor set height = ?, atten_factor = ?, p0 = ? where room_id = ?",   
	                new PreparedStatementSetter(){  
	                    @Override  
	                    public void setValues(PreparedStatement ps) throws SQLException {  
	                        ps.setDouble(1, envFactor.getHeight());
	                        ps.setDouble(2, envFactor.getN());
	                        ps.setDouble(3, envFactor.getP0());
	                        ps.setInt(4, envFactor.getRoomId());
	                    }  
	                }  
	        );  
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		Server.envFactors.put(envFactor.getRoomId(), new Double[]{envFactor.getHeight(), envFactor.getN(), envFactor.getP0()});
		return true;
	}

	@Override
	public boolean deleteEnvFactor(final Integer roomId) {
		try {
			this.jdbcTemplate.update(  
					"delete from env_factor  where room_id = ?",   
	                new PreparedStatementSetter(){  
	                    @Override  
	                    public void setValues(PreparedStatement ps) throws SQLException {  
	                        ps.setInt(1, roomId); 
	                    }  
	                }  
	        );  
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		Server.envFactors.remove(roomId);
		return true;	
	}
}
