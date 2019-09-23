package com.wqp.common.signal.core;

/**
 * 串口配置
 * 网口配置
 */
public class SignalConf {
    private String netip;           // 网口ip
    private String netport;         // 网口端口
    private String uartport;        // 串口端口
    private Integer baudrate;       // 串口波特率
    private Integer databit;        // 串口数据位
    private Integer stopbit;        // 串口停止位
    private Integer parity;         // 串口检验位
    private Integer flowcontrol;    // 串口流控制 FLOW_CONTROL_RTS_ENABLED, FLOW_CONTROL_CTS_ENABLED, FLOW_CONTROL_DTR_ENABLED, FLOW_CONTROL_DSR_ENABLED, FLOW_CONTROL_XONXOFF_IN_ENABLED, FLOW_CONTROL_XONXOFF_OUT_ENABLED
    private String uartdelimit;     // 串口数据分隔符(结束码)
    private Integer uartpacksize;   // 串口必须读取的所需字节数

    public SignalConf() {
    }

    public SignalConf(String netport) {
        this(null, netport);
    }

    public SignalConf(String netip, String netport) {
        this.netip = netip;
        this.netport = netport;
    }

    public SignalConf(String uartport, Integer baudrate, Integer databit, Integer stopbit) {
        this(uartport, baudrate, databit, stopbit, 0);
    }

    public SignalConf(String uartport, Integer baudrate, Integer databit, Integer stopbit, String usrtdelimit) {
        this(uartport, baudrate,databit, stopbit, 0,0, usrtdelimit,0);
    }

    public SignalConf(String uartport, Integer baudrate, Integer databit, Integer stopbit, Integer parity, Integer flowcontrol, Integer uartpacksize) {
        this(uartport, baudrate, databit, stopbit, parity, flowcontrol, null, uartpacksize);
    }

    public SignalConf(String uartport, Integer baudrate, Integer databit, Integer stopbit, Integer parity) {
        this(uartport, baudrate, databit, stopbit, parity, 0, null,0);
    }

    public SignalConf(String uartport, Integer baudrate, Integer databit, Integer stopbit, Integer parity, Integer flowcontrol, String uartdelimit, Integer uartpacksize) {
        this.uartport = uartport;
        this.baudrate = baudrate;
        this.databit = databit;
        this.stopbit = stopbit;
        this.parity = parity;
        this.flowcontrol = flowcontrol;
        this.uartdelimit = uartdelimit;
        this.uartpacksize = uartpacksize;
    }

    public String getNetip() {
        return netip;
    }

    public void setNetip(String netip) {
        this.netip = netip;
    }

    public String getNetport() {
        return netport;
    }

    public void setNetport(String netport) {
        this.netport = netport;
    }

    public String getUartport() {
        return uartport;
    }

    public void setUartport(String uartport) {
        this.uartport = uartport;
    }

    public Integer getBaudrate() {
        return baudrate == 0 ? 115200 : baudrate;
    }

    public void setBaudrate(Integer baudrate) {
        this.baudrate = baudrate;
    }

    public Integer getDatabit() {
        return databit == 0 ? 8 : databit;
    }

    public void setDatabit(Integer databit) {
        this.databit = databit;
    }

    public Integer getStopbit() {
        return stopbit == 0 ? 1 : stopbit;
    }

    public void setStopbit(Integer stopbit) {
        this.stopbit = stopbit;
    }

    public Integer getParity() {
        return parity;
    }

    public void setParity(Integer parity) {
        this.parity = parity;
    }

    public Integer getFlowcontrol() {
        return flowcontrol;
    }

    public void setFlowcontrol(Integer flowcontrol) {
        this.flowcontrol = flowcontrol;
    }

    public String getUartdelimit() {
        return uartdelimit;
    }

    public void setUartdelimit(String uartdelimit) {
        this.uartdelimit = uartdelimit;
    }

    public Integer getUartpacksize() {
        return uartpacksize;
    }

    public void setUartpacksize(Integer uartpacksize) {
        this.uartpacksize = uartpacksize;
    }
}
