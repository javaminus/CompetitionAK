import java.util.*;
import java.io.*;

public class Main {
    static final int N = 500005;
    static int n, m, R, dn, depth[], dp[][];
    static List<Integer> g[];

    static int get(int x, int y) {
        return depth[x] < depth[y] ? x : y;
    }

    static void dfs(int x, int fa) {
        depth[x] = ++dn;
        dp[0][depth[x]] = fa;
        for (int y : g[x]) if (y != fa) dfs(y, x);
    }

    static int lca(int u, int v) {
        if (u == v) return u;
        if ((u = depth[u]) > (v = depth[v])) {
            int temp = u;
            u = v;
            v = temp;
        }
        int d = (int) (Math.log(v - u++) / Math.log(2));
        return get(dp[d][u], dp[d][v - (1 << d) + 1]);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        g = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) g[i] = new ArrayList<>();
        for (int i = 2, u, v; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            u = Integer.parseInt(st.nextToken());
            v = Integer.parseInt(st.nextToken());
            g[u].add(v);
            g[v].add(u);
        }
        depth = new int[n + 1];
        dp = new int[19][n + 1];
        dfs(R, 0);
        for (int i = 1; i <= 18; i++)
            for (int j = 1; j + (1 << i) - 1 <= n; j++)
                dp[i][j] = get(dp[i - 1][j], dp[i - 1][j + (1 << i - 1)]);
        for (int i = 1, u, v; i <= m; i++) {
            st = new StringTokenizer(br.readLine());
            u = Integer.parseInt(st.nextToken());
            v = Integer.parseInt(st.nextToken());
            pw.println(lca(u, v));
        }
        pw.close();
    }
}
