package org.hqu.indoor_pos.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.hqu.indoor_pos.bean.LoginUser;

public interface Login extends Remote{
	
	/**
	 * 登录
	 * @param str
	 */
	public LoginUser login(String str) throws RemoteException; 
	
}
