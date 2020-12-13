package com.jvm.pull;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName : PullProducer
 * @Description : 拉模式生产者  同步发送
 * @Author : tianshuo
 * @Date: 2020-12-13 17:29
 */
public class PullProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("pullProducerGroup");
        defaultMQProducer.setNamesrvAddr("192.168.31.98:9876");
        defaultMQProducer.start();


        for (int i = 0; i < 1000; i++) {
            Message message = new Message("pullTopic","pullTag",("pull"+i).getBytes("UTF-8"));
            // 同步发送
            SendResult send = defaultMQProducer.send(message);
            System.out.println(send);
        }

        defaultMQProducer.shutdown();
    }
}

