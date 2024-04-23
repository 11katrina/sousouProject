package com.katrina.pojo;

public class SuperPackage extends ServicePackage implements CallService,SendService,NetService{
    public int talkTime;
    public int smsCount;
    public int flow;

    public SuperPackage(int talkTime, int smsCount, int flow,double price) {
        super.price = price;
        this.talkTime = talkTime;
        this.smsCount = smsCount;
        this.flow = flow;
    }
    public SuperPackage() {
        this.talkTime = 200;
        this.flow = 1*1024;
        this.smsCount = 50;
        this.price = 78;
    }

    @Override
    public String showInfo() {
        String Info1 = super.showInfo();
        String Info = Info1+"，通话时长：" + talkTime + "，短信条数：" + smsCount + "，上网流量：" + flow ;
        return Info;
    }

    @Override
    public int call(int minCount, MobileCard card) throws Exception {
        int temp = 0;//这次实际消耗的
        int remain = this.talkTime - card.realTalkTime;
        for (int i = 0; i < minCount; i++) {
            if (remain > 1) {
                card.realTalkTime += 1;
                temp++;
            } else if (card.money >= 0.2) {
                card.realTalkTime += 1;
                card.money -= 0.2;
                card.cardNumber += 0.2;
                temp++;
            } else {
                Exception exception = new Exception("通话已" + temp + "分钟，您的余额不足，请充值后再使用！");
                throw exception;
            }
        }
        return temp;
    }

    @Override
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

    @Override
    public int send(int count, MobileCard card) throws Exception {
        int temp = 0;
        int remain = this.smsCount - card.realSMSCount;
        for (int i = 0; i < count; i++) {
            if (remain > 1) {
                card.realSMSCount += 1;
                temp++;
            } else if (card.money >= 0.1) {
                card.realSMSCount += 1;
                card.money -= 0.1;
                card.cardNumber += 0.1;
                temp++;
            } else {
                Exception exception = new Exception("发送信息已" + temp + "条，您的余额不足，请充值后再使用！");
                throw exception;
            }
        }
        return temp;
    }
}
