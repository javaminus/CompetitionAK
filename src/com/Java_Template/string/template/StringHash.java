package com.Java_Template.string.template;

import java.util.Random;


// 用法： StringHash sh = new StringHash(sb.toString());
//       if (sh.eq(n - 1, n + m - 2, i, i + m - 1)) {
//                cnt++;
//          }

class StringHash {
    String s;
    long[] p = {131, 13331};
    long[][] pow;
    long[] mod = {(long) (1e9 + 7), 998244353};
    int m = p.length;
    int n;
    long[][] hash;

    public StringHash(String s) {
        this(s, (long) (1e9 + 7) + new Random().nextInt(100000));
    }

    public StringHash(String s, long mod0) {
        mod[0] = mod0;
        this.s = s;
        char[] chars = s.toCharArray();
        n = chars.length;
        pow = new long[m][n + 1];
        for (int i = 0; i < m; i++) {
            pow[i][0] = 1;
            for (int j = 1; j <= n; j++) {
                pow[i][j] = pow[i][j - 1] * p[i] % mod[i];
            }
        }
        // 前j个字符的hash
        hash = new long[m][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 1; j <= n; j++) {
                hash[i][j] = (hash[i][j - 1] * p[i] + chars[j - 1]) % mod[i];
            }
        }
    }

    /**
     * 下标从0开始，判断[l1, r1]和[l2, r2]是否相等
     */
    public boolean eq(int l1, int r1, int l2, int r2) {
        if (l1 < 0 || r1 >= n || l2 < 0 || r2 >= n || l1 > r1 || l2 > r2 || (r1 - l1 != r2 - l2)) {
            return false;
        }
        long[] h1 = hash(l1, r1);
        long[] h2 = hash(l2, r2);
        return eq(h1, h2);
    }

    public long[] hash(int l, int r) {
        long[] h = new long[m];
        for (int i = 0; i < m; i++) {
            h[i] = hash[i][r + 1] - hash[i][l] * pow[i][r - l + 1] % mod[i];
            h[i] %= mod[i];
            h[i] += mod[i];
            h[i] %= mod[i];
        }
        return h;
    }

    public static boolean eq(long[] h1, long[] h2) {
        if (h1.length != h2.length) {
            return false;
        }
        for (int i = 0; i < h1.length; i++) {
            if (h1[i] != h2[i]) {
                return false;
            }
        }
        return true;
    }
}