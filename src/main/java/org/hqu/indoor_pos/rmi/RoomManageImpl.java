package org.hqu.indoor_pos.rmi;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hqu.indoor_pos.bean.RoomInfo;
import org.hqu.indoor_pos.util.DBUtil;

public class RoomManageImpl extends UnicastRemoteObject implements RoomManage{

	private static final long serialVersionUID = 1L;

	private Connection conn = DBUtil.getConnection();
	
	public RoomManageImpl() throws RemoteException {
		super();
	}

	/**
	 * 查找所有房间信息
	 */
	@Override
	public List<RoomInfo> findAllRoomInfo() throws RemoteException {
		
		List<RoomInfo> roomInfos = new ArrayList<RoomInfo>();
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from room");
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				Blob blob = rs.getBlob(3);
				RoomInfo roomInfo = new RoomInfo(rs.getInt(1), rs.getString(2), blobToBytes(blob), rs.getInt(4));
				roomInfos.add(roomInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roomInfos;
	}
	
	/**
	 * 得到房间列表
	 */
	@Override
	public List<String> findRoomList() throws RemoteException {
		
		List<String> roomNames = new ArrayList<String>();
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select room_name from room order by room_id");
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				String roomName = rs.getString(1);
				roomNames.add(roomName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roomNames;
	}
	
	/**
	 * 根据roomName查询房间信息
	 * @param roomName  
	 */
	@Override
	public RoomInfo getRoomInfoByName(String roomName) throws RemoteException {
		
		RoomInfo roomInfo = null;
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from room where room_name = ?");
			stat.setString(1, roomName);
			ResultSet rs = stat.executeQuery();
			rs.next();
			Blob blob = rs.getBlob(3);
			roomInfo = new RoomInfo(rs.getInt(1), rs.getString(2), blobToBytes(blob), rs.getInt(4));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roomInfo;
	}

	/**
	 * 根据roomID查询房间信息
	 * @param roomId
	 */
	@Override
	public RoomInfo getRoomInfoById(Integer roomId) throws RemoteException {
		
		RoomInfo roomInfo = null;
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from room where room_id = ?");
			stat.setInt(1, roomId);
			ResultSet rs = stat.executeQuery();
			rs.next();
			Blob blob = rs.getBlob(3);
			roomInfo = new RoomInfo(rs.getInt(1), rs.getString(2), blobToBytes(blob), rs.getInt(4));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roomInfo;
	}

	/**
	 * 保存房间信息
	 * @param roomInfo
	 */
	@Override
	public boolean saveRoomInfo(RoomInfo roomInfo) throws RemoteException {
		
		try {
			byte[] layoutImageData = roomInfo.getLayoutImage();
			PreparedStatement stat; 
			stat = conn.prepareStatement("insert into room values (?, ?, ?, ?)");
			stat.setInt(1, roomInfo.getRoomId());
			stat.setString(2, roomInfo.getRoomName());
			stat.setBinaryStream(3, new ByteArrayInputStream(layoutImageData), layoutImageData.length);
			stat.setInt(4, roomInfo.getPixelsPerM());
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 修改房间信息
	 * @param roomInfo
	 */
	@Override
	public boolean updateRoomInfo(RoomInfo roomInfo) throws RemoteException {
		try {
			byte[] layoutImageData = roomInfo.getLayoutImage();
			PreparedStatement stat; 
			stat = conn.prepareStatement("update room set room_name = ?, layout_image = ?, pixels_per_m = ? where room_id = ?");
			stat.setString(1, roomInfo.getRoomName());
			stat.setBinaryStream(2, new ByteArrayInputStream(layoutImageData), layoutImageData.length);
			stat.setInt(3, roomInfo.getPixelsPerM());
			stat.setInt(4, roomInfo.getRoomId());
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 删除房间信息
	 * @param roomId
	 */
	@Override
	public boolean deleteRoomInfo(Integer roomId) throws RemoteException {
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("delete from room  where room_id = ?");
			stat.setInt(1,roomId);
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * blobToBytes
	 * @param blob
	 */
	public byte[] blobToBytes(Blob blob) {

		  BufferedInputStream is = null;
		  try {
		    is = new BufferedInputStream(blob.getBinaryStream());
		    byte[] bytes = new byte[(int) blob.length()];
		    int len = bytes.length;
		    int offset = 0;
		    int read = 0;
		    while (offset < len && (read = is.read(bytes, offset, len-offset)) >= 0) {
		      offset += read;
		    }
		    return bytes;
		  } catch (Exception e) {
		    return null;
		  } finally {
		    try {
		      is.close();
		      is = null;
		    } catch (IOException e) {
		      return null;
		    }
		  }
		}
}
