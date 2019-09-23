package com.wqp.common.stream.netty.client;


import com.wqp.common.stream.netty.core.AbstractChannelHandler;
import com.wqp.common.stream.netty.core.NettyConf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 客户端通道数据处理抽象类
 */
public abstract class AbstractChannelHandlerClient extends AbstractChannelHandler {
    private NettyConf nettyConf;

    protected NettyConf getNettyConf() {
        return nettyConf;
    }

    protected void setNettyConf(NettyConf nettyConf) {
        this.nettyConf = nettyConf;
    }

    /**
     * 客户端接收数据-发送应答服务端数据
     * @param ctx
     * @param msg
     */
    @Override
    protected void receiveRepay(ChannelHandlerContext ctx, Object msg) {
        // 忽略,客户端不作应答处理
        // 如果需要可覆盖此方法
    }

    @Override
    protected void channelUserTriggered(ChannelHandlerContext ctx, Object evt) {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent ise = (IdleStateEvent) evt;
            if(ise.state() == IdleState.WRITER_IDLE){ // 验证写状态
                System.out.println("Netty客户端未发送消息至服务端");
                System.out.println("Netty客户端主动关闭丢失通道");
                ctx.channel().close();
            }
        }
    }

    @Override
    protected void channelClose(ChannelHandlerContext ctx) {
        // 客户端主动关闭通道
        Channel channel = ctx.channel();
        if(channel.isActive()) ctx.close();
        setChannelHandlerContext(null);
        setChannelStatus(null);
    }

}
