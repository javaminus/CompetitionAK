package com.Java_Template.graph.dijkstra;

import java.util.*;

/**
 * Dijkstra
 */
public class problemImpl implements problem {
    // 朴素Dijkstra版本 不推荐，推荐下面的堆优化版本
    @Override
    public int networkDelayTime(int[][] times, int n, int k) { // Dijkstra算法求距离点k的最大距离
        final int INF = Integer.MAX_VALUE / 2;
        int[][] g = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(g[i], INF);
        }
        for (int[] time : times) {
            int x = time[0] - 1, y = time[1] - 1;
            g[x][y] = time[2];
        }
        boolean[] visited = new boolean[n];
        int[] distance = new int[n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(distance, INF);
        }
        distance[k - 1] = 0;
        for (int i = 0; i < n; i++) {
            int x = -1;
            // 在所有的没有访问的边里面，找距离最短的边。
            for (int y = 0; y < n; y++) {
                if (!visited[y] && (x == -1 || distance[y] < distance[x])) {
                    x = y;
                }
            }
            visited[x] = true; // 最短的点x标记被访问
            for (int j = 0; j < n; j++) {
                // 更新其他节点加入节点x的距离
                distance[j] = Math.min(distance[j], distance[x] + g[x][j]);
            }
        }
        int ans = Arrays.stream(distance).max().getAsInt();
        return ans == INF ? -1 : ans;
    }
/*  堆优化版本
    public int networkDelayTime(int[][] times, int n, int k) { // 使用一个小根堆来寻找「未确定节点」中与起点距离最近的点
        // 定义一个常量 INF 表示无穷大，为防止溢出，取 Integer.MAX_VALUE 的一半
        final int INF = Integer.MAX_VALUE / 2;
        // 创建一个邻接表，用于表示图的结构
        List<int[]>[] g = new List[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<int[]>();
        }
        // 遍历输入的 times 数组，构建邻接表
        for (int[] time : times) {
            int x = time[0] - 1, y = time[1] - 1, z = time[2];
            g[x].add(new int[]{y, z});
        }
        // 初始化距离数组，用于记录起点到各点的最短距离，默认为无穷大
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[k - 1] = 0;
        // 使用优先队列（最小堆）存储节点和到达该节点的时间
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{k - 1, 0}); // 将起点加入优先队列

        // 进行 Dijkstra 算法的主循环
        while (!pq.isEmpty()) {
            int[] poll = pq.poll();
            int x = poll[0], time = poll[1];
            if (dist[x] < time) {
                continue;  // 如果当前节点的最短距离已经更新，则跳过
            }
            for (int[] e : g[x]) {
                int y = e[0], d = e[1];
                if (dist[y] > dist[x] + d) {
                    dist[y] = dist[x] + d; // 更新到达节点 y 的最短距离
                    pq.offer(new int[]{y, dist[y]}); // 将节点 y 加入优先队列
                }
            }
        }
        // 找到最终的最大距离，即为答案
        int ans = Arrays.stream(dist).max().getAsInt();
        return ans == INF ? -1 : ans; // 如果存在无法到达的节点，则返回 -1
    }*/


    // 1976. 到达目的地的方案数
    public int countPaths(int n, int[][] roads) {
        int Mod = (int) 1e9 + 7;
        long INF = Long.MAX_VALUE / 2;
        List<int[]>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<int[]>());
        for (int[] road : roads) {
            int x = road[0], y = road[1], z = road[2];
            g[x].add(new int[]{y, z});
            g[y].add(new int[]{x, z});
        }
        long[] dist = new long[n];
        Arrays.fill(dist, INF);
        int[] dp = new int[n];
        dp[0] = 1;
        dist[0] = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
        pq.offer(new long[]{0, 0});
        while (!pq.isEmpty()) {
            long[] poll = pq.poll();
            int x = (int) poll[0];
            long road = poll[1];
            if (dist[x] < road) {
                continue;
            }
            for (int[] e : g[x]) {
                int y = e[0];
                long z = e[1];
                if (dist[y] > dist[x] + z) {
                    dist[y] = dist[x] + z;
                    dp[y] = dp[x];
                    pq.offer(new long[]{y, dist[y]});
                } else if (dist[y] == dist[x] + z) {
                    dp[y] = (dp[y] + dp[x]) % Mod;
                }
            }
        }
        return dp[n - 1];
    }


    // 1514. 概率最大的路径
    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {

        // 构造图
        List<double[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<double[]>();
        }
        for (int i = 0; i < edges.length; i++) {
            int idstart = edges[i][0];
            int idend = edges[i][1];
            double weight = succProb[i];

            graph[idstart].add(new double[]{idend, weight});
            graph[idend].add(new double[]{idstart, weight});
        }

        // 自定义排序，优先更新概率大的节点
        Queue<State> pq = new PriorityQueue<State>((a, b) -> {  // NOTE 学会！！！
            if (a.prop > b.prop) {
                return -1;
            } else if (a.prop < b.prop) {
                return 1;
            } else {
                return 0;
            }
        });
        pq.offer(new State(start, 1));

        double[] res = new double[n];
        res[start] = 1;
        while (!pq.isEmpty()) {
            State curNode = pq.poll();
            // 找到了，因为这个节点已经是队列里概率最大的节点了，再通过其他节点去更新这个节点，只会越更新概率越小，所以不用找了，这个就是我们要的答案。
            if (curNode.id == end) { // 减枝
                return curNode.prop;
            }

            // 更新邻居的概率
            for (double[] neighbor : graph[curNode.id]) {
                int nextID = (int) neighbor[0];
                double nextProp = curNode.prop * neighbor[1];
                if (nextProp > res[nextID]) {
                    res[nextID] = nextProp;
                    pq.offer(new State(nextID, nextProp));
                }

            }
        }

        return res[end];
    }

    /*  3112. 访问消失节点的最少时间
        给你一个二维数组 edges 表示一个 n 个点的无向图，其中 edges[i] = [ui, vi, lengthi] 表示节点 ui 和节点 vi 之间有一条需要 lengthi 单位时间通过的无向边。

        同时给你一个数组 disappear ，其中 disappear[i] 表示节点 i 从图中消失的时间点，在那一刻及以后，你无法再访问这个节点。

        注意，图有可能一开始是不连通的，两个节点之间也可能有多条边。

        请你返回数组 answer ，answer[i] 表示从节点 0 到节点 i 需要的 最少 单位时间。如果从节点 0 出发 无法 到达节点 i ，那么 answer[i] 为 -1 。*/
    public int[] minimumTime(int n, int[][] edges, int[] disappear) { // dijkstra堆优化模板
        final int INF = Integer.MAX_VALUE / 2;
        List<int[]>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<int[]>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1], z = edge[2];
            g[x].add(new int[]{y, z});
            g[y].add(new int[]{x, z});
        }
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[0] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{0, 0});
        while (!pq.isEmpty()) {
            int[] poll = pq.poll();
            int x = poll[0], len = poll[1];
            if (dist[x] < len) {
                continue;
            }
            for (int[] e : g[x]) {
                int y = e[0], d = e[1];
                if (dist[y] > dist[x] + d && dist[x] + d < disappear[y]) {
                    dist[y] = dist[x] + d;
                    pq.offer(new int[]{y, dist[y]});
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (dist[i] >= INF) {
                dist[i] = -1;
            }
        }
        return dist;
    }
}

class State{
    int id;
    double prop;

    State(int id, double prop) {
        this.id = id;
        this.prop = prop;
    }
}
