package org.hqu.indoor_pos.bean;

import java.io.Serializable;

public class RoomInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer roomId;

	private String roomName;

	/*房间布局图*/
	private byte[] layoutImage;

	/*比例关系*/
	private Integer pixelsPerM;

	public RoomInfo(Integer roomId, String roomName, byte[] layoutImage, Integer pixelsPerM) {
		this.roomId = roomId;
		this.roomName = roomName;
		this.layoutImage = layoutImage;
		this.pixelsPerM = pixelsPerM;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public byte[] getLayoutImage() {
		return layoutImage;
	}

	public void setLayoutImage(byte[] layoutImage) {
		this.layoutImage = layoutImage;
	}

	public int getPixelsPerM() {
		return pixelsPerM;
	}

	public void setPixelsPerM(Integer pixelsPerM) {
		this.pixelsPerM = pixelsPerM;
	}

}
