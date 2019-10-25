package com.wqp.common.stream.mqtt.core;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * mqtt 客户端处理回调
 */
public class MqttClientCallback implements MqttCallbackExtended {

    /**
     * 连接丢失回调(pub/sub)
     * @param throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("throwable = [" + throwable + "]");
    }

    /**
     * 发布模式下无响应(pub)
     * 订阅模式下接收数据(sub)
     * @param topic
     * @param mqttMessage
     */
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        System.out.println("topic = [" + topic + "], mqttMessage = [" + mqttMessage + "]");
    }

    /**
     * 发布消息完成回调(publish)
     * @param iMqttDeliveryToken
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("iMqttDeliveryToken = [" + iMqttDeliveryToken + "]");
    }

    /**
     * 连接服务端成功回调
     * @param b
     * @param s
     */
    @Override
    public void connectComplete(boolean b, String s) {

    }
}
