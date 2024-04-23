package com.katrina.pojo;

public class NetPackage extends ServicePackage implements NetService{

    public int flow;

    public NetPackage(int flow,double price) {
        super.price = price;
        this.flow = flow;
    }

    public NetPackage() {
        this.flow = 3*1024;//MB
        this.price = 68;
    }

    @Override
    public String showInfo() {
        String Info1 = super.showInfo();
        String Info =  Info1+ "，上网流量：" + flow/1024 ;
        return Info;
    }

    @Override
    //情景模拟
    public int netPlay(int flow, MobileCard card) throws Exception {
        //将GB转为MB
        flow *= Math.pow(2,10);
        int temp = 0;
        int remain = this.flow - card.realFlow;
        for (int i = 0; i < flow; i++) {
            if (remain > 1) {
                card.realFlow += 1;
                temp++;
            } else if (card.money >= 0.1) {
                card.realFlow += 0.001;
                card.money -= 0.1;
                card.cardNumber += 0.1;
                temp++;
            } else {
                Exception exception = new Exception("流量已使用" + temp + "MB，您的余额不足，请充值后再使用！");
                throw exception;
            }
        }
        return temp;
    }
}
