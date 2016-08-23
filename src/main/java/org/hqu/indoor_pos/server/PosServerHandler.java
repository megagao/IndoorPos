package org.hqu.indoor_pos.server;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hqu.indoor_pos.bean.Location;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class PosServerHandler extends ChannelHandlerAdapter {

	public  static Map<Integer,Location> locsToDB = new ConcurrentHashMap<Integer,Location>();
	
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        //System.out.println("server channelRead..");
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String str = new String(req, "UTF-8");
        //String str = (String) msg;
        System.out.println(str);
    	Location loc = Server.dealer.getLocation(str);
        if(loc!=null){
            Server.locs.put(loc);
        }
       
    }   
    

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //System.out.println("server channelReadComplete..");
        ctx.flush();//刷新后才将数据发出到SocketChannel
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.out.println("server exceptionCaught..");
        cause.printStackTrace();
        ctx.close();
    }


}
