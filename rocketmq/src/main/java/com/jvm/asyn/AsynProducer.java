package com.jvm.asyn;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName : AsynProducer
 * @Description : 异步消息发送
 * @Author : tianshuo
 * @Date: 2020-12-13 12:03
 */
public class AsynProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {

        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("AsyncProducerGroup");
        defaultMQProducer.setNamesrvAddr("192.168.31.98:9876");
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(0);

        defaultMQProducer.start();

        CountDownLatch countDownLatch = new CountDownLatch(1000);

        for (int i = 0; i < 1000; i++) {
            Message message = new Message("asynTopic", "asynTag", ("异步消息: "+ i).getBytes(Charset.defaultCharset()));
            defaultMQProducer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    countDownLatch.countDown();
                    System.out.println("消息发送成功： "+sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable throwable) {
                    countDownLatch.countDown();
                    System.out.println("发送失败：  "+throwable);
                }
            });
        }

        countDownLatch.await();
        defaultMQProducer.shutdown();




    }
}

