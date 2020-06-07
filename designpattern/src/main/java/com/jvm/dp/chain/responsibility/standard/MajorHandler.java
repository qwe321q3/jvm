package com.jvm.dp.chain.responsibility.standard;

public class MajorHandler extends Handler<MyRequest> {

    @Override
    public void setHandler(Handler handler) {
        this.successor = handler;
    }

    @Override
    public void handleRequest(MyRequest myRequest) {
        System.out.println("MajorHandler: "+myRequest);

        if(myRequest.getDays()>3){
            System.out.println("MajorHandler：请假天数太多，请上级领导审批!");
        }

        if (successor != null) {
            successor.handleRequest(myRequest);
        }
    }
}
