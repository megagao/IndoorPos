package org.hqu.indoor_pos.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.hqu.indoor_pos.algorithm.Dealer;
import org.hqu.indoor_pos.algorithm.Trilateral;
import org.hqu.indoor_pos.bean.Location;
import org.hqu.indoor_pos.util.CopyOnWriteMap;
import org.hqu.indoor_pos.util.SpringUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;


public class Server {
	
	/*定位结果队列*/
	public static BlockingQueue<Location> locs;
	
	/*定位算法*/
	public static Dealer dealer;
	
	/*员工id的查询缓存*/
	public static Map<String, String> empIds;
	
	/*房间id的查询缓存*/
	public static Map<String, Integer> roomIds;
	
	/*基站坐标的查询缓存*/
	public static Map<String, Double[]> baseStationLocs;
	
	/*环境因子的查询缓存*/
	public static Map<Integer, Double[]> envFactors;
	
	private static JdbcTemplate jdbcTemplate;
	
    public static void main(String[] args) throws Exception {
    	
    	jdbcTemplate = (JdbcTemplate) SpringUtil.context.getBean("jdbcTemplate");
    	
        int port = 50006;
        
        locs = new LinkedBlockingQueue<Location>();
        
        dealer = new Trilateral();
        
        empIds = new CopyOnWriteMap<String, String>();
        
        roomIds = new CopyOnWriteMap<String, Integer>();
        
        baseStationLocs = new CopyOnWriteMap<String, Double[]>();
        
        envFactors = new CopyOnWriteMap<Integer, Double[]>();
        
        jdbcTemplate.query("select * from env_factor",   
                new RowCallbackHandler() {     
              
                    @Override    
                    public void processRow(ResultSet rs) throws SQLException {     
                    	envFactors.put(rs.getInt(1), new Double[]{rs.getDouble(2), rs.getDouble(3), rs.getDouble(4)});
                    }     
        });   
		
        jdbcTemplate.query("select terminal_id, emp_id from employee",   
                new RowCallbackHandler() {     
              
                    @Override    
                    public void processRow(ResultSet rs) throws SQLException {     
                    	empIds.put(rs.getString(1), rs.getString(2));
                    }     
        });   
		
        jdbcTemplate.query("select base_id, room_id, x_axis, y_axis from base_station",   
                new RowCallbackHandler() {     
              
                    @Override    
                    public void processRow(ResultSet rs) throws SQLException {     
                    	roomIds.put(rs.getString(1), rs.getInt(2));
        				baseStationLocs.put(rs.getString(1), new Double[]{rs.getDouble(3), rs.getDouble(4)});
                    }     
        });   
		
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }
        
        /*启动显示客户端监听线程*/
        new Thread(new Runnable() {
			@Override
			public void run() {
				 DispServer dispServer = new DispServer();
			        dispServer.startDispServer();
			}
		}).start();
       
        new Server().bindPos(port);
       
    }
    
	public void bindPos(int port) throws Exception {
    	
    	/*配置服务器的NIO线程租*/
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());

            /*绑定端口，同步等待成功*/
            ChannelFuture f = b.bind(port).sync();
            /*等待服务端监听端口关闭*/
            f.channel().closeFuture().sync();
        } finally {
            /*优雅退出，释放线程池资源*/
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel sc) throws Exception {
        	
            System.out.println("server initChannel..");
            // 解码转String
            sc.pipeline().addLast(new LineBasedFrameDecoder(1024));
            //sc.pipeline().addLast(new ByteArrayEncoder());
            // 解码转String
            //sc.pipeline().addLast(new StringDecoder());
    		// 编码器 String
            //sc.pipeline().addLast(new StringEncoder()); 
            
            //业务处理
            sc.pipeline().addLast(new PosServerHandler());
            
            //sc.pipeline().addLast(new ByteArrayDecoder());
        }
    }
    
}