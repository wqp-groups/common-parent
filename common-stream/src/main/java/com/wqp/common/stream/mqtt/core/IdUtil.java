package com.wqp.common.stream.mqtt.core;

import java.util.UUID;

public class IdUtil {

    public static String getId(){
        return UUID.randomUUID().toString();
    }
}
