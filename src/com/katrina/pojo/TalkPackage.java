package com.katrina.pojo;

public class TalkPackage extends ServicePackage implements CallService,SendService {
    public int talkTime;
    public int smsCount;

    public TalkPackage(int talkTime, int smsCount, double price) {
        super.price = price;
        this.talkTime = talkTime;
        this.smsCount = smsCount;
    }

    //在构造函数初始化属性
    public TalkPackage() {
        this.talkTime = 500;
        this.smsCount = 30;
        this.price = 58;
    }

    @Override
    public String showInfo() {
        String Info1 = super.showInfo();
        String Info = Info1 + "，通话时长：" + talkTime + "，短信条数：" + smsCount;
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
