package com.wqp.common.signal.core;

public interface SignalRoot<D> {

    // 启动连接
    void launcher();

    // 接收数据
    void receive(D rawdata);

    // 发送数据
    byte[] send(D rawdata);

    // 关闭连接
    void shutdown();
}
