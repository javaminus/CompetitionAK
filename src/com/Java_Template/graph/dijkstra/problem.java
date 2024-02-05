package com.Java_Template.graph.dijkstra;

/**
 * 743. 网络延迟时间（https://leetcode.cn/problems/network-delay-time/description/）
 * 1976. 到达目的地的方案数(https://leetcode.cn/problems/number-of-ways-to-arrive-at-destination/description/?envType=daily-question&envId=Invalid%20Date)
 * 2642. 设计可以求最短路径的图类(https://leetcode.cn/problems/design-graph-with-shortest-path-calculator/)
 * 1514. 概率最大的路径(https://leetcode.cn/problems/path-with-maximum-probability/description/)
 */
public interface problem {
    // 743. 网络延迟时间
    public int networkDelayTime(int[][] times, int n, int k);

    // 1976. 到达目的地的方案数
    public int countPaths(int n, int[][] roads);


    // 1514. 概率最大的路径
    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end);

}
