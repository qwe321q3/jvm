package com.jvm.pull;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.MessageQueueListener;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @ClassName : PullConsumer
 * @Description : 拉取消费
 * @Author : tianshuo
 * @Date: 2020-12-13 17:33
 */
public class PullConsumer {

    public static void main(String[] args) throws MQClientException {
        DefaultLitePullConsumer defaultLitePullConsumer = new DefaultLitePullConsumer("pullConsumerGroup");
        defaultLitePullConsumer.setNamesrvAddr("192.168.31.98:9876");
        defaultLitePullConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        defaultLitePullConsumer.subscribe("pullTopic","*");

        defaultLitePullConsumer.start();

        try {
            while (true) {
                List<MessageExt> messageExts = defaultLitePullConsumer.poll();
                System.out.printf("%s%n", messageExts);
            }
        }finally {
            defaultLitePullConsumer.shutdown();
        }
    }
}

