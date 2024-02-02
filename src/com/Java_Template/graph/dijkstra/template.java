package com.Java_Template.graph.dijkstra;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author Minus
 * @date 2024/1/29 12:07
 */
public class template {
}

class Solution {
    // 图结点
    private int N = 110;
    private int M = 6010;

    // 邻接表
    // 存储与 结点v 直接相连的边编号集合(链表)的头结点(边编号)
    private int[] head = new int[N];

    // 存储某一条边指向的结点, 用于访问编号为idx的边所指向的结点
    private int[] edge = new int[M];

    // 由于以链表的形式存储边, 故可根据与 结点v 直接相连的边集合(链表)的头结点在 nextEdge数组 中找到下一条边
    // 存储 链表中边编号为idx的边指向/连接的下一条边
    // this.nextEdge[idx]: 保存边编号为idx边连接的下一条边
    private int[] nextEdge = new int[M];

    // 记录某条边的权重
    private int[] weight = new int[M];

    // 无穷大上界
    private int INF = 0x3f3f3f3f;

    // 结点数
    private int n;

    // 源点
    private int src;

    // 用于对边进行编号: 会自动初始化 赋0
    private int idx = 0;

    /**
     * 邻接表 加边操作
     *
     * @param srcV:   源点
     * @param desV:   目标点
     * @param weight: 边权重
     */
    private void add(int srcV, int desV, int weight) {
        // 由源点(srcV)出发 经 编号为idx的边 指向 目标点(desV)
        // 新建一条编号为idx, 指向desV结点的边
        this.edge[idx] = desV;

        // 头插法
        this.nextEdge[idx] = this.head[srcV];
        this.head[srcV] = idx;

        // 将编号为idx的边的权值 赋为 weight
        this.weight[idx] = weight;
        idx++;
    }


    /**
     * 遍历 结点src 与其相连的所有边
     *
     * @param src
     */
    private void traversal(int src) {
        for (int idx = head[src]; idx != -1; idx = this.nextEdge[idx]) {
            int des = this.edge[idx];
            System.out.println("结点" + src + " -> 结点" + des);
            System.out.println("编号为" + idx + "的边, 权重为: " + weight[idx]);
        }
    }


    public int networkDelayTime(int[][] times, int n, int k) {
        this.n = n;
        this.src = k;

        Arrays.fill(head, -1);

        for (int[] time : times) {
            int src = time[0];
            int des = time[1];
            int weight = time[2];
            this.add(src, des, weight);
        }

        // 最短路径
        this.dijkstraOptimizedByHeap();

        int ans = 0;
        for (int i = 1; i <= n; i++) {
            ans = Math.max(ans, dist[i]);
        }

        return ans == INF ? -1 : ans;
    }


    // dist[v] = m 表示从[源点/起点] 到 结点v 的最短距离为 m
    private int[] dist = new int[N];

    // 记录结点的访问状态
    private boolean[] visited = new boolean[N];

    /**
     * Dijkstra算法: 单源最短路径算法
     * <p>
     * 堆优化邻接表实现
     */
    private void dijkstraOptimizedByHeap() {
        Arrays.fill(dist, INF);
        Arrays.fill(visited, false);

        dist[src] = 0;

        // 升序排列, 最小堆
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[1] - b[1]);

        // new int[]{结点v, 与源点src的最短距离}
        queue.add(new int[]{this.src, 0});

        while (!queue.isEmpty()) {
            // 每次抛出与源点(src)最短距离最小的结点
            int[] poll = queue.poll();

            int v = poll[0];
            int distance = poll[1];

            // 已访问, 则跳过
            if (visited[v]) {
                continue;
            }

            // 将 结点v 置为已访问状态
            visited[v] = true;

            for (int idx = head[v]; idx != -1; idx = nextEdge[idx]) {
                int des = edge[idx];

                // 若源点(src) -> 结点v 的最短距离 + 结点v -> 结点des 的距离 < 源点(src) -> 结点des的最短距离
                if (dist[v] + weight[idx] < dist[des]) {
                    // 更新 结点des 的最短距离
                    dist[des] = dist[v] + weight[idx];
                    queue.add(new int[]{des, dist[des]});
                }
            }
        }
    }
}