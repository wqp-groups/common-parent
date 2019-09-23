package com.wqp.common.stream.netty.core;

/**
 * Netty连接状态
 */
public enum ConnectStatus {
    NONE("None"), // 空
    CONNECTION("Connection"), // 连接中
    RUNING("Runing"), // 运行中
    CLOSED("Closed"), // 已关闭
    SHUTDOWN("Shutdown");   // 已停止

    ConnectStatus(String type){
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }

}
