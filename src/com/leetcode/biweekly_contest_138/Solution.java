package com.leetcode.biweekly_contest_138;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 组合数学，巧用回文串
 */
public class Solution {
    public long countGoodIntegers(int n, int k) {
        long[] fac = new long[n + 1]; // 计算i的阶乘
        fac[0] = 1;
        for (int i = 1; i <= n; i++) {
            fac[i] = fac[i - 1] * i;
        }
        long ans = 0;
        int m = (n + 1) / 2;
        HashSet<String> set = new HashSet<>();
        int base = (int) Math.pow(10, (n - 1) / 2); // 5->100
        for (int i = base; i < base * 10; i++) { // 枚举左边的位
            String s = Integer.toString(i);
            StringBuilder rev = new StringBuilder(s).reverse();
            s += rev.substring(n % 2);
            if (Long.parseLong(s) % k != 0) {
                continue;
            }
            char[] cs = s.toCharArray();
            Arrays.sort(cs);
            if (!set.add(new String(cs))) {
                continue;
            }
            int[] cnt = new int[10];
            for (char c : cs) {
                cnt[c - '0']++;
            }
            long res = (n - cnt[0]) * (fac[n - 1]);
            for (int c : cnt) {
                if (c > 1) {
                    res /= fac[c];
                }
            }
            ans += res;
        }
        return ans;
    }
}
