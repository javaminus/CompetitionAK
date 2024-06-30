import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public int minimumDiameterAfterMerge(int[][] edges1, int[][] edges2) {
        int d1 = diameter(edges1);
        int d2 = diameter(edges2);
        return Math.max(Math.max(d1, d2), (d1 + 1) / 2 + (d2 + 1) / 2 + 1);
    }

    int res;
    private int diameter(int[][] edges) { // 求一棵树的直径
        int n = edges.length + 1;
        List<Integer>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[x].add(y);
            g[y].add(x);
        }
        res = 0;
        dfs(g, 0, -1);
        return res;
    }

    private int dfs(List<Integer>[] g, int x, int fa) {
        int maxLen = 0; // 从点x出发的最长子链
        for (int y : g[x]) {
            if (y != fa) {
                int subLen = dfs(g, y, x) + 1;
                res = Math.max(res, subLen + maxLen);
                maxLen = Math.max(maxLen, subLen);
            }
        }
        return maxLen;
    }
}