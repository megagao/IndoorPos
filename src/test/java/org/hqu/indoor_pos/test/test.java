package org.hqu.indoor_pos.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;
import org.hqu.indoor_pos.algorithm.Centroid;
import org.hqu.indoor_pos.algorithm.Dealer;
import org.hqu.indoor_pos.algorithm.DoGroup;
import org.hqu.indoor_pos.algorithm.Revise;
import org.hqu.indoor_pos.bean.BaseStation;
import org.hqu.indoor_pos.bean.BleBase;
import org.hqu.indoor_pos.bean.Employee;
import org.hqu.indoor_pos.bean.EnvFactor;
import org.hqu.indoor_pos.bean.Location;
import org.hqu.indoor_pos.bean.LoginUser;
import org.hqu.indoor_pos.bean.RoomInfo;
import org.hqu.indoor_pos.bean.Round;
import org.hqu.indoor_pos.rmi.BaseStationManage;
import org.hqu.indoor_pos.rmi.BaseStationManageImpl;
import org.hqu.indoor_pos.rmi.Login;
import org.hqu.indoor_pos.rmi.LoginImpl;
import org.hqu.indoor_pos.rmi.RoomManage;
import org.hqu.indoor_pos.rmi.RoomManageImpl;
import org.hqu.indoor_pos.server.Server;
import org.hqu.indoor_pos.util.CopyOnWriteMap;
import org.hqu.indoor_pos.util.SpringUtil;

