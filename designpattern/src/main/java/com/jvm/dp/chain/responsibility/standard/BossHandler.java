package com.jvm.dp.chain.responsibility.standard;

public class BossHandler extends Handler<MyRequest> {

    @Override
    public void setHandler(Handler handler) {
        this.successor = handler;
    }

    @Override
    public void handleRequest(MyRequest myRequest) {
        System.out.println("BossHandler: "+myRequest);
        System.out.println("BossHandler: 批准");
        if (successor!=null){
           successor.handleRequest(myRequest);
        }
    }
}
