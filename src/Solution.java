import java.util.Arrays;

class TreeAncestor {
    private static int MX = 50001;
    private static int power;
    private static int cnt;
    private static int[] head = new int[MX];
    private static int[] next = new int[MX];
    private static int[] to = new int[MX];

    private static int[] deep = new int[MX];
    private static int[][] stjump = new int[MX][32];

    public TreeAncestor(int n, int[] parent) {
        power = 32 - Integer.numberOfLeadingZeros(n);
        cnt = 1;
        Arrays.fill(head, 0);
        for (int i = 1; i < parent.length; i++) {
            addEdge(parent[i], i);
        }
        dfs(0, 0);
    }

    private static void addEdge(int u, int v) {
        next[cnt] = head[u];
        to[cnt] = v;
        head[u] = cnt++;
    }

    private static void dfs(int x, int fa) {
        if (x == 0) {
            deep[x] = 1;
        }else{
            deep[x] = deep[fa] + 1;
        }
        stjump[x][0] = fa;
        for (int i = 1; i <= power; i++) {
            stjump[x][i] = stjump[stjump[x][i - 1]][i - 1];
        }
        for (int e = head[x]; e != 0; e = next[e]) {
            dfs(to[e], x);
        }
    }

    public int getKthAncestor(int node, int k) {
        if (deep[node] <= k) {
            return -1;
        }
        int s = deep[node] - k;
        for (int p = 32 - Integer.numberOfLeadingZeros(k); p >= 0; p--) {
            if (deep[stjump[node][p]] >= s) {
                node = stjump[node][p];
            }
        }
        return node;
    }
}

/**
 * Your TreeAncestor object will be instantiated and called as such:
 * TreeAncestor obj = new TreeAncestor(n, parent);
 * int param_1 = obj.getKthAncestor(node,k);
 */