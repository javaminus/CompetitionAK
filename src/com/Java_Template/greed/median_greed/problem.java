package com.Java_Template.greed.median_greed;

/**
 * 中位数贪心
 * LCP 24. 数字游戏(https://leetcode.cn/problems/5TxKeK/description/?envType=daily-question&envId=2024-02-01)
 * 295. 数据流的中位数(https://leetcode.cn/problems/find-median-from-data-stream/description/) 构造题
 * 462. 最小操作次数使数组元素相等 II(https://leetcode.cn/problems/minimum-moves-to-equal-array-elements-ii/description/)
 * 2033. 获取单值网格的最小操作数 1672(https://leetcode.cn/problems/minimum-operations-to-make-a-uni-value-grid/description/)
 * 2448. 使数组相等的最小开销 2005(https://leetcode.cn/problems/minimum-cost-to-make-array-equal/description/) 为什么可以二分
 * 2607. 使子数组元素和相等 2071(https://leetcode.cn/problems/make-k-subarray-sums-equal/description/) 环形数组+子数组 裴蜀定理
 * 2967. 使数组成为等数数组的最小代价 2116(https://leetcode.cn/problems/minimum-cost-to-make-array-equalindromic/description/)
 * 2968. 执行操作使频率分数最大 2444(https://leetcode.cn/problems/apply-operations-to-maximize-frequency-score/description/)
 * 1703. 得到连续 K 个 1 的最少相邻交换次数 2467
 *
 *
 * 贪心
 * Saving the City（https://codeforces.com/contest/1443/problem/B）
 */
public interface problem {
    // LCP 24. 数字游戏
    public int[] numsGame(int[] nums);

    // 2448. 使数组相等的最小开销 2005
    public long minCost(int[] nums, int[] cost);

    // 使子数组元素和相等 2071
    public long makeSubKSumEqual(int[] arr, int k);

    // 使数组成为等数数组的最小代价 2116
    public long minimumCost(int[] nums);

    // 执行操作使频率分数最大 2444
    public int maxFrequencyScore(int[] nums, long k);
}
