package com.wqp.common.stream;

import com.wqp.common.stream.mqtt.client.MqttExecutorClient;
import com.wqp.common.stream.mqtt.core.MqttConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommonStreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonStreamApplication.class, args);
        MqttExecutorClient mqttExecutorClient = new MqttExecutorClient();
        testMqttPublish(mqttExecutorClient);
//        testMqttSubscribe(mqttExecutorClient);
    }

    private static void testMqttPublish(MqttExecutorClient mqttExecutorClient){

        MqttConf mqttConf = new MqttConf();
        mqttConf.setHost("202.116.104.36").setPort(1883).setUsername("aiot_pub_client").setPassword("123456");
        mqttExecutorClient.setMqttConf(mqttConf);
        mqttExecutorClient.publish("aiot/YK20190808CP001/YK20190808ED001/YK20190808SD004", "这是一条手环测试内容");
    }

    private static void testMqttSubscribe(MqttExecutorClient mqttExecutorClient){
        MqttConf mqttConf = new MqttConf();
        mqttConf.setHost("202.116.104.36").setPort(1883).setUsername("aiot_pub_client").setPassword("123456");
        mqttExecutorClient.setMqttConf(mqttConf);
        mqttExecutorClient.subscribe("aiot/wqp");
    }
}
