package com.wqp.common.signal.net;

/**
 * 网口服务端
 */
public class NetExecutorServer extends AbstractNetProcess {
    @Override
    public void receive(String rawdata) {

    }

    @Override
    public byte[] send(String rawdata) {
        return new byte[0];
    }

}
