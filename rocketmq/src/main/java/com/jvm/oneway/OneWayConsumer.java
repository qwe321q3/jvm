package com.jvm.oneway;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

public class OneWayConsumer {
    public static void main(String[] args) throws MQClientException {
        // 用推消息模式
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("oneWayGroup");
        defaultMQPushConsumer.setNamesrvAddr("192.168.31.98:9876");
        // 消息的最后读取
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 采用集群的消息消费模式
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);

        defaultMQPushConsumer.subscribe("oneWayTopic","oneTag");

        /**
         *  注册消费监听器
         */
        defaultMQPushConsumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {

//            MessageExt messageExt = list.get(0);
//            System.out.println("receive message : "+ new String(messageExt.getBody()));
//            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

            for (int i = 0; i < list.size(); i++) {
                System.out.println(new java.lang.String(list.get(i).getBody()));

            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        defaultMQPushConsumer.start();

    }
}
