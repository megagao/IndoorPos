package org.hqu.indoor_pos.rmi;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hqu.indoor_pos.bean.RoomInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

public class RoomManageImpl extends UnicastRemoteObject implements RoomManage{

	private static final long serialVersionUID = 1L;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public RoomManageImpl() throws RemoteException {
		super();
	}

	/**
	 * 查找所有房间信息
	 */
	@Override
	public List<RoomInfo> findAllRoomInfo() throws RemoteException {
		
		return this.jdbcTemplate.query("select * from room",   
                new RowMapper<RoomInfo>(){  
              
                    @Override  
                    public RoomInfo mapRow(ResultSet rs, int rowNum) throws SQLException { 
                    	Blob blob = rs.getBlob(3);
                    	RoomInfo roomInfo = new RoomInfo(rs.getInt(1), rs.getString(2), blobToBytes(blob), rs.getInt(4));
                        return roomInfo;  
                    }  
        });  
	}
	
	/**
	 * 得到房间列表
	 */
	@Override
	public List<String> findRoomList() throws RemoteException {
		
		return this.jdbcTemplate.query("select room_name from room order by room_id",   
                new RowMapper<String>(){  
              
                    @Override  
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException { 
                    	String roomName = rs.getString(1);
                        return roomName;  
                    }  
        });  
	}
	
	/**
	 * 根据roomName查询房间信息
	 * @param roomName  
	 */
	@Override
	public RoomInfo getRoomInfoByName(String roomName) throws RemoteException {
		
		return (RoomInfo) this.jdbcTemplate.queryForObject(  
                "select * from room where room_name = ?",   
                new Object[]{roomName}, 
                new int[]{java.sql.Types.VARCHAR},
                new RowMapper<RoomInfo>(){  
  
                    @Override  
                    public RoomInfo mapRow(ResultSet rs,int rowNum)throws SQLException {
                    	Blob blob = rs.getBlob(3);
                    	RoomInfo roomInfo = new RoomInfo(rs.getInt(1), rs.getString(2), blobToBytes(blob), rs.getInt(4));
                        return roomInfo;  
                    }  
        }); 
	}

	/**
	 * 根据roomID查询房间信息
	 * @param roomId
	 */
	@Override
	public RoomInfo getRoomInfoById(Integer roomId) throws RemoteException {
		
		return (RoomInfo) this.jdbcTemplate.queryForObject(  
                "select * from room where room_id = ?",   
                new Object[]{roomId}, 
                new int[]{java.sql.Types.INTEGER},
                new RowMapper<RoomInfo>(){  
  
                    @Override  
                    public RoomInfo mapRow(ResultSet rs,int rowNum)throws SQLException {
                    	Blob blob = rs.getBlob(3);
                    	RoomInfo roomInfo = new RoomInfo(rs.getInt(1), rs.getString(2), blobToBytes(blob), rs.getInt(4));
                        return roomInfo;  
                    }  
        }); 
	}

	/**
	 * 保存房间信息
	 * @param roomInfo
	 */
	@Override
	public boolean saveRoomInfo(final RoomInfo roomInfo) throws RemoteException {
		
		try {
			final byte[] layoutImageData = roomInfo.getLayoutImage();
			this.jdbcTemplate.update(  
					"insert into room values (?, ?, ?, ?)",   
	                new PreparedStatementSetter(){  
	                    @Override  
	                    public void setValues(PreparedStatement ps) throws SQLException {  
	                        ps.setInt(1, roomInfo.getRoomId());
	                        ps.setString(2, roomInfo.getRoomName());
	                        ps.setBinaryStream(3, new ByteArrayInputStream(layoutImageData), layoutImageData.length);
	                        ps.setInt(4, roomInfo.getPixelsPerM());
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
	 * 修改房间信息
	 * @param roomInfo
	 */
	@Override
	public boolean updateRoomInfo(final RoomInfo roomInfo) throws RemoteException {
		
		try {
			final byte[] layoutImageData = roomInfo.getLayoutImage();
			this.jdbcTemplate.update(  
					"update room set room_name = ?, layout_image = ?, pixels_per_m = ? where room_id = ?",   
	                new PreparedStatementSetter(){  
	                    @Override  
	                    public void setValues(PreparedStatement ps) throws SQLException {  
	                        ps.setString(1, roomInfo.getRoomName());
	                        ps.setBinaryStream(2, new ByteArrayInputStream(layoutImageData), layoutImageData.length);
	                        ps.setInt(3, roomInfo.getPixelsPerM());
	                        ps.setInt(4, roomInfo.getRoomId());
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
	 * 删除房间信息
	 * @param roomId
	 */
	@Override
	public boolean deleteRoomInfo(final Integer roomId) throws RemoteException {
		
		try {
			this.jdbcTemplate.update(  
					"delete from room  where room_id = ?",   
	                new PreparedStatementSetter(){  
	                    @Override  
	                    public void setValues(PreparedStatement ps) throws SQLException {  
	                        ps.setInt(1,roomId); 
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
