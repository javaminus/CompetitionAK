import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    List<Integer>[] g;
    int[][] nodes;
    int[] ans;
    public int[] timeTaken(int[][] edges) {
        int n = edges.length + 1;
        g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[x].add(y);
            g[y].add(x);
        }
        nodes = new int[n][3];
        dfs(0, -1);
        reRoot(0, -1, 0);
        return ans;
    }

    private int dfs(int x, int fa) {
        int maxD = 0;
        int maxD2 = 0;
        int my = 0;
        for (int y : g[x]) {
            if (y == fa) {
                continue;
            }
            int dis = dfs(y, x) + 2 - y % 2;
            if (dis > maxD) {
                maxD2 = maxD;
                maxD = dis;
                my = y;
            } else if (dis > maxD2) {
                maxD2 = dis;
            }
        }
        nodes[x][0] = maxD;
        nodes[x][1] = maxD2;
        nodes[x][2] = my;
        return maxD;
    }

    private void reRoot(int x, int fa, int fromUp) {
        int maxD = nodes[x][0];
        int maxD2 = nodes[x][1];
        int my = nodes[x][2];
        ans[x] = Math.max(maxD, fromUp);
        for (int y : g[x]) {
            if (y != fa) {
                reRoot(y, x, Math.max(fromUp, (y == my ? maxD2 : maxD)) + 2 - x % 2);
            }
        }
    }



}