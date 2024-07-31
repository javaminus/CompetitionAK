import java.util.Arrays;
import java.util.HashSet;

class Solution {
    public int minimumIncompatibility(int[] nums, int k) {
        int n = nums.length;
        int mask = 1 << n;
        int m = n / k;
        int[] g = new int[mask]; // 预处理所有组合的不兼容值
        Arrays.fill(g, -1);
        for (int s = 1; s < mask; s++) {
            if (Integer.bitCount(s) != m) {
                continue;
            }
            HashSet<Integer> set = new HashSet<>();
            int mn = Integer.MAX_VALUE, mx = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                if (((s >> i) & 1) == 1) {
                    if (!set.add(nums[i])) {
                        break;
                    }
                    mn = Math.min(mn, nums[i]);
                    mx = Math.max(mx, nums[i]);
                }
            }
            if (set.size() == m) {
                g[s] = mx - mn;
            }
        }

        int[] dp = new int[mask]; // 表示组合为s时的最小不兼容性
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;
        for (int s = 1; s < mask; s++) {
            HashSet<Integer> set = new HashSet<>();
            int pre = 0;
            for (int i = 0; i < n; i++) {
                if (((s >> i) & 1) == 0 && !set.contains(nums[i])) {
                    set.add(nums[i]);
                    pre |= (1 << i);
                }
            }
            if (set.size() < m) {
                continue;
            }
            // set.size() >= m，枚举set中长度为 m 的子集
            for (int i = pre; i > 0; i = (i - 1) & pre) {
                if (g[i] != -1) { // 保证i中有m个1组成
                    dp[s | i] = Math.min(dp[s | i], dp[s] + g[i]);
                }
            }
        }
        return dp[mask - 1] == Integer.MAX_VALUE / 2 ? -1 : dp[mask - 1];
    }
}