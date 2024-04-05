package com.Java_Template.graph.topological_sort;

import java.util.List;

/**
 * 310. 最小高度树(https://leetcode.cn/problems/minimum-height-trees/description/) 经典拓扑排序  快手一面手撕原题-2023.9
 * 2192. 有向无环图中一个节点的所有祖先(https://leetcode.cn/problems/all-ancestors-of-a-node-in-a-directed-acyclic-graph/description/)
 *
 */
public interface problem {
    // 310. 最小高度树
    public List<Integer> findMinHeightTrees(int n, int[][] edges);

    // 2192. 有向无环图中一个节点的所有祖先
    public List<List<Integer>> getAncestors(int n, int[][] edges);

}
