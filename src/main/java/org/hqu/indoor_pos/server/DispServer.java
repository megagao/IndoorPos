package org.hqu.indoor_pos.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.hqu.indoor_pos.bean.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;


public class DispServer {

	/*显示客户端连接端口号*/
    public static final int DISP_SERVERPORT = 5005;
    
    /*定位结果传输端口号*/
    public static final int DISP_PORT = 5006;
    
    /*显示定位结果的客户端列表*/
    private CopyOnWriteArrayList<DispClient> dispClients;
    
    /*缓存向数据库存储定位结果的计数值*/
    private int i = 0;
    
    /*本机地址*/
    private String host;
    
    /*定位结果向数据库存储的缓存*/
    public Map<Integer, Location> locsToDB = new HashMap<Integer, Location>();
    
    @Autowired
	private JdbcTemplate jdbcTemplate;
    
	public void startDispServer() {
		
		
		/*获取本机地址*/
		try {
			host = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(host);
			
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
        		for (int k=1; k<=i; k++){
        			final Location location = locsToDB.get(k);
        			try{
		        		this.jdbcTemplate.update(  
		    					"insert into location(emp_id, x_axis, y_axis, timestamp, room_id) values(?, ?, ?, ?, ?)",   
		    	                new PreparedStatementSetter(){  
		    	                    @Override  
		    	                    public void setValues(PreparedStatement ps) throws SQLException {  
		    	                        ps.setString(1, location.getEmpId());
		    	                        ps.setDouble(2, location.getxAxis());
		    	                        ps.setDouble(3, location.getyAxis());
		    	                        ps.setTimestamp(4, location.getTimeStamp());
		    	                        ps.setInt(5, location.getRoomId());
		    	                    }  
		    	                }  
		    	        ); 
        			}catch(Exception e){
        				
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
