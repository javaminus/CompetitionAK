import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public int[] countPairsOfConnectableServers(int[][] edges, int signalSpeed) {
        int n = edges.length + 1;
        List<int[]>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1], z = edge[2];
            g[x].add(new int[]{y, z});
            g[y].add(new int[]{x, z});
        }
        int[] ans = new int[n]; // 这个图没有环，所以不用visited数组，这其实就是一棵树
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int[] e : g[i]) {
                int cnt = dfs(e[0], i, g, e[1], signalSpeed);
                ans[i] += cnt * sum;
                sum += cnt;
            }
        }
        return ans;
    }

    private int dfs(int x, int fa, List<int[]>[] g, int sum, int signalSpeed) {
        int res = sum % signalSpeed == 0 ? 1 : 0;
        for (int[] y : g[x]) {
            if (y[0] != fa) {
                res += dfs(y[0], x, g, sum + y[1], signalSpeed);
            }
        }
        return res;
    }

}