package com.wqp.common.stream.mqtt.client;

import com.wqp.common.stream.mqtt.core.AbstractMqttExecutor;

/**
 * Mqtt 客户端
 */
public class MqttExecutorClient extends AbstractMqttExecutor {

    @Override
    public void publish(String topic, String message) {
        super.publish(topic, message);
    }

    @Override
    public void subscribe(String... topic) {
        super.subscribe(topic);
    }
}
