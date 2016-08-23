package org.hqu.indoor_pos.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hqu.indoor_pos.bean.Location;
import org.hqu.indoor_pos.util.DBUtil;

public class HistoryLocationImpl extends UnicastRemoteObject implements HistoryLocation{

	private static final long serialVersionUID = 1L;

	private Connection conn = DBUtil.getConnection();
	
	public HistoryLocationImpl() throws RemoteException {
		super();
	}

	/**
	 * 查找所有历史位置，以最近时间向下排列
	 */
	@Override
	public List<Location> findAllHistoryLocation() throws RemoteException {
		
		List<Location> locations = new ArrayList<Location>();
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from location");
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				Location location = new Location(rs.getString(2), rs.getInt(3), rs.getDouble(4),
						rs.getDouble(5), rs.getTimestamp(6));
				locations.add(location);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locations;
	}

	/**
	 * 按员工id查询该用户所有历史位置
	 * @param empId
	 */
	@Override
	public List<Location> findHisLocByEmpId(String empId)
			throws RemoteException {

		List<Location> locations = new ArrayList<Location>();
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from location where emp_id = ?");
			stat.setString(1, empId);
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				Location location = new Location(rs.getString(2), rs.getInt(3), rs.getDouble(4),
						rs.getDouble(5), rs.getTimestamp(6));
				locations.add(location);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locations;
	}

	/**
	 * 按时间查询所有定位结果
	 * @param fromTime, toTime
	 */
	@Override
	public List<Location> findHisLocByTime(String fromTime, String toTime)
			throws RemoteException {
		
		List<Location> locations = new ArrayList<Location>();
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from location where timestamp between  ? and ?");
			stat.setString(1, fromTime);
			stat.setString(2, toTime);
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				Location location = new Location(rs.getString(2), rs.getInt(3), rs.getDouble(4),
						rs.getDouble(5), rs.getTimestamp(6));
				locations.add(location);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locations;
	}

	/**
	 * 按时间及员工id查询
	 * @param empId, fromTime, toTime
	 */
	@Override
	public List<Location> findHisLocByEmpIdAndTime(String empId,
			String fromTime, String toTime) throws RemoteException {

		List<Location> locations = new ArrayList<Location>();
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from location where emp_id = ? and timestamp between  ? and ?");
			stat.setString(1, empId);
			stat.setString(2, fromTime);
			stat.setString(3, toTime);
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				Location location = new Location(rs.getString(2), rs.getInt(3), rs.getDouble(4),
						rs.getDouble(5), rs.getTimestamp(6));
				locations.add(location);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locations;
	}

	/**
	 * 按房间id查询历史位置
	 * @param roomId
	 */
	@Override
	public List<Location> findHisLocByRoomId(Integer roomId)
			throws RemoteException {

		List<Location> locations = new ArrayList<Location>();
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from location where room_id = ?");
			stat.setInt(1, roomId);
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				Location location = new Location(rs.getString(2), rs.getInt(3), rs.getDouble(4),
						rs.getDouble(5), rs.getTimestamp(6));
				locations.add(location);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locations;
	}

	/**
	 * 按时间及房间id查询
	 * @param roomId
	 */
	@Override
	public List<Location> findHisLocByRoomIdAndTime(Integer roomId,
			String fromTime, String toTime) throws RemoteException {

		List<Location> locations = new ArrayList<Location>();
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from location where room_id = ? and timestamp between  ? and ?");
			stat.setInt(1, roomId);
			stat.setString(2, fromTime);
			stat.setString(3, toTime);
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				Location location = new Location(rs.getString(2), rs.getInt(3), rs.getDouble(4),
						rs.getDouble(5), rs.getTimestamp(6));
				locations.add(location);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locations;
	}
	
}
