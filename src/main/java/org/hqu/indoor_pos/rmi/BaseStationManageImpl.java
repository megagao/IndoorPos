package org.hqu.indoor_pos.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hqu.indoor_pos.bean.BaseStation;
import org.hqu.indoor_pos.server.Server;
import org.hqu.indoor_pos.util.DBUtil;

public class BaseStationManageImpl extends UnicastRemoteObject implements BaseStationManage{

	private static final long serialVersionUID = 1L;

	private Connection conn = DBUtil.getConnection();
	
	public BaseStationManageImpl() throws RemoteException {
		super();
	}
	
	/**
	 * 查找所有基站
	 */
	@Override
	public List<BaseStation> findAllBaseStation() throws RemoteException {

		List<BaseStation> bases = new ArrayList<BaseStation>();
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from base_station");
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				BaseStation base = new BaseStation(rs.getString(1),rs.getInt(2),rs.getDouble(3),rs.getDouble(4));
				bases.add(base);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bases;
	}
	
	/**
	 * 根据房间id查找房间内的基站
	 * @param roomId
	 */
	@Override
	public List<BaseStation> findBaseStationByRoomId(Integer roomId) throws RemoteException {
		
		List<BaseStation> bases = new ArrayList<BaseStation>();
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from base_station where room_id = ?");
			stat.setInt(1, roomId);
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				BaseStation base = new BaseStation(rs.getString(1),rs.getInt(2),rs.getDouble(3),rs.getDouble(4));
				bases.add(base);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bases;
	}

	/**
	 * 保存基站
	 * @param baseStation
	 */
	@Override
	public boolean saveBaseStation(BaseStation baseStation) throws RemoteException {
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("insert into base_station values (?, ?, ?, ?)");
			stat.setString(1, baseStation.getBaseId());
			stat.setInt(2, baseStation.getRoomId());
			stat.setDouble(3, baseStation.getxAxis());
			stat.setDouble(4, baseStation.getyAxis());
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		Server.roomIds.put(baseStation.getBaseId(), baseStation.getRoomId());
		Server.baseStationLocs.put(baseStation.getBaseId(), new Double[]{baseStation.getxAxis(), baseStation.getyAxis()});
		return true;
	}

	/**
	 * 修改基站信息
	 * @param baseStation
	 */
	@Override
	public boolean updateBaseStation(BaseStation baseStation)
			throws RemoteException {
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("update base_station set room_id = ?, x_axis = ?, y_axis = ? where base_id = ?");
			stat.setInt(1, baseStation.getRoomId());
			stat.setDouble(2, baseStation.getxAxis());
			stat.setDouble(3, baseStation.getyAxis());
			stat.setString(4, baseStation.getBaseId());
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		Server.roomIds.put(baseStation.getBaseId(), baseStation.getRoomId());
		Server.baseStationLocs.put(baseStation.getBaseId(), new Double[]{baseStation.getxAxis(), baseStation.getyAxis()});
		return true;
	}

	/**
	 * 删除基站
	 * @param baseId
	 */
	@Override
	public boolean deleteBaseStation(String baseId) throws RemoteException {
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("delete from base_station  where room_id = ?");
			stat.setString(1, baseId);
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		Server.roomIds.remove(baseId);
		Server.baseStationLocs.remove(baseId);
		return true;
	}

	/**
	 * 根据基站id查询基站
	 * @param baseId
	 */
	@Override
	public BaseStation getBaseStationById(String baseId) throws RemoteException {
		
		BaseStation base = null;
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from base_station where base_id = ?");
			stat.setString(1, baseId);
			ResultSet rs = stat.executeQuery();
			rs.next();
			base = new BaseStation(rs.getString(1),rs.getInt(2),rs.getDouble(3),rs.getDouble(4));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return base;
	}

}
