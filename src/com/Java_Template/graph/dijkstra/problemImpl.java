package com.Java_Template.graph.dijkstra;

import java.util.Arrays;

/**
 * @author Minus
 * @date 2024/1/29 12:07
 */
public class problemImpl implements problem {

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
/* public int networkDelayTime(int[][] times, int n, int k) { // 使用一个小根堆来寻找「未确定节点」中与起点距离最近的点
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






}
