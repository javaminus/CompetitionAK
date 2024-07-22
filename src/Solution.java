import java.util.HashMap;

class Solution {
    public int maximumLength(int[] nums, int k) {
        // dp[i][j]表示以i结尾的剩余j个不同下标可用的最长子序列
        HashMap<Integer, int[]> fs = new HashMap<>();
        int[] mx = new int[k + 1]; // mx[i]表示不同个数为i的子序列最大长度
        for (int x : nums) {
            int[] f = fs.computeIfAbsent(x, e -> new int[k + 1]);
            for (int i = k; i >= 0; i--) {
                f[i]++;
                if (i > 0) {
                    f[i] = Math.max(f[i], mx[i - 1] + 1);
                }
                mx[i] = Math.max(mx[i], f[i]);
            }
        }
        return mx[k];
    }
}