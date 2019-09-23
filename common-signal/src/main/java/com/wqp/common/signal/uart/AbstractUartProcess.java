package com.wqp.common.signal.uart;


import com.fazecast.jSerialComm.*;
import com.wqp.common.signal.core.SignalConf;
import com.wqp.common.signal.core.UartProcess;
import com.wqp.common.util.common.CharUtil;
import com.wqp.common.util.common.HexUtil;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.util.Assert;

/**
 * 串口处理抽象类
 */
public abstract class AbstractUartProcess implements UartProcess<String> {
    private SerialPort serialPort;
    private SignalConf signalConf;

    public SignalConf getSignalConf() {
        return signalConf;
    }

    public void setSignalConf(SignalConf signalConf) {
        this.signalConf = signalConf;
    }


    @Override
    public void launcher() {
        Assert.notNull(signalConf, "SignalConf未配置");
        serialPort = SerialPort.getCommPort(signalConf.getUartport());
        serialPort.setComPortParameters(signalConf.getBaudrate(), signalConf.getDatabit(), signalConf.getStopbit(), signalConf.getParity());
        serialPort.setFlowControl(signalConf.getFlowcontrol());
        if(!serialPort.isOpen()) serialPort.openPort();
        serialPort.addDataListener( getEventListener(getUartEventCategory()));
        System.out.println("串行端口：" + getSignalConf().getUartport() + "已启动");
    }

    @Override
    public void shutdown() {
        serialPort.removeDataListener();
        serialPort.closePort();
    }

    protected UartEventCategory getUartEventCategory(){
        return UartEventCategory.DEFAULT;
    }

    private SerialPortDataListener getEventListener(UartEventCategory category){
        SerialPortDataListener listener;
        switch (category){
            case DEFAULT:
                listener = new LocalDelimitMessageReceivedSerialPortMessageListener();
                break;
            case DATA_AVAILABLE_READING:
                listener = new LocalDataAvailableReadingSerialPortDataListener();
                break;
            case DELIMITED_MESSAGE_RECEIVED:
                listener = new LocalDelimitMessageReceivedSerialPortMessageListener();
                break;
            case DATA_BYTES_RECEIVED:
                listener = new LocalDataBytesReceivedSerialPortDataListener();
                break;
            case FIXED_LENGTH_DATA_PACKET_RECEIVED:
                listener = new LocalFixedLengthDataPacketReceivedSerialPortPacketListener();
                break;
            default:
                listener = new LocalDelimitMessageReceivedSerialPortMessageListener();
        }
        return listener;
    }

    private void process(byte[] data){
        String hexString = HexUtil.byteToString(data, CharUtil.SPACE);
        String newStringUtf8 = StringUtils.newStringUtf8(data);
        System.out.println("接收端收到数据为：" + newStringUtf8);

        // 先响应
        byte[] send = send(hexString);
        if(send != null && send.length > 0){
            int sendStatus = serialPort.writeBytes(send, send.length);
            if(sendStatus == -1) System.out.println("串行端口：" + getSignalConf().getUartport() + "发送数据失败");
        }
        // 再处理
        receive(hexString);
    }


    /**
     * 定义：串行端口上有可用的数据可以读取，就会触发回调。
     *      一旦您的回调被触发，您可以选择调用bytesavailable（）来确定有多少数据可供读取，
     *      并且您必须使用read（）或readbytes（）方法实际读取数据
     */
    private class LocalDataAvailableReadingSerialPortDataListener implements SerialPortDataListener{

        @Override
        public int getListeningEvents() {
            return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
        }

        @Override
        public void serialEvent(SerialPortEvent serialPortEvent) {
            if(serialPortEvent.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) return;
            byte[] data = new byte[serialPort.bytesAvailable()];
            serialPort.readBytes(data, data.length);
            process(data);
        }
    }

    /**
     * 定义：从串行端口实际读取了一定数量的数据就会触发回调
     */
    private class LocalDataBytesReceivedSerialPortDataListener implements SerialPortDataListener{

        @Override
        public int getListeningEvents() {
            return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
        }

        @Override
        public void serialEvent(SerialPortEvent serialPortEvent) {
            process(serialPortEvent.getReceivedData());
        }
    }

    private class LocalFixedLengthDataPacketReceivedSerialPortPacketListener implements SerialPortPacketListener {

        @Override
        public int getPacketSize() {
            return 0;
        }

        @Override
        public int getListeningEvents() {
            return 0;
        }

        @Override
        public void serialEvent(SerialPortEvent serialPortEvent) {
            process(serialPortEvent.getReceivedData());
        }
    }

    /**
     * 定义：根据指定的自定义分隔符接收到消息，就会触发回调。
     * 此分隔符可以包含一个或多个连续字节，并且可以指示数据包的开始或结束
     */
    private class LocalDelimitMessageReceivedSerialPortMessageListener implements SerialPortMessageListener {

        @Override
        public byte[] getMessageDelimiter() {
            byte[] bytesUtf8 = StringUtils.getBytesUtf8(signalConf.getUartdelimit());
            return bytesUtf8 == null ? new byte[0] : bytesUtf8;
        }

        /**
         * 分隔符表示消息结束
         * @return true表示分隔应用于结束位置,否则应用于开始位置
         */
        @Override
        public boolean delimiterIndicatesEndOfMessage() {
            return true;
        }

        @Override
        public int getListeningEvents() {
            return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
        }

        @Override
        public void serialEvent(SerialPortEvent serialPortEvent) {
            process(serialPortEvent.getReceivedData());
        }
    }
}
