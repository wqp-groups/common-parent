package com.wqp.common.signal.net;

/**
 * 网口客户端
 */
public class NetExecutorClient extends AbstractNetProcess {


    @Override
    public void receive(String rawdata) {

    }

    @Override
    public byte[] send(String rawdata) {
        return new byte[0];
    }
}
