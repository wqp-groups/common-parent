package com.wqp.common.stream.netty.client;

import com.wqp.common.stream.netty.core.AbstractNettyExecutor;
import com.wqp.common.stream.netty.core.ChannelHandler;
import com.wqp.common.stream.netty.core.ChannelStatus;
import com.wqp.common.stream.netty.core.ConnectStatus;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import org.springframework.util.Assert;

/**
 * Netty客户端
 */
public final class NettyExecutorClient extends AbstractNettyExecutor {

    @Override
    public void launcher() {
        Assert.notNull(getNettyConf(), "NettyConf未配置");
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        setConnectStatus(ConnectStatus.CONNECTION);
        getNettyConf().getChannelHandler().stream().filter(d -> d instanceof ChannelHandler).forEach(d -> {
            setChannelHandler((ChannelHandler) d);
            ((AbstractChannelHandlerClient)getChannelHandler()).setNettyConf(getNettyConf());
        });
        bootstrap.group(workGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 8000);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) {
                addSocketChannel(socketChannel);
            }
        });
        try {
            ChannelFuture future = bootstrap.connect(getNettyConf().getHost(), getNettyConf().getPort()).sync();
            future.addListener(future1 -> {
                if(future1.isSuccess()){
                    setConnectStatus(ConnectStatus.RUNING);
                    System.out.println("Netty客户端连接服务端IP：" + getNettyConf().getHost() + ",端口：" + getNettyConf().getPort() + "正常");
                }
            });
            ChannelFuture closeFuture = future.channel().closeFuture().sync();
            closeFuture.addListener(future1 -> {
                if(future1.isSuccess()){
                    setConnectStatus(ConnectStatus.CLOSED);
                    System.out.println("Netty客户端连接服务端IP：" + getNettyConf().getHost() + ",端口：" + getNettyConf().getPort() + "已断开");
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Future<?> future = workGroup.shutdownGracefully();
            future.addListener(future1 -> {
                if(future1.isSuccess()){
                    setConnectStatus(ConnectStatus.SHUTDOWN);
                    System.out.println("Netty客户端WorkGroup关闭");
                }
            });
        }

    }


    /**
     * 发送连接
     */
    private void initiate(){
        if(getConnectStatus() == ConnectStatus.NONE || getConnectStatus() == ConnectStatus.CLOSED || getConnectStatus() == ConnectStatus.SHUTDOWN){
            new Thread(() -> launcher()).start();
        }
    }

    /**
     * 客户端发布数据,默认连接等待12秒
     * @param msg 消息体
     * @param l 发布回调监听
     */
    public void publish(Object msg, OnPublishListener l){
        if(this.getChannelHandler() == null || this.getChannelHandler().getChannelStatus() == ChannelStatus.UNREGISTERED || this.getChannelHandler().getChannelHandlerContext() == null) initiate();
        int loop=0;
        do{
            ChannelHandler channelHandler = this.getChannelHandler();
            if(channelHandler != null && channelHandler.getChannelHandlerContext() != null){
                ByteBuf request = null;
                if(msg instanceof ByteBuf){
                    request = (ByteBuf) msg;
                }
                if(msg instanceof String){
                    request = Unpooled.wrappedBuffer(Unpooled.copiedBuffer(msg.toString().getBytes()), Unpooled.copiedBuffer(getNettyConf().getFrameDelimiter().getBytes()));
                }
                ChannelFuture future = channelHandler.getChannelHandlerContext().writeAndFlush(request);
                future.addListener(future1 -> {
                    if(future1.isSuccess()){
                        if(l != null) l.published(true, "发送成功");
                    }else {
                        Throwable cause = future1.cause();
                        if(l != null) l.published(false, cause.getMessage());
                        cause.printStackTrace();
                    }
                });
                break;
            }
            try {
                // 因通道连接预停留1秒
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loop++;
        } while (loop < 12);
        if(loop >= 12 && l != null) l.published(false, "发送失败");
    }

    /**
     * 客户端发布数据,默认连接等待10秒
     * @param msg 消息体
     */
    public boolean publish(Object msg){
        if(this.getChannelHandler() == null || this.getChannelHandler().getChannelStatus() == ChannelStatus.UNREGISTERED || this.getChannelHandler().getChannelHandlerContext() == null) initiate();
        boolean status = false;
        int loop=0;
        do{
            ChannelHandler channelHandler = this.getChannelHandler();
            if(channelHandler != null && channelHandler.getChannelHandlerContext() != null){
                ByteBuf request = null;
                if(msg instanceof ByteBuf){
                    request = (ByteBuf) msg;
                }
                if(msg instanceof String){
                    request = Unpooled.wrappedBuffer(Unpooled.copiedBuffer(msg.toString().getBytes()), Unpooled.copiedBuffer(getNettyConf().getFrameDelimiter().getBytes()));
                }
                channelHandler.getChannelHandlerContext().writeAndFlush(request);
                status = true;
                break;
            }
            try {
                // 因通道连接预停留1秒
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loop++;
        } while (loop < 10);
        return status;
    }

    /**
     * 客户端发布监听接口
     */
    public interface OnPublishListener{
        /**
         * 发送回调
         * @param status 发送结果
         */
        void published(boolean status, String msg);
    }

}
