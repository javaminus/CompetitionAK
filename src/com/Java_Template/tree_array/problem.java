package com.Java_Template.tree_array;

import java.util.List;

/**
 * 算法：树状数组、二维树状数组
 * 功能：进行数组区间加减，和区间值求和（单点可转换为区间）
 * 题目：
 *
 * ===================================力扣===================================
 * 2426. 满足不等式的数对数目(https://leetcode.cn/problems/number-of-pairs-satisfying-inequality/description/) 统计前缀和
 * LCR 170. 交易逆序对的总数(https://leetcode.cn/problems/shu-zu-zhong-de-ni-xu-dui-lcof/description/) 统计前缀和
 * 315. 计算右侧小于当前元素的个数(https://leetcode.cn/problems/count-of-smaller-numbers-after-self/description/) 统计前缀和
 * 307. 区域和检索 - 数组可修改（https://leetcode.cn/problems/range-sum-query-mutable）PointChangeRangeSum
 * 1409. 查询带键的排列（https://leetcode.cn/problems/queries-on-a-permutation-with-key/description/）经典树状数组模拟
 * 300. 最长递增子序列（https://leetcode.cn/problems/longest-increasing-subsequence/description/）
 * 1626. 无矛盾的最佳球队（https://leetcode.cn/problems/best-team-with-no-conflicts/）树状数组维护前缀最大值，也可使用动态规划求解
 * 6353. 网格图中最少访问的格子数（https://leetcode.cn/problems/minimum-number-of-visited-cells-in-duplicate-grid/）树状数组维护前缀区间最小值单点更新
 * 308. 二维区域和检索 - 可变（https://leetcode.cn/problems/range-sum-query-2d-mutable/）二维树状数组，单点增减与区间和查询
 * 2659. 将数组清空（https://leetcode.cn/problems/make-array-empty/submissions/）经典模拟删除，可以使用树状数组也可以使用SortedList也可以使用贪心
 * 1505. 最多 K 次交换相邻数位后得到的最小整数（https://leetcode.cn/problems/minimum-possible-integer-after-at-most-k-adjacent-swaps-on-digits/）经典树状数组模拟计数移动，也可以使用SortedList
 * 2193. 得到回文串的最少操作次数（https://leetcode.cn/problems/minimum-number-of-moves-to-make-palindrome/description/）使用树状数组贪心模拟交换构建回文串，相同题目（P5041求回文串）
 * 2407. 最长递增子序列 II（https://leetcode.cn/problems/longest-increasing-subsequence-ii/description/）树状数组加线性DP
 * 100112. 平衡子序列的最大和（https://leetcode.cn/problems/maximum-balanced-subsequence-sum/）离散化树状数组加线性DP
 * 2736. 最大和查询（https://leetcode.cn/problems/maximum-sum-queries/）PointAddPreMax
 *
 * ===================================洛谷===================================
 * P2068 统计和（https://www.luogu.com.cn/problem/P2068）单点更新与区间求和
 * P2345 [USACO04OPEN] MooFest G（https://www.luogu.com.cn/problem/P2345）使用两个树状数组计数与加和更新查询
 * P2357 守墓人（https://www.luogu.com.cn/problem/P2357）区间更新与区间求和
 * P2781 传教（https://www.luogu.com.cn/problem/P2781）区间更新与区间求和
 * P5200 [USACO19JAN]Sleepy Cow Sorting G（https://www.luogu.com.cn/problem/P5200）树状数组加贪心模拟
 * P3374 树状数组 1（https://www.luogu.com.cn/problem/P3374）区间值更新与求和
 * P3368 树状数组 2（https://www.luogu.com.cn/problem/P3368）区间值更新与求和
 * P5677 配对统计（https://www.luogu.com.cn/problem/P5677）区间值更新与求和
 * P5094 [USACO04OPEN] MooFest G 加强版（https://www.luogu.com.cn/problem/P5094）单点更新增加值与前缀区间和查询
 * P1816 忠诚（https://www.luogu.com.cn/problem/P1816）树状数组查询静态区间最小值
 * P1908 逆序对（https://www.luogu.com.cn/problem/P1908）树状数组求逆序对
 * P1725 琪露诺（https://www.luogu.com.cn/problem/P1725）倒序线性DP，单点更新值，查询区间最大值
 * P3586 [POI2015] LOG（https://www.luogu.com.cn/problem/P3586）离线查询、离散化树状数组，单点增减，前缀和查询
 * P1198 [JSOI2008] 最大数（https://www.luogu.com.cn/problem/P1198）树状数组，查询区间最大值
 * P4868 Preprefix sum（https://www.luogu.com.cn/problem/P4868）经典转换公式单点修改，使用两个树状数组维护前缀和的前缀和
 * P5463 小鱼比可爱（加强版）（https://www.luogu.com.cn/problem/P5463）经典使用树状数组维护前缀计数，枚举最大值计算所有区间数贡献
 * P6225 [eJOI2019] 异或橙子（https://www.luogu.com.cn/problem/P6225）经典使用树状数组维护前缀异或和
 * P1972 [SDOI2009] HH的项链（https://www.luogu.com.cn/problem/P1972）经典使用树状数组离线查询区间不同数的个数 PointChangeRangeSum OfflineQuery
 *
 * ================================AtCoder================================
 * D - Islands War（https://atcoder.jp/contests/abc103/tasks/abc103_d）经典贪心加树状数组
 * F - Absolute Minima （https://atcoder.jp/contests/abc127/tasks/abc127_f）经典离散化与两个树状数组进行加和与计数
 * Vertex Add Subtree Sum（https://judge.yosupo.jp/problem/vertex_add_subtree_sum）use tree array and dfs order
 *
 * ================================CodeForces================================
 * F. Range Update Point Query（https://codeforces.com/problemset/problem/1791/F）树状数组维护区间操作数与查询单点值
 * H2. Maximum Crossings (Hard Version)（https://codeforces.com/contest/1676/problem/H2）树状数组维护前缀区间和
 * C. Three displays（https://codeforces.com/problemset/problem/987/C）枚举中间数组，使用树状数组维护前后缀最小值
 * F. Moving Points（https://codeforces.com/contest/1311/problem/F）经典两个离散化树状数组，计数与加和
 * C. Game on Permutation（https://codeforces.com/contest/1860/problem/C）PointDescendRangeMin
 * C. Manhattan Subarrays（https://codeforces.com/contest/1550/problem/C）PointAscendPreMax
 *
 * 135. 二维树状数组3（https://loj.ac/p/135）区间修改，区间查询
 * 134. 二维树状数组2（https://loj.ac/p/134）区间修改，单点查询
 *
 * 参考：OI WiKi（https://oi-wiki.org/ds/fenwick/）
 */
public interface problem {
    // 2426. 满足不等式的数对数目  经典一边更新数组，一边查询数组中小于x的数目
    public long numberOfPairs(int[] nums1, int[] nums2, int diff);

    // LCR 170. 交易逆序对的总数
    public int reversePairs(int[] record);

    // 315. 计算右侧小于当前元素的个数
    public List<Integer> countSmaller(int[] nums);

    // 1409. 查询带键的排列
    public int[] processQueries(int[] queries, int m);

    // 300. 最长递增子序列
    public int lengthOfLIS(int[] nums);

    // 1626. 无矛盾的最佳球队
    public int bestTeamScore(int[] scores, int[] ages);



}
