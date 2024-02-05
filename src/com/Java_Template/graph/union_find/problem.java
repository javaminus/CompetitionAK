package com.Java_Template.graph.union_find;

import java.util.List;

/**
 * 684. 冗余连接(https://leetcode.cn/problems/redundant-connection/description/)
 * 990. 等式方程的可满足性(https://leetcode.cn/problems/satisfiability-of-equality-equations/description/)
 * 1722. 执行交换操作后的最小汉明距离(https://leetcode.cn/problems/minimize-hamming-distance-after-swap-operations/description/)
 * 1202. 交换字符串中的元素(https://leetcode.cn/problems/smallest-string-with-swaps/description/)
 * 839. 相似字符串组(https://leetcode.cn/problems/similar-string-groups/description/)
 * 765. 情侣牵手(https://leetcode.cn/problems/couples-holding-hands/description/)
 * 721. 账户合并(https://leetcode.cn/problems/accounts-merge/description/)
 */
public interface problem {

    // 684. 冗余连接
    public int[] findRedundantConnection(int[][] edges);

    // 1722. 执行交换操作后的最小汉明距离
    public int minimumHammingDistance(int[] source, int[] target, int[][] allowedSwaps);

    // 1202. 交换字符串中的元素
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs);

    // 839. 相似字符串组
    public int numSimilarGroups(String[] strs);

    // 765. 情侣牵手
    public int minSwapsCouples(int[] row);
}
