package com.Java_Template.math.combinatorial_mathematics;


import java.util.Arrays;

public class math_comb {
    private static final int MOD = 1000000007; // 定义模数
    private int[] f; // 阶乘数组
    private int[] invF; // 阶乘逆元数组

    // 构造函数，初始化阶乘和逆元数组
    public math_comb() {
        f = new int[1];
        invF = new int[1];
        f[0] = 1; // 0! = 1
        invF[0] = 1; // 0! 的逆元 = 1
    }

    // 扩展阶乘和逆元数组
    private void grow(int mx) {
        int n = f.length;
        f = Arrays.copyOf(f, mx + 1); // 扩展阶乘数组
        for (int i = n; i <= mx; i++) {
            f[i] = (int) ((long) f[i - 1] * i % MOD); // 计算 i! = (i-1)! * i
        }
        invF = Arrays.copyOf(invF, mx + 1); // 扩展逆元数组
        invF[mx] = pow(f[mx], MOD - 2); // 计算 mx! 的逆元
        for (int i = mx; i > n; i--) {
            invF[i - 1] = (int) ((long) invF[i] * i % MOD); // 计算 (i-1)! 的逆元
        }
    }

    // 返回 n! 的值
    public int f(int n) {
        if (n >= f.length) {
            grow(n * 2); // 如果 n 超出当前数组大小，扩展数组
        }
        return f[n];
    }

    // 返回 n! 的逆元
    public int invF(int n) {
        if (n >= invF.length) {
            grow(n * 2); // 如果 n 超出当前数组大小，扩展数组
        }
        return invF[n];
    }

    // 计算组合数 C(n, k)
    public int c(int n, int k) {
        if (k < 0 || k > n) {
            return 0; // 不合法的 k，返回 0
        }
        // C(n, k) = n! / (k! * (n-k)!)
        return (int) ((long) f(n) * invF(k) % MOD * invF(n - k) % MOD);
    }

    // 计算排列数 P(n, k)
    public int p(int n, int k) {
        if (k < 0 || k > n) {
            return 0; // 不合法的 k，返回 0
        }
        // P(n, k) = n! / (n-k)!
        return (int) ((long) f(n) * invF(n - k) % MOD);
    }

    // 计算基数的模逆
    private int pow(int base, int exp) {
        int result = 1;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (int) ((long) result * base % MOD); // 乘以当前基数
            }
            base = (int) ((long) base * base % MOD); // 平方基数
            exp >>= 1; // 将指数右移一位
        }
        return result;
    }
}
