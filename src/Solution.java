import java.util.HashMap;

class Solution {
    private static int INF = Integer.MAX_VALUE / 2;
    int n, m;
    int[] nums, andValues;
    HashMap<String, Integer> memo = new HashMap<>();
    public int minimumValueSum(int[] nums, int[] andValues) {
        this.nums = nums;
        this.andValues = andValues;
        n = nums.length;
        m = andValues.length;
        int res = dfs(0, 0, (1 << 18) - 1, 0);
        return res == INF ? -1 : res;
    }

    private int dfs(int i, int j, int sum, int last) { // 表示到达位置i,前
        if (n - 1 - i < m - 1 - j) {
            return INF;
        }
        if (i == n || j == m) {
            return i == n && j == m ? 0 : INF;
        }
        if (sum < andValues[j]) {
            return INF;
        }
        String key = getKey(i, j, sum);
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        int res = INF;
        if ((sum & nums[i]) == andValues[j]) {
            res = Math.min(res, dfs(i + 1, j + 1, (1 << 18) - 1, nums[i] + last)) + nums[i];
        }
        res = Math.min(res, dfs(i + 1, j, sum & nums[i], last));
        memo.put(key, res);
        return res;
    }

    private String getKey(int i, int j, int sum) {
        return i + "_" + j + "_" + sum;
    }

}