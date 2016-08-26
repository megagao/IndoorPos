package org.hqu.indoor_pos.rmi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hqu.indoor_pos.bean.BaseStation;
import org.hqu.indoor_pos.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

/**
 * created on 2016年8月26日
 *
 * @description: 基站管理服务实现对象
 *
 * @author: megagao
 * @version: 0.0.1
 */
public class BaseStationManageImpl implements BaseStationManage{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public BaseStationManageImpl() {
		super();
	}
	
	@Override
	public List<BaseStation> findAllBaseStation() {

		return this.jdbcTemplate.query("select * from base_station",   
                new RowMapper<BaseStation>(){  
              
                    @Override  
                    public BaseStation mapRow(ResultSet rs, int rowNum) throws SQLException {  
                    	BaseStation base = new BaseStation(rs.getString(1),rs.getInt(2),
                    			rs.getDouble(3),rs.getDouble(4));
                        return base;  
                    }  
        });  
	}
	
	@Override
	public List<BaseStation> findBaseStationByRoomId(Integer roomId) {
		
		return this.jdbcTemplate.query("select * from base_station where room_id = ?",
				new Object[]{roomId},   
                new int[]{java.sql.Types.INTEGER},
                new RowMapper<BaseStation>(){  
              
                    @Override  
                    public BaseStation mapRow(ResultSet rs, int rowNum) throws SQLException {  
                    	BaseStation base = new BaseStation(rs.getString(1),rs.getInt(2),
                    			rs.getDouble(3),rs.getDouble(4));
                        return base;  
                    }  
        });  
	}

	@Override
	public boolean saveBaseStation(BaseStation baseStation) {
        
		try {
			this.jdbcTemplate.update("insert into base_station values (?, ?, ?, ?)",   
	                new Object[]{baseStation.getBaseId(), baseStation.getRoomId(),
						baseStation.getxAxis(), baseStation.getyAxis()});  
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		Server.roomIds.put(baseStation.getBaseId(), baseStation.getRoomId());
		Server.baseStationLocs.put(baseStation.getBaseId(),
				new Double[]{baseStation.getxAxis(), baseStation.getyAxis()});
		return true;
	}

	@Override
	public boolean updateBaseStation(final BaseStation baseStation) {
		
		try {
			this.jdbcTemplate.update(  
					"update base_station set room_id = ?, x_axis = ?, y_axis = ? where base_id = ?",   
	                new PreparedStatementSetter(){  
	                    @Override  
	                    public void setValues(PreparedStatement ps) throws SQLException {  
	                        ps.setInt(1, baseStation.getRoomId());  
	                        ps.setDouble(2, baseStation.getxAxis());
	                        ps.setDouble(3, baseStation.getyAxis());
	                        ps.setString(4, baseStation.getBaseId());
	                    }  
	                }  
	        );  
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		Server.roomIds.put(baseStation.getBaseId(), baseStation.getRoomId());
		Server.baseStationLocs.put(baseStation.getBaseId(), 
				new Double[]{baseStation.getxAxis(), baseStation.getyAxis()});
		return true;
	}

	@Override
	public boolean deleteBaseStation(final String baseId) {
		
		try {
			this.jdbcTemplate.update(  
					"delete from base_station  where room_id = ?",   
	                new PreparedStatementSetter(){  
	                    @Override  
	                    public void setValues(PreparedStatement ps) throws SQLException {  
	                        ps.setString(1, baseId);  
	                    }  
	                }  
	        );  
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		Server.roomIds.remove(baseId);
		Server.baseStationLocs.remove(baseId);
		return true;
	}

	@Override
	public BaseStation getBaseStationById(String baseId) {
		
		return (BaseStation) this.jdbcTemplate.queryForObject(  
                "select * from base_station where base_id = ?",   
                new Object[]{baseId},  
                new RowMapper<BaseStation>(){  
  
                    @Override  
                    public BaseStation mapRow(ResultSet rs,int rowNum)throws SQLException {  
                    	BaseStation baseStation  = new BaseStation(rs.getString(1),rs.getInt(2),
                    			rs.getDouble(3),rs.getDouble(4));  
                        return baseStation;  
                    }  
        }); 
	}
	
}
