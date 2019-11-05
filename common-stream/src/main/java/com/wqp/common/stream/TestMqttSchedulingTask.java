package com.wqp.common.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wqp.common.stream.mqtt.client.MqttExecutorClient;
import com.wqp.common.stream.mqtt.core.MqttConf;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class TestMqttSchedulingTask implements SchedulingConfigurer {
    private int sequence = 1;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        MqttExecutorClient mqttExecutorClient = new MqttExecutorClient();
        MqttConf mqttConf = new MqttConf();
        mqttConf.setHost("202.116.104.36").setPort(1883).setUsername("aiot_pub_client").setPassword("123456");
        mqttExecutorClient.setMqttConf(mqttConf);
        scheduledTaskRegistrar.addTriggerTask(() -> process(mqttExecutorClient), triggerContext -> new CronTrigger("0/5 * * * * ?").nextExecutionTime(triggerContext));
    }

    private void process(MqttExecutorClient mqttExecutorClient){
        sequence += 1;
        MqttPayload mqttPayload = new MqttPayload();
        mqttPayload.setTitle("标题"+sequence);
        mqttPayload.setPayload("这是一条手环测试内容"+sequence);
        String payload = null;
        try {
            payload = objectMapper.writeValueAsString(mqttPayload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(payload != null) mqttExecutorClient.publish("aiot/YK20190808CP001/YK20190808ED001/YK20190808SD004", payload);
    }

    private class MqttPayload{
        private String title;
        private String payload;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPayload() {
            return payload;
        }

        public void setPayload(String payload) {
            this.payload = payload;
        }
    }
}
