package com.Java_Template.util.General;

/**
 * 埃氏筛法 求n以内的所有素数
 */
public class generate_prime_number {
    private final static int mx = (int) 1e5;
    // note pn[i]==false为素数，pn[i]==true不是素数
    private final static boolean[] pn = new boolean[mx + 1];
    static {
        pn[1] = true;
        for (int i = 2; i * i <= mx; i++) {
            if (!pn[i]) {
                for (int j = i * i; j <= mx; j += i) {
                    pn[j] = true;
                }
            }
        }
    }
}
