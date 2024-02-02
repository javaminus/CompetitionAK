package com.template.util;

/**
 * @author Minus
 * @date 2023/12/9 20:00
 */
public class 寻找质数 {
    public static int findLargestPrime(int n) {
        for (int i = n; i >= 2; i--) {
            if (isPrime(i)) {
                return i;
            }
        }
        return -1; // 如果没有找到质数，可以返回一个合适的默认值
    }

    public static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
