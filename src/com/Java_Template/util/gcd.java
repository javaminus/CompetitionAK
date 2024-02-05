package com.Java_Template.util;


public class gcd {

    // 求最大公约数
    public static int findGCD(int num1, int num2) {
        while (num2 != 0) {
            int temp = num2;
            num2 = num1 % num2;
            num1 = temp;
        }
        return num1;
    }

    public int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    // 求最小公倍数
    public static int findLCM(int num1, int num2) {
        return (num1 * num2) / findGCD(num1, num2);
    }

}
