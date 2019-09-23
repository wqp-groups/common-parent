package com.wqp.common.stream.netty.core;

/**
 * Netty通道状态
 */
public enum ChannelStatus {
//    NONE("None"),                   // 空
    ACTIVE("Active"),               // 激活
    INACTIVE("InActive"),           // 活动中
    REGISTERED("Registered"),       // 注册
    UNREGISTERED("UnRegistered"),   // 未注册
    WRITABILITY("Writability"),     // 可写
    READ("Read"),                   // 读
    READCOMPLETE("ReadComplete"),   // 读完成
    USEREVENT("userEvent"),         // 使用
    EXECPTION("Exception");         // 异常

    ChannelStatus(String type) {
        this.type = type;
    }

    public String type;

    public String getType() {
        return type;
    }
}
