package com.Java_Template.monotonic_queue;

/**
 * 单调队列就是滑动窗口
 *
 * 1438. 绝对差不超过限制的最长连续子数组(https://leetcode.cn/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/description/)
 * 84. 柱状图中最大的矩形(https://leetcode.cn/problems/largest-rectangle-in-histogram/description/)
 */
public interface problem {

    // 1438. 绝对差不超过限制的最长连续子数组
    public int longestSubarray(int[] nums, int limit);

    // 84. 柱状图中最大的矩形
    public int largestRectangleArea(int[] heights);
}
