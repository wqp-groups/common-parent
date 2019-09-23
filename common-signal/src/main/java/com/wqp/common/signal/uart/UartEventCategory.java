package com.wqp.common.signal.uart;

/**
 * 串口基于事件类别
 */
public enum UartEventCategory {
    DEFAULT("Default"),
    DATA_AVAILABLE_READING("Data_Available_Reading"),
    DATA_BYTES_RECEIVED("Data_Bytes_Received"),
    FIXED_LENGTH_DATA_PACKET_RECEIVED("Fixed_Length_Data_Packed_Received"),
    DELIMITED_MESSAGE_RECEIVED("Delimited_Message_Received");

    UartEventCategory(String type){
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }
}
