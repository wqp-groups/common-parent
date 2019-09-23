package com.wqp.common.stream.netty.core;

import io.netty.channel.ChannelHandler;

import java.util.List;

public class NettyConf {
    // netty host
    private String host;
    // netty port
    private Integer port;
    // netty channel handler
    private List<ChannelHandler> channelHandler;
    // data frame delimiter
    private String frameDelimiter;


    public String getHost() {
        return host;
    }

    public NettyConf setHost(String host) {
        this.host = host;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public NettyConf setPort(Integer port) {
        this.port = port;
        return this;
    }

    public List<ChannelHandler> getChannelHandler() {
        return channelHandler;
    }

    public NettyConf setChannelHandler(List<ChannelHandler> channelHandler) {
        this.channelHandler = channelHandler;
        return this;
    }

    public String getFrameDelimiter() {
        return frameDelimiter;
    }

    public NettyConf setFrameDelimiter(String frameDelimiter) {
        this.frameDelimiter = frameDelimiter;
        return this;
    }

}
