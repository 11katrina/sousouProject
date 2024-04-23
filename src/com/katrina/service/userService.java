package com.katrina.service;

import com.katrina.pojo.MobileCard;
import com.katrina.pojo.Scene;
import com.katrina.util.CardUtil;

import java.util.Scanner;

public class userService {
    public void mainMenu(CardUtil cardUtil){
        String cardNumber;
        boolean Flag = true;
        do{
            System.out.println("************************欢迎使用嗖嗖移动业务大厅************************");
            System.out.println("1.用户登录\t2.用户注册\t3.使用嗖嗖\t4.话费充值\t5.资费说明\t6.退出系统");
            Scanner scanner = new Scanner(System.in);
            System.out.print("请选择：");
            int number = scanner.nextInt();
            switch (number){
                case 1:
                    System.out.print("请输入手机卡号：");
                    cardNumber = scanner.next();
                    System.out.print("请输入密码：");
                    String password = scanner.next();
                    if(cardUtil.isExistCard(cardNumber,password)){
                        cardMenu(cardUtil,cardNumber);
                    }
                    break;
                case 2:
                    MobileCard card = new MobileCard();
                    cardUtil.addCard(card);
                    break;
                case 3:
                    System.out.print("请输入手机卡号：");
                    cardNumber = scanner.next();
                    cardUtil.userSoso(cardNumber);
                    break;
                case 4:
                    System.out.print("请输入手机卡号：");
                    cardNumber = scanner.next();
                    System.out.print("请输入充值金额：");
                    double money = scanner.nextDouble();
                    cardUtil.chargeMoney(cardNumber,money);
                    break;
                case 5:
                    System.out.print("请输入手机卡号：");
                    cardNumber = scanner.next();
                    cardUtil.showDescription(cardNumber);
                    break;
                case 6:
                    Flag = false;
                    System.out.println("谢谢使用!");
                    break;
                default:
                    System.out.println("输入错误，请重新选择");
            }
        }while (Flag);
    }

    public void cardMenu(CardUtil cardUtil,String cardNumber){
        boolean flag = true;
        do{
            System.out.println("************嗖嗖移动用户菜单************");
            System.out.println("1.本月账单查询\t2.套餐余量查询\t3.打印消费清单\t4.套餐变更\t5.办理退网");
            Scanner scanner = new Scanner(System.in);
            System.out.print("请选择(输入1-5选择功能，按其他数字返回上一级)：");
            int number = scanner.nextInt();
            switch (number){
                case 1:
                    System.out.println("***********本月账单查询***********");
                    cardUtil.showAmountDetail(cardNumber);
                    break;
                case 2:
                    System.out.println("***********套餐余量查询***********");
                    cardUtil.showRemainDetail(cardNumber);
                    break;
                case 3:
                    System.out.println("***********打印消费清单***********");
                    cardUtil.printAmountDetail(cardNumber);
                    break;
                case 4:
                    System.out.println("***********套餐变更***********");
                    System.out.println("1.话痨套餐\t2.网虫套餐\t3.超人套餐\t请选择(序号)：");
                    int n = scanner.nextInt();
                    cardUtil.changingPack(cardNumber,n);
                    break;
                case 5:
                    System.out.println("***********办理退网***********");
                    cardUtil.delCard(cardNumber);
                    flag = false;
                    break;
                default:
                    flag = false;
            }
        }while (flag);
    }

    public static void main(String[] args) {
        CardUtil cardUtil = new CardUtil();
        cardUtil.unit();
        userService userService = new userService();
        userService.mainMenu(cardUtil);
    }
}
