package org.hqu.indoor_pos.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hqu.indoor_pos.bean.EnvFactor;
import org.hqu.indoor_pos.server.Server;
import org.hqu.indoor_pos.util.DBUtil;

public class EnvFactorManageImpl extends UnicastRemoteObject implements EnvFactorManage{

	private static final long serialVersionUID = 1L;

	private Connection conn = DBUtil.getConnection();
	
	public EnvFactorManageImpl() throws RemoteException {
		super();
	}

	/**
	 * 查找所有房间的环境因子
	 */
	@Override
	public List<EnvFactor> findAllEnvFactor() throws RemoteException {
		
		List<EnvFactor> envFactors = new ArrayList<EnvFactor>();
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from env_factor");
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				EnvFactor envFactor = new EnvFactor(rs.getInt(1), rs.getDouble(2), rs.getDouble(3), rs.getDouble(4));
				envFactors.add(envFactor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return envFactors;
	}

	/**
	 * 根据房间id查找相应的环境因子
	 * @param roomId
	 */
	@Override
	public EnvFactor findEnvFactorByRoomId(Integer roomId)
			throws RemoteException {

		EnvFactor envFactor = null;
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from env_factor where room_id = ?");
			stat.setInt(1, roomId);
			ResultSet rs = stat.executeQuery();
			rs.next();
			envFactor = new EnvFactor(rs.getInt(1), rs.getDouble(2), rs.getDouble(3), rs.getDouble(4));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return envFactor;
	}

	/**
	 * 保存环境因子
	 * @param envFactor
	 */
	@Override
	public boolean saveEnvFactor(EnvFactor envFactor)
			throws RemoteException {
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("insert into env_factor (room_id, height, atten_factor, p0) values (?, ?, ?, ?)");
			stat.setInt(1, envFactor.getRoomId());
			stat.setDouble(2, envFactor.getHeight());
			stat.setDouble(3, envFactor.getN());
			stat.setDouble(4, envFactor.getP0());
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		Server.envFactors.put(envFactor.getRoomId(), new Double[]{envFactor.getHeight(), envFactor.getN(), envFactor.getP0()});
		return true;
	}

	/**
	 * 修改环境因子
	 * @param envFactor
	 */
	@Override
	public boolean updateEnvFactor(EnvFactor envFactor)
			throws RemoteException {
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("update env_factor set height = ?, atten_factor = ?, p0 = ? where room_id = ?");
			stat.setDouble(1, envFactor.getHeight());
			stat.setDouble(2, envFactor.getN());
			stat.setDouble(3, envFactor.getP0());
			stat.setInt(4, envFactor.getRoomId());
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		Server.envFactors.put(envFactor.getRoomId(), new Double[]{envFactor.getHeight(), envFactor.getN(), envFactor.getP0()});
		return true;
	}

	/**
	 * 删除环境因子
	 * @param roomId
	 */
	@Override
	public boolean deleteEnvFactor(Integer roomId) throws RemoteException {
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("delete from env_factor  where room_id = ?");
			stat.setInt(1, roomId);
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		Server.envFactors.remove(roomId);
		return true;	
	}

}
