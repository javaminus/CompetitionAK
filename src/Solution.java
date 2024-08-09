import java.util.Arrays;
import java.util.HashMap;

class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.canDistribute(new int[]{1, 1, 2, 2}, new int[]{2, 2});
    }
    // 未来避免重复的选择，可以利用0-1背包的选择
    public boolean canDistribute(int[] nums, int[] quantity) {
        int m = quantity.length;
        int mask = 1<<m;
        int[] sum = new int[mask];
        for(int s = 1;s < mask;s++){
            for (int i = 0; i < m; i++) {
                if (((s >> i) & 1) == 0) {
                    continue;
                }
                sum[s] += quantity[i];
            }
        }
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int x : nums) {
            cnt.merge(x, 1, Integer::sum);
        }
        int n = cnt.size();
        boolean[][] dp = new boolean[n + 1][mask]; // 表示考虑前i个数构成情况为s的boolean
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }
        int i = 0;
        int[] cc = new int[n];
        for (int x : cnt.values()) {
            cc[i++] = x;
        }
        for (i = 0; i < n; i++) {
            for (int s = 1; s < mask; s++) {
                if (dp[i][s]) {
                    dp[i + 1][s] = true;
                    continue;
                }
                for (int p = s; p != 0; p = (p - 1) & s) {
                    if (sum[p] <= cc[i] && dp[i][s ^ p]) {
                        dp[i + 1][s] = true;
                    }
                }
            }
        }
        return dp[n][mask - 1];
    }
}