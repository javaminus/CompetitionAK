import java.util.Arrays;

class Solution {
    public int[] concatenatedDivisibility(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        int[] pow10 = new int[n];
        for (int i = 0; i < n; i++) {
            pow10[i] = (int) Math.pow(10, Integer.toString(nums[i]).length());
        }
        int[] ans = new int[n];
        boolean[][] vis = new boolean[1 << n][k];
        if (!dfs((1 << n) - 1, 0, nums, pow10, k, vis, ans)) {
            return new int[0];
        }
        return ans;
    }

    boolean dfs(int s, int x, int[] nums, int[] pow10, int k, boolean[][] vis, int[] ans){
        if (s == 0) {
            return x == 0;
        }
        if (vis[s][x]) {
            return false;
        }
        vis[s][x] = true;
        for (int i = 0; i < nums.length; i++) {
            if (((s >> i) & 1) == 1 && dfs(s ^ (1 << i), (x * pow10[i] + nums[i]) % k, nums, pow10, k, vis, ans)) {
                ans[nums.length - Integer.bitCount(s)] = nums[i];
                return true;
            }
        }
        return false;
    }
}