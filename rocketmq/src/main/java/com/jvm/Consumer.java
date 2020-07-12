package com.jvm;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

public class Consumer {
    public static void main(String[] args) throws MQClientException {

        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("bb");
        defaultMQPushConsumer.setNamesrvAddr("192.168.31.3:9876;192.168.31.4:9876");
        defaultMQPushConsumer.subscribe("RocketTopic", "");
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        /**
         * 广播 消费全量消息
         * 如果有10条消息 ，2个消费端 ，那么2个消费端共同消费这10条消息
         */
//        defaultMQPushConsumer.setMessageModel(MessageModel.BROADCASTING);

        /**
         * 集群 默认集群是集群模式
         *
         * 如果有10条消息 ，2个消费端  ，那么2个消费端，每人都会消费10条消息
         */
        defaultMQPushConsumer.setMessageModel(MessageModel.BROADCASTING);


        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt messageExt = list.get(0);
                System.out.println("receive message : "+ new String(messageExt.getBody()));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        defaultMQPushConsumer.start();
    }
}
