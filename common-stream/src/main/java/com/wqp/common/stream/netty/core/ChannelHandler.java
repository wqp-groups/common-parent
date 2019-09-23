package com.wqp.common.stream.netty.core;

import io.netty.channel.ChannelHandlerContext;

public interface ChannelHandler extends NettyRoot{

    // 通道上下文
    ChannelHandlerContext getChannelHandlerContext();

    // 通道状态
    ChannelStatus getChannelStatus();

//    void setNettyConf(NettyConf nettyConf);

    // 通道接收数据
//    Object receive(ChannelHandlerContext ctx, byte[] bytes);

    // 通道应答数据
//    void receiveRepay(ChannelHandlerContext ctx, Object msg);

    // 通道使用触发
//    void channelUserTriggered(ChannelHandlerContext ctx, Object evt);

    // 通道关闭：关闭原则遵循谁打开谁关闭
//    void channelClose(ChannelHandlerContext ctx);

}
