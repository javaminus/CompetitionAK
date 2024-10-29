import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int i = 1; i < n; i++) {
            g[parent[i]].add(i);
        }
        int[] ancestor = new int[26];
        Arrays.fill(ancestor, -1);
        dfs(0, g, s.toCharArray(), ancestor);
        int[] nodes = new int[n];
        dfs1(0, g, nodes);
        return nodes;
    }

    private int dfs1(int x, List<Integer>[] g, int[] nodes) {
        nodes[x] = 1;
        for (int y : g[x]) {
            if (y != -1) {
                nodes[x] += dfs1(y, g, nodes);
            }
        }
        return nodes[x];
    }
    private void dfs(int x, List<Integer>[] g, char[] s, int[] ancestor) {
        int sx = s[x] - 'a';
        int old = ancestor[sx];
        ancestor[sx] = x;
        for (int i = g[x].size() - 1; i >= 0; i--) {
            int y = g[x].get(i);
            int anc = ancestor[s[y] - 'a'];
            if (anc != -1) {
                g[anc].add(y);
                g[x].set(i, -1); // -1 表示删除 y
            }
            dfs(y, g, s, ancestor);
        }
        ancestor[sx] = old;
    }


}