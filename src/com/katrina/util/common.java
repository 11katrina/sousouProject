package com.katrina.util;

import java.text.DecimalFormat;

public class common {
    //double类型格式转化
    public static String dataFormat(double data) {
        DecimalFormat formatData = new DecimalFormat("#.0");
        return formatData.format(data);
    }
}
