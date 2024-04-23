package com.katrina.pojo;

public class ConsumInfo {
    public String cardNumber;
    public String type;
    public int consumData;//消费数据

    public ConsumInfo(String cardNumber, String type, int consumData) {
        this.cardNumber = cardNumber;
        this.type = type;
        this.consumData = consumData;
    }
}
