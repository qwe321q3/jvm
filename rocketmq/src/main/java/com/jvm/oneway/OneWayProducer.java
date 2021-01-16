package com.jvm.oneway;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.Charset;

/**
 * Hello world!
 * 步骤：
 * 1、创建producer ,设置消费者组
 * 2、设置nameServer
 * 3、启动消费者
 * 4、发送mq消息
 * 生产者
 *
 */
public class OneWayProducer
{
    public static void main( String[] args ) throws InterruptedException, MQClientException, RemotingException {
        // 定义producer
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("oneWayProducerGroup");
        // 设置 nameServer ，namesvr中记录了broker的地址及路由
        defaultMQProducer.setNamesrvAddr("192.168.31.3:9876;192.168.31.4:9876");

        defaultMQProducer.start();

        for (int i = 0; i < 100; i++) {
            String msg = "oneWay"+i;
            Message message = new Message("oneWayTopic","oneTag",msg.getBytes(Charset.defaultCharset()));
            defaultMQProducer.sendOneway(message);
        }

        defaultMQProducer.shutdown();
    }
}
