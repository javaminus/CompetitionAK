import java.util.Arrays;

class Solution {
    private static int MX = 41;
    private static int Mod = (int) 1e9 + 7;
    private static long[] F, INV_F;
    static {
        F = new long[MX];
        INV_F = new long[MX];
        F[0] = 1;
        for (int i = 1; i < MX; i++) {
            F[i] = F[i - 1] * i % Mod;
        }
        INV_F[MX - 1] = qpow(F[MX - 1], Mod - 2);
        for (int i = MX - 1; i > 0; i--) {
            INV_F[i - 1] = INV_F[i] * i % Mod;
        }
    }

    private static long qpow(long a, long b) {
        long res = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                res = res * a % Mod;
            }
            a = a * a % Mod;
            b >>= 1;
        }
        return res;
    }

    long[][][] memo;
    int[] cnt;
    public int countBalancedPermutations(String num){
        int n = num.length();
        cnt = new int[10];
        int sum = 0;
        for (char c : num.toCharArray()) {
            int x = c - '0';
            cnt[x]++;
            sum += x;
        }
        if ((sum & 1) == 1) {
            return 0;
        }
        for (int i = 1; i < 10; i++) {
            cnt[i] += cnt[i - 1];
        }
        int n1 = n / 2;
        memo = new long[10][n1 + 1][sum / 2 + 1];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < n1 + 1; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }
        return (int) (F[n1] * F[n - n1] % Mod * dfs(9, n1, sum / 2) % Mod);
    }

    private long dfs(int i, int left1, int leftSum) {
        if (i < 0) {
            return leftSum == 0 ? 1 : 0;
        }
        if (memo[i][left1][leftSum] != -1) {
            return memo[i][left1][leftSum];
        }
        long res = 0;
        int c = cnt[i] - (i > 0 ? cnt[i - 1] : 0); // 剩余数字可用的总数
        int left2 = cnt[i] - left1; // 第二个集合的数字数目
        for (int k = Math.max(0, c - left2); k <= Math.min(c, left1) && k * i <= leftSum; k++) {
            long r = dfs(i - 1, left1 - k, leftSum - k * i);
            res = (res + r * INV_F[k] % Mod * INV_F[c - k] % Mod);
        }
        return memo[i][left1][leftSum] = res;
    }

}