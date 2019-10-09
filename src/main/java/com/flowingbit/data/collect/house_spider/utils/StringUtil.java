package com.flowingbit.data.collect.house_spider.utils;

public class StringUtil {

    public static String collectStringNumber(String str) {
        if(str==null||str.length()<=0){
            return null;
        }else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                    sb.append(str.charAt(i));
                }
            }
            return sb.toString();
        }
    }
}
