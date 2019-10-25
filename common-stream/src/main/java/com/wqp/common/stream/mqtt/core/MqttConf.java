package com.wqp.common.stream.mqtt.core;

import org.eclipse.paho.client.mqttv3.MqttCallback;

public class MqttConf {
    // mqtt host
    private String host;
    // mqtt port
    private Integer port;
    // mqtt username
    private String username;
    // mqtt password
    private String password;
    // mqtt client callback
    private MqttCallback mqttCallback;

    public String getHost() {
        return host;
    }

    public MqttConf setHost(String host) {
        this.host = host;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public MqttConf setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public MqttConf setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MqttConf setPassword(String password) {
        this.password = password;
        return this;
    }

    public MqttCallback getMqttCallback() {
        return mqttCallback;
    }

    public MqttConf setMqttCallback(MqttCallback mqttCallback) {
        this.mqttCallback = mqttCallback;
        return this;
    }
}
