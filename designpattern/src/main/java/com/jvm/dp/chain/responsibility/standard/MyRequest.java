package com.jvm.dp.chain.responsibility.standard;

/**
 * 请求信息
 *
 */
public class MyRequest {

    //备注
    private String remark;
    // 请假天数
    private int days;
    // 名字
    private String name;

    public MyRequest() {
    }

    public MyRequest(String remark, int days, String name) {
        this.remark = remark;
        this.days = days;
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyRequest{" +
                "remark='" + remark + '\'' +
                ", days=" + days +
                ", name='" + name + '\'' +
                '}';
    }
}
