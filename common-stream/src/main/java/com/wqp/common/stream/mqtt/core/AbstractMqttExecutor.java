package com.wqp.common.stream.mqtt.core;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.util.Assert;

public abstract class AbstractMqttExecutor implements MqttExecutor {
    private MqttConf mqttConf; // mqtt配置
    private MqttClient mqttClient; // mqtt client

    private String getServerUri(){
        return "tcp://" + mqttConf.getHost() + ":" + mqttConf.getPort();
    }

    public MqttConf getMqttConf() {
        return mqttConf;
    }

    public void setMqttConf(MqttConf mqttConf) {
        this.mqttConf = mqttConf;
        if(this.mqttConf.getMqttCallback() == null) this.mqttConf.setMqttCallback(new MqttClientCallback());
    }

    /**
     * 启动且连接mqtt客户端
     */
    private void launcher() {
        Assert.notNull(getMqttConf(), "MqttConf未配置");
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName(mqttConf.getUsername());
        mqttConnectOptions.setPassword(mqttConf.getPassword().toCharArray());
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setConnectionTimeout(15);
        mqttConnectOptions.setKeepAliveInterval(10);
        mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
        try {
            mqttClient = new MqttClient(getServerUri(), IdUtil.getId(), new MemoryPersistence());
            mqttClient.setCallback(mqttConf.getMqttCallback());
            mqttClient.connect(mqttConnectOptions);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证mqtt客户端连接
     */
    private void verifyMqttClient() {
        if(mqttClient == null){
            launcher();
        }else{
            if(!mqttClient.isConnected()){
                try {
                    mqttClient.reconnect();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 发布消息
     * @param topic 主题
     * @param message 消息
     */
    @Override
    public void publish(String topic, String message) {
        verifyMqttClient();
        try {
            mqttClient.publish(topic, message.getBytes(), 2, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅消息
     * @param topic 主题
     */
    @Override
    public void subscribe(String... topic) {
        verifyMqttClient();
        try {
            mqttClient.subscribe(topic, new int[]{2});
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


}
