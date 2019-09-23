package com.wqp.common.stream.netty.server;

import com.wqp.common.stream.netty.core.AbstractNettyExecutor;
import com.wqp.common.stream.netty.core.ChannelHandler;
import com.wqp.common.stream.netty.core.ConnectStatus;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.util.Assert;

/**
 * netty服务端
 */
public final class NettyExecutorServer extends AbstractNettyExecutor {

    @Override
    public void launcher() {
        Assert.notNull(getNettyConf(), "NettyConf未配置");
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        setConnectStatus(ConnectStatus.CONNECTION);
        getNettyConf().getChannelHandler().stream().filter(d -> d instanceof ChannelHandler).forEach(d -> {
            setChannelHandler((ChannelHandler) d);
            ((AbstractChannelHandlerServer)getChannelHandler()).setNettyConf(getNettyConf());
        });

        serverBootstrap.group(boosGroup, workGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) {
                addSocketChannel(socketChannel);
            }
        });
        try {
            ChannelFuture future = serverBootstrap.bind(getNettyConf().getPort()).sync();
            future.addListener(future1 -> {
                if(future1.isSuccess()){
                    setConnectStatus(ConnectStatus.RUNING);
                    System.out.println("Netty服务端口：" + getNettyConf().getPort() + "启动正常");
                }
            });
            ChannelFuture closeFuture = future.channel().closeFuture().sync();
            closeFuture.addListener(future1 -> {
                if(future1.isSuccess()){
                    setConnectStatus(ConnectStatus.CLOSED);
                    System.out.println("Netty服务端口：" + getNettyConf().getPort() + "已断开");
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(boosGroup != null) {
                boosGroup.shutdownGracefully();
                if(boosGroup.isShutdown()) System.out.println("Netty服务端BoosGroup关闭");
            }
            if(workGroup != null){
                workGroup.shutdownGracefully();
                if(workGroup.isShutdown()) System.out.println("Netty服务端WorkGroup关闭");
            }
        }
    }

}
