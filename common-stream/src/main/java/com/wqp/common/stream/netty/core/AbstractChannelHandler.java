package com.wqp.common.stream.netty.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public abstract class AbstractChannelHandler extends ChannelInboundHandlerAdapter implements ChannelHandler {
    private ChannelHandlerContext channelHandlerContext;
    private ChannelStatus channelStatus;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        setChannelStatus(ChannelStatus.INACTIVE);
        System.out.println("channelInactive(ChannelHandlerContext ctx)");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        setChannelStatus(ChannelStatus.ACTIVE);
        setChannelHandlerContext(ctx);
        System.out.println("channelActive(ChannelHandlerContext ctx)");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        setChannelStatus(ChannelStatus.REGISTERED);
        System.out.println("channelRegistered(ChannelHandlerContext ctx)");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        setChannelStatus(ChannelStatus.UNREGISTERED);
        System.out.println("channelUnregistered(ChannelHandlerContext ctx)");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg); // 此处切记禁止调用super方法,否则会导致ByteBuf内部计数器提前清空,产生io.netty.util.IllegalReferenceCountException: refCnt: 0
        setChannelStatus(ChannelStatus.READ);
        System.out.println("channelRead(ChannelHandlerContext ctx, Object msg)");
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        Object o = receive(ctx, bytes);
        receiveRepay(ctx, o);
        channelClose(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        setChannelStatus(ChannelStatus.READCOMPLETE);
        System.out.println("channelReadComplete(ChannelHandlerContext ctx)");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        setChannelStatus(ChannelStatus.USEREVENT);
        System.out.println("userEventTriggered(ChannelHandlerContext ctx, Object evt)");
        channelUserTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
        setChannelStatus(ChannelStatus.WRITABILITY);
        System.out.println("channelWritabilityChanged(ChannelHandlerContext ctx)");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        setChannelStatus(ChannelStatus.EXECPTION);
        System.out.println("exceptionCaught(ChannelHandlerContext ctx, Throwable cause)");
        receiveRepay(ctx, cause.getMessage());
        channelClose(ctx);
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    protected void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    public ChannelStatus getChannelStatus() {
        return channelStatus;
    }

    protected void setChannelStatus(ChannelStatus channelStatus) {
        this.channelStatus = channelStatus;
    }

    // 通道接收数据
    protected abstract Object receive(ChannelHandlerContext ctx, byte[] bytes);

    // 通道应答数据
    protected abstract void receiveRepay(ChannelHandlerContext ctx, Object msg);

    // 通道使用触发
    protected abstract void channelUserTriggered(ChannelHandlerContext ctx, Object evt);

    // 通道关闭：关闭原则遵循谁打开谁关闭
    protected abstract void channelClose(ChannelHandlerContext ctx);
}
