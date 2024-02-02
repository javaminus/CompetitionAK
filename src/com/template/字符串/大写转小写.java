package com.template.字符串;

/**
 * @author Minus
 * @date 2023/11/11 14:59
 */
public class 大写转小写 {

    // 方法一
    public String toLowerCase1(String s) {
        String s1 = s.toLowerCase();
        return s1;
    }

    // 方法2
    public String toLowerCase2(String s) {
        int len = s.length();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            stringBuilder.append(c |= 32);
        }
        return stringBuilder.toString();
    }
}
