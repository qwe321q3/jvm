package com.jvm.dp.chain.responsibility.standard;

public class Client {
    public static void main(String[] args) {

        MyRequest myRequest = new MyRequest();
        myRequest.setName("amos");
        myRequest.setDays(5);
        myRequest.setRemark("生病请假");
        BossHandler bossHandler = new BossHandler();
        MajorHandler majorHandler  = new MajorHandler();
        majorHandler.setHandler(bossHandler);
        HrHandler handler = new HrHandler();
        handler.setHandler(majorHandler);
        handler.handleRequest(myRequest);


    }
}
