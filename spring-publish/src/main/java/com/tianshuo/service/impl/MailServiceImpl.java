package com.tianshuo.service.impl;

import com.tianshuo.model.Order;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl {

    @Async
    @EventListener
    public void sendMail(Order order){
        System.out.print(Thread.currentThread().getName() +"\t ");
        System.out.println(order);
        throw new RuntimeException("dd");
    }

}
