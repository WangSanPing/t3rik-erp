package com.t3rik.print.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

public class PrintServerDefaultHandler extends ChannelInboundHandlerAdapter {

    public static ConcurrentHashMap<SocketAddress, Channel> chanelMap = new ConcurrentHashMap<>();


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //客户端和通道映射 也是一对一关系
        chanelMap.put(ctx.channel().remoteAddress(), ctx.channel());
        System.out.println("客户端连接已建立：" + ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        chanelMap.remove(ctx.channel().remoteAddress());
        System.out.println("客户端连接已关闭：" + ctx.channel().remoteAddress());
    }
}
