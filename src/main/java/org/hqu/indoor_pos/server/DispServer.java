package org.hqu.indoor_pos.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.hqu.indoor_pos.bean.Location;
import org.hqu.indoor_pos.rmi.BaseStationManage;
import org.hqu.indoor_pos.rmi.BaseStationManageImpl;
import org.hqu.indoor_pos.rmi.EmployeeManage;
import org.hqu.indoor_pos.rmi.EmployeeManageImpl;
import org.hqu.indoor_pos.rmi.EnvFactorManage;
import org.hqu.indoor_pos.rmi.EnvFactorManageImpl;
import org.hqu.indoor_pos.rmi.HistoryLocation;
import org.hqu.indoor_pos.rmi.HistoryLocationImpl;
import org.hqu.indoor_pos.rmi.Login;
import org.hqu.indoor_pos.rmi.LoginImpl;
import org.hqu.indoor_pos.rmi.LoginInfoManage;
import org.hqu.indoor_pos.rmi.LoginInfoManageImpl;
import org.hqu.indoor_pos.rmi.RoomManage;
import org.hqu.indoor_pos.rmi.RoomManageImpl;
import org.hqu.indoor_pos.util.DBUtil;


public class DispServer {

	/*显示客户端连接端口号*/
    public static final int DISP_SERVERPORT = 5005;
    
    /*定位结果传输端口号*/
    public static final int DISP_PORT = 5006;
    
    /*显示定位结果的客户端列表*/
    private CopyOnWriteArrayList<DispClient> dispClients;
    
    /*缓存向数据库存储定位结果的计数值*/
    private int i = 0;
    
    /*远程对象注册到RMI注册服务器上,绑定的URL前缀*/
    private String serverURL;  
    
    /*本机地址*/
    private String host;
    
    /*定位结果向数据库存储的缓存*/
    public Map<Integer, Location> locsToDB = new HashMap<Integer, Location>();
    
