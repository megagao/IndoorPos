package org.hqu.indoor_pos.rmi;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hqu.indoor_pos.bean.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * created on 2016年8月26日
 *
 * 历史位置管理服务接口实现类
 *
 * @author  megagao
 * @version  0.0.1
 */
public class HistoryLocationImpl implements HistoryLocation{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public HistoryLocationImpl() throws RemoteException {
		super();
	}

	@Override
	public List<Location> findAllHistoryLocation() {
		
		return this.jdbcTemplate.query("select * from location order by timestamp desc",   
                new RowMapper<Location>(){  
              
                    @Override  
                    public Location mapRow(ResultSet rs, int rowNum) throws SQLException {  
                    	Location location = new Location(rs.getString(2), rs.getInt(3), rs.getDouble(4),
        						rs.getDouble(5), rs.getTimestamp(6));
                        return location;  
                    }  
        });  
	}

	@Override
	public List<Location> findHisLocByEmpId(String empId) {

		return this.jdbcTemplate.query(
				"select * from base_station where room_id = ? order by timestamp desc",
				new Object[]{empId},   
                new int[]{java.sql.Types.VARCHAR},
                new RowMapper<Location>(){  
              
                    @Override  
                    public Location mapRow(ResultSet rs, int rowNum) throws SQLException {  
                    	Location location = new Location(rs.getString(2), rs.getInt(3), rs.getDouble(4),
        						rs.getDouble(5), rs.getTimestamp(6));
                        return location;  
                    }  
        });  
	}

	@Override
	public List<Location> findHisLocByTime(String fromTime, String toTime) {
		
		return this.jdbcTemplate.query(
				"select * from location where timestamp between  ? and ? order by timestamp desc",
				new Object[]{fromTime, toTime},   
                new int[]{java.sql.Types.VARCHAR, java.sql.Types.VARCHAR},
                new RowMapper<Location>(){  
              
                    @Override  
                    public Location mapRow(ResultSet rs, int rowNum) throws SQLException {  
                    	Location location = new Location(rs.getString(2), rs.getInt(3), rs.getDouble(4),
        						rs.getDouble(5), rs.getTimestamp(6));
                        return location;  
                    }  
        });  
	}

	@Override
	public List<Location> findHisLocByEmpIdAndTime(String empId, String fromTime, String toTime) {

		return this.jdbcTemplate.query(
				"select * from location where emp_id = ? and timestamp between  ? and ? order by timestamp desc",
				new Object[]{empId, fromTime, toTime},   
                new int[]{java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR},
                new RowMapper<Location>(){  
              
                    @Override  
                    public Location mapRow(ResultSet rs, int rowNum) throws SQLException {  
                    	Location location = new Location(rs.getString(2), rs.getInt(3), rs.getDouble(4),
        						rs.getDouble(5), rs.getTimestamp(6));
                        return location;  
                    }  
        });  
	}

	@Override
	public List<Location> findHisLocByRoomId(Integer roomId) {

		return this.jdbcTemplate.query(
				"select * from location where room_id = ? order by timestamp desc",
				new Object[]{roomId},   
                new int[]{java.sql.Types.INTEGER},
                new RowMapper<Location>(){  
              
                    @Override  
                    public Location mapRow(ResultSet rs, int rowNum) throws SQLException {  
                    	Location location = new Location(rs.getString(2), rs.getInt(3), rs.getDouble(4),
        						rs.getDouble(5), rs.getTimestamp(6));
                        return location;  
                    }  
        });  
	}

	@Override
	public List<Location> findHisLocByRoomIdAndTime(Integer roomId,
			String fromTime, String toTime) {

		return this.jdbcTemplate.query(
				"select * from location where room_id = ? and timestamp between  ? and ? order by timestamp desc",
				new Object[]{roomId, fromTime, toTime},   
                new int[]{java.sql.Types.INTEGER, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR},
                new RowMapper<Location>(){  
              
                    @Override  
                    public Location mapRow(ResultSet rs, int rowNum) throws SQLException {  
                    	Location location = new Location(rs.getString(2), rs.getInt(3), rs.getDouble(4),
        						rs.getDouble(5), rs.getTimestamp(6));
                        return location;  
                    }  
        });  
	}
	
}
