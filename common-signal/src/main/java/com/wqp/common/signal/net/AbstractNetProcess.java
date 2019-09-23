package com.wqp.common.signal.net;

import com.wqp.common.signal.core.NetProcess;
import com.wqp.common.stream.netty.client.AbstractChannelHandlerClient;
import com.wqp.common.stream.netty.client.NettyExecutorClient;
import com.wqp.common.stream.netty.server.AbstractChannelHandlerServer;
import com.wqp.common.stream.netty.server.NettyExecutorServer;
import com.wqp.common.util.common.CharUtil;
import com.wqp.common.util.common.HexUtil;
import io.netty.channel.ChannelHandlerContext;

/**
 * 网口处理抽象类
 */
public abstract class AbstractNetProcess  implements NetProcess<String> {
    private NettyExecutorClient nettyExecutorClient;
    private NettyExecutorServer nettyExecutorServer;

    @Override
    public void launcher() {

    }

    @Override
    public void shutdown() {

    }


    private void process(ChannelHandlerContext ctx, byte[] bytes){
        String hexString = HexUtil.byteToString(bytes, CharUtil.SPACE);
        byte[] send = AbstractNetProcess.this.send(hexString);
        ctx.writeAndFlush(send);
        AbstractNetProcess.this.receive(hexString);
    }


    /**
     * Netty客户端通道
     */
    private class NetNettyChannelHandlerClient extends AbstractChannelHandlerClient {

        @Override
        protected Object receive(ChannelHandlerContext ctx, byte[] bytes) {
            process(ctx, bytes);
            return null;
        }
    }

    /**
     * Netty服务端通道
     */
    private class NetNettyChannelHandlerServer extends AbstractChannelHandlerServer {

        @Override
        protected Object receive(ChannelHandlerContext ctx, byte[] bytes) {
            process(ctx, bytes);
            return null;
        }
    }


}
