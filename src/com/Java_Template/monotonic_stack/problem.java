package com.Java_Template.monotonic_stack;

import java.util.List;

/**
 * Note: 单调栈一般建议存下标，下标可以映射值。但是值不好映射下标
 * Problem: 2866. 美丽塔 II(https://leetcode.cn/problems/beautiful-towers-II/description/) 2072 单调栈+动态规划
 * 739. 每日温度(https://leetcode.cn/problems/daily-temperatures/description/)
 * 496. 下一个更大元素 I(https://leetcode.cn/problems/next-greater-element-i/description/)
 * 901. 股票价格跨度(https://leetcode.cn/problems/online-stock-span/description/)
 * 1124. 表现良好的最长时间段(https://leetcode.cn/problems/longest-well-performing-interval/description/) 1908
 * 42. 接雨水(https://leetcode.cn/problems/trapping-rain-water/description/)
 */
public interface problem {
    // Problem: 2866. 美丽塔 II
    public long maximumSumOfHeights(List<Integer> maxHeights);

    // Problem: 496. 下一个更大元素 I
    public int[] nextGreaterElement(int[] nums1, int[] nums2);

    // 1124. 表现良好的最长时间段
    public int longestWPI(int[] hours);

    // 42. 接雨水
    public int trap(int[] height);
}
