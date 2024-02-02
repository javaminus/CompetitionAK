package com.template.util;

/**
 * @author Minus
 * @date 2023/12/11 9:28
 */
public class 快速幂 {
    // 空间 o(1), 时间o(logn)
    public double myPow(double x, int n) {
        if (x == 0.0f) {
            return 0.0d;
        }
        long b = n;
        double res = 1.0;
        if (b < 0) {
            x = 1 / x;
            b = -b;
        }
        while (b > 0) {
            if ((b & 1) == 1) { // 取最小位是否为1
                res *= x;
            }
            x *= x; // x = x**2
            b >>= 1;
        }
        return res;
    }
}
