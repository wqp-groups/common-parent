package com.wqp.common.stream.mqtt.core;

public interface MqttExecutor {

    void publish(String topic, String message);

    void subscribe(String... topic);

}
