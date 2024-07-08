import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public long maximumValueSum(int[] nums, int k, int[][] edges) {
        int n = nums.length;
        List<Integer>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }
        return dfs(0, -1, g, nums, k)[0];
    }

    private long[] dfs(int x, int fa, List<Integer>[] g, int[] nums, int k) {
        long f0 = 0, f1 = Long.MIN_VALUE;
        for (int y : g[x]) {
            if (y != fa) {
                long[] r = dfs(y, x, g, nums, k);
                long t = Math.max(f1 + r[0], f0 + r[1]);
                f0 = Math.max(f0 + r[0], f1 + r[1]);
                f1 = t;
            }
        }
        return new long[]{Math.max(f0 + nums[x], f1 + (nums[x] ^ k)),
                Math.max(f1 + nums[x], f0 + (nums[x] ^ k))};
    }
}