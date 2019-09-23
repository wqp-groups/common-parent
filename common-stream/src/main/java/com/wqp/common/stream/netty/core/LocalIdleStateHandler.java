package com.wqp.common.stream.netty.core;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

@Sharable
public class LocalIdleStateHandler extends IdleStateHandler {
    public LocalIdleStateHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
        super(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
    }

    public LocalIdleStateHandler(long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit) {
        super(readerIdleTime, writerIdleTime, allIdleTime, unit);
    }

    public LocalIdleStateHandler(boolean observeOutput, long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit) {
        super(observeOutput, readerIdleTime, writerIdleTime, allIdleTime, unit);
    }

}
