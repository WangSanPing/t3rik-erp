package com.t3rik.print.server;

import com.t3rik.print.protocol.PrintMessageProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

public class PrintClientInfoMessageHandler extends SimpleChannelInboundHandler<PrintMessageProto.Printer> {

    public static ConcurrentHashMap<String, SocketAddress> socketAddressMap = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PrintMessageProto.Printer printClientInfoMessage) throws Exception {
        //打印机名称和打印机客户端地址映射   一对一关系
        socketAddressMap.put(printClientInfoMessage.getPrintClientInfoMessage().getLocation(), channelHandlerContext.channel().remoteAddress());
    }
}
