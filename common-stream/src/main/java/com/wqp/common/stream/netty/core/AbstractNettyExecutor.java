package com.wqp.common.stream.netty.core;

import io.netty.buffer.Unpooled;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

public abstract class AbstractNettyExecutor implements NettyExecutor {
    private NettyConf nettyConf; // netty配置
    private ChannelHandler channelHandler;  // 连接通道
    private ConnectStatus connectStatus = ConnectStatus.NONE; // Netty连接状态

    protected void addSocketChannel(SocketChannel socketChannel){
        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(32768,true,true, Unpooled.copiedBuffer(nettyConf.getFrameDelimiter().getBytes())));
        nettyConf.getChannelHandler().stream().forEach(d -> socketChannel.pipeline().addLast(d));
    }

    public NettyConf getNettyConf() {
        return nettyConf;
    }

    /**
     * 在netty客户端创建时注入NettyConf
     * @param nettyConf
     */
    public void setNettyConf(NettyConf nettyConf) {
        this.nettyConf = nettyConf;
    }

    public ChannelHandler getChannelHandler() {
        return channelHandler;
    }

    public void setChannelHandler(ChannelHandler channelHandler) {
        this.channelHandler = channelHandler;
    }

    public ConnectStatus getConnectStatus() {
        return connectStatus;
    }

    public void setConnectStatus(ConnectStatus connectStatus) {
        this.connectStatus = connectStatus;
    }

}