	public void startDispServer() {
		
		try {
			/*获取本机地址*/
			host = InetAddress.getLocalHost().getHostAddress();
			System.out.println(host);
			
			/**jdk中的说明：
			 * Naming 类提供在对象注册表中存储和获得远程对远程对象引用的方法。
			 * Naming 类的每个方法都可将某个名称作为其一个参数，
			 * 该名称是使用以下形式的 URL 格式（没有 scheme 组件）的 java.lang.String： 
			 *    //host:port/name
			 */
			serverURL = "rmi://"+host+":"+DISP_SERVERPORT+"/";
			
			/*本地主机上的远程对象注册表Registry的实例，并指定端口为5005（Java默认端口是1099），必不可缺的一步，缺少注册表创建，则无法绑定对象到远程注册表上 */
			LocateRegistry.createRegistry(5005); 
			
			/*实例化实现了Login接口的远程服务LoginImpl对象*/
		    Login login = new LoginImpl(); 
		    
		    /*实例化实现了RoomManage接口的远程服务RoomManageImpl对象*/ 
		    RoomManage roomManage = new RoomManageImpl();
		    
		    /*实例化实现了BaseStationManage接口的远程服务BaseStationManageImpl对象*/ 
		    BaseStationManage baseStationManage = new BaseStationManageImpl();
		    
		    /*实例化实现了EmployeeManage接口的远程服务EmployeeManageImpl对象*/ 
		    EmployeeManage employeeManage = new EmployeeManageImpl();
		    
		    /*实例化实现了EnvFactorManage接口的远程服务EnvFactorManageImpl对象*/ 
		    EnvFactorManage envFactorManage = new EnvFactorManageImpl();
		    
		    /*实例化实现了LoginInfoManage接口的远程服务LoginInfoManageImpl对象*/ 
		    LoginInfoManage loginInfoManage = new LoginInfoManageImpl();
		    
		    /*实例化实现了HistoryLocation接口的远程服务HistoryLocationImpl对象*/ 
		    HistoryLocation historyLocation = new HistoryLocationImpl();
		    
		    /*把远程对象注册到RMI注册服务器上，并命名为login,绑定的URL标准格式为：rmi://host:port/name,其中协议名(rmi)可以省略  */		    
		    Naming.bind(serverURL+"login", login); 
		    Naming.bind(serverURL+"roomManage", roomManage); 
		    Naming.bind(serverURL+"baseStationManage", baseStationManage); 
		    Naming.bind(serverURL+"employeeManage", employeeManage); 
		    Naming.bind(serverURL+"envFactorManage", envFactorManage); 
		    Naming.bind(serverURL+"loginInfoManage", loginInfoManage);
		    Naming.bind(serverURL+"historyLocation", historyLocation);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*启动显示客户端监听线程*/
        Thread dispThread =new Thread(new DispThread());  
        dispThread.start();
        
        Location loc = null;
        
        /*服务器端持续将实时的定位数据向维护的客户端列表中的客户端写*/
        while(true){
        	
        	try {
				loc = Server.locs.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	String str = loc.getEmpId()+","+loc.getRoomId()+","+loc.getxAxis()+","+loc.getyAxis();
        	for (DispClient DispClient : dispClients) {
				DispClient.sendLoc(str);
			}
        	
        	locsToDB.put(++i, loc);
        	/*每15条存一次数据库*/
        	if(i == 15){
        		Connection conn = DBUtil.getConnection();
                try {
					PreparedStatement stat = conn.prepareStatement("insert into location(emp_id, x_axis, y_axis, timestamp, room_id) values(?, ?, ?, ?, ?)");
					for (int k=1; k<=i; k++){
						Location location = locsToDB.get(k);
					    	stat.setString(1, location.getEmpId());
					    	stat.setDouble(2, location.getxAxis());
					    	stat.setDouble(3, location.getyAxis());
					    	stat.setTimestamp(4, location.getTimeStamp());
					    	stat.setInt(5, location.getRoomId());
					    	stat.executeUpdate();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
             	locsToDB = new ConcurrentHashMap<Integer,Location>();
        	}
        }
	}
	
	/*显示客户端*/
	private class DispThread implements Runnable{
		
		@Override
		public void run() {
			
			ServerSocket dispServerSocket = null;
			
			dispClients = new CopyOnWriteArrayList<DispClient>();
			
			try {
				
	            System.out.println("Display Starting...");  
	  
	            dispServerSocket = new ServerSocket(DISP_PORT);
	            
	            while (true) {  
	            	
	            	/*等待接收显示客户端请求*/
	                Socket dispClient = dispServerSocket.accept();  
	                System.out.println("Display Started"); 
	                DispClient dc = new DispClient(dispClient);
	                
	                new Thread(dc).start();
	                
	                dispClients.add(dc);
	                
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally{
	        	 try {  
	                 if(dispServerSocket != null){  
	                	 dispServerSocket.close();  
	                 }  
	             } catch (IOException e) {  
	                 e.printStackTrace();  
	             }  
	        }
			
		}
	}
	
	class DispClient implements Runnable {
		private OutputStream os = null;
		//private InputStream is = null;
		private DataOutputStream dos = null;
		//private DataInputStream dis = null;

		DispClient(Socket socket) {
			try {
				os = socket.getOutputStream();
				//is = socket.getInputStream();
				dos = new DataOutputStream(os);
				//dis = new DataInputStream(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			/*while(true){
				try {
					if(dis.readUTF()=="startDisplay"){
						dispClients.add(this);
					}
				} catch (IOException e) {
					dispClients.remove(this);
					e.printStackTrace();
				}
			}*/
		}

		public void sendLoc(String string) {
			try {
				dos.writeUTF(string);
			} catch (SocketException exception) {
				System.out.println("Thread:" + dispClients.remove(this));//
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
