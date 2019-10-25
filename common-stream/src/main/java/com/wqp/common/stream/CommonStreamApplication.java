package com.wqp.common.stream;

import com.wqp.common.stream.mqtt.client.MqttExecutorClient;
import com.wqp.common.stream.mqtt.core.MqttConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommonStreamApplication {
    @Autowired
    private MqttExecutorClient mqttExecutorClient;

    public static void main(String[] args) {
        SpringApplication.run(CommonStreamApplication.class, args);

    }

    private void testMqttPublish(){
        MqttConf mqttConf = new MqttConf();
        mqttConf.setHost("202.116.104.36").setPort(1883).setUsername("aiot_pub_client").setPassword("123456");
        mqttExecutorClient.setMqttConf(mqttConf);
        mqttExecutorClient.publish("aiot/wqp", "这是一条测试内容");
    }

    private void testMqttSubscribe(){
        MqttConf mqttConf = new MqttConf();
        mqttConf.setHost("202.116.104.36").setPort(1883).setUsername("aiot_pub_client").setPassword("123456");
        mqttExecutorClient.setMqttConf(mqttConf);
        mqttExecutorClient.subscribe("aiot/wqp");
    }
}
