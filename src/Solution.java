class Solution {

    private static final int Mod = 10;
    private static final int MX = (int) 1e5;
    private static final int[] f = new int[MX + 1]; // i的阶乘
    private static final int[] invF = new int[MX + 1];  // i的阶乘的逆元
    private static final int[] p2 = new int[MX + 1]; // i中2因子的个数
    private static final int[] p5 = new int[MX + 1]; // i中5因子的个数
    
    static {
        f[0] = 1;
        for (int i = 1; i <= MX; i++) {
            int x = i;
            int e2 = Integer.numberOfTrailingZeros(x); // 计算x末尾0的个数
            x >>= e2;
            int e5 = 0;
            while (x % 5 == 0) {
                e5++;
                x /= 5;
            }
            f[i] = f[i - 1] * x % Mod;
            p2[i] = p2[i - 1] + e2;
            p5[i] = p5[i - 1] + e5;
        }
        invF[MX] = qpow(f[MX], 3);
        for (int i = MX; i > 0; i--) {
            int x = i;
            x >>= Integer.numberOfTrailingZeros(x);
            while (x % 5 == 0) {
                x /= 5;
            }
            invF[i - 1] = invF[i] * x % Mod;
        }
    }

    private static int qpow(int a, int b) {
        int ans = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                ans = (ans * a) % Mod;
            }
            b >>= 1;
            a = (a * a) % Mod;
        }
        return ans;
    }

    private int comb(int n, int k) {
        // 由于每项都 < 10，所以无需中途取模
        return f[n] * invF[k] * invF[n - k] * qpow(2, p2[n] - p2[k] - p2[n - k]) * qpow(5, p5[n] - p5[k] - p5[n - k]) % Mod;
    }
    
    public boolean hasSameDigits(String s) {
        return solve(s.substring(0, s.length() - 1)) == solve(s.substring(1));
    }

    private int solve(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            res += comb(s.length() - 1, i) * (s.charAt(i) - '0');
        }
        return res % Mod;
    }
}