public class test {
	/*@Test
	public void testjdbc(){
		Connection c = DBUtil.getConnection();
		try {
			PreparedStatement stat = c.prepareStatement("select base_id,x_axis,y_axis from base_station where base_id in (?,?,?,?)");
			stat.setString(1, "1111");
			stat.setString(2, "5555");
			stat.setString(3, "9999");
			stat.setString(4, "7777");
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				System.out.println(rs.getString(1));
				System.out.println(rs.getDouble(2));
				System.out.println(rs.getDouble(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	@Test
	public void testSpringJDBC(){
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringUtil.context.getBean("jdbcTemplate");
		System.out.println(jdbcTemplate.queryForObject("select room_id from base_station where base_id="+"10001", Integer.class));
	}
	
	@Test
	public void testSpringJDBC11(){
		final Map<Integer, Double[]> envFactors = new CopyOnWriteMap<Integer, Double[]>();
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringUtil.context.getBean("jdbcTemplate");
		jdbcTemplate.query("select * from env_factor",   
	                new RowCallbackHandler() {     
	              
	                    @Override    
	                    public void processRow(ResultSet rs) throws SQLException {     
	                    	envFactors.put(rs.getInt(1), new Double[]{rs.getDouble(2), rs.getDouble(3), rs.getDouble(4)});
	                    }     
	        });   
		/*jdbcTemplate.query("select * from env_factor",   
                new RowMapper(){  
              
                    @Override  
                    public void mapRow(ResultSet rs, int rowNum) throws SQLException {  
                    	envFactors.put(rs.getInt(1), new Double[]{rs.getDouble(2), rs.getDouble(3), rs.getDouble(4)});
                    }  
        });  */
		System.out.println(envFactors.size());
	}
	@Test
	public void testSpringJDBC7(){
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringUtil.context.getBean("jdbcTemplate");
		List<Location> l = jdbcTemplate.query("select * from location where timestamp between  ? and ? order by timestamp desc",
				new Object[]{"2016-08-05 16:15:35", "2016-08-05 16:15:37"},   
                new int[]{java.sql.Types.VARCHAR, java.sql.Types.VARCHAR},
                new RowMapper<Location>(){  
              
                    @Override  
                    public Location mapRow(ResultSet rs, int rowNum) throws SQLException {  
                    	Location location = new Location(rs.getString(2), rs.getInt(3), rs.getDouble(4),
        						rs.getDouble(5), rs.getTimestamp(6));
                        return location;  
                    }  
        });  
		System.out.println(l.size());
	}
	
	@Test
	public void testSpringJDBC9() throws RemoteException{
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringUtil.context.getBean("jdbcTemplate");
		try{
			LoginUser a = (LoginUser) jdbcTemplate.queryForObject(  
                "select * from login where username = ? and password = ?",   
                new Object[]{"aaa", "aaa"},  
                new RowMapper<LoginUser>(){  
  
                    @Override  
                    public LoginUser mapRow(ResultSet rs,int rowNum)throws SQLException {  
                    	LoginUser lu = new LoginUser(rs.getString(2), rs.getString(3), rs.getString(4));  
                        return lu;  
                    } 
        });
			System.out.println(a);
		}catch(Exception e){
		
		System.out.println("null");
		}
	}
	@Test
	public void testSpringJDBC1(){
		final Map<String, double[]> basesLocation =new HashMap<String, double[]>();
		basesLocation.put("1", new double[]{1.0,1.0});
		basesLocation.put("2", new double[]{1.0,1.0});
	}
	@SuppressWarnings("rawtypes")
	@Test
	public void testSpringJDBC2() throws RemoteException{
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringUtil.context.getBean("jdbcTemplate");
		@SuppressWarnings("unchecked")
		List<BaseStation> a = jdbcTemplate.query("select * from base_station",   
                new RowMapper(){  
            
            @Override  
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	BaseStation base = new BaseStation(rs.getString(1),rs.getInt(2),rs.getDouble(3),rs.getDouble(4));
                return base;  
            }  
		});  
		System.out.println(a.size());
	}
	
	@Test
	public void testSpringJDBC3() throws RemoteException{
		boolean b = false;
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringUtil.context.getBean("jdbcTemplate");
		BaseStation baseStation = new BaseStation("dsdsa",2,3.0,4.0);
		try{
			jdbcTemplate.update("insert into base_station values (?, ?, ?, ?)",   
	            new Object[]{baseStation.getBaseId(), baseStation.getRoomId(), baseStation.getxAxis(), baseStation.getyAxis()}); 
		}catch(Exception e){
			b=false;
		}
		
		System.out.println(b);
	}
	@Test
	public void testSpringJDBC5() throws RemoteException{
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringUtil.context.getBean("jdbcTemplate");
		BaseStation baseStation = (BaseStation) jdbcTemplate.queryForObject(  
                "select * from base_station where base_id = ?",   
                new Object[]{10001},  
                new RowMapper<BaseStation>(){  
  
                    @Override  
                    public BaseStation mapRow(ResultSet rs,int rowNum)throws SQLException {  
                    	BaseStation baseStation  = new BaseStation(rs.getString(1),rs.getInt(2),rs.getDouble(3),rs.getDouble(4));  
                        return baseStation;  
                    }  
              
        }); 
		
		System.out.println(baseStation.getBaseId());
		System.out.println(baseStation.getRoomId());
	}
	@Test
	public void testjdbc1() throws Exception{
		File file = new File("D:/Layout.jpg");
		InputStream is = new FileInputStream(file);
		
		List<RoomInfo> roomInfos = new ArrayList<RoomInfo>();
		        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
		        byte[] buff = new byte[100];  
		        int rc = 0;  
		        while ((rc = is.read(buff, 0, 100)) > 0) {  
		            swapStream.write(buff, 0, rc);  
		        }  
		        byte[] in2b = swapStream.toByteArray();  
		        RoomInfo roomInfo = new RoomInfo(2, "a", in2b, 66);
		        RoomManage rm = new RoomManageImpl();
		        rm.updateRoomInfo(roomInfo);
		/*try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from room");
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				Blob blob = rs.getBlob(3);
				RoomInfo roomInfo = new RoomInfo(rs.getInt(1), rs.getString(2), blob.getBinaryStream(), rs.getInt(4));
				roomInfos.add(roomInfo);
				byte[] data = new byte[(int) blob.length()];
				blob.getBinaryStream().read(data);
				data
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}

	@Test
	public void te3() throws RemoteException{
		Login l = new LoginImpl();
		LoginUser u = l.login("aaa,aaa");
		System.out.println(u.getRole());
	}
	@Test
	public void te0(){
		try { 
            //在RMI服务注册表中查找名称为RHello的对象，并调用其上的方法 
            Login l =(Login) Naming.lookup("rmi://120.32.211.193:5005/login"); 
            System.out.println("111111");
        } catch (NotBoundException e) { 
            e.printStackTrace(); 
        } catch (MalformedURLException e) { 
            e.printStackTrace(); 
        } catch (RemoteException e) { 
            e.printStackTrace();   
        } 
	}
	@Test
	public void te1(){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = df.parse("2013-03-04");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		long timestamp = cal.getTimeInMillis();
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		System.out.println(ts);
		System.out.println(timestamp);
	}
	
	/*@Test
	public void te2(){
		List<Location> locations = new ArrayList<Location>();
		Connection conn = DBUtil.getConnection();
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from location where timestamp between  ? and ?");
			
			//stat = conn.prepareStatement("SELECT * FROM location WHERE FROM_UNIXTIME(timestamp, '%Y-%m-%d') BETWEEN '2016-08-01' AND '2016-08-05'");
			
			stat.setString(1, "2016-07-27");
			stat.setString(2, "2016-08-05");
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				Location location = new Location(rs.getString(2), rs.getInt(3), rs.getDouble(4), rs.getDouble(5), rs.getTimestamp(6));
				locations.add(location);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(locations.size());
	}*/
	@Test
	public void SBSQL3(){
		/*LoginUser i = null;
		Connection conn = DBUtil.getConnection();
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from login where name=? and password=?");
			stat.setString(1, "aa");
			stat.setString(2, "aaa");
			ResultSet rs = stat.executeQuery();
			if(rs.next()){
				i= new LoginUser(rs.getString(1),rs.getString(2));
			}else{
				i=null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		if(i==null){
			System.out.println("null");
		}else{
			System.out.println(i.getEmpId());
			System.out.println(i.getRole());
		}*/
	}
	
	@Test
	public void SBSQL5(){
	}
	
	@Test
	public void SBSQL6(){
		Map<Integer,String> a = new HashMap<>();
		a.put(1, "1111111");
		if(a.get(2)==null){
			System.out.println("222");
		}
	}
	
	/*@Test
	public void SBSQL7(){
		BaseStation baseStation = new BaseStation("aaa", 1, 3.0, 3.1);
		Connection conn = DBUtil.getConnection();
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("update base_station set room_id = ?, x_axis = ?, y_axis = ? WHERE base_id = ?");
			stat.setInt(1, baseStation.getRoomId());
			stat.setDouble(2,baseStation.getxAxis());
			stat.setDouble(3,baseStation.getyAxis());
			stat.setString(4,baseStation.getBaseId() );
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/
	
	/*@Test
	public void SBSQL8(){
		String baseId = "aaa";
		Connection conn = DBUtil.getConnection();
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("delete from base_station  where base_id = ?");
			stat.setString(1,baseId);
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/
	
	/*@Test
	public void SBSQL9(){
		BaseStation base = null;
		Connection conn = DBUtil.getConnection();
		String baseId = "10001";
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
	}*/
	
	@Test
	public void SBSQL2(){
		List<Integer> rssis = new ArrayList<Integer>();
		rssis.add(1);
		rssis.add(3);
		rssis.add(2);
		Collections.sort(rssis);
		System.out.println(rssis.get(2));
		
	}
	
	@Test
	public void SBSQL4(){
		List<String> rssis = new ArrayList<String>();
		rssis.add("1");
		rssis.add("2");
		rssis.remove("3");
		
	}
	
	@Test
	public void SBSQL(){
		StringBuilder sb = new StringBuilder("select base_id,x_axis,y_axis from base_station where base_id in (?,?,?");
		if(true){
			sb.append(",?");
			System.out.println(sb);
		}
		if(true){
			sb.append(")");
			System.out.println(sb);
		}
	}
	
	@Test
	public void testconcurrent(){
		AtomicInteger i = new AtomicInteger();
		System.out.println(i.get());
		System.out.println(i.incrementAndGet());
		
	}
	
	@Test
	public void SBSQL1(){
		StringBuilder str = new StringBuilder();
		str.append("select base_id,x_axis,y_axis from base_station where base_id in (");
		
		System.out.println(str.toString());
	}
	
	@Test
	public void centroid(){
		Round r1 = new Round(0, 0, 1.2);
		Round r2 = new Round(-1, 1, 1.8);
		Round r3 = new Round(0.3, 1.4, 1.7);
		System.out.println(Centroid.triCentroid(r1, r2, r3));
	}
	@Test
	public void main(){
		
		String id = "";
		int rssi = 0;
		
		
		List<BleBase> bases = null;
		//分别根据接收到的基站的id和对应的rssi值创建BleBase对象，并将这些对象分别添加到bases中，此部分可在客户端完成，也可在s端
		BleBase base1 = new BleBase(id, rssi);
		bases.add(base1);
		//创建一个继承自Dealer接口的具体算法对象，调用该对象的getLocation方法，传入接收到的一个基站列表，即可得到定位坐标
		Dealer dealer = new Centroid();//使用加权质心定位算法
		//Dealer dealer = new Trilateral();使用加权三边定位算法
		//double[] location = dealer.getLocation(bases);//其中location[0]是定位得到的横坐标，location[2]是纵坐标
	}
	/*@Test
	public void env(){
		
		try {
			Connection conn = DBUtil.getConnection();
			PreparedStatement stat; 
			stat = conn.prepareStatement("select height,atten_factor,p0 from env_factor order by time_stamp");
			ResultSet rs = stat.executeQuery();
			rs.last();//根据时间戳找出最近一次修改的环境因素值，也可根据修改的版本号指定特定的环境因素值，相应的修改sql语句即可
			System.out.println(rs.getString(1));
			System.out.println(rs.getDouble(2));
			System.out.println(rs.getDouble(3));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/
	
	@Test
	public void map(){
		Map<Integer, String> m = new HashMap<>();
		m.put(1, "a");
		m.put(1, "b");
		System.out.println(m);
		
	}
	
	@Test
	public void subList(){
		List<Integer> a = new ArrayList<Integer>();
		a.add(2);
		a.add(1);
		a.add(6);
		a.add(3);
		a.add(5);
		a.add(4);
		Collections.sort(a);
		System.out.println(a.get(0));
		System.out.println(a.get(2));
		System.out.println(a.get(5));
		System.out.println(a.subList(a.size()-4, a.size()));
	}
	
	@Test
	public void testReturn(){
		List<Integer> a = new ArrayList<Integer>();
		a.add(2);
		a.add(1);
		a.add(6);
		a.add(3);
		a.add(5);
		a.add(4);
		Collections.sort(a);
		if(a.size()>3){
			return;
		}
		System.out.println(a.get(0));
		System.out.println(a.get(2));
		System.out.println(a.get(5));
		System.out.println(a.subList(a.size()-4, a.size()));
		return;
	}
	
	/*@Test
	public void testDb(){
		
		try {
			Connection conn = DBUtil.getConnection();
			PreparedStatement stat = conn.prepareStatement("select base_id,x_axis,y_axis from base_station where base_id in (?,?,?)");
			
			stat.setString(1, "0000");
			stat.setString(2, "2222");
			stat.setString(3, "1111");
			
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				double[] loc = new double[2];
				loc[0]=rs.getDouble(2);
				loc[1]=rs.getDouble(3);
				System.out.println(rs.getString(1)+" "+loc);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	/*@Test
	public void testDb1(){
		
		try {
			Connection conn = DBUtil.getConnection();
			PreparedStatement stat = conn.prepareStatement("select base_id,x_axis,y_axis from base_station where base_id ='0000'");
			
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				double[] loc = new double[2];
				loc[0]=rs.getDouble(2);
				loc[1]=rs.getDouble(3);
				System.out.println(rs.getString(1)+" "+loc);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	/*@Test
	public void testDb2(){
		List<Location> locations = new ArrayList<Location>();
		int j = 0;
		int i = 0;
		try {
			while(j<=50){
			     Timestamp ts = new Timestamp(System.currentTimeMillis());
			     Location location = new Location("111",1, Math.random(), Math.random(), ts);
			     locations.add(location);
			     i++;
			     if(i>=10){
			     	Connection conn = DBUtil.getConnection();
			     	PreparedStatement stat = conn.prepareStatement("insert into location values(?,?,?,?)");
			     	for (Location l : locations) {
			     		stat.setString(1, l.getEmpId());
			     		stat.setDouble(2, l.getxAxis());
			     		stat.setDouble(3, l.getyAxis());
			     		stat.setTimestamp(4, l.getTimeStamp());
			     		stat.executeUpdate();
						}
			     	i = 0;
			     	locations = new ArrayList<Location>();
			     }
			     j++;
			     if(j==46){
			    	 throw  new Exception();
			     }
			  }
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
	     	try {
	     		Connection conn = DBUtil.getConnection();
				PreparedStatement stat = conn.prepareStatement("insert into location values(?,?,?,?)");
				for (Location l : locations) {
					stat.setString(1, l.getEmpId());
					stat.setDouble(2, l.getxAxis());
					stat.setDouble(3, l.getyAxis());
					stat.setTimestamp(4, l.getTimeStamp());
					stat.executeUpdate();
					}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/
	@Test
	public void tesr(){
		List<Integer> l = new ArrayList<Integer>();
		l.add(1);
		l.add(2);
		System.out.println(l.get(0));
	}
	
	@Test
	public void tesr1(){
		BleBase b1 = new BleBase("111", -68);
		BleBase b2 = new BleBase("112", -68);
		BleBase b3 = new BleBase("112", -69);
		BleBase b4 = new BleBase("111", -69);
		BleBase b5 = new BleBase("111", -70);
		BleBase b6 = new BleBase("111", -71);
		BleBase b7 = new BleBase("111", -72);
		BleBase b8 = new BleBase("111", -63);
		BleBase b9 = new BleBase("113", -68);
		BleBase b10 = new BleBase("113", -69);
		BleBase b11 = new BleBase("112", -70);
		BleBase b12 = new BleBase("111", -73);
		BleBase b13 = new BleBase("112", -71);
		BleBase b14 = new BleBase("114", -68);
		List<BleBase> l = new ArrayList<BleBase>();
		l.add(b14);
		l.add(b13);
		l.add(b12);
		l.add(b11);
		l.add(b10);
		l.add(b9);
		l.add(b4);
		l.add(b3);
		l.add(b5);
		l.add(b6);
		l.add(b7);
		l.add(b2);
		l.add(b1);
		l.add(b8);
		DoGroup doGroup = new DoGroup();
		//
	}
	
	@Test
	public void tesr2(){
		double n = 2.3;
		double h = 0.4;
		double p = -68;
		BleBase b1 = new BleBase("111", -68);
		BleBase b2 = new BleBase("112", -72);
		BleBase b3 = new BleBase("112", -76);
		BleBase b4 = new BleBase("111", -80);
		BleBase b5 = new BleBase("111", -81);
		BleBase b6 = new BleBase("111", -83);
		BleBase b7 = new BleBase("111", -85);
		BleBase b8 = new BleBase("111", -90);
		BleBase b9 = new BleBase("113", -75);
		BleBase b10 = new BleBase("113", -76);
		BleBase b11 = new BleBase("112", -70);
		BleBase b12 = new BleBase("111", -73);
		BleBase b13 = new BleBase("112", -71);
		BleBase b14 = new BleBase("114", -68);
		System.out.println(b1.getRssi()+"  "+b1.getDistance(h, n, p));
		System.out.println(b2.getRssi()+"  "+b2.getDistance(h, n, p));
		System.out.println(b3.getRssi()+"  "+b3.getDistance(h, n, p));
		System.out.println(b4.getRssi()+"  "+b4.getDistance(h, n, p));
		System.out.println(b5.getRssi()+"  "+b5.getDistance(h, n, p));
		System.out.println(b6.getRssi()+"  "+b6.getDistance(h, n, p));
		System.out.println(b7.getRssi()+"  "+b7.getDistance(h, n, p));
		System.out.println(b8.getRssi()+"  "+b8.getDistance(h, n, p));
		System.out.println(b9.getRssi()+"  "+b9.getDistance(h, n, p));
		System.out.println(b10.getRssi()+"  "+b10.getDistance(h, n, p));
	}
	
	@Test
	public void t(){
		System.out.println(Math.log10(30));
	}
	
	@Test
	public void t1(){
		Revise r = new Revise();
		System.out.println(r.revise(-75,1.975));
	}
	/*@Test
	public void t2(){
		Connection conn = DBUtil.getConnection();
			try {
				PreparedStatement stat; 
				stat = conn.prepareStatement("select terminal_id,emp_id from employee");
				ResultSet rs = stat.executeQuery();
				while(rs.next()){
					
					System.out.println(rs.getString(1));
					System.out.println(rs.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}*/
}
