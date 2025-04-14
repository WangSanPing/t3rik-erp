package com.t3rik.print.server;

import com.t3rik.print.protocol.PrintMessageProto;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class PrintServer implements DisposableBean {

    EventLoopGroup bossGroup = null;
    EventLoopGroup workerGroup = null;
    int port = 9016;


    @PostConstruct
    public void nettyServerInit() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new service());
    }

    @Override
    public void destroy() throws Exception {
        if (bossGroup!=null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup!=null) {
            workerGroup.shutdownGracefully();
        }
    }

    public class service implements Runnable {

        @Override
        public void run() {
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap serverBootstrap = new ServerBootstrap();
                serverBootstrap.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            public void initChannel(SocketChannel ch) {
                                ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                                ch.pipeline().addLast(new ProtobufDecoder(PrintMessageProto.Printer.getDefaultInstance()));
                                ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                                ch.pipeline().addLast(new ProtobufEncoder());
                                ch.pipeline().addLast(new PrintClientInfoMessageHandler());
                                ch.pipeline().addLast(new PrintServerDefaultHandler());
                            }
                        });
                ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
                System.out.println("==========Netty服务端启动成功========");
                channelFuture.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
                if (bossGroup!=null) {
                    bossGroup.shutdownGracefully();
                }
                if (workerGroup!=null) {
                    workerGroup.shutdownGracefully();
                }
            }
        }
    }
}
