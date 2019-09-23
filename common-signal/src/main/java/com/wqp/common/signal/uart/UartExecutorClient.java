package com.wqp.common.signal.uart;


/**
 * 串口客户端
 */
public class UartExecutorClient extends AbstractUartProcess {

    @Override
    public void receive(String rawdata) {
        System.out.println(rawdata);
    }

    @Override
    public byte[] send(String rawdata) {
//        System.out.println("send:接收到数据为："+rawdata);
        byte[] res = null;
        if("05".equals(rawdata)) res = "06".getBytes();
        return res;
    }
}
