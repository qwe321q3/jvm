package com.jvm.asyn;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @ClassName : AsynConsumer
 * @Description : 异步消息消费
 * 默认消息模式为集群模式
 *
 *
 * @Author : tianshuo
 * @Date: 2020-12-13 12:03
 */
public class AsynConsumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("AsynConsumerGroup");
        defaultMQPushConsumer.subscribe("asynTopic", "asynTag");
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        defaultMQPushConsumer.setNamesrvAddr("192.168.31.3:9876;192.168.31.4:9876");

        defaultMQPushConsumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            for (int i = 0; i < list.size(); i++) {

                System.out.println(new String(list.get(i).getBody()));

            }

           // 3种情况下，会触发消费重试

            // 设置消费失败，查看重试机制
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        });
        defaultMQPushConsumer.start();
    }
}

