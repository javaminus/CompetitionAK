package com.Java_Template.util.General;

/**
 * 快速幂 时间 O(logn),空间 O(1)
 */
public class my_pow {
    private double myPow(double x, int b) {
        if (x == 0.0f) {
            return 0.0d;
        }
        long n = b;
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }
        double res = 1;
        while (n > 0) {
            if ((n & 1) == 1) {
                res *= x;
            }
            x *= x;
            n >>= 1;
        }
        return res;
    }

    int Mod = 1337;
    public int pow(int x, int n) {
        int res = 1;
        while (n != 0) {
            if ((n & 1) == 1) {
                res = (int) ((long) res * x % Mod);
            }
            x = (int) ((long) x * x % Mod);
            n >>= 1;
        }
        return res;
    }
}
