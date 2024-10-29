package test;

import java.util.*;

class Test {
    static final int MOD = 998244353;
    static final int M = 1000000;
    static long[] f = new long[M + 1];
    static long[] g = new long[M + 1];
    static int[] cnt = new int[M + 1];


    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int n = sc.nextInt();

        // Precompute factorials and inverse factorials
        f[0] = 1;
        for (int i = 1; i <= M; i++) {
            f[i] = f[i - 1] * i % MOD;
        }

        g[M] = quickPow(f[M], MOD - 2, MOD);
        for (int i = M; i >= 1; i--) {
            g[i - 1] = g[i] * i % MOD;
        }

        List<List<Integer>> pools = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int k = sc.nextInt();
            List<Integer> pool = new ArrayList<>();
            for (int j = 0; j < k; j++) {
                int x = sc.nextInt();
                pool.add(x);
                cnt[x]++;
            }
            pools.add(pool);
        }

        long ans = 0;
        for (List<Integer> pool : pools) {
            int k = pool.size();
            System.out.println(k);
            for (int v : pool) {
                ans = (ans + cnt[v] * inv(k) % MOD) % MOD;
                // System.out.println(inv(k));
            }
        }
        System.out.println(ans * inv(n) % MOD * inv(n) % MOD);
    }

    static long quickPow(long base, long power, long mod) {
        if (power == 0) return 1 % mod;
        long cur = quickPow(base, power / 2, mod);
        return (power % 2 == 1) ? base * cur % mod * cur % mod : cur * cur % mod;
    }

    static long inv(int x) {
        return f[x - 1] * g[x] % MOD;
    }
}