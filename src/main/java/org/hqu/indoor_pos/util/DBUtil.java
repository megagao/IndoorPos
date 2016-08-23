package org.hqu.indoor_pos.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;

public class DBUtil {
	
	/*数据库连接地址*/
 	private static String url;  
 	
 	/*用户名*/
    private static String username;  
      
    /*密码*/
    private static String password;  
    
    /*数据源*/
    private static DataSource ds_pooled;  
    
    /*加载类时，先将jdbc连接数据库信息获取并赋值*/
    static {  
    	
        Properties prop = new Properties();  
        InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("dbconfig.properties");  
        
        try {  
            prop.load(in);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        
        
        try {
			/*1. 加载驱动类 */ 
			Class.forName(prop.getProperty("jdbc.driverClassName"));  
			  
			/*设置连接数据库的配置信息*/  
			url = prop.getProperty("jdbc.url");  
			username = prop.getProperty("jdbc.username");  
			password = prop.getProperty("jdbc.password");    
			  
			DataSource ds_unpooled = DataSources.unpooledDataSource(url, username, password);  
			  
			Map<String, Object> pool_conf = new HashMap<String, Object>(); 
			
			/*设置连接池中保留的最大连接数。默认值: 15*/  
			pool_conf.put("maxPoolSize", 20);  
			
			/*设置连接池中保留的最小连接数，默认为：3--*/  
			pool_conf.put("minPoolSize", 2);
			
			ds_pooled = DataSources.pooledDataSource(ds_unpooled,  
			        pool_conf);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }  
      
    /** 
     *  获取连接对象 
     */  
    public static Connection getConnection() {  

    	Connection conn = null;
		try {
			conn = ds_pooled.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return conn;  
    }  
      
    /** 
     * 释放连接池资源 
     */  
    public static void clearup(){  
        if(ds_pooled != null){  
            try {  
                DataSources.destroy(ds_pooled);  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
      
      
    /** 
     * 资源关闭 
     *  
     * @param rs 
     * @param stmt 
     * @param conn 
     */  
    public static void close(ResultSet rs, Statement stmt  
            , Connection conn) {  
        if (rs != null) {  
            try {  
                rs.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
  
        if (stmt != null) {  
            try {  
                stmt.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
  
        if (conn != null) {  
            try {  
                conn.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
}
