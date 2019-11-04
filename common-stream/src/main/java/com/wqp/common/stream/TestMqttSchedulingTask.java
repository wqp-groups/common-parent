package com.wqp.common.stream;

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
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        MqttExecutorClient mqttExecutorClient = new MqttExecutorClient();
        MqttConf mqttConf = new MqttConf();
        mqttConf.setHost("202.116.104.36").setPort(1883).setUsername("aiot_pub_client").setPassword("123456");
        mqttExecutorClient.setMqttConf(mqttConf);
        scheduledTaskRegistrar.addTriggerTask(() -> process(mqttExecutorClient), triggerContext -> new CronTrigger("0/5 * * * * ?").nextExecutionTime(triggerContext));
    }

    private void process(MqttExecutorClient mqttExecutorClient){
        mqttExecutorClient.publish("aiot/YK20190808CP001/YK20190808ED001/YK20190808SD004", "{\"title\":\"标题\",\"payload\":\"这是一条手环测试内容\"}");
    }
}
