package com.Java_Template.graph.basic;

import java.util.List;

/**
 * 997. 找到小镇的法官(https://leetcode.cn/problems/find-the-town-judge/description/)
 * 1791. 找出星型图的中心节点(https://leetcode.cn/problems/find-center-of-star-graph/)
 * 1971. 寻找图中是否存在路径(https://leetcode.cn/problems/find-if-path-exists-in-graph/description/) 经典图查找
 * LCP 07. 传递信息(https://leetcode.cn/problems/chuan-di-xin-xi/description/)
 * 133. 克隆图（https://leetcode.cn/problems/clone-graph/description/） 拷贝一个图，所有节点都要自己重新new出来
 * 207. 课程表(https://leetcode.cn/problems/course-schedule/description/) 判断一个图没有环
 * 210. 课程表 II(https://leetcode.cn/problems/course-schedule-ii/description/)
 * 310. 最小高度树(https://leetcode.cn/problems/minimum-height-trees/description/) 将图转换为树，返回树的最小高度的根节点集合 NOTE 树形DP模板题
 * 547. 省份数量(https://leetcode.cn/problems/number-of-provinces/description/) NOTE 求图有多少个非连通子图
 * 785. 判断二分图(https://leetcode.cn/problems/is-graph-bipartite/description/)
 * 797. 所有可能的路径（https://leetcode.cn/problems/all-paths-from-source-to-target/description/）
 * 841. 钥匙和房间(https://leetcode.cn/problems/keys-and-rooms/description/)
 * 2242. 节点序列的最大得分(https://leetcode.cn/problems/maximum-score-of-a-node-sequence/description/)
 * 3067. 在带权树网络中统计可连接服务器对数目(https://leetcode.cn/problems/count-pairs-of-connectable-servers-in-a-weighted-tree-network/description/)
 * 3108. 带权图里旅途的最小代价(https://leetcode.cn/problems/minimum-cost-walk-in-weighted-graph/description/)
 */
public interface problem {
    // 997. 找到小镇的法官
    public int findJudge(int n, int[][] trust);

    // 1791. 找出星型图的中心节点
    public int findCenter(int[][] edges);

    // 1971. 寻找图中是否存在路径()
    public boolean validPath(int n, int[][] edges, int source, int destination);

    // LCP 07. 传递信息
    public int numWays(int n, int[][] edges, int k);

    // 133. 克隆图
    public Node cloneGraph(Node node);

    // 207. 课程表
    public boolean canFinish(int numCourses, int[][] prerequisites);

    // 210. 课程表 II
    public int[] findOrder(int numCourses, int[][] prerequisites);

    // 547. 省份数量
    public int findCircleNum(int[][] isConnected);

    //  785. 判断二分图
    public boolean isBipartite(int[][] graph);

    // 797. 所有可能的路径
    public List<List<Integer>> allPathsSourceTarget(int[][] graph);

    // 2242. 节点序列的最大得分
    public int maximumScore(int[] scores, int[][] edges);

    // 3067. 在带权树网络中统计可连接服务器对数目
    public int[] countPairsOfConnectableServers(int[][] edges, int signalSpeed);

    // 3108. 带权图里旅途的最小代价
    public int[] minimumCost(int n, int[][] edges, int[][] query);
}
