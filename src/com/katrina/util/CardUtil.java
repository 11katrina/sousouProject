package com.katrina.util;

import com.katrina.pojo.*;
import org.junit.Test;

import java.io.*;
import java.util.*;

public class CardUtil {
    public  Map<String, MobileCard> cards = new HashMap<>();//用户列表
    public  Map<String, List<ConsumInfo>>consumInfos = new HashMap<>();//消费记录
    //使用场景列表
    private List<Scene> scenes = new ArrayList<>();
/**
 * 初始化
 * 电话卡  使用记录
 *
 * */
    //初始化
    public  void unit(){
        //话痨
        MobileCard card1=new MobileCard("13901234567", "小陈", "123456", new TalkPackage(), 58,30,0,0,0 );
        //网虫
        MobileCard card2=new MobileCard("13908765431", "小许", "987654", new NetPackage(), 68,200,0,0,0);
        //超级
        MobileCard card3=new MobileCard("13092322791", "小薛", "123123", new SuperPackage(), 78,300,0,0,0);
        cards.put(card1.getCardNumber(),card1);
        cards.put(card2.getCardNumber(),card2);
        cards.put(card3.getCardNumber(),card3);

        //初始化消费记录列表

        //卡1消费记录,
        List<ConsumInfo> c1= new ArrayList<>();
        c1.add(new ConsumInfo(card1.getCardNumber(),"通话",100));
        card1.setRealTalkTime(card1.getRealTalkTime()+100);
        consumInfos.put(card1.getCardNumber(),c1);
        //卡2消费记录
        List<ConsumInfo> c2= new ArrayList<>();
        c2.add(new ConsumInfo(card2.getCardNumber(),"上网",1));
        card2.setRealFlow(card2.getRealFlow()+1*1024);
        consumInfos.put(card2.getCardNumber(),c2);
        //消费记录卡3
        List<ConsumInfo> c3= new ArrayList<>();
        c3.add(new ConsumInfo(card3.getCardNumber(),"通话",100));
        c3.add(new ConsumInfo(card3.getCardNumber(),"上网",1024));
        c3.add(new ConsumInfo(card3.getCardNumber(),"发短信",20));
        card3.setRealTalkTime(card3.getRealTalkTime()+100);
        card3.setRealFlow(card3.getRealFlow()+1*1024);
        card3.setRealSMSCount(card3.getRealSMSCount()+20);
        consumInfos.put(card3.getCardNumber(),c3);

        /**
         * 初始化场景
         * */
            //初始化场景
        scenes.add(new Scene("通话", 90, "问候客户，谁知其如此难缠，通话90分钟"));
        scenes.add(new Scene("通话", 30, "询问妈妈身体状况，本地通话30分钟"));
        scenes.add(new Scene("短信", 5, "参与环境保护实施方案问卷调查，发送短信5条"));
        scenes.add(new Scene("短信", 50, "通知朋友手机换号，发送短信50条"));
        scenes.add(new Scene("上网", 1, "和女朋友用微信视频聊天，使用流量1GB"));
        scenes.add(new Scene("上网", 2, "晚上手机在线看韩剧，不留神睡着啦！使用2GB"));

    }
    //添加用户账号
    public void addCard(MobileCard card){
        System.out.println("************可选择的卡号************");
        String[] strings = getNewNumbers(9);
        Scanner scanner = new Scanner(System.in);
        for (int i = 1; i < strings.length+1; i++) {
            if(i%3==0){
                System.out.print(i + "." + strings[i - 1] + "\t");
                System.out.println();
            }
            else {
                System.out.print(i + "." + strings[i - 1] + "\t");
            }
        }
        System.out.print("请选择卡号（输入1-9的序号）：");
        int num = scanner.nextInt();
        card.cardNumber = strings[num-1];
        System.out.println("1.话痨套餐\t2.网虫套餐\t3.超人套餐");
        System.out.print("请选择套餐（输入序号）");
        int num1 = scanner.nextInt();
        switch (num1){
            case 1:
                card.serPackage = new TalkPackage();
                break;
            case 2:
                card.serPackage = new NetPackage();
                break;
            case 3:
                card.serPackage = new SuperPackage();
                break;
            default:
                System.out.println("输入错误！");
        }
        System.out.print("请输入姓名：");
        card.userName = scanner.next();
        System.out.print("请输入密码：");
        card.passWord = scanner.next();
        System.out.print("请输入预存话费：");
        double money = scanner.nextDouble();
        while (money<card.serPackage.price){
            System.out.print("您预存的话费金额不足以支付本月固定套餐资费，请重新充值：");
            money = scanner.nextDouble();
        }
        card.money = money;
        System.out.println("注册成功！");
        System.out.println(card.showMeg());
        cards.put(card.cardNumber,card);
    }
    //话费充值
    public void chargeMoney(String number,double money){
        if(isExistCard(number)){
            MobileCard card = cards.get(number);
            card.money += money;
            System.out.println("充值成功，当前话费余额为"+card.money+"元");
        }else
            System.out.println("卡号输入错误！");
    }
    public void userSoso(String number){
        Random random = new Random();
        ServicePackage servicePackage = cards.get(number).serPackage;
        while (true){
            int num  = random.nextInt(6);
            int temp = 0;
            switch (num){
                case 0:
                case 1:
                    if(servicePackage instanceof CallService){
                        try {
                            temp = ((CallService) servicePackage).call(scenes.get(num).data,cards.get(number));
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }finally {
                            System.out.println("您使用的情景模式是："+scenes.get(num).description);
                            //添加消费记录
                            List<ConsumInfo> consumInfoList = consumInfos.get(number);
                            consumInfoList.add(new ConsumInfo(number,scenes.get(num).type,temp));
                            break;
                        }

                    }
                    continue;
                case 2:
                case 3:
                    if(servicePackage instanceof SendService){
                        try {
                            temp = ((SendService) servicePackage).send(scenes.get(num).data,cards.get(number));
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }finally {
                            System.out.println("您使用的情景模式是："+scenes.get(num).description);
                            //添加消费记录
                            List<ConsumInfo> consumInfoList = consumInfos.get(number);
                            consumInfoList.add(new ConsumInfo(number,scenes.get(num).type,temp));
                            break;
                        }

                    }
                    continue;
                case 4:
                case 5:
                    if(servicePackage instanceof NetService){
                        try {
                            temp = ((NetService) servicePackage).netPlay(scenes.get(num).data,cards.get(number));
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }finally {
                            System.out.println("您使用的情景模式是："+scenes.get(num).description);
                            //添加消费记录
                            List<ConsumInfo> consumInfoList = consumInfos.get(number);
                            consumInfoList.add(new ConsumInfo(number,scenes.get(num).type,temp));
                            break;
                        }

                    }
                    continue;
            }
            //退出while循环
            break;
        }
    }
    public void showDescription(String cardNumber){//读取消费记录文件
        //先把消费信息全部写入到文件
        if(isExistCard(cardNumber)){
            MobileCard card = cards.get(cardNumber);
            File file;
            InputStream in = null;
            OutputStream ot = null;
            file = new File("D:\\JavaProject\\sousouProject\\消费记录.txt");
            try {
                in = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int len;
                StringBuffer sb = new StringBuffer();
                while ((len=in.read(buffer))!=-1){
                    sb.append(new String(buffer,"UTF-8"));
                }
                System.out.println(sb);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }finally {
                if(in!=null){
                    try {
                        in.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }else
            System.out.println("卡号输入错误！");
    }
    //本月账单查询
    public void showAmountDetail(String number){
        MobileCard card = cards.get(number);
        System.out.println("您的卡号：" + number);
        System.out.println("套餐资费：" + card.serPackage.price);
        System.out.println("合计：" + card.consumAmount);
        System.out.println("账户余额：" + String.format("%.2f", card.money));

    }
    //套餐余量查询
    public void showRemainDetail(String number){
        int remainTalk;
        int remainSmsCount;
        int remainFlow;
        StringBuffer meg = new StringBuffer();
        MobileCard card = cards.get(number);
        meg.append("您的卡号是"+number+"\n");
        ServicePackage servicePackage = card.serPackage;
        //判断转型是否成功
        if(servicePackage instanceof TalkPackage){
            meg.append("套餐是：话痨套餐，套餐剩余：\n");
            TalkPackage talkPackage = (TalkPackage)servicePackage;
            remainTalk = talkPackage.talkTime>card.realTalkTime?talkPackage.talkTime-card.realTalkTime:0;
            remainSmsCount = talkPackage.smsCount>card.realSMSCount?talkPackage.smsCount-card.realSMSCount:0;
            meg.append("通话时长："+remainTalk+"分钟\n");
            meg.append("短信条数："+remainSmsCount+"条\n");
            System.out.println(meg);
        }else if(servicePackage instanceof SuperPackage){
            meg.append("套餐是：超人套餐，套餐剩余：\n");
            SuperPackage superPackage = (SuperPackage) servicePackage;
            remainTalk = superPackage.talkTime>card.realTalkTime?superPackage.talkTime-card.realTalkTime:0;
            remainSmsCount = superPackage.smsCount>card.realSMSCount?superPackage.smsCount-card.realSMSCount:0;
            remainFlow = superPackage.flow>card.realFlow?superPackage.flow-card.realFlow:0;
            meg.append("通话时长："+remainTalk+"分钟\n");
            meg.append("短信条数："+remainSmsCount+"条\n");
            meg.append("上网流量："+remainFlow+"MB\n");
            System.out.println(meg);
        }else{
            meg.append("套餐是：网虫套餐，套餐剩余：\n");
            NetPackage netPackage = (NetPackage) servicePackage;
            remainFlow = netPackage.flow>card.realFlow?netPackage.flow-card.realFlow:0;
            meg.append("上网流量："+remainFlow+"MB\n");
            System.out.println(meg);
        }
    }
    //打印消费清单到文本文件
    public void printAmountDetail(String number){
        FileWriter file;
        if(consumInfos.containsKey(number)) {
            try {
                file = new FileWriter("消费记录.txt");
                StringBuffer sb = new StringBuffer("***********************" + number + "的消费记录***********************\n");
                List<ConsumInfo> consumInfo = consumInfos.get(number);
                sb.append("序号\t类型\t数据（通话（分钟）/上网（MB）/短信（条））\n");
                int i = 1;
                for (ConsumInfo c : consumInfo) {
                    sb.append(i + "\t" + c.type + "\t" + c.consumData+"\n");
                    i++;
                }
                file.write(sb.toString());
                System.out.println(sb.toString());
                file.flush();
                file.close();
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else
            System.out.println("对不起，不存在该号码的消费记录，不能打印！");
    }
    public void changingPack(String number,int packID){//修改套餐
        MobileCard card = cards.get(number);
        ServicePackage servicePackage = createPack(packID);
        if(servicePackage instanceof TalkPackage){
            TalkPackage talkPackage = (TalkPackage) servicePackage;
            if(card.serPackage instanceof TalkPackage){
                System.out.println("对不起，您已经是该套餐用户，无需换套餐！");
            }else {
                if (card.money > servicePackage.price) {
                    card.money = card.money - talkPackage.price;
                    card.realFlow = 0;
                    card.realSMSCount = 0;
                    card.realSMSCount = 0;
                    card.serPackage = talkPackage;
                    card.consumAmount = talkPackage.price;
                    System.out.println("更换套餐成功！"+talkPackage.showInfo());

                } else {
                    System.out.println("对不起，您的余额不足以支付新套餐的本月资费，请充值后再办理更换套餐业务！");
                }
            }
        }else if(servicePackage instanceof SuperPackage){
            SuperPackage superPackage = (SuperPackage) servicePackage;
            if(card.serPackage instanceof SuperPackage){
                System.out.println("对不起，您已经是该套餐用户，无需换套餐！");
            }else {
                if (card.money > superPackage.price) {
                    card.money = card.money - superPackage.price;
                    card.realFlow = 0;
                    card.realSMSCount = 0;
                    card.realSMSCount = 0;
                    card.serPackage = superPackage;
                    card.consumAmount = superPackage.price;
                    System.out.println("更换套餐成功！"+superPackage.showInfo());
                } else {
                    System.out.println("对不起，您的余额不足以支付新套餐的本月资费，请充值后再办理更换套餐业务！");
                }
            }
        }else {
            NetPackage netPackage = (NetPackage) servicePackage;
            if (card.serPackage instanceof NetPackage) {
                System.out.println("对不起，您已经是该套餐用户，无需换套餐！");
            } else {
                if (card.money > netPackage.price) {
                    card.money = card.money - netPackage.price;
                    card.realFlow = 0;
                    card.realSMSCount = 0;
                    card.realSMSCount = 0;
                    card.serPackage = netPackage;
                    card.consumAmount = netPackage.price;
                    System.out.println("更换套餐成功！"+netPackage.showInfo());
                } else {
                    System.out.println("对不起，您的余额不足以支付新套餐的本月资费，请充值后再办理更换套餐业务！");
                }
            }
        }
    }
    public void delCard(String number){
        cards.remove(number);
        System.out.println("卡号"+number+"办理退网成功！\n谢谢使用！");
    }
    //判断该卡是否存在
    public boolean isExistCard(String number,String password){
        boolean flag = false;
        if(cards.containsKey(number)){
            MobileCard card = cards.get(number);
            if(card.passWord.equals(password)){
                flag = true;
            }else
                System.out.println("密码错误！");
        }else
            System.out.println("暂无该卡号！");
        return flag;
    }
    public boolean isExistCard(String number){
        if(cards.containsKey(number)){
            return true;
        }else
            return false;
    }
    //生成一个随机数
    public String createNumber(){
        //获取已注册用户的所有号码
        Set<String> numbers = cards.keySet();
        String num = "139";
        StringBuffer number = new StringBuffer();
        boolean flag = false;
        //判断号码是否重复
        do {
            Random random = new Random();
            int ran = random.nextInt(100000000);
            number.append(num);
            number.append(ran);
            for (String cardnumber : numbers) {
                if(number.toString().equals(cardnumber)){
                    flag = true;
                    //清除
                    number.delete(0,number.length());
                }
            }
        }while (flag);
        return number.toString();
    }
    public String[] getNewNumbers(int count){
        String[] strings = new String[count];
        for (int i = 0; i < count; i++) {
            String newNumber = createNumber();
            strings[i] = newNumber;
        }
        return strings;

    }
    public void addConsumInfo(String number,ConsumInfo card){}
    public ServicePackage createPack(int packId){
        ServicePackage servicePackage = null;
        switch (packId){
            case 1:
                servicePackage = new TalkPackage();
                break;
            case 2:
                servicePackage = new NetPackage();
                break;
            case 3:
                servicePackage = new SuperPackage();
                break;
        }
        return servicePackage;
    }

    @Test
    public void test1(){

        showAmountDetail("13901234567");
    }

}
