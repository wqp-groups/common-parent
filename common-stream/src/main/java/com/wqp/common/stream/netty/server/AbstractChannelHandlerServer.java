package com.wqp.common.stream.netty.server;

import com.wqp.common.stream.netty.core.AbstractChannelHandler;
import com.wqp.common.stream.netty.core.NettyConf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 服务端通道数据处理抽象类
 * 注：服务端需要继承该抽象类进行接收消息
 */
public abstract class AbstractChannelHandlerServer extends AbstractChannelHandler {
    private NettyConf nettyConf;
    // 通道丟失次数计数
    private int channelLossCount = 0;

    protected NettyConf getNettyConf() {
        return nettyConf;
    }

    protected void setNettyConf(NettyConf nettyConf) {
        this.nettyConf = nettyConf;
    }

    /**
     * 服务端接收数据-发送应答客户端数据
     * @param ctx
     * @param msg
     */
    @Override
    protected void receiveRepay(ChannelHandlerContext ctx, Object msg) {
        ByteBuf response = null;
        if(msg instanceof ByteBuf){
            response = Unpooled.wrappedBuffer(Unpooled.copiedBuffer(((ByteBuf)msg)), Unpooled.copiedBuffer(getNettyConf().getFrameDelimiter().getBytes()));
        }
        if(msg instanceof String){
            response = Unpooled.wrappedBuffer(Unpooled.copiedBuffer(msg.toString().getBytes()), Unpooled.copiedBuffer(getNettyConf().getFrameDelimiter().getBytes()));
        }
        ctx.writeAndFlush(response);
    }

    /**
     * 自定义通道使用触发机制
     * @param ctx
     * @param evt
     */
    @Override
    protected void channelUserTriggered(ChannelHandlerContext ctx, Object evt) {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent ise = (IdleStateEvent) evt;
            if(ise.state() == IdleState.READER_IDLE){ // 验证读状态
                System.out.println("Netty服务端未收到客户端的消息");
                channelLossCount++;
                if(channelLossCount > 2){
                    System.out.println("Netty服务端主动关闭丢失通道");
                    ctx.channel().close();
                }
            }
        }
    }

    @Override
    protected void channelClose(ChannelHandlerContext ctx) {
        // 忽略,服务端不做主动关闭操作
    }

}
