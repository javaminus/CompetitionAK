package com.Java_Template.string.string_hash_template;

import java.util.Random;

class HashString {
    long[] preHash;// Hash前缀和
    long[] powBase;// pow的幂次
    int MOD = (int) 1e9 + 7;
    int BASE = (int) 8e8 + new Random().nextInt((int) 1e8);
    char[] chars;
    int n;

    HashString(String s) {
        chars = s.toCharArray();
        n = chars.length;
        getHash();
    }

    // 当需要多次hash的时候就用这个方法,固定BASE,否则每次BASE不同 会影响结果
    HashString(String s, int MOD, int BASE) {
        chars = s.toCharArray();
        n = chars.length;
        this.MOD = MOD;
        this.BASE = BASE;
        getHash();
    }

    public void getHash() {
        preHash = new long[n + 1];
        powBase = new long[n + 1];
        powBase[0] = 1;
        for (int i = 0; i < n; i++) {
            powBase[i + 1] = powBase[i] * BASE % MOD;
            preHash[i + 1] = (preHash[i] * BASE + chars[i]) % MOD;
        }
    }

    // 闭区间
    public long query(int l, int r) {
        long ans = ((preHash[r + 1] - preHash[l] * powBase[r - l + 1]) % MOD + MOD) % MOD;
        return ans;
    }
}