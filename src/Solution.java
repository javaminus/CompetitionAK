class Solution { // 方法一：转换成 0-1 背包方案数
    private static int Mod = (int) 1e9 + 7;
    private static int[] prime = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
    private static int MX = 31;
    private static int[] g = new int[MX];
    static {
        for (int i = 2; i < MX; i++) {
            for (int j = 0; j < 10; j++) {
                int p = prime[j];
                if (i % p == 0) {
                    if (i % (p * p) == 0) { // 有平方因子
                        g[i] = -1;
                        break;
                    }
                    g[i] |= (1 << j); // 把j加入集合
                }
            }
        }
    }
    public int squareFreeSubsets(int[] nums) {
        int m = 1 << 10;
        int[] cnt = new int[MX + 1];
        int pow2 = 1;
        for (int x : nums)
            if (x == 1) pow2 = pow2 * 2 % Mod;
            else ++cnt[x];

        long[] f = new long[m]; // f[j] 表示恰好组成质数集合 j 的方案数
        f[0] = pow2; // 用 1 组成空质数集合的方案数
        for (int x = 2; x <= MX; ++x) {
            int mask = g[x], c = cnt[x];
            if (mask > 0 && c > 0) {
                int other = (m - 1) ^ mask, j = other; // mask 的补集 other
                do { // 枚举 other 的子集 j
                    f[j | mask] = (f[j | mask] + f[j] * cnt[x]) % Mod; // 不选 mask + 选 mask
                    j = (j - 1) & other;
                } while (j != other);
            }
        }
        long ans = -1L; // 去掉空集（nums 的空子集）
        for (long v : f) ans += v;
        return (int) (ans % Mod);
    }


}