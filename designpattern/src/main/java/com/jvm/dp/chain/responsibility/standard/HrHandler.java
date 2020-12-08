package com.jvm.dp.chain.responsibility.standard;

/**
 * Hr处理人
 *
 */
public class HrHandler extends Handler<MyRequest> {

    @Override
    public void setHandler(Handler handler) {
        this.successor = handler;
    }

    @Override
    public void handleRequest(MyRequest myRequest) {
        System.out.println("HrHandler: "+myRequest);
        if(myRequest.getDays()>2){
            System.out.println("HrHandler: 没权利审批，请求上级审批!");
        }

        if (successor != null) {
            successor.handleRequest(myRequest);
        }

    }
}
