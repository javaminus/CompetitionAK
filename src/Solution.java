import java.util.Arrays;

class Solution {
    int n;
    int[][] memo;
    int[] cost, time;
    public int paintWalls(int[] cost, int[] time) {
        n = cost.length;
        this.cost = cost;
        this.time = time;
        memo = new int[n][n * 2 + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, 0);
    }

    private int dfs(int i, int j) { // 表示刷到第i堵墙，付费时间 - 免费时间 = j
        if (j > n - i) { // 剩余的墙都可以免费刷
            return 0;
        }
        if (i >= n) {
            return Integer.MAX_VALUE / 2;
        }
        int k = j + n; // 加上偏移量，防止出现负数，只有在memo里面才需要使用k
        if (memo[i][k] != -1) {
            return memo[i][k];
        }
        int res = dfs(i + 1, j + time[i]) + cost[i];
        res = Math.min(res, dfs(i + 1, j - 1)); // 主要是j的值可以暂时为负数！！！
        return memo[i][k] = res;
    }
}