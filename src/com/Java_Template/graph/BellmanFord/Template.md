```java
import java.util.*;

class BellmanFordAdjList {
    static final int INF = Integer.MAX_VALUE;

    public static void bellmanFord(List<int[]>[] g, int n, int source) {
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[source] = 0;

        // Bellman-Ford 核心
        for (int i = 0; i < n; i++) {
            for (int u = 0; u < n; u++) {
                if (dist[u] == INF) continue;
                for (int[] edge : g[u]) {
                    int v = edge[0], w = edge[1];
                    if (dist[u] + w < dist[v]) {
                        dist[v] = dist[u] + w;
                    }
                }
            }
        }
        
        // 检测负权环 这部分可以不要
        boolean hasNegativeCycle = false;
        for (int u = 0; u < n; u++) {
            if (dist[u] == INF) continue;
            for (int[] edge : g[u]) {
                int v = edge[0], w = edge[1];
                if (dist[u] + w < dist[v]) {
                    hasNegativeCycle = true;
                    break;
                }
            }
            if (hasNegativeCycle) break;
        }

        // 输出结果
        if (hasNegativeCycle) {
            System.out.println("图中存在负权环！");
        } else {
            System.out.println("从源点 " + source + " 到各点的最短路径：");
            for (int i = 0; i < n; i++) {
                System.out.println("到点 " + i + " 的最短距离是：" +
                        (dist[i] == INF ? "∞" : dist[i]));
            }
        }
    }
}

```